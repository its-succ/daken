package jp.co.esm.its.daken

import com.google.api.client.extensions.appengine.http.UrlFetchTransport
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.json.jackson2.JacksonFactory
import spark.Route
import java.util.*
import spark.Spark.*

class AuthResource {

  private val verifier: GoogleIdTokenVerifier

  init {
    verifier = createGoogleIdTokenVerifier();
    setup()
  }

  private fun createGoogleIdTokenVerifier(): GoogleIdTokenVerifier {
    val transport = UrlFetchTransport.getDefaultInstance()
    val jsonFactory = JacksonFactory.getDefaultInstance()
    return GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList(Main.clientId()))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build()
  }

  private fun setup() {
    post("/auth", createVerifyTokenHandler())
  }

  private fun createVerifyTokenHandler() = Route({ req, res ->
    val idTokenString = req.queryParams("idToken")
    val idToken: GoogleIdToken? = verifier.verify(idTokenString)
    if (idToken == null) {
      res.status(400)
    } else {
      val domain = idToken.payload.hostedDomain
      if ("esm.co.jp" != (domain)) {
        res.status(400)
      } else {
        res.status()
      }
    }
  })

}
