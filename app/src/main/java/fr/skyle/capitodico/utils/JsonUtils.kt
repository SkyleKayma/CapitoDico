package fr.skyle.capitodico.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.openium.kotlintools.ext.inputStreamToString
import fr.skyle.capitodico.R

object JsonUtils {

    val events = mutableMapOf<String, Event>()
    val cards = mutableListOf<Card>()

    data class ParsedJson(
        var events: Map<String, Event>,
        var cards: List<Card>
    )

    data class Event(
        var readable_name: String = "",
        var color: String = ""
    )

    data class Card(
        var name: String = "",
        var value: Double,
        var priority: Double,
        var description: String,
        var events: List<String> = listOf(),
        var effect: String
    )

    fun init(context: Context) {
        val parsedJson = context.resources.openRawResource(R.raw.jsonformatter).inputStreamToString()
        val parsedJsonObject: ParsedJson? = Gson().fromJson(parsedJson, object : TypeToken<ParsedJson?>() {}.type)

        parsedJsonObject?.events?.let {
            events.putAll(it)
        }

        parsedJsonObject?.cards?.let {
            cards.addAll(it)
        }
    }
}