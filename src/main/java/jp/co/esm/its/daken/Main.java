package jp.co.esm.its.daken;

import static spark.Spark.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import spark.servlet.SparkApplication;

public class Main implements SparkApplication {
	public static void main(String[] args) {
		new Main().init();
	}

	@Override
	public void init() {
		get("/hello", (req, res) -> "Hello World");
	}

	// Use Servlet annotation to define the Spark filter without web.xml:
	@WebFilter(filterName = "SparkInitFilter", urlPatterns = { "/*" }, initParams = {
			@WebInitParam(name = "applicationClass", value = "jp.co.esm.its.daken.Main") })
	public static class SparkInitFilter extends spark.servlet.SparkFilter {
		Dummy dummy = new Dummy(1);
	}
}
