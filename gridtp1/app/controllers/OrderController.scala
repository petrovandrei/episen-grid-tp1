package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

@Singleton
class OrderController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def addItemToStock(itemId: Long) = Action {

    Ok(Json.toJson(itemId))
  }

}
