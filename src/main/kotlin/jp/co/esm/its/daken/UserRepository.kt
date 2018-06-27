package jp.co.esm.its.daken

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.Query.FilterPredicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * ユーザーに関するリポジトリクラス
 */
@Repository
class UserRepository {

    @Autowired
    internal var datastore: DatastoreService? = null

    fun create(model: User): Entity {
        val entity = Entity(User.KIND)
        model.convert(entity)
        datastore!!.put(entity)
        return entity
    }

    fun readByNfcCode(nfcCode: String): User? {
        val userFilter = FilterPredicate(User.NFC_CODE, Query.FilterOperator.EQUAL, nfcCode)
        val query = Query(User.KIND).setFilter(userFilter)
        val preparedQuery = datastore!!.prepare(query)
        val entities = preparedQuery.asQueryResultIterator()
        val users = entitiesToModels(entities)

        if (users.isEmpty()) {
            // 該当ユーザーなし
            return null
        }
        // TODO 2件以上はないはず
        return users[0]
    }

    private fun entityToModel(entity: Entity): User = User(
            id = entity.key.id,
            mailAddress = entity.getProperty(User.MAIL_ADDRESS) as String,
            nfcCode = entity.getProperty(User.NFC_CODE) as String,
            password = entity.getProperty(User.PASSWORD) as String
    )

    private fun entitiesToModels(results: Iterator<Entity>): List<User> {
        val users: MutableList<User> = mutableListOf()
        while (results.hasNext()) {
            users.add(entityToModel(results.next()))
        }
        return users
    }
}
