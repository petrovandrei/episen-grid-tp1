package controllers

import play.api.{Logger, db}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.mvc.Controller
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.core.commands.BSONCommandError
import reactivemongo.play.json._
import collection._
import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import models._
import play.api.libs.json._
import reactivemongo.api.Cursor

import scala.concurrent.{ExecutionContext, Future}

class ItemController @Inject()(
                                components: ControllerComponents,
                                val reactiveMongoApi: ReactiveMongoApi
                              ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("items"))

  def create = Action.async {
    val item = Item(1, "test", "stock")

    // insert the user
    val futureResult = collection.flatMap(_.insert.one(item))

    // when the insert is performed, send a OK 200 result
    futureResult.map(_ => Ok)
  }

  def createFromJson = Action.async(parse.json) { request =>
    request.body.validate[Item].map { item =>
      // `user` is an instance of the case class `models.User`
      collection.flatMap(_.insert.one(item)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findById(id: Long) = Action.async {
    // let's do our query
    val cursor: Future[Cursor[Item]] = collection.map {
      _.find(Json.obj("_id" -> id)).
        cursor[Item]()
    }
    // gather all the JsObjects in a list
    val futureItemList: Future[List[Item]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[Item]]()))

    // everything's ok! Let's reply with the array
    futureItemList.map { item =>
      Ok(item.toString)
    }
  }
}
