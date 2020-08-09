package fr.skyle.capitodico.enum

enum class SortType {
    ALPHABETICAL,
    ACTIVATION,
    VALUE;

    companion object {
        fun fromOrdinal(position: Int): SortType =
            values().firstOrNull { it.ordinal == position } ?: ALPHABETICAL
    }
}