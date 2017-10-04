package jp.co.esm.its.daken

import spark.Spark.*

import javax.servlet.annotation.WebFilter
import javax.servlet.annotation.WebInitParam

import spark.servlet.SparkApplication

class Main : SparkApplication {

  override fun init() {
    get("/hello") { req, res -> "Hello World" }
  }

  // Use Servlet annotation to define the Spark filter without web.xml:
  @WebFilter(filterName = "SparkInitFilter", urlPatterns = arrayOf("/*"),
      initParams = arrayOf(WebInitParam(name = "applicationClass", value = "jp.co.esm.its.daken.Main")))
  class SparkInitFilter : spark.servlet.SparkFilter() {
    internal var dummy = Dummy(1)
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      Main().init()
    }
  }
}
