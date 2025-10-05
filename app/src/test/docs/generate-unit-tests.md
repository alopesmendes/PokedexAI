# Unit Test Generation Guide

> **Important:** This guide must be used in conjunction with:
> - **@./docs/coding-style.md** - General Kotlin and Compose conventions
> - **@./docs/project-architecture-style.md** - Complete project structure and MVI pattern
> - **@./docs/generate-features.md** - Feature generation guidelines

## Table of Contents
- [Test Naming Conventions](#test-naming-conventions)
- [Base Test Classes](#base-test-classes)
- [Mock Data Builders](#mock-data-builders)
- [Test Examples](#test-examples)
- [Validation Checklist](#validation-checklist)

---

## Test Naming Conventions

### Unit Test Template
```kotlin
`given [precondition] when [action] then [expected result]`
```

### Examples
```kotlin
`given successful response when getPokemonList then return PokemonListDto`
`given HTTP 404 error when getPokemonList then throw HttpError`
`given repository success when invoke then return Success with Profile`
`given LoadProfile event when use case succeeds then emit Success state`
```

### Rules
- Use **backticks** for test names
- Use **lowercase** throughout
- Structure: **given** (setup) → **when** (action) → **then** (outcome)
- Be **specific** and **descriptive**

---

## Base Test Classes

### Location
All base test classes are located in `test_utils/` package.

### Available Base Classes

#### BaseRemoteDatasourceTest
**Path:** `test_utils/BaseRemoteDatasourceTest.kt`

**Purpose:** Test remote data sources with Ktor MockEngine

**Provides:**
- `mockEngine: MockEngine`
- `httpClient: HttpClient`
- `testDispatcher: TestDispatcher`
- `prepareSuccessResponse(jsonBody: String)`
- `prepareHttpErrorResponse(statusCode: HttpStatusCode, errorBody: String)`
- `prepareNetworkError(exception: Throwable)`

#### BaseRepositoryTest
**Path:** `test_utils/BaseRepositoryTest.kt`

**Purpose:** Test repositories with mocked data sources

**Provides:**
- `testDispatcher: TestDispatcher`

#### BaseUseCaseTest

**Path:** `test_utils/BaseUseCaseTest.kt`

**Purpose:** Test use cases

**Provides:**

- `testDispatcher: TestDispatcher`

#### BaseReducerTest

**Path:** `test_utils/BaseReducerTest.kt`

**Purpose:** Test reducers

**Provides:**

- `testDispatcher: TestDispatcher`

#### BaseViewModelTest
**Path:** `test_utils/BaseViewModelTest.kt`

**Purpose:** Test ViewModels

**Provides:**
- `testDispatcher: TestDispatcher`
- `InstantTaskExecutorRule`
- Dispatcher setup/teardown

---

## Mock Data Builders

To ensure consistency and reduce boilerplate in tests, each feature should have a centralized location for creating mock DTOs and domain entities.

### Structure
- Inside your feature's test package (e.g., `features/home/`), create a `mocks` package.
- Inside `mocks`, create a `MockData.kt` file.

### Mock Function Rules
- All mock-builder functions must be `internal` to restrict their usage to the specific feature's tests.
- Functions should be named with a `mock` prefix (e.g., `mockPokemonFormDto`).
- Functions should provide default values for all parameters, allowing tests to override only what's necessary.

### Example: `MockData.kt`
This file contains builder functions for creating test instances of DTOs and entities.

```kotlin
// In /app/src/test/java/com/ailtontech/pokedewai/features/home/mocks/MockData.kt
/**
 * Creates a mock [PokemonFormDto] for testing.
 * @param id The mock ID.
 * @param name The mock name.
 * @return A mock [PokemonFormDto] instance.
 */
internal fun mockPokemonFormDto(
    id: Int = 1,
    name: String = "bulbasaur"
    // ... other parameters with default values
): PokemonFormDto = PokemonFormDto(
    id = id,
    name = name,
    // ...
)

/**
 * Creates a mock [PokemonForm] entity for testing.
 * @return A mock [PokemonForm] instance.
 */
internal fun mockPokemonForm(
    // ... parameters with default values
): PokemonForm = PokemonForm(
    // ...
)
```

### Usage in Tests
Use these helper functions to simplify the "Given" (Arrange) block of your tests.

```kotlin
// In PokemonsRepositoryImplTest.kt
@Test
fun `given successful responses, when getPaginatedPokemons is called, then return success`() = runTest {
    // Given
    val pokemonListDto = mockPokemonListDto(count = 100)
    val pokemonFormDto = mockPokemonFormDto()
    val expectedPokemonList = mockPokemonList(count = 100)

    coEvery { pokemonListRemoteDatasource.getPokemonList(any(), any()) } returns pokemonListDto
    coEvery { pokemonFormRemoteDatasource.getPokemonForm(any()) } returns pokemonFormDto

    // When
    val result = repository.getPaginatedPokemons(count = 100, offset = 0, limit = 20)

    // Then
    assertTrue(result.isSuccess)
    assertEquals(expectedPokemonList, result.getOrNull())
}
```
This approach makes tests cleaner, more readable, and easier to maintain.

---

## Test Examples

### Remote DataSource Test

```kotlin
@ExperimentalCoroutinesApi
class PokemonListRemoteDatasourceImplTest : BaseRemoteDatasourceTest() {

    private lateinit var datasource: IPokemonListRemoteDatasource

    @Before
    override fun setUp() {
        super.setUp()
        datasource = PokemonListRemoteDatasourceImpl(
            dispatcher = testDispatcher,
            httpClient = httpClient
        )
    }

    @Test
    fun `given a valid response, when getPokemonList is called, then it should return a PokemonListDto`() = runTest {
        // Given
        val json = JsonReader.readJsonFile("pokemon_list_success.json")
        prepareSuccessResponse(json)

        // When
        val result = datasource.getPokemonList(offset = 0, limit = 20)

        // Then
        assertEquals(1302, result.count)
        assertEquals(20, result.results.size)
        assertEquals("bulbasaur", result.results.first().name)
    }

    @Test
    fun `given a 404 error, when getPokemonList is called, then it should throw HttpError`() = runTest {
        // Given
        prepareHttpErrorResponse(HttpStatusCode.NotFound)

        // When & Then
        val exception = assertFailsWith<Error.HttpError> {
            datasource.getPokemonList(offset = 9999, limit = 20)
        }
        assertEquals(404, exception.statusCode)
    }
}
```

### Repository Test

```kotlin
@ExperimentalCoroutinesApi
class ProfileRepositoryImplTest : BaseRepositoryTest() {

    private lateinit var remoteDataSource: IProfileRemoteDataSource
    private lateinit var repository: ProfileRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()
        remoteDataSource = mockk()
        repository = ProfileRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `given successful datasource response when getProfile then return Success with Profile`() = runTest(testDispatcher) {
        coEvery { remoteDataSource.getProfile(any()) } returns validDto

        val result = repository.getProfile("123")

        assertTrue(result.isSuccess)
        assertEquals("123", result.getOrNull()?.userId)
        coVerify(exactly = 1) { remoteDataSource.getProfile("123") }
    }

    @Test
    fun `given datasource error when getProfile then return Failure`() = runTest(testDispatcher) {
        val exception = Exception("Network error")
        coEvery { remoteDataSource.getProfile(any()) } throws exception

        val result = repository.getProfile("123")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { remoteDataSource.getProfile("123") }
    }
}
```

### Use Case Test

```kotlin
@ExperimentalCoroutinesApi
class GetPokemonsUseCaseImplTest : BaseUseCaseTest() {

    private lateinit var repository: IPokemonsRepository
    private lateinit var useCase: GetPokemonsUseCaseImpl

    @Before
    override fun setUp() {
        super.setUp()
        repository = mockk()
        useCase = GetPokemonsUseCaseImpl(repository)
    }

    @Test
    fun `given repository success, when invoke is called, then return success`() = runTest {
        // Given
        val pokemonList = mockPokemonList()
        val command = PokemonPaginationParams(count = 100, offset = 0, limit = 20)
        coEvery { repository.getPaginatedPokemons(any(), any(), any()) } returns Result.success(pokemonList)

        // When
        val result = useCase(command)

        // Then
        when(result) {
            is PokemonListQuery.Success -> assertEquals(pokemonList, result.pokemonList)
            is PokemonListQuery.Error -> fail("Should be a success, but got a failure: ${result.failure}")
        }
    }

    @Test
    fun `given repository failure, when invoke is called, then return failure`() = runTest {
        // Given
        val failure = PokemonsFailure.NetworkFailure("No connection")
        val command = PokemonPaginationParams(count = 100, offset = 0, limit = 20)
        coEvery { repository.getPaginatedPokemons(any(), any(), any()) } returns Result.failure(failure)

        // When
        val result = useCase(command)

        // Then
        when(result) {
            is PokemonListQuery.Success -> fail("Should be a failure, but got a success: ${result.pokemonList}")
            is PokemonListQuery.Error -> assertEquals(failure, result.failure)
        }
    }
}
```

### Reducer Test

Reducer tests verify that a given state and event produce the correct next state and/or effect. Each
event path must be tested, including success and failure scenarios from use case interactions.

```kotlin
@ExperimentalCoroutinesApi
class PokemonsReducerTest : BaseReducerTest() {

    private lateinit var getPokemonsUseCase: IGetPokemonsUseCase
    private lateinit var reducer: PokemonsReducer

    @Before
    override fun setUp() {
        super.setUp()
        getPokemonsUseCase = mockk()
        reducer = PokemonsReducer(getPokemonsUseCase)
    }

    @Test
    fun `given PokemonsListLoading event, when reducer is invoked, then state should be loading`() = runTest {
        // Given
        val initialState = PokemonsState()
        val event = PokemonsEvent.PokemonsListLoading

        // When
        val next = reducer(initialState, event)

        // Then
        assertTrue(next.state!!.isLoading)
    }

    @Test
    fun `given GetPokemonsList event and use case success, when reducer is invoked, then state should be updated with new data`() = runTest {
        // Given
        val initialState = PokemonsState()
        val event = PokemonsEvent.GetPokemonsList(offset = 0, limit = 20)
        val pokemonList = mockPokemonList()
        val successResult = PokemonListQuery.Success(pokemonList)
        coEvery { getPokemonsUseCase(any()) } returns successResult

        // When
        val next = reducer(initialState, event)

        // Then
        assertFalse(next.state!!.isLoading)
        assertEquals(pokemonList.pokemonForms, next.state!!.pokemons)
        assertEquals(pokemonList.count, next.state!!.count)
    }

    @Test
    fun `given GetPokemonsList event and use case failure, when reducer is invoked, then state should be updated with failure`() = runTest {
        // Given
        val initialState = PokemonsState()
        val event = PokemonsEvent.GetPokemonsList(offset = 0, limit = 20)
        val failure = PokemonsFailure.NetworkFailure("No connection")
        val errorResult = PokemonListQuery.Error(failure)
        coEvery { getPokemonsUseCase(any()) } returns errorResult

        // When
        val next = reducer(initialState, event)

        // Then
        assertFalse(next.state!!.isLoading)
        assertEquals(failure, next.state!!.failure)
    }

    @Test
    fun `given GetPokemonDetail event, when reducer is invoked, then it should emit NavigateToPokemonDetail effect`() = runTest {
        // Given
        val initialState = PokemonsState()
        val event = PokemonsEvent.GetPokemonDetail(id = 1)

        // When
        val next = reducer(initialState, event)

        // Then
        assertNull(next.state)
        assertTrue(next.effect is PokemonsEffect.NavigateToPokemonDetail)
        assertEquals(1, (next.effect as PokemonsEffect.NavigateToPokemonDetail).id)
    }
}
```

---

## Validation Checklist

### Unit Test Requirements
- [ ] Test name follows `given when then` convention in lowercase
- [ ] Test extends appropriate Base test class
- [ ] Test uses `runTest(testDispatcher)` for coroutines
- [ ] Test follows Arrange-Act-Assert pattern
- [ ] Test uses MockK for mocking dependencies
- [ ] Test verifies method calls with `coVerify`
- [ ] DataSource tests cover: success, HTTP error, network error, serialization error
- [ ] Repository tests use mocked data sources
- [ ] Use case tests verify repository calls
- [ ] ViewModel tests use Turbine for state collection

---

**Remember:** Always cross-reference with:
- **@./docs/coding-style.md** for detailed Kotlin and Compose conventions
- **@./docs/project-architecture-style.md** for complete project structure