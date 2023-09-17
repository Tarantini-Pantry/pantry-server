import com.tarantini.pantry.app.Dependencies
import com.tarantini.pantry.item.itemEndpoints
import com.tarantini.pantry.user.userEndpoints
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.createRouting(dependencies: Dependencies) {
   routing {
      itemEndpoints(dependencies.itemService)
      userEndpoints(dependencies.userService)
   }
}
