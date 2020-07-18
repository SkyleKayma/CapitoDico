package fr.skyle.capitodico.ext

fun Double.trimTrailingZero(): String? {
    val stringValue = this.toString()

    return if (stringValue.isNotEmpty()) {
        if (stringValue.indexOf(".") < 0) {
            stringValue
        } else stringValue.replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")
    } else stringValue
}