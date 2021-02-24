package models


import play.api.libs.json.{Format, Json}
import reactivemongo.api.bson.compat.fromDocument
import reactivemongo.api.bson.{BSONDocument, BSONObjectID, Macros}
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter}
import reactivemongo.bson.BSONHandler.provided


case class Item(
               _id: Long,
               name: String,
               status: String
               )


object Item {
  implicit val itemFormat = Json.format[Item]
}



