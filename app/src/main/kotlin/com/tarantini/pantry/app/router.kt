import com.tarantini.pantry.app.Dependencies
import com.tarantini.pantry.authentication.authenticationRoutes
import com.tarantini.pantry.item.itemEndpoints
import com.tarantini.pantry.user.userEndpoints
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.createRouting(dependencies: Dependencies) {
   routing {
      authenticationRoutes(dependencies.httpClient)
      itemEndpoints(dependencies.itemService)
      userEndpoints(dependencies.userService)
   }
}
