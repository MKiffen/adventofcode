package nl.kiffen.adventofcode

import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.GET
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.enterprise.event.Observes
import io.quarkus.runtime.StartupEvent
import nl.kiffen.adventofcode.util.SolutionLoader
import java.io.File


@Path("/adventofcode")
class AdventOfCodeController {

    private val listOfExercises = mutableListOf<Pair<String, String>>()

    fun readFolders(@Observes ev: StartupEvent) {
        val paths = File("src/main/resources").walk(FileWalkDirection.TOP_DOWN)
        paths.forEach {
            it.toString().endsWith("input")
            if (it.isFile and it.endsWith("input")) {
                val yearsAndDays = it.toString().split("/")
                val yearDay = Pair(yearsAndDays[yearsAndDays.size - 3], yearsAndDays[yearsAndDays.size - 2])
                listOfExercises.add(yearDay)
            }

        }
    }

    @GET
    @Path("/{year}/{day}")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(@PathParam("year") year: String, @PathParam("day") day: String): String {
        return if (listOfExercises.contains(Pair(year, day))) {
            SolutionLoader.loadInput(year, day)
        } else {
            "No solution for $year and $day"
        }
    }
}