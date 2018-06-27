package jp.co.esm.its.daken

import com.google.appengine.api.datastore.Entity

/**
 * ユーザーのデータクラス
 */
data class User(var id: Long, var mailAddress: String, var nfcCode: String, var password: String) {

    /**
     * モデルの内容を指定のエンティティに設定します。
     * @param entity エンティティ
     */
    fun convert(entity: Entity) {
        entity.setProperty(ID, id)
        entity.setProperty(MAIL_ADDRESS, mailAddress)
        entity.setProperty(NFC_CODE, nfcCode)
        entity.setProperty(PASSWORD, password)
    }

    companion object {

        // エンティティの種類
        const val KIND = "User"
        // プロパティ名：label
        const val ID = "id"
        // プロパティ名：label
        const val NFC_CODE = "nfcCode"
        // プロパティ名：value
        const val MAIL_ADDRESS = "mailAddress"
        // プロパティ名：price
        const val PASSWORD = "password"
    }
}
