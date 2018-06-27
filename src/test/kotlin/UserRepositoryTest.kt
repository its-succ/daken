import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import jp.co.esm.its.daken.DakenApplication
import jp.co.esm.its.daken.User
import jp.co.esm.its.daken.UserRepository
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(DakenApplication::class)])
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    var datastore: DatastoreService? = null

    private val helper = LocalServiceTestHelper(LocalDatastoreServiceTestConfig())

    @Before
    fun setUp() {
        helper.setUp()
        val entity = Entity("User")
        val user = User(1, "mail", "12345", "pass!")
        user.convert(entity)
        datastore!!.put(entity)
    }

    @After
    fun tearDown() {
        helper.tearDown()
    }

    @Test
    fun 指定したNFCコードに該当するユーザーが存在する場合データが取得できること() {
        var result = userRepository.readByNfcCode("12345")
        Assertions.assertThat(result).isEqualTo(User(1, "mail", "12345", "pass!"))
    }

    @Test
    fun 指定したNFCコードに該当するユーザーが存在しない場合データがnullであること() {
        var result = userRepository.readByNfcCode("54321")
        Assertions.assertThat(result).isEqualTo(null)
    }
}
