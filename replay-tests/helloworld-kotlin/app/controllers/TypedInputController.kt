package controllers

import binding.IdentifierTBinder
import model.Identifier
import model.User
import play.data.binding.As
import play.mvc.PlayController
import play.mvc.results.BadRequest
import play.mvc.results.Result
import play.rebel.View

@Suppress("unused")
class TypedInputController : PlayController {

  fun printDirect(userId: Long?): Result {
    if (userId == null) return BadRequest("direct userId missing")
    return View(
      "typedinput.html",
      mapOf(
        "userId" to userId,
        "innerType" to userId::class.simpleName,
        "outerType" to "N/A"
      )
    )
  }

  fun printForced(user: User?): Result {
    if (user == null) return BadRequest("forced user missing")
    return View(
      "typedinput.html",
      mapOf(
        "userId" to user.id,
        "innerType" to user.id.value::class.simpleName,
        "outerType" to user.id.getTypeString()
      )
    )
  }

  fun printGeneric(@As(binder = IdentifierTBinder::class) userId: Identifier<User>?): Result {
    if (userId == null) return BadRequest("generic userId missing")
    return View(
      "typedinput.html",
      mapOf(
        "userId" to userId,
        "innerType" to userId.value::class.simpleName,
        "outerType" to userId.getTypeString()
      )
    )
  }
}