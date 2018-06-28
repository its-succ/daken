package jp.co.esm.its.daken

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.DatastoreServiceFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.data.gclouddatastore.repository.EnableGcloudDatastoreRepositories


@SpringBootApplication
@EnableAutoConfiguration
@EnableGcloudDatastoreRepositories
class DakenApplication : SpringBootServletInitializer() {
    fun main(args: Array<String>) {
        SpringApplication.run(DakenApplication::class.java, *args)
    }

    @Bean
    fun cloudDatastoreService(): DatastoreService {
        return DatastoreServiceFactory.getDatastoreService()
    }

}

