# AGENT.md

# Context
You're an expert in Kotlin and Jetpack Compose development specializing in clean architecture, MVI pattern, and test-driven development.

# Goal
Create a Pokedex application to view and search Pokemon and see their stats, attacks, and other details. Also, search and see the locations, items, and games.

# Documentation References

## Core Guidelines
- **@./docs/coding-style.md** - Kotlin and Jetpack Compose coding conventions
- **@./docs/project-architecture-style.md** - Complete MVI architecture structure

## Development Guides
- **@./docs/generate-features.md** - Feature creation workflow and code generation
- **@./app/src/test/docs/generate-unit-tests.md** - Unit testing guidelines
- **@./app/src/androidTest/docs/generate-integration-test.md** - Integration testing guidelines

# Instructions

## General Workflow
1. **Always follow TDD**: Write tests first, then implementation
2. **Always validate**: Check against rules in @./docs/rules.md before completion
3. **Always reference**: Use the documentation files for structure and examples

## When Creating Features
Follow the complete workflow in **@./docs/generate-features.md**:
- Create folder structure → Data layer → Domain layer → Presentation layer
- Generate unit tests for: DataSources, Repositories, Use Cases, ViewModels
- Generate integration tests for: Components, Screens

## When Creating Remote DataSources
Follow **@./docs/generate-features.md#remote-datasource-creation**:
1. Verify DTO exists (stop if not)
2. Create interface and implementation
3. Use `ApiServices.execute` wrapper
4. Reference https://pokeapi.co/docs/v2 for endpoints
5. Generate unit tests

## When Creating Components/Screens
- Think adaptive first (foldables, flip phones)
- Always include preview functions
- Use `collectAsStateWithLifecycle()` for ViewModels
- Generate integration tests

## When Creating Journey Tests
Follow **https://developer.android.com/studio/gemini/journeys**:
- Generate journey tests to validate complete user flows across multiple screens
- Use journey tests for end-to-end scenarios (e.g., search Pokemon → view details → see stats)
- Journey tests complement unit and integration tests by validating full user experiences

## Key Libraries
- **Ktor Client** - https://ktor.io/docs/welcome.html
- **Koin** - https://insert-koin.io/docs/reference/koin-compose/compose
- **Kotlin Serialization** - https://kotlinlang.org/docs/serialization.html
- **Coil** - https://coil-kt.github.io/coil/compose/
- **Navigation 3** - https://developer.android.com/guide/navigation/navigation-3
- **Material3 Adaptive** - https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation
- **Window Size Classes** - https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes
- **Material3** (v1.5.0-alpha04+) for expressive theme
- **Material Icons Extended**

## API Source
**PokeAPI**: https://pokeapi.co/docs/v2

---

**Always refer to the documentation files (@./docs/) for detailed examples, complete guidelines, and validation checklists.**