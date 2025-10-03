# Project Architecture Style Guide

## Architecture Pattern
The project follows the **MVI (Model-View-Intent)** architecture pattern.

---

## Project Structure

```
project-root/
├── di/                     // Dependency injection (Koin modules)
│
├── data/                   // Generic API services, common models & errors
│   ├── datasources/
│   ├── errors/
│   ├── models/
│   └── mappers/
│
├── utils/                  // Helper functions and extensions
│
├── presentation/           // Atomic design and adaptive
│   ├── ui/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   ├── Type.kt
│   │   └── Dimensions.kt  // Spacing, sizes for compact, medium, expanded
│   ├── components/
│   ├── models/
│   ├── navigation/
│   │   └── Routes.kt
│   ├── reducers/
│   │   └── Reducer.kt
│   └── viewModels/
│       └── BasicViewModel.kt
│
└── features/
    └── {featureName}/
        ├── data/
        │   ├── datasources/
        │   ├── errors/
        │   ├── repositories/
        │   ├── models/
        │   └── mappers/
        │
        ├── domain/
        │   ├── failures/
        │   ├── repositories/
        │   ├── entities/
        │   ├── commands/
        │   └── useCases/
        │
        └── presentation/
            ├── components/
            ├── screens/
            ├── reducers/
            │   ├── events/      // User interactions actions or state changing event
            │   └── effects/     // One-time side-effect
            ├── viewModels/
            ├── models/
            └── mappers/
```

---

## Root Level Folders

### `/di` - Dependency Injection
Koin module definitions for dependency injection.

**Example:**
```
di/
├── AppModule.kt
├── NetworkModule.kt
└── RepositoryModule.kt
```

### `/data` - Shared Data Layer
Common data infrastructure reused across multiple features.

**Example:**
```
data/
├── datasources/
│   └── ApiService.kt              // Shared API service
├── errors/
│   └── NetworkError.kt            // Common network errors
├── models/
│   └── ApiResponse.kt             // Generic API response wrapper
└── mappers/
    └── ErrorMapper.kt             // Common error mapping
```

**Contains:**
- Shared API services
- Common DTOs used by multiple features
- Generic error definitions
- Reusable data mappers

### `/utils` - Utilities
Helper functions, extension functions, and utility classes.

**Example:**
```
utils/
├── Extensions.kt                  // String.toDate(), Int.toCurrency()
├── Constants.kt                   // API_BASE_URL, TIMEOUT_SECONDS
└── DateUtils.kt                   // formatDate(), parseDate()
```

### `/presentation` - Shared Presentation Layer
Theme, shared UI components, navigation, and base classes.

**Example:**
```
presentation/
├── ui/
│   ├── Color.kt                   // Primary, Secondary, Error colors
│   ├── Theme.kt                   // AppTheme composable
│   ├── Type.kt                    // Typography definitions
│   └── Dimensions.kt              // Spacing, sizes for screen classes
├── components/
│   ├── AppButton.kt               // Reusable button component
│   ├── AppTextField.kt            // Reusable text field
│   └── LoadingIndicator.kt        // Loading spinner
├── models/
│   └── UiState.kt                 // Generic UI state wrapper
├── navigation/
│   └── Routes.kt                  // All app routes (sealed interface)
├── reducers/
│   └── Reducer.kt                 // Base reducer interface
└── viewModels/
    └── BasicViewModel.kt          // Base ViewModel with state/effects
```

**Key Files:**
- **BasicViewModel.kt**: Base class all ViewModels extend
- **Routes.kt**: Centralized navigation routes (BR-05)
- **Dimensions.kt**: Adaptive dimensions for responsive design

---

## Feature Structure

Each feature is self-contained with Data, Domain, and Presentation layers.

### Feature Example: `profile`

```
features/profile/
├── data/
│   ├── datasources/
│   │   ├── impl
│   │   │   └── ProfileRemoteDataSourceImpl.kt
│   │   ├── IProfileRemoteDataSource.kt
│   ├── errors/
│   │   └── ProfileDataError.kt
│   ├── repositories/
│   │   └── ProfileRepositoryImpl.kt
│   ├── models/
│   │   └── ProfileDto.kt
│   └── mappers/
│       └── ProfileDataMapper.kt
│
├── domain/
│   ├── failures/
│   │   └── ProfileFailure.kt
│   ├── repositories/
│   │   └── IProfileRepository.kt
│   ├── entities/
│   │   └── Profile.kt
│   ├── commands/
│   │   ├── CreateProfileCommand.kt
│   │   ├── ReadProfileCommand.kt
│   │   ├── UpdateProfileCommand.kt
│   │   └── DeleteProfileCommand.kt
│   └── useCases/
│       ├── GetProfileUseCase.kt
│       ├── UpdateProfileUseCase.kt
│       └── DeleteProfileUseCase.kt
│
└── presentation/
    ├── components/
    │   ├── ProfileCard.kt
    │   └── ProfileAvatar.kt
    ├── screens/
    │   └── ProfileScreen.kt
    ├── reducers/
    │   ├── ProfileState.kt
    │   ├── events/
    │   │   └── ProfileEvent.kt
    │   └── effects/
    │       └── ProfileEffect.kt
    ├── viewModels/
    │   └── ProfileViewModel.kt
    ├── models/
    │   └── ProfileModel.kt
    └── mappers/
        └── ProfilePresentationMapper.kt
```

