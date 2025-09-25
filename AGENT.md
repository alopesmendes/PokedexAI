# Context
You're an expert in Kotlin and Jetpack Compose development.
# Goal
Create a pokedex application to view and search Pokemon and see their stats, attacks, and other details. Also, search and see the locations, items, and games.

# Archi MVI
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
# Guidelines
- When creating a `@Composable` function, always create a private preview function like the following example:

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
    @PreviewParameter(ModelProvider::class) modelType: ModelType
) {  
    ComposableCard(modelType = modelType)  
}
```
- When creating a new reducer, extend the interface `Reducer.kt`
- When creating a state class, all the values of the class should have a default value
- When creating a new view model, extend the base view model by giving the initial state and the reducer. The view models should be like this :
```kotlin
class FeatureViewModel(  
    reducer: FeatureReducer,  
) : BaseViewModel<FeatureUiState, FeatureEvent, FeatureEffect>(  
       initialState = FeatureUiState(),  
       reducer = reducer,  
    ) {  
    init {  
       sendEvent(  
          FeatureEvent.SomeEvent,  
       )  
    }  
}  
```
- For datasources should catch the original error throw an more personal error like the following example:
```kotlin
class FeatureRemoteDatasourceImpl(  
    private val httpClient: HttpClient  
): FeatureRemoteDatasource {  
    override suspend fun fetchDatasource(): FeatureDto {  
        try {  
            httpClient.get("/path")  
        } catch (e: Exception) {  
            when (e) {  
                is NetworkException -> throw FeatureNetworkException(e)  
            }  
        }  
    }  
}
```
- For repositories, wrap the response or error in a Result. Map the error into a failure associated with it by the mapping functions in the directory `mappers`
- For use cases, ensure the parameters passed to it are a data class and it returns a `Result` of a data class. Needs to respect CRUD principles by using data classes in directory commands. The use cases should also ensure that the parameters are corrected; otherwise, return a `Result.failure` with the associated failure.
- When creating a new composant, try to think adaptive first, how it should be displayed in fold and flip phone. For example, the list of pokemon should be displayed on the left and the details on the right if the device is open.
- Use the method `collectWithLifecycle` when getting the state from a viewModel
- Adopt a TDD strategy, always start by writing tests and update them with code changes
- For dependency injection, use Koin and write modules like the following
```
fun viewModelModule(): Module =  
    module {  
       viewModelOf(::FeatureViewModel)  
    }
    
fun repositoryModule(): Module =  
    module {  
       singleOf(::FeautureRepositoryImpl) bind IFeatureRepository::class 
    }    
```
- When adding a new library should be added by module, except if there are multiple library for the same module
    - also grouped them in bundles if there are multiple library
```toml
[versions]
moduleVersion = "x.x.x"
libsVersion = "x.x.x"

[libraries]
module-core = { module = "module.group:module-core", version.ref = "moduleVersion" }
libs-core = { group = "libs.group", name = "libs-core", version.ref = "libsVersion" }  
libs-lifecycle = { group = "libs.group", name = "libs-lifecycle", version.ref = "libsVersion" }  
libs-view = { group = "libs.group", name = "libs-view", version.ref = "libsVersion" }

[bundles]  
libs = [  
    "libs-core",  
    "libs-lifecycle",  
    "libs-view",  
]
```
- For NavRoutes they should be `sealed class` that inherits from `Routes` having the label be a string id, an unselected and selected icon.
  - Should have a companion object with all navRoutes
- The test name should be like `given ... when ... then ...` for unitTest and for integration test it should be given[...]When[...]Then[...]
- When I give you instructions to how to create a datasource, repository, useCase, reducer, you should always generate the unit test files. And for the components, screens always generate the integration tests.

# Sources
- Api source : https://pokeapi.co/docs/v2
- For the principal library's use :
    - [ktor client](https://ktor.io/docs/welcome.html)
    - [koin](https://insert-koin.io/docs/reference/koin-compose/compose)
    - [kotlin.serialization](https://kotlinlang.org/docs/serialization.html)
    - [coil](https://coil-kt.github.io/coil/compose/)
    - adaptive and window size class libraries
        - [navigation3](https://developer.android.com/guide/navigation/navigation-3)
            - [adaptive navigation3](https://developer.android.com/guide/navigation/navigation-3/custom-layouts)
        - [material3-adaptive-navigation-suite](https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation)
        - [widow-size-classes](https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes)
    - material3
        - To have material3 expressive should at least have the version `1.5.0-alpha04`
        - Add `material-icons-extended`
# Rules

- The events and effects should be an `@Immutable` sealed interface
- All the state should be @Immutable data classes
- The screens file should end with Screen in the name
- Use cases should only have the invoke method
- The models in the `presentation` should have the annotation `@Immutable` or `@Stable`
- The error and failure classes should extend `Throwable`
- The data classes in the folder commands should respect the CRUD naming conventions
- For dto classes, their suffix name should always be `Dto`. The classes should always be annotated with `@Serializable`, and the parameters of the classes should be annotated with `@SerialName('name')`
- The order of parameters in a function (specially in a `@Composable` function) should always be obligatory parameters => parameters with a default value => functions
- All interface names should start with I
- For adaptive composable respect the rules define in the official android documentation https://developer.android.com/develop/ui/compose/layouts/adaptive/adaptive-dos-and-donts
- The Routes.kt should have the parent `sealed interface Route` and every other Route should inherited from it. Every route should be annotated with `@Serializable`