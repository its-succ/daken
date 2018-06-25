package jp.co.esm.its.daken

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import spark.Request
import spark.Spark.*

import javax.servlet.annotation.WebFilter
import javax.servlet.annotation.WebInitParam

import spark.servlet.SparkApplication
import java.nio.charset.Charset
import java.util.*


class Main : SparkApplication {

  var publishedMessage :String =""

  override fun init() {
    staticFiles.location("/public") // Static Files
    get("/hello") { req, res -> "Hello World!! message:" + publishedMessage  }
    post("/_ah/push-handlers/receivePublish") { req, res -> receivePublish(req) }
    AuthResource()
  }

  // Use Servlet annotation to define the Spark filter without web.xml:
  @WebFilter(filterName = "SparkInitFilter", urlPatterns = ["/*"],
      initParams = [WebInitParam(name = "applicationClass", value = "jp.co.esm.its.daken.Main")])
  class SparkInitFilter : spark.servlet.SparkFilter() {
    internal var dummy = Dummy(1)
  }

  // Deal with publish(put request) from PubSub:
  private fun receivePublish(req:Request) : String{

    // Parse the JSON message to the POJO model class.
    val mapper = ObjectMapper().registerModule(KotlinModule())
    val node = mapper.readTree(req.body())
    // get data from pubsubMessage(JSON)
    val messageId = node.get("message").get("messageId").asText()
    val dataStringBase64 = node.get("message").get("data").asText()
    // Decode Base64 and Encode UTF-8
    val dataStringUTF8 = String(Base64.getDecoder().decode(dataStringBase64), Charset.forName("UTF-8"))
    publishedMessage += String.format("ID:%s, Data:%s ／",messageId,dataStringUTF8)
    return "ok. message published."
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      Main().init()
    }

    fun clientId(): String {
      return "512704348312-hhq0htjk798tahele7tf32n2ckc4supa.apps.googleusercontent.com"
      // Main.javaClass.getResource("/secret.json").readText()
    }
  }
}
