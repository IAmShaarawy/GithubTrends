package dev.shaarawy.githubtrends

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

suspend fun readTextFile(fileName: String): String =
    withContext(Dispatchers.IO) {
        File(javaClass.classLoader!!.getResource(fileName).file).readText()
    }

suspend inline fun <reified T> readJSONFile(fileName: String): T =
    withContext(Dispatchers.Default) {
        Json.decodeFromString(readTextFile(fileName))
    }

const val fakeDataPath = "data/response.json"