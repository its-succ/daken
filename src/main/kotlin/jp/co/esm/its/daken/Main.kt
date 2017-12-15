package jp.co.esm.its.daken

import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.appengine.repackaged.com.google.api.client.http.HttpTransport
import com.google.appengine.repackaged.com.google.api.client.json.JsonFactory
import com.google.appengine.repackaged.com.google.api.client.json.jackson2.JacksonFactory
import spark.Spark.*

import javax.servlet.annotation.WebFilter
import javax.servlet.annotation.WebInitParam

import spark.servlet.SparkApplication
import java.util.*
import javax.servlet.http.HttpServletRequest

class Main : SparkApplication {

  override fun init() {
    staticFiles.location("/public") // Static Files
    get("/hello") { req, res -> "Hello World" }
    AuthResource()
  }

  // Use Servlet annotation to define the Spark filter without web.xml:
  @WebFilter(filterName = "SparkInitFilter", urlPatterns = ["/*"],
      initParams = [WebInitParam(name = "applicationClass", value = "jp.co.esm.its.daken.Main")])
  class SparkInitFilter : spark.servlet.SparkFilter() {
    internal var dummy = Dummy(1)
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
