# Feature Generation Guide

> **Important:** This guide must be used in conjunction with:
> - **@./coding-style.md** - General Kotlin and Compose conventions
> - **@./project-architecture-style.md** - Complete project structure and MVI pattern
> - **@./app/src/test/docs/generate-unit-tests.md** - Unit testing guidelines
> - **@./app/src/androidTest/docs/generate-integration-test.md** - Integration testing guidelines

## Table of Contents
- [Feature Creation Workflow](#feature-creation-workflow)
- [Remote DataSource Creation](#remote-datasource-creation)
- [Repository Creation](#repository-creation)
- [Use Case Creation](#use-case-creation)
- [Reducer Creation](#reducer-creation)
- [Code Generation Examples](#code-generation-examples)
- [Validation Checklist](#validation-checklist)

---

## Feature Creation Workflow

When creating a new feature, follow these steps in order:

### Step 1: Create Feature Folder Structure

Create the complete folder hierarchy for the feature following the structure defined in **@path/to/project-architecture-style.md**.

Refer to the project architecture document for the exact folder structure under `features/{featureName}/`.

### Step 2: Create Data Layer

1. **DTOs** (`data/models/`)
    - Follow **OP-03**: Suffix with `Dto`, use `@Serializable` and `@SerialName`

2. **DataSources** (`data/datasources/`)
    - Follow **OP-02**: Interface names start with `I`
    - Create interface first, then implementation

3. **Repositories** (`data/repositories/`)
    - Follow **OP-02**: Interface names start with `I`
    - Implement domain repository interface

4. **Mappers** (`data/mappers/`)
    - Map DTOs to domain entities

5. **Errors** (`data/errors/`)
    - Follow **SF-01**: Extend `Throwable`

### Step 3: Create Domain Layer

1. **Entities** (`domain/entities/`)
    - Pure Kotlin data classes
    - No Android dependencies

2. **Repository Interfaces** (`domain/repositories/`)
    - Follow **OP-02**: Start with `I`
    - Define contracts for data access

3. **Commands** (`domain/commands/`)
    - Follow **OP-04**: Use CRUD naming (Create, Read, Update, Delete)

4. **Use Cases** (`domain/useCases/`)
    - Follow **BR-04**: Only expose `invoke()` method

5. **Failures** (`domain/failures/`)
    - Follow **SF-01**: Extend `Throwable`

### Step 4: Create Presentation Layer

1. **Models** (`presentation/models/`)
    - Follow **BR-03**: Annotate with `@Immutable` or `@Stable`
    - Follow **SF-02**: Use immutable collections

2. **Events** (`presentation/reducers/events/`)
    - Follow **BR-02**: `@Immutable sealed interface`

3. **Effects** (`presentation/reducers/effects/`)
    - Follow **BR-02**: `@Immutable sealed interface`

4. **State** (`presentation/reducers/`)
    - Follow **BR-01**: Annotate with `@Immutable`
    - Follow **SF-02**: Use immutable collections

5. **ViewModels** (`presentation/viewModels/`)
    - Manage state with `StateFlow`
    - Handle events and emit effects

6. **Screens** (`presentation/screens/`)
    - Follow **OP-01**: File and function end with `Screen`

7. **Components** (`presentation/components/`)
    - Reusable UI components

8. **Mappers** (`presentation/mappers/`)
    - Map domain entities to presentation models

---

## Remote DataSource Creation

### Workflow for Creating Remote DataSources

#### Step 1: Verify DTO Exists
Before creating the datasource, ensure the required DTO is already created in `data/models/`. If not, **stop and notify** that the DTO must be created first.

#### Step 2: Create Interface
Create the datasource interface following **OP-02** (start with `I`):

```kotlin
interface IRemoteDataSource {
    suspend fun getData(id: String): DataDto
}
```

#### Step 3: Create Implementation
Implement the interface using `ApiServices.execute`:

```kotlin
class RemoteDataSourceImpl(
    private val apiService: ApiService
) : IRemoteDataSource {
    
    override suspend fun getData(id: String): DataDto {
        return ApiServices.execute {
            apiService.getData(id)
        }
    }
}
```

#### Step 4: API Documentation Reference
When implementing API calls, always reference the official API documentation (e.g., https://pokeapi.co/docs/v2 for Pokemon API).

### ApiServices.execute Usage

The `ApiServices.execute` wrapper should handle:
- Network errors
- HTTP errors
- Serialization errors
- Timeout errors

Example implementation pattern:

```kotlin
suspend fun getResource(id: String): ResourceDto {
    return ApiServices.execute {
        apiService.getResource(id)
    }
}
```

#### Step 5: DI
Add the datasource to the DatasourceModule:

Example of implementation: 

```kotlin
    single {
        PokemonListRemoteDatasourceImpl(
            dispatcher = get(DispatcherQualifiers.Io),
            httpClient = get()
        )
    } bind IPokemonListRemoteDatasource::class
```

---

## Repository Creation

### Workflow for Creating Repositories

This workflow outlines the steps to create a repository, which is responsible for abstracting data sources and providing a clean API to the domain layer.

#### Step 1: Verify Domain Entity Exists
Before creating the repository, ensure the domain entity you need to expose already exists in the `domain/entities/` directory. If it does not, **stop and ask** for the entity to be created first. The repository should only work with and expose domain models.

#### Step 2: Create Repository Interface
Define the repository contract in the `domain/repositories/` directory. This interface should only expose domain entities and use the `Result` wrapper for all data-fetching operations.

**Example:**
```kotlin
// In domain/repositories/IProfileRepository.kt
interface IProfileRepository {
    suspend fun getProfile(id: String): Result<Profile>
}
```

#### Step 3: Create Failure Sealed Class
If a specific failure class for the feature does not exist, create one in the `domain/failures/` directory. This class should extend `Throwable` and represent all possible business-rule or data-access failures for the feature.

**Example:**
```kotlin
// In domain/failures/ProfileFailure.kt
sealed class ProfileFailure(
    override val message: String?,
    override val cause: Throwable?
) : Throwable(message, cause) {

    data class UserNotFound(val id: String) : ProfileFailure("User with ID $id not found.", null)
    data class NetworkError(override val cause: Throwable) : ProfileFailure("A network error occurred.", cause)
    data class UnknownError(override val cause: Throwable) : ProfileFailure("An unknown error occurred.", cause)
}
```

#### Step 4: Create DTO to Entity Mapper

In the `data/mappers/` package, create or update the mapper file to include an extension function
that converts the data layer'''s DTO to the domain entity.

**Rules for creating mappers:**

- **Check for Entity Existence**: Before creating a mapper, look up the entities of the current
  feature. Only create a `Dto` to `Entity` mapper if the entity exists; otherwise, it is not needed.
- **Mapping `NamedAPIResourceDto`**: When a DTO contains a field of type `NamedAPIResourceDto`, the
  mapper should map it to the `name` property of the `NamedAPIResourceDto` field in the entity.

**Example of a standard mapper:**
```kotlin
// In data/mappers/ProfileMapper.kt
fun ProfileDto.toEntity(): Profile {
    return Profile(
        userId = this.userId,
        fullName = this.fullName,
        email = this.email
    )
}
```

**Example of a `NamedAPIResourceDto` mapper:**

```kotlin
fun MoveDto.toEntity(): Move {
    return Move(
        move = this.move.name,
    )
}
```

#### Step 5: Create Repository Implementation
In `data/repositories/`, create the repository implementation. This class will depend on one or more data sources. It is responsible for calling the datasource, catching any `Error` types, and mapping them to the feature-specific `Failure`.

**Example:**
```kotlin
// In data/repositories/ProfileRepositoryImpl.kt
class ProfileRepositoryImpl(
    private val remoteDataSource: IProfileRemoteDataSource
) : IProfileRepository {

    override suspend fun getProfile(id: String): Result<Profile> {
        return try {
            val profileDto = remoteDataSource.getProfile(id)
            Result.success(profileDto.toEntity())
        } catch (e: Error) {
            Result.failure(e.toProfileFailure()) // Maps a generic Error to a specific ProfileFailure
        }
    }
}
```

#### Step 6: Add Documentation
Ensure all new public classes and methods have clear and concise KDoc documentation explaining their purpose, parameters, and return values.

#### Step 7: Register with Koin
Finally, register the repository implementation in the `repositoryModule.kt` file for dependency injection.

**Example:**
```kotlin
// In @/app/src/main/java/com/ailtontech/pokedewai/di/RepositoryModule.kt
val repositoryModule: Module = module {
    // ... other repositories
    singleOf(::ProfileRepositoryImpl) bind IProfileRepository::class
}
```

---

## Use Case Creation

### Workflow for Creating Use Cases

Use cases orchestrate the flow of data from repositories and contain core business logic. They are
invoked by ViewModels.

#### Step 1: Create Interface, Parameters, and Return Type

First, create the use case interface in the `domain/usecases/` directory. It should have a single
`invoke` operator method.

- If the use case requires input, create a `params` data class in `domain/commands/`.
- The `invoke` method should return a `query` sealed class, also in `domain/commands/`, which
  represents the possible outcomes (`Success`, `Failure`).

**Example:**
```kotlin
// 1. Create Params (if needed)
// file: domain/commands/PokemonDetailParams.kt
data class PokemonDetailParams(val name: String)

// 2. Create Return Type
// file: domain/commands/PokemonDetailQuery.kt
sealed class PokemonDetailQuery {
    data class Success(val data: PokemonDetail) : PokemonDetailQuery()
    data class Failure(val error: PokemonDetailFailure) : PokemonDetailQuery()
}

// 3. Create Interface
// file: domain/usecases/IGetPokemonDetailUseCase.kt
interface IGetPokemonDetailUseCase {
    suspend operator fun invoke(params: PokemonDetailParams): PokemonDetailQuery
}
```

#### Step 2: Create Implementation

Next, create the implementation class for the use case in `domain/usecases/impl/`. This class will
implement the interface, depend on a repository, and contain the business logic to fetch and map
data.

**Example:**
```kotlin
// In domain/usecases/impl/GetPokemonDetailUseCaseImpl.kt
class GetPokemonDetailUseCaseImpl(
    private val repository: IPokemonDetailRepository
) : IGetPokemonDetailUseCase {
    override suspend fun invoke(params: PokemonDetailParams): PokemonDetailQuery {
        return repository.getPokemonDetail(params.name).fold(
            onSuccess = { PokemonDetailQuery.Success(it) },
            onFailure = { PokemonDetailQuery.Failure(it as PokemonDetailFailure) }
        )
    }
}
```

#### Step 3: Register with Koin

Finally, register the use case implementation in the `@UseCasesModule.kt` file for dependency
injection.

**Example:**
```kotlin
// In @/app/src/main/java/com/ailtontech/pokedewai/di/UseCasesModule.kt
val useCasesModule: Module = module {
    // ... other use cases
    singleOf(::GetPokemonDetailUseCaseImpl) bind IGetPokemonDetailUseCase::class
}
```

---

## Reducer Creation

### Workflow for Creating Reducers

Reducers are a core component of the MVI architecture. They are responsible for processing events,
executing business logic (via use cases), and producing new states or side effects.

#### Step 1: Create Events and Effects

If they do not already exist for the feature, create the `Event` and `Effect` sealed interfaces in
the `presentation/reducers/events/` and `presentation/reducers/effects/` directories. These must
follow rule **BR-02** (`@Immutable sealed interface`).

**Example:**

```kotlin
// In presentation/reducers/events/PokemonsEvent.kt
@Immutable
sealed interface PokemonsEvent {
    data object PokemonsListLoading : PokemonsEvent
    data class GetPokemonsList(val offset: Int, val limit: Int) : PokemonsEvent
    data class GetPokemonDetail(val id: Int) : PokemonsEvent
}

// In presentation/reducers/effects/PokemonsEffect.kt
@Immutable
sealed interface PokemonsEffect {
    data class NavigateToPokemonDetail(val id: Int) : PokemonsEffect
}
```

#### Step 2: Create Reducer Implementation

In the `presentation/reducers/` directory, create the reducer class. It must implement the
`IReducer<State, Event, Effect>` interface.

**Example:**

```kotlin
// In presentation/reducers/PokemonsReducer.kt
class PokemonsReducer(
    private val getPokemonsUseCase: IGetPokemonsUseCase,
) : IReducer<PokemonsState, PokemonsEvent, PokemonsEffect> {
    override suspend fun invoke(
        state: PokemonsState,
        event: PokemonsEvent
    ): Next<PokemonsState, PokemonsEffect> {
        return when (event) {
            is PokemonsEvent.PokemonsListLoading -> {
                Next(state = state.copy(isLoading = true, failure = null))
            }
            is PokemonsEvent.GetPokemonsList -> {
                // ... call use case and return Next(state=...)
            }
            is PokemonsEvent.GetPokemonDetail -> {
                Next(effect = PokemonsEffect.NavigateToPokemonDetail(id = event.id))
            }
        }
    }
}
```

#### Step 3: Add Documentation

Add clear and concise KDoc to all new classes and methods to explain their purpose and
functionality.

#### Step 4: Register with Koin

Finally, add the reducer to the `reducerModule.kt` in the `di` package.

**Example:**

```kotlin
// In @/app/src/main/java/com/ailtontech/pokedewai/di/ReducerModule.kt
val reducerModule: Module = module {
    // ... other reducers
    singleOf(::PokemonsReducer) bind IReducer::class
}
```

---

## Feature Structure Template

Refer to **@./project-architecture-style.md** for the complete feature folder structure and organization.

---

## Code Generation Examples

### Example 1: DTO Creation

```kotlin
// File: features/profile/data/models/ProfileDto.kt
@Serializable
data class ProfileDto(
    @SerialName("user_id") val userId: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("email") val email: String,
    @SerialName("avatar_url") val avatarUrl: String?
)
```

### Example 2: Domain Entity

```kotlin
// File: features/profile/domain/entities/Profile.kt
data class Profile(
    val userId: String,
    val fullName: String,
    val email: String,
    val avatarUrl: String?
)
```

### Example 3: Remote DataSource

```kotlin
// File: features/profile/data/datasources/IProfileRemoteDataSource.kt
interface IProfileRemoteDataSource {
    suspend fun getProfile(userId: String): ProfileDto
    suspend fun updateProfile(userId: String, profileDto: ProfileDto): ProfileDto
    suspend fun deleteProfile(userId: String): Unit
}

// File: features/profile/data/datasources/ProfileRemoteDataSourceImpl
```