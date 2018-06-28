package jp.co.esm.its.daken

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.Query.FilterPredicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.gclouddatastore.repository.GcloudDatastoreRepository
import org.springframework.stereotype.Repository

/**
 * ユーザーに関するリポジトリクラス
 */
interface UserRepository: GcloudDatastoreRepository<User, Long> {

    fun findByNfcCode(nfcCode: String): List<User>
}
