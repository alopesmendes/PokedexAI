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

    @Test
    fun `given a network error, when getPokemonList is called, then it should throw NetworkError`() = runTest {
        // Given
        prepareNetworkError()

        // When & Then
        assertFailsWith<Error.NetworkError> {
            datasource.getPokemonList(offset = 0, limit = 20)
        }
    }

    @Test
    fun `given a malformed json, when getPokemonList is called, then it should throw SerializationError`() = runTest {
        // Given
        prepareSuccessResponse("""{"invalid":"json"}""")

        // When & Then
        val exception = assertFailsWith<Error.SerializationError> {
            datasource.getPokemonList(offset = 0, limit = 20)
        }
        assertNotNull(exception.cause)
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

    private val validDto = ProfileDto(
        userId = "123",
        fullName = "John Doe",
        email = "john@example.com",
        avatarUrl = null
    )

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
class GetProfileUseCaseTest {

    private lateinit var repository: IProfileRepository
    private lateinit var useCase: GetProfileUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetProfileUseCase(repository)
    }

    private val validProfile = Profile(
        userId = "123",
        fullName = "John Doe",
        email = "john@example.com",
        avatarUrl = null
    )

    @Test
    fun `given repository success when invoke then return Success with Profile`() = runTest {
        coEvery { repository.getProfile(any()) } returns Result.success(validProfile)

        val result = useCase("123")

        assertTrue(result.isSuccess)
        assertEquals(validProfile, result.getOrNull())
        coVerify(exactly = 1) { repository.getProfile("123") }
    }

    @Test
    fun `given repository failure when invoke then return Failure`() = runTest {
        val exception = Exception("Network error")
        coEvery { repository.getProfile(any()) } returns Result.failure(exception)

        val result = useCase("123")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getProfile("123") }
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