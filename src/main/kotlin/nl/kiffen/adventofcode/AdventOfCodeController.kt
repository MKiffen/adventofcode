package nl.kiffen.adventofcode

import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.GET
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.enterprise.event.Observes
import io.quarkus.runtime.StartupEvent
import java.nio.file.Paths
import java.nio.file.Files
import io.quarkus.logging.Log
import io.smallrye.mutiny.tuples.Tuple
import nl.kiffen.adventofcode.util.SolutionLoader


@Path("/adventofcode")
class AdventOfCodeController {
    
    private val listOfExercises = mutableListOf<Pair<String,String>>()    
    
    fun readFolders(@Observes ev: StartupEvent) {
        val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
        val resourcesPath = Paths.get(projectDirAbsolutePath, "/src/main/resources")
        val paths = Files.walk(resourcesPath)
                    .filter { item -> Files.isRegularFile(item) }
                    .filter { item -> item.toString().endsWith("input") }
        paths.forEach({
            val yearsAndDays = it.toString().split("/")
            val yearDay = Pair(yearsAndDays.get(yearsAndDays.size - 3),yearsAndDays.get(yearsAndDays.size - 2))
            listOfExercises.add(yearDay)
        })
    }

    @GET
    @Path("/{year}/{day}")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(@PathParam("year") year:String, @PathParam("day") day:String):String {
        if(listOfExercises.contains(Pair(year,day))) {
            
            return SolutionLoader.loadInput(year,day)
        } else {
            return "No solution for ${year} and ${day}"
        }
    }
}