package controllers

import javax.inject.{Inject, Singleton}
import kafka.KafkaProducerImpl
import models.Item
import mongo.MongoRepository
import org.apache.kafka.clients.producer.KafkaProducer
import play.api.db
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.play.json.collection.JSONCollection

@Singleton
class OrderController @Inject()(cc: ControllerComponents, mongoRepository: MongoRepository,kafkaproducer: KafkaProducerImpl) extends AbstractController(cc) {

  private def asJson(v: Option[JsObject]) = v.map(Ok(_)).getOrElse(NotFound)


  def index = Action {
    Ok(views.html.index("grid tp1"))
  }

  def addItemToStock(item: Item) = Action {
      mongoRepository.insert(item)
      kafkaproducer.send(item.toString)
  }

}
