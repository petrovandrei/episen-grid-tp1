package kafka

import java.util.{Date, Properties}

import scala.util.Random
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class KafkaProducerImpl extends App {
  val events = args(0).toInt
  val topic = args(1)
  val brokers = args(2)
  val rnd = new Random()
  val props = new Properties()
  props.put("bootstrap.servers", brokers)
  props.put("client.id", "ScalaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  val producer = new KafkaProducer[String, String](props)

  def send(item:String): Unit ={
    val msg = item;
    val data = new ProducerRecord[String,String](topic,msg)
    producer.send(data)
    System.out.println("sent data: " + data)
  }


  producer.close()

}
