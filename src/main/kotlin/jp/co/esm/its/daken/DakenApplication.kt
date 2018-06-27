package jp.co.esm.its.daken

import com.google.appengine.api.datastore.DatastoreService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer
import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.googlecode.objectify.ObjectifyFactory
import com.googlecode.objectify.ObjectifyService
import org.springframework.context.annotation.Bean


@SpringBootApplication
class DakenApplication : SpringBootServletInitializer() {
    fun main(args: Array<String>) {
        SpringApplication.run(DakenApplication::class.java, *args)
    }

    @Bean
    fun cloudDatastoreService(): DatastoreService {
        return DatastoreServiceFactory.getDatastoreService()
    }

}

