# Unit Test Generation Guide

> **Important:** This guide must be used in conjunction with:
> - **@./docs/coding-style.md** - General Kotlin and Compose conventions
> - **@./docs/project-architecture-style.md** - Complete project structure and MVI pattern
> - **@./docs/generate-features.md** - Feature generation guidelines

## Table of Contents
- [Test Naming Conventions](#test-naming-conventions)
- [Base Test Classes](#base-test-classes)
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

## Test Examples

### Remote DataSource Test

```kotlin
@ExperimentalCoroutinesApi
class ProfileRemoteDatasourceImplTest : BaseRemoteDatasourceTest() {

    private lateinit var datasource: ProfileRemoteDatasourceImpl

    @Before
    override fun setUp() {
        super.setUp()
        datasource = ProfileRemoteDatasourceImpl(
            dispatcher = testDispatcher,
            httpClient = httpClient
        )
    }

    private val validDto = ProfileDto(
        userId = "123",
        fullName = "John Doe",
        email = "john@example.com",
        avatarUrl = null
    )
    private val validJson = Json.encodeToString(ProfileDto.serializer(), validDto)

    @Test
    fun `given successful response when getProfile then return ProfileDto`() = runTest(testDispatcher) {
        prepareSuccessResponse(validJson)

        val result = datasource.getProfile("123")

        assertEquals(validDto.userId, result.userId)
        assertEquals(validDto.fullName, result.fullName)
    }

    @Test
    fun `given HTTP 404 error when getProfile then throw HttpError`() = runTest(testDispatcher) {
        val statusCode = HttpStatusCode.NotFound
        prepareHttpErrorResponse(statusCode, """{"detail":"Not found"}""")

        var caughtError: Throwable? = null
        try {
            datasource.getProfile("123")
        } catch (e: Throwable) {
            caughtError = e
        }

        assertNotNull("An error should have been thrown", caughtError)
        assertTrue("Error should be of type Error.HttpError", caughtError is Error.HttpError)
        assertEquals(statusCode.value, (caughtError as Error.HttpError).statusCode)
    }

    @Test
    fun `given network error when getProfile then throw NetworkError`() = runTest(testDispatcher) {
        prepareNetworkError(IOException("No internet"))

        var caughtError: Throwable? = null
        try {
            datasource.getProfile("123")
        } catch (e: Throwable) {
            caughtError = e
        }

        assertNotNull(caughtError)
        assertTrue(caughtError is Error.NetworkError)
    }

    @Test
    fun `given malformed JSON when getProfile then throw SerializationError`() = runTest(testDispatcher) {
        prepareSuccessResponse("""{"invalid": [MALFORMED JSON]}""")

        var caughtError: Throwable? = null
        try {
            datasource.getProfile("123")
        } catch (e: Throwable) {
            caughtError = e
        }

        assertNotNull(caughtError)
        assertTrue(caughtError is Error.SerializationError)
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