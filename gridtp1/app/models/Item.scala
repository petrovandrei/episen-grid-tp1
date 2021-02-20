package models


import play.api.libs.json.{Format, Json}

case class Item(
               id: Long,
               name: String,
               status: String
               )


//companion object ItemStatus extends Enumeration {
//  val inStock = Value("In Stock")
//  val delivered = Value("Delivered")
//}


