# Android Jetpack Compose Coding Style Guide

## Table of Contents
- [General Kotlin Conventions](#general-kotlin-conventions)
- [Compose-Specific Guidelines](#compose-specific-guidelines)
- [Architecture & State Management](#architecture--state-management)
- [Naming Conventions](#naming-conventions)
- [File Organization](#file-organization)
- [Performance Best Practices](#performance-best-practices)

## General Kotlin Conventions

### Code Formatting
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use trailing commas in multi-line collections and parameter lists
- Place opening braces on the same line

### Nullability
```kotlin
// Prefer non-nullable types
val name: String = "John"

// Use safe calls and Elvis operator
val length = name?.length ?: 0

// Also use methods apply, let, run
val length = name?.let {
    length
} ?: 0

// Avoid !! operator unless absolutely necessary
```

### Immutability
```kotlin
// Prefer val over var
val immutableList = listOf(1, 2, 3)
val mutableList = mutableListOf(1, 2, 3) // When mutation is needed

// Use data classes for immutable data
data class User(val id: String, val name: String)
```

## Compose-Specific Guidelines

### Composable Function Naming
```kotlin
// Use PascalCase for Composables that emit UI
@Composable
fun UserProfile(user: User, modifier: Modifier = Modifier) {
    // Implementation
}

// Use camelCase for Composables that remember state
@Composable
fun rememberUserState(userId: String): UserState {
    // Implementation
}
```

### Modifier Parameter
```kotlin
// Always include a Modifier parameter and default it to Modifier
// Place it as the first optional parameter (after required params)
@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier, // First optional parameter
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    // Implementation
}
```

### Composable Organization
```kotlin
@Composable
fun ScreenName(
    // 1. Required state parameters + viewModels
    viewModel: ScreenViewModel = koinViewModel(),
    text: String,

    // 2. Modifier (first optional parameter)
    modifier: Modifier = Modifier,

    // 4. Other optional parameters
    showHeader: Boolean = true,
    
    // 4. callback parameters
    onNavigateBack: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    // Implementation
}
```

### State Hoisting
```kotlin
// BAD: State managed inside reusable composable
@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
    TextField(
        value = query,
        onValueChange = { query = it }
    )
}

// GOOD: State hoisted to caller
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
    )
}
```

### Preview Annotations
```kotlin
@Preview
@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun UserProfilePreview() {
    AppTheme {
        UserProfile(
            user = User(id = "1", name = "John Doe")
        )
    }
}
```

```kotlin
// In this example ModelType is an enum
private class ModelProvider : PreviewParameterProvider<ModelType> {  
    override val values = sequenceOf(ModelType.CAMERA, ModelType.VIDEO)  
}

@Composable  
@Preview(name = "VisualCard Preview")  
@PreviewLightDark  
@PreviewScreenSizes  
private fun ComposableCardPreview(  
    // Create a parameter for the preview when given classes
    @PreviewParameter(ModelProvider::class) modelType: ModelType
) {  
    ComposableCard(modelType = modelType)  
}
```

## Architecture & State Management

### ViewModel Pattern
```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    fun loadUser(userId: String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            userRepository.getUser(userId)
                .onSuccess { user -> _uiState.value = UserUiState.Success(user) }
                .onFailure { error -> _uiState.value = UserUiState.Error(error.message) }
        }
    }
}

sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val user: User) : UserUiState
    data class Error(val message: String?) : UserUiState
}
```

### State Collection
```kotlin
@Composable
fun UserScreen(
    viewModel: UserViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // Handle UI state
}
```


## Naming Conventions

### Files
- Screen composables: `UserProfileScreen.kt`
- Reusable components: `CustomButton.kt`
- ViewModels: `UserProfileViewModel.kt`
- UI state: `UserProfileUiState.kt`

### Variables
```kotlin
// State variables
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
var searchQuery by remember { mutableStateOf("") }

// Boolean variables: use is/has/can prefix
val isLoading: Boolean
val hasError: Boolean
val canSubmit: Boolean

// Collections: use plural nouns
val users: List<User>
val itemIds: Set<String>
```

### Functions
```kotlin
// Event handlers: use on prefix
fun onButtonClick() { }
fun onValueChange(value: String) { }

// Actions: use verb prefix
fun loadUser() { }
fun saveSettings() { }
fun updateProfile() { }
```

## File Organization

### Package Structure
```
|-- di                     // Dependency injection
|-- data                   // Generic api services, common models & errors 
|	|-- datasources
|	|-- errors
|	|-- models
|	|-- mappers
|-- utils                  // Helper functions or extensions
|-- presentation           // Atomic design and adaptive 
|	|-- ui
|	|	|-- Color.kt
|	|	|-- Theme.kt
|	|	|-- Type.kt
|	|	|-- Dimensions.kt. // Spacing, sizes for compact, medium, expanded
|	|-- components
|	|-- models
|	|-- navigation
|	|	|-- Routes.kt
|   |-- reducers
|   |   |-- Reducer.kt   
|   |-- viewModels
|
|-- features
|	|-- featureA
|	|	|-- domain
|	|	|	|-- failures
|	|	|	|-- repositories
|	|	|	|-- entities
|   |   |   |-- commands
|	|	|	|-- useCases
|	|	|-- data
|	|	|	|-- datasources
|	|	|	|-- errors
|	|	|	|-- repositories
|	|	|	|-- models
|	|	|	|-- mappers
|	|	|-- presentation
|	|	|	|-- components
|	|	|	|-- screens
|	|	|	|-- reducers
|	|	|	|	|-- events  //user interactions actions or state changing event
|	|	|	|	|-- effects // one-time side-effect
|	|	|	|-- viewModels
|	|	|	|-- models
|	|	|	|-- mappers
|	     
```

### File Content Order
1. Package declaration
2. Imports (organized alphabetically)
3. Top-level constants
4. Main class/composable
5. Helper functions
6. Preview functions (always private)

## Performance Best Practices

### Stable Parameters
```kotlin
// Use @Stable or @Immutable for better recomposition performance
@Immutable
data class UserProfile(val id: String, val name: String, val avatar: String)

// Avoid passing lambda parameters that recreate on each recomposition
@Composable
fun MyScreen(viewModel: MyViewModel) {
    // BAD: Creates new lambda on every recomposition
    Button(onClick = { viewModel.onAction() }) { }
    
    // GOOD: Remember the lambda
    val onClick = rememberUpdatedState(viewModel::onAction)
    Button(onClick = onClick) { }
    
    // EVEN BETTER: Pass ViewModel function reference when possible
    Button(onClick = viewModel::onAction) { }
}
```

### Avoid Unnecessary Recomposition
```kotlin
// Use derivedStateOf for computed values
@Composable
fun ListScreen(items: List<Item>) {
    val filteredItems by remember(items) {
        derivedStateOf {
            items.filter { it.isActive }
        }
    }
}

// Use keys in lazy lists
LazyColumn {
    items(users, key = { it.id }) { user ->
        UserItem(user)
    }
}
```

### Resource Management
```kotlin
// Load resources properly
@Composable
fun UserGreeting(nameRes: Int) {
    Text(text = stringResource(id = nameRes))
}

// Use painterResource for images
Image(
    painter = painterResource(id = R.drawable.profile),
    contentDescription = "Profile picture"
)

// Use ImageVector for icons
Icon(
    imageVector = Icons.Default.Settings,
    contentDescription = "Settings icon"
)
```

## Documentation

### KDoc Comments
```kotlin
/**
 * Displays a user profile card with avatar, name, and bio.
 *
 * @param user The user data to display
 * @param onProfileClick Callback invoked when the profile card is clicked
 * @param modifier Modifier to be applied to the root layout
 * @param showBio Whether to show the user's bio. Defaults to true
 */
@Composable
fun UserProfileCard(
    user: User,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBio: Boolean = true
) {
    // Implementation
}
```

### Accessibility
```kotlin
// Always provide content descriptions
Image(
    painter = painterResource(id = R.drawable.icon),
    contentDescription = "Settings icon"
)

// Use semantics for custom interactions
Box(
    modifier = Modifier.semantics {
        contentDescription = "Custom button"
        role = Role.Button
    }
)
```

---

**Note:** This style guide should be used in conjunction with the official [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html) and [Jetpack Compose Guidelines](https://developer.android.com/jetpack/compose/performance).