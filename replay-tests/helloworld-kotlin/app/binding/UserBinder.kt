package binding

import model.Identifier
import model.User
import play.data.binding.Global
import play.data.binding.TypeBinder
import play.mvc.Http
import play.mvc.Scope
import java.lang.reflect.Type

@Global
class UserBinder : TypeBinder<User> {
  override fun bind(
    request: Http.Request?,
    session: Scope.Session?,
    name: String?,
    annotations: Array<out Annotation?>?,
    value: String?,
    actualClass: Class<*>?,
    genericType: Type?
  ): Any? {
    return value?.let { User(Identifier(it.toLong())) }
  }
}