---

## Layer Details

### Data Layer (`features/{feature}/data/`)

**Purpose:** Handle data operations, API calls, database access, and data transformation.

**Folders:**
- **datasources/**: API and database interfaces + implementations
    - `IProfileRemoteDataSource.kt` - Interface (starts with `I`)
    - `ProfileRemoteDataSourceImpl.kt` - Implementation
    - `IProfileLocalDataSource.kt` - Local data source (optional)

- **models/**: Data Transfer Objects (DTOs)
    - `ProfileDto.kt` - API response/request models
    - Use `@Serializable` and `@SerialName`

- **repositories/**: Domain repository implementations
    - `ProfileRepositoryImpl.kt` - Implements domain interface
    - Coordinates data sources
    - Handles error mapping

- **mappers/**: Convert DTOs ↔ Domain entities
    - `ProfileDataMapper.kt` - `toDomain()`, `toDto()` functions

- **errors/**: Data-specific errors
    - `ProfileDataError.kt` - Extends `Throwable`

### Domain Layer (`features/{feature}/domain/`)

**Purpose:** Business logic, entities, and use cases. Framework-independent.

**Folders:**
- **entities/**: Business models
    - `Profile.kt` - Pure Kotlin data class
    - No Android dependencies

- **repositories/**: Repository interfaces
    - `IProfileRepository.kt` - Contract for data operations
    - Starts with `I`

- **commands/**: Request objects for operations
    - `CreateProfileCommand.kt`
    - `ReadProfileCommand.kt`
    - `UpdateProfileCommand.kt`
    - `DeleteProfileCommand.kt`
    - Follow CRUD naming

- **useCases/**: Single-responsibility business operations
    - `GetProfileUseCase.kt` - Only expose `invoke()` method
    - `UpdateProfileUseCase.kt`
    - `DeleteProfileUseCase.kt`

- **failures/**: Domain-specific failures
    - `ProfileFailure.kt` - Extends `Throwable`

### Presentation Layer (`features/{feature}/presentation/`)

**Purpose:** UI logic, user interactions, and presentation state (MVI pattern).

**Folders:**
- **models/**: UI-specific models
    - `ProfileModel.kt` - Annotated with `@Immutable`
    - Uses immutable collections

- **reducers/**: State management (MVI)
    - `ProfileState.kt` - UI state (`@Immutable`)
    - **events/**: `ProfileEvent.kt` - User actions (`@Immutable sealed interface`)
    - **effects/**: `ProfileEffect.kt` - Side effects (`@Immutable sealed interface`)

- **viewModels/**: State holders
    - `ProfileViewModel.kt` - Extends `BasicViewModel`
    - Manages `StateFlow<State>`
    - Handles events, emits effects

- **screens/**: Screen composables
    - `ProfileScreen.kt` - Ends with `Screen`
    - Collects state with `collectAsStateWithLifecycle()`

- **components/**: Reusable UI components
    - `ProfileCard.kt` - Stateless components
    - `ProfileAvatar.kt` - Accept modifiers

- **mappers/**: Convert domain ↔ presentation
    - `ProfilePresentationMapper.kt` - `toPresentation()`, `toDomain()`

---

## MVI Pattern Structure

### State
Complete UI state at any point in time.
```kotlin
@Immutable
data class ProfileState(
    val isLoading: Boolean = false,
    val profile: ProfileModel? = null,
    val error: String? = null
)
```

### Events (Intent)
User actions that trigger state changes.
```kotlin
@Immutable
sealed interface ProfileEvent {
    data class LoadProfile(val userId: String) : ProfileEvent
    data object RefreshProfile : ProfileEvent
}
```

### Effects
One-time side effects (navigation, messages).
```kotlin
@Immutable
sealed interface ProfileEffect {
    data class ShowMessage(val message: String) : ProfileEffect
    data object NavigateBack : ProfileEffect
}
```

--