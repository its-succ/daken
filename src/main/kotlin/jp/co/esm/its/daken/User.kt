package jp.co.esm.its.daken

import com.google.appengine.api.datastore.Entity
import org.springframework.data.annotation.Id

/**
 * ユーザーのデータクラス
 */
data class User(@Id var id: Long = 0L, var mail: String = "", var nfcCode: String = "", var password: String = "")
