package mongo

import models.Item
import org.mongodb.scala.bson.{BsonObjectId, Document}
import org.mongodb.scala.model.Filters
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{FindObservable, MongoClient, MongoCollection, MongoDatabase, Observable, Observer, Subscription}
import play.api.libs.json.Json


class MongoRepository {

  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("grid")

  val collection: MongoCollection[Document] = database.getCollection("items");

  def insert(item:Item): Unit ={
    val doc: Document = Document("_id" -> 0,"name" -> item.name,"status" -> item.status)
    return collection.insertOne(doc)
  }

  def update(item:Item): Unit = {
      collection.updateOne(equal("status","stock"),set("status","delivered"))
  }

  def find(): Unit ={
    val observable: FindObservable[Document] = collection.find();
    observable.subscribe ( new Observer[Document] {
      override def onNext(result: Document): Unit = println(result.toJson())
      override def onError(e: Throwable): Unit = println("Failed" + e.getMessage)
      override def onComplete(): Unit = println("Completed")
    })
  }

  def updateField(_id : String, imputItem: Item): Unit ={
    val updateDocument = Document("$set" -> Document(imputItem.toString))
    return collection
      .updateOne(Filters.eq("_id", BsonObjectId(_id)), updateDocument)
      .toFuture()
  }



}
