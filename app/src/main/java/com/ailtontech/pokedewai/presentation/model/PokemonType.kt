package com.ailtontech.pokedewai.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
enum class PokemonType(val color: Color) {
    BUG(Color(0xFF729F3F)),
    DARK(Color(0xFF707070)),
    DRAGON(Color(0xFF53A4CF)),
    ELECTRIC(Color(0xFFEED535)),
    FAIRY(Color(0xFFFDB9E9)),
    FIGHTING(Color(0xFFD56723)),
    FIRE(Color(0xFFFD7D24)),
    FLYING(Color(0xFF3DC7EF)),
    GHOST(Color(0xFF7B62A3)),
    GRASS(Color(0xFF9BCC50)),
    GROUND(Color(0xFFAB9842)),
    ICE(Color(0xFF51C4E7)),
    NORMAL(Color(0xFFA8A77A)),
    POISON(Color(0xFFB97FC9)),
    PSYCHIC(Color(0xFFF366B9)),
    ROCK(Color(0xFFB6A136)),
    STEEL(Color(0xFF9EB7B8)),
    WATER(Color(0xFF4592C4)),
    UNKNOWN(Color.Gray); // Fallback

    companion object {
        fun fromString(type: String): PokemonType {
            return entries.find { it.name.equals(type, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
