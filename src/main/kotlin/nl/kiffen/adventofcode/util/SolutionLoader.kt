package nl.kiffen.adventofcode.util

import java.nio.file.Files
import java.nio.file.Paths
import io.quarkus.logging.Log

object SolutionLoader {

    public fun loadInput(year:String,day:String):String {
        val filePath = String.format("%s/%s/input", year, day);
        try {
            val classLoader = Thread.currentThread().getContextClassLoader();
            return Files.readString(Paths.get(classLoader.getResource(filePath)?.toURI()));
        } catch (e: Exception) {
            Log.error(e.printStackTrace())
            Log.error("Error loading input: ${e.message}")
            return "No input found for ${year}/${day}"
        }
    }
}