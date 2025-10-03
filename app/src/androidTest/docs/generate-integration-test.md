# Integration Test Generation Guide

> **Important:** This guide must be used in conjunction with:
> - **@./docs/coding-style.md** - General Kotlin and Compose conventions
> - **@./docs/project-architecture-style.md** - Complete project structure

## Table of Contents
- [Test Naming Conventions](#test-naming-conventions)
- [Base Test Classes](#base-test-classes)
- [Composable Component Tests](#composable-component-tests)
- [Screen Integration Tests](#screen-integration-tests)
- [Accessibility Tests](#accessibility-tests)
- [Common Test Scenarios](#common-test-scenarios)
- [Validation Checklist](#validation-checklist)

---

## Test Naming Conventions

### Integration Test Template
```kotlin
`given[Precondition]When[Action]Then[ExpectedResult]`
```

### Examples
```kotlin
`givenValidProfileWhenDisplayedThenShowsUserName`
`givenLoadingStateWhenRenderedThenShowsProgressIndicator`
`givenErrorStateWhenDisplayedThenShowsErrorMessage`
`givenButtonClickWhenInvokedThenCallsOnClick`
```

### Rules
- Use **PascalCase** (no spaces)
- Structure: **given** → **When** → **Then**
- Capitalize first letter of each word after given/When/Then
- Be **specific** and **descriptive**

---

## Base Test Classes

### Location
All base test classes are located in `androidTest/test_utils/` package.

### Available Base Classes

#### BaseComposeTest
**Path:** `androidTest/test_utils/BaseComposeTest.kt`

**Purpose:** Base class for Compose UI component tests

**Provides:**
- `composeTestRule: ComposeContentTestRule`
- Common test setup for Compose testing
- Theme wrapper utilities

#### BaseScreenTest
**Path:** `androidTest/test_utils/BaseScreenTest.kt`

**Purpose:** Base class for full screen integration tests

**Provides:**
- `composeTestRule: ComposeContentTestRule`
- `navController: TestNavHostController`
- Navigation test utilities
- Screen test helpers

---

## Composable Component Tests

### Test Structure

```kotlin
class {Component}Test : BaseComposeTest() {

    @Test
    fun givenValidDataWhenDisplayedThenShowsContent() {
        // Arrange
        val testData = {Model}Model(/* ... */)

        // Act
        composeTestRule.setContent {
            {Component}(data = testData)
        }

        // Assert
        composeTestRule.onNodeWithText("Expected Text").assertIsDisplayed()
    }

    @Test
    fun givenButtonClickWhenInvokedThenCallsCallback() {
        // Arrange
        var clicked = false
        val onClick = { clicked = true }

        // Act
        composeTestRule.setContent {
            {Component}(onClick = onClick)
        }
        composeTestRule.onNodeWithText("Button Text").performClick()

        // Assert
        assert(clicked)
    }
}
```

### Complete Example

```kotlin
class ProfileCardTest : BaseComposeTest() {

    private val testProfile = ProfileModel(
        userId = "123",
        displayName = "John Doe",
        email = "john@example.com",
        avatarUrl = null,
        isVerified = true
    )

    @Test
    fun givenValidProfileWhenDisplayedThenShowsUserName() {
        composeTestRule.setContent {
            ProfileCard(profile = testProfile)
        }

        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
    }

    @Test
    fun givenValidProfileWhenDisplayedThenShowsEmail() {
        composeTestRule.setContent {
            ProfileCard(profile = testProfile)
        }

        composeTestRule.onNodeWithText("john@example.com").assertIsDisplayed()
    }

    @Test
    fun givenVerifiedProfileWhenDisplayedThenShowsVerifiedBadge() {
        composeTestRule.setContent {
            ProfileCard(profile = testProfile)
        }

        composeTestRule.onNodeWithContentDescription("Verified badge").assertIsDisplayed()
    }

    @Test
    fun givenClickableCardWhenClickedThenInvokesCallback() {
        var clicked = false
        val onClick = { clicked = true }

        composeTestRule.setContent {
            ProfileCard(
                profile = testProfile,
                onClick = onClick
            )
        }

        composeTestRule.onNodeWithText("John Doe").performClick()

        assert(clicked)
    }

    @Test
    fun givenLoadingStateWhenDisplayedThenShowsLoadingIndicator() {
        composeTestRule.setContent {
            ProfileCard(isLoading = true)
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}
```

---

## Screen Integration Tests

### Test Structure

```kotlin
class {Feature}ScreenTest : BaseScreenTest() {

    @Test
    fun givenScreenLaunchedWhenLoadedThenShowsContent() {
        // Arrange & Act
        composeTestRule.setContent {
            {Feature}Screen(
                navController = navController
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Expected Title").assertIsDisplayed()
    }

    @Test
    fun givenBackButtonWhenClickedThenNavigatesBack() {
        // Arrange
        composeTestRule.setContent {
            {Feature}Screen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Act
        composeTestRule.onNodeWithContentDescription("Navigate back").performClick()

        // Assert
        assert(navController.currentBackStackEntry == null)
    }
}
```

### Complete Screen Test Example

```kotlin
class ProfileScreenTest : BaseScreenTest() {

    @Test
    fun givenScreenLaunchedWhenLoadedThenShowsProfileContent() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
    }

    @Test
    fun givenLoadingStateWhenDisplayedThenShowsProgressIndicator() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
    }

    @Test
    fun givenEditButtonWhenClickedThenNavigatesToEditScreen() {
        var navigatedToEdit = false

        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController,
                onNavigateToEdit = { navigatedToEdit = true }
            )
        }

        composeTestRule.onNodeWithText("Edit Profile").performClick()

        assert(navigatedToEdit)
    }

    @Test
    fun givenBackButtonWhenClickedThenNavigatesBack() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composeTestRule.onNodeWithContentDescription("Navigate back").performClick()

        assert(navController.currentBackStackEntry == null)
    }
}
```

---

## Accessibility Tests

### Purpose
Accessibility tests ensure your UI is usable by everyone, including users with disabilities. These tests verify:
- All interactive elements have content descriptions
- Text has sufficient contrast
- Touch targets meet minimum size requirements
- Screen reader compatibility

### Test Structure

```kotlin
class {Component}AccessibilityTest : BaseComposeTest() {

    @Test
    fun givenInteractiveElementWhenDisplayedThenHasContentDescription() {
        composeTestRule.setContent {
            {Component}()
        }

        composeTestRule
            .onNodeWithContentDescription("Expected description")
            .assertExists()
    }

    @Test
    fun givenButtonWhenDisplayedThenHasButtonRole() {
        composeTestRule.setContent {
            {Component}()
        }

        composeTestRule
            .onNode(hasRole(Role.Button))
            .assertExists()
    }
}
```

### Complete Accessibility Test Example

```kotlin
class ProfileCardAccessibilityTest : BaseComposeTest() {

    private val testProfile = ProfileModel(
        userId = "123",
        displayName = "John Doe",
        email = "john@example.com",
        avatarUrl = "https://example.com/avatar.jpg",
        isVerified = true
    )

    @Test
    fun givenProfileImageWhenDisplayedThenHasContentDescription() {
        composeTestRule.setContent {
            ProfileCard(profile = testProfile)
        }

        composeTestRule
            .onNodeWithContentDescription("Profile picture of John Doe")
            .assertExists()
    }

    @Test
    fun givenVerifiedBadgeWhenDisplayedThenHasContentDescription() {
        composeTestRule.setContent {
            ProfileCard(profile = testProfile)
        }

        composeTestRule
            .onNodeWithContentDescription("Verified badge")
            .assertExists()
    }

    @Test
    fun givenEditButtonWhenDisplayedThenHasContentDescriptionAndButtonRole() {
        composeTestRule.setContent {
            ProfileCard(
                profile = testProfile,
                onEditClick = {}
            )
        }

        composeTestRule
            .onNode(
                hasContentDescription("Edit profile") and hasRole(Role.Button)
            )
            .assertExists()
            .assertIsEnabled()
    }

    @Test
    fun givenClickableCardWhenDisplayedThenHasMergedSemantics() {
        composeTestRule.setContent {
            ProfileCard(
                profile = testProfile,
                onClick = {}
            )
        }

        // Verify card is clickable and has proper semantics
        composeTestRule
            .onNode(hasClickAction())
            .assertExists()
    }

    @Test
    fun givenLoadingStateWhenDisplayedThenHasLoadingDescription() {
        composeTestRule.setContent {
            ProfileCard(isLoading = true)
        }

        composeTestRule
            .onNodeWithContentDescription("Loading profile")
            .assertExists()
    }

    @Test
    fun givenErrorStateWhenDisplayedThenHasErrorDescription() {
        composeTestRule.setContent {
            ProfileCard(
                error = "Failed to load profile"
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Error: Failed to load profile")
            .assertExists()
    }

    @Test
    fun givenTextContentWhenDisplayedThenIsMergeable() {
        composeTestRule.setContent {
            ProfileCard(profile = testProfile)
        }

        // Verify that text content can be read as a single block
        composeTestRule
            .onNode(hasText("John Doe", substring = true))
            .assert(hasAnyAncestor(hasClickAction()))
    }

    @Test
    fun givenAllInteractiveElementsWhenDisplayedThenHaveMinimumTouchTarget() {
        composeTestRule.setContent {
            ProfileCard(
                profile = testProfile,
                onEditClick = {},
                onDeleteClick = {}
            )
        }

        // Verify buttons have minimum 48dp touch target
        composeTestRule
            .onAllNodes(hasClickAction())
            .assertCountEquals(3) // Card, Edit, Delete
            .apply {
                fetchSemanticsNodes().forEach { node ->
                    val size = node.size
                    assert(size.width >= 48 && size.height >= 48) {
                        "Touch target size ${size.width}x${size.height} is below minimum 48x48dp"
                    }
                }
            }
    }
}
```

### Screen Accessibility Test Example

```kotlin
class ProfileScreenAccessibilityTest : BaseScreenTest() {

    @Test
    fun givenScreenWhenDisplayedThenHasNavigationContentDescriptions() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController
            )
        }

        // Verify navigation button has content description
        composeTestRule
            .onNodeWithContentDescription("Navigate back")
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun givenScreenWhenDisplayedThenAllButtonsHaveDescriptions() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController
            )
        }

        // Verify all buttons have content descriptions or text
        composeTestRule
            .onAllNodes(hasRole(Role.Button))
            .assertAll(
                hasContentDescription() or hasText("", substring = false)
            )
    }

    @Test
    fun givenFormFieldsWhenDisplayedThenHaveLabels() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController,
                isEditing = true
            )
        }

        // Verify text fields have labels
        composeTestRule
            .onNodeWithText("Name")
            .assertExists()

        composeTestRule
            .onNodeWithText("Email")
            .assertExists()
    }

    @Test
    fun givenImagesWhenDisplayedThenHaveContentDescriptions() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController
            )
        }

        // Verify all images have content descriptions
        composeTestRule
            .onAllNodes(hasContentDescription())
            .assertCountEquals(2) // Profile image + verified badge
    }

    @Test
    fun givenScreenNavigationWhenPerformedThenAnnouncesChanges() {
        composeTestRule.setContent {
            ProfileScreen(
                userId = "123",
                navController = navController
            )
        }

        // Navigate to edit mode
        composeTestRule
            .onNodeWithText("Edit Profile")
            .performClick()

        // Verify screen reader announcement
        composeTestRule
            .onNodeWithContentDescription("Edit profile mode")
            .assertExists()
    }
}
```

### Accessibility Verification Checklist

- [ ] All images have meaningful content descriptions
- [ ] All icons have content descriptions (or null if parent has it)
- [ ] All buttons have content descriptions or visible text labels
- [ ] All interactive elements have minimum 48dp touch targets
- [ ] All text fields have labels
- [ ] Loading states announce to screen readers
- [ ] Error messages are readable by screen readers
- [ ] Navigation changes are announced
- [ ] Clickable cards use merged semantics
- [ ] All interactive elements have appropriate roles
- [ ] Color is not the only means of conveying information
- [ ] Text has sufficient contrast ratio (4.5:1 for normal text)

---

## Common Test Scenarios

### Testing Display Content

```kotlin
@Test
fun givenDataWhenDisplayedThenShowsAllFields() {
    composeTestRule.setContent {
        MyComponent(data = testData)
    }

    composeTestRule.onNodeWithText("Title").assertIsDisplayed()
    composeTestRule.onNodeWithText("Description").assertIsDisplayed()
}
```

### Testing User Interactions

```kotlin
@Test
fun givenButtonWhenClickedThenTriggersAction() {
    var actionTriggered = false
    
    composeTestRule.setContent {
        MyComponent(onAction = { actionTriggered = true })
    }

    composeTestRule.onNodeWithText("Submit").performClick()
    assert(actionTriggered)
}
```

### Testing Text Input

```kotlin
@Test
fun givenTextFieldWhenInputProvidedThenUpdatesValue() {
    var inputValue = ""
    
    composeTestRule.setContent {
        MyTextField(
            value = inputValue,
            onValueChange = { inputValue = it }
        )
    }

    composeTestRule.onNodeWithTag("text_field").performTextInput("Hello")
    assert(inputValue == "Hello")
}
```

### Testing Conditional Rendering

```kotlin
@Test
fun givenLoadingStateWhenTrueThenShowsLoader() {
    composeTestRule.setContent {
        MyComponent(isLoading = true)
    }

    composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
}

@Test
fun givenLoadingStateWhenFalseThenHidesLoader() {
    composeTestRule.setContent {
        MyComponent(isLoading = false)
    }

    composeTestRule.onNodeWithTag("loading_indicator").assertDoesNotExist()
}
```

### Testing Lists

```kotlin
@Test
fun givenItemListWhenDisplayedThenShowsAllItems() {
    val items = listOf("Item 1", "Item 2", "Item 3")
    
    composeTestRule.setContent {
        MyList(items = items)
    }

    items.forEach { item ->
        composeTestRule.onNodeWithText(item).assertIsDisplayed()
    }
}
```

---

## Semantic Finders

### Text-based Finders
```kotlin
onNodeWithText("Exact text")
onNodeWithText("text", substring = true)
onAllNodesWithText("text")
```

### Content Description Finders
```kotlin
onNodeWithContentDescription("Image description")
onAllNodesWithContentDescription("description")
```

### Test Tag Finders
```kotlin
onNodeWithTag("unique_tag")
onAllNodesWithTag("tag")
```

### Compound Finders
```kotlin
onNode(hasText("text") and isEnabled())
onNode(hasContentDescription("desc") or hasTestTag("tag"))
```

---

## Common Assertions

### Visibility Assertions
```kotlin
assertIsDisplayed()
assertIsNotDisplayed()
assertExists()
assertDoesNotExist()
```

### State Assertions
```kotlin
assertIsEnabled()
assertIsNotEnabled()
assertIsSelected()
assertIsNotSelected()
assertIsFocused()
assertIsNotFocused()
```

### Content Assertions
```kotlin
assertTextEquals("text")
assertTextContains("partial text")
assertContentDescriptionEquals("description")
```

---

## Common Actions

### Click Actions
```kotlin
performClick()
performTouchInput { click() }
```

### Text Input Actions
```kotlin
performTextInput("text")
performTextClearance()
performTextReplacement("new text")
```

### Scroll Actions
```kotlin
performScrollTo()
performScrollToIndex(index)
performScrollToNode(hasText("text"))
```

### Gesture Actions
```kotlin
performTouchInput { swipeLeft() }
performTouchInput { swipeRight() }
performTouchInput { swipeUp() }
performTouchInput { swipeDown() }
```

---

## Validation Checklist

### Integration Test Requirements
- [ ] Test name follows `given[...]When[...]Then[...]` convention in PascalCase
- [ ] Test extends appropriate Base test class (`BaseComposeTest` or `BaseScreenTest`)
- [ ] Test sets content with `composeTestRule.setContent { }`
- [ ] Test uses semantic finders: `onNodeWithText`, `onNodeWithContentDescription`, `onNodeWithTag`
- [ ] Test performs actions: `performClick()`, `performTextInput()`, etc.
- [ ] Test asserts UI state: `assertIsDisplayed()`, `assertExists()`, etc.
- [ ] Test covers: display, interaction, state changes
- [ ] Test uses test tags for non-text elements
- [ ] Components have contentDescription for accessibility
- [ ] Tests verify callbacks are invoked
- [ ] Screen tests verify navigation behavior

### Accessibility Test Requirements
- [ ] All images have content description tests
- [ ] All icons have content description tests
- [ ] All buttons have content description or text tests
- [ ] Interactive elements have minimum 48dp touch target tests
- [ ] Text fields have label tests
- [ ] Loading states have announcement tests
- [ ] Error messages have screen reader tests
- [ ] Navigation changes have announcement tests
- [ ] Clickable cards have merged semantics tests
- [ ] All interactive elements have role tests

---

## Best Practices

### Use Test Tags
```kotlin
@Composable
fun MyComponent() {
    CircularProgressIndicator(
        modifier = Modifier.testTag("loading_indicator")
    )
}
```

### Use Content Descriptions
```kotlin
@Composable
fun MyIcon() {
    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "Success icon"
    )
}
```

### Test User Flows
```kotlin
@Test
fun givenFormWhenFilledAndSubmittedThenShowsSuccess() {
    composeTestRule.setContent {
        MyForm(onSubmit = { })
    }

    // Fill form
    composeTestRule.onNodeWithTag("name_field").performTextInput("John")
    composeTestRule.onNodeWithTag("email_field").performTextInput("john@example.com")
    
    // Submit
    composeTestRule.onNodeWithText("Submit").performClick()
    
    // Verify
    composeTestRule.onNodeWithText("Success").assertIsDisplayed()
}
```

---

**Remember:** Always cross-reference with:
- **@./docs/coding-style.md** for detailed Kotlin and Compose conventions
- **@./docs/project-architecture-style.md** for complete project structure