# Feature Generation Guide

> **Important:** This guide must be used in conjunction with:
> - **@./coding-style.md** - General Kotlin and Compose conventions
> - **@./project-architecture-style.md** - Complete project structure and MVI pattern
> - **@./app/src/test/docs/generate-unit-tests.md** - Unit testing guidelines
> - **@./app/src/androidTest/docs/generate-integration-test.md** - Integration testing guidelines

## Table of Contents
- [Feature Creation Workflow](#feature-creation-workflow)
- [Remote DataSource Creation](#remote-datasource-creation)
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

// File: features/profile/data/datasources/ProfileRemoteDataSourceImpl.kt
class ProfileRemoteDataSourceImpl(
    private val apiService: ProfileApiService
) : IProfileRemoteDataSource {
    
    override suspend fun getProfile(userId: String): ProfileDto {
        return ApiServices.execute {
            apiService.getProfile(userId)
        }
    }
    
    override suspend fun updateProfile(userId: String, profileDto: ProfileDto): ProfileDto {
        return ApiServices.execute {
            apiService.updateProfile(userId, profileDto)
        }
    }
    
    override suspend fun deleteProfile(userId: String) {
        return ApiServices.execute {
            apiService.deleteProfile(userId)
        }
    }
}
```

### Example 4: Data Mapper

```kotlin
// File: features/profile/data/mappers/ProfileMapper.kt
object ProfileMapper {
    
    fun ProfileDto.toDomain(dto: ProfileDto): Profile {
        return Profile(
            userId = dto.userId,
            fullName = dto.fullName,
            email = dto.email,
            avatarUrl = dto.avatarUrl
        )
    }
    
    fun Profile.toDto(entity: Profile): ProfileDto {
        return ProfileDto(
            userId = entity.userId,
            fullName = entity.fullName,
            email = entity.email,
            avatarUrl = entity.avatarUrl
        )
    }
}
```

### Example 5: Repository

```kotlin
// File: features/profile/domain/repositories/IProfileRepository.kt
interface IProfileRepository {
    suspend fun getProfile(userId: String): Result<Profile>
    suspend fun updateProfile(userId: String, profile: Profile): Result<Profile>
    suspend fun deleteProfile(userId: String): Result<Unit>
}

// File: features/profile/data/repositories/ProfileRepositoryImpl.kt
class ProfileRepositoryImpl(
    private val remoteDataSource: IProfileRemoteDataSource
) : IProfileRepository {
    
    override suspend fun getProfile(userId: String): Result<Profile> {
        return runCatching {
            val dto = remoteDataSource.getProfile(userId)
            ProfileMapper.toDomain(dto)
        }
    }
    
    override suspend fun updateProfile(userId: String, profile: Profile): Result<Profile> {
        return runCatching {
            val dto = ProfileMapper.toDto(profile)
            val updatedDto = remoteDataSource.updateProfile(userId, dto)
            ProfileMapper.toDomain(updatedDto)
        }
    }
    
    override suspend fun deleteProfile(userId: String): Result<Unit> {
        return runCatching {
            remoteDataSource.deleteProfile(userId)
        }
    }
}
```

### Example 6: Use Case

```kotlin
// File: features/profile/domain/useCases/GetProfileUseCase.kt
class GetProfileUseCase(
    private val repository: IProfileRepository
) {
    suspend operator fun invoke(userId: String): Result<Profile> {
        return repository.getProfile(userId)
    }
}
```

### Example 7: Commands

```kotlin
// File: features/profile/domain/commands/CreateProfileCommand.kt
data class CreateProfileCommand(
    val fullName: String,
    val email: String,
    val avatarUrl: String?
)

// File: features/profile/domain/commands/ReadProfileCommand.kt
data class ReadProfileCommand(
    val userId: String
)

// File: features/profile/domain/commands/UpdateProfileCommand.kt
data class UpdateProfileCommand(
    val userId: String,
    val fullName: String?,
    val email: String?,
    val avatarUrl: String?
)

// File: features/profile/domain/commands/DeleteProfileCommand.kt
data class DeleteProfileCommand(
    val userId: String
)
```

### Example 8: Presentation Models

```kotlin
// File: features/profile/presentation/models/ProfileModel.kt
@Immutable
data class ProfileModel(
    val userId: String,
    val displayName: String,
    val email: String,
    val avatarUrl: String?,
    val isVerified: Boolean
)
```

### Example 9: Events and Effects

```kotlin
// File: features/profile/presentation/reducers/events/ProfileEvent.kt
@Immutable
sealed interface ProfileEvent {
    data class LoadProfile(val userId: String) : ProfileEvent
    data object RefreshProfile : ProfileEvent
    data class UpdateProfile(
        val fullName: String,
        val email: String
    ) : ProfileEvent
    data object DeleteProfile : ProfileEvent
}

// File: features/profile/presentation/reducers/effects/ProfileEffect.kt
@Immutable
sealed interface ProfileEffect {
    data class ShowMessage(val message: String) : ProfileEffect
    data class ShowError(val error: String) : ProfileEffect
    data object NavigateBack : ProfileEffect
    data object NavigateToSettings : ProfileEffect
}
```

### Example 10: State

```kotlin
// File: features/profile/presentation/reducers/ProfileState.kt
@Immutable
data class ProfileState(
    val isLoading: Boolean = false,
    val profile: ProfileModel? = null,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
```

### Example 11: Screen

```kotlin
// File: features/profile/presentation/screens/ProfileScreen.kt
@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(userId) {
        viewModel.onEvent(ProfileEvent.LoadProfile(userId))
    }
    
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ProfileEffect.NavigateBack -> onNavigateBack()
                is ProfileEffect.ShowMessage -> {
                    // Show snackbar
                }
                is ProfileEffect.ShowError -> {
                    // Show error
                }
                is ProfileEffect.NavigateToSettings -> {
                    // Navigate
                }
            }
        }
    }
    
    ProfileScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.profile != null -> {
                ProfileCard(
                    profile = state.profile,
                    onRefresh = { onEvent(ProfileEvent.RefreshProfile) }
                )
            }
            state.error != null -> {
                Text(text = "Error: ${state.error}")
            }
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreenContent(
            state = ProfileState(
                profile = ProfileModel(
                    userId = "1",
                    displayName = "John Doe",
                    email = "john@example.com",
                    avatarUrl = null,
                    isVerified = true
                )
            ),
            onEvent = {}
        )
    }
}
```

---

## Validation Checklist

Before considering a feature complete, validate against all rules:

### Behavioral Rules (BR)
- [ ] **BR-01:** All state classes annotated with `@Immutable`
- [ ] **BR-02:** Events and effects are `@Immutable sealed interface`
- [ ] **BR-03:** Presentation models use `@Immutable` or `@Stable`
- [ ] **BR-04:** Use cases only expose `invoke()` method
- [ ] **BR-05:** Routes inherit from `sealed interface Route` with `@Serializable`

### Operational Rules (OP)
- [ ] **OP-01:** Screen files/functions end with `Screen`
- [ ] **OP-02:** All interfaces start with `I`
- [ ] **OP-03:** DTOs have `Dto` suffix, `@Serializable`, and `@SerialName` on all parameters
- [ ] **OP-04:** Commands follow CRUD naming conventions
- [ ] **OP-05:** Function parameters ordered: required → defaults → functions
- [ ] **OP-06:** Adaptive composables follow official guidelines

### Safety Rules (SF)
- [ ] **SF-01:** Error/failure classes extend `Throwable`
- [ ] **SF-02:** State classes use immutable collections only

### Additional Checks
- [ ] All DTOs created before datasources
- [ ] API documentation referenced for endpoints
- [ ] `ApiServices.execute` used for all API calls
- [ ] Mappers created for data ↔ domain and domain ↔ presentation
- [ ] ViewModels extend BasicViewModel (refer to project standards)
- [ ] Screens collect state with `collectAsStateWithLifecycle()`
- [ ] Preview composables are private and include multiple preview annotations

---

## Quick Command Reference

### Create New Feature
Refer to **@path/to/project-architecture-style.md** for the complete folder structure.

```
1. Create folder: features/{featureName}/
2. Create data layer subdirectories
3. Create domain layer subdirectories
4. Create presentation layer subdirectories
```

### Create Remote DataSource
```
1. Verify DTO exists (stop if not)
2. Create interface I{Name}RemoteDataSource
3. Create implementation {Name}RemoteDataSourceImpl
4. Create test file
5. Use ApiServices.execute wrapper
6. Reference API documentation
```

### Create Complete Layer
```
Data:
1. DTOs (models/)
2. DataSources (datasources/)
3. Repositories (repositories/)
4. Mappers (mappers/)
5. Errors (errors/)

Domain:
1. Entities (entities/)
2. Repository Interfaces (repositories/)
3. Commands (commands/)
4. Use Cases (useCases/)
5. Failures (failures/)

Presentation:
1. Models (models/)
2. Events (reducers/events/)
3. Effects (reducers/effects/)
4. State (reducers/)
5. ViewModels (viewModels/)
6. Screens (screens/)
7. Components (components/)
8. Mappers (mappers/)
```

---

**Remember:** Always cross-reference with:
- **@./coding-style.md** for detailed Kotlin and Compose conventions
- **@path/to/rules.md** for all BR, OP, and SF rules
- **@./project-architecture-style.md** for complete project structure
- **@./app/src/test/docs/generate-unit-tests.md** for unit testing guidelines
- **@./app/src/androidTest/docs/generate-integration-test.md** for integration testing guidelines