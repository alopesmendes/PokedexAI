package com.ailtontech.pokedewai.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

class ToolsTest {

    @Test
    fun `given a standard pokemon url, when extractPokemonIdFromUrl is called, then it should return the correct id`() {
        // Given
        val url = "https://pokeapi.co/api/v2/pokemon/25/"
        val expectedId = 25

        // When
        val result = Tools.extractPokemonIdFromUrl(url)

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `given a malformed pokemon url, when extractPokemonIdFromUrl is called, then it should throw an exception`() {
        // Given
        val malformedUrl = "https://pokeapi.co/api/v2/pokemon/"

        // When & Then
        assertFailsWith<NumberFormatException> {
            Tools.extractPokemonIdFromUrl(malformedUrl)
        }
    }

    @Test
    fun `given a url with query params, when extractValueFromQueryParam is called for limit, then it should return the correct value`() {
        // Given
        val url = "https://pokeapi.co/api/v2/pokemon?limit=151&offset=0"
        val expectedValue = "151"

        // When
        val result = Tools.extractValueFromQueryParam(url, "limit")

        // Then
        assertEquals(expectedValue, result)
    }

    @Test
    fun `given a url with query params, when extractValueFromQueryParam is called for offset, then it should return the correct value`() {
        // Given
        val url = "https://pokeapi.co/api/v2/pokemon?limit=151&offset=0"
        val expectedValue = "0"

        // When
        val result = Tools.extractValueFromQueryParam(url, "offset")

        // Then
        assertEquals(expectedValue, result)
    }

    @Test
    fun `given a url without the query param, when extractValueFromQueryParam is called, then it should return an empty string`() {
        // Given
        val url = "https://pokeapi.co/api/v2/pokemon?limit=151"

        // When
        val result = Tools.extractValueFromQueryParam(url, "offset")

        // Then
        assertEquals("", result)
    }
}
