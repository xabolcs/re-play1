package binding

import model.Identifier
import play.data.binding.TypeBinder
import play.mvc.Http
import play.mvc.Scope
import java.lang.reflect.Type

class IdentifierTBinder<T>: TypeBinder<Identifier<T>> {
  override fun bind(
    request: Http.Request?,
    session: Scope.Session?,
    name: String?,
    annotations: Array<out Annotation?>?,
    value: String?,
    actualClass: Class<*>?,
    genericType: Type?
  ): Any? {
    return value?.toLong()?.let { Identifier<T>(it) }
  }
}