package org.yaraneba.model.DAO

import org.yaraneba.model.DBAccess
import org.yaraneba.model.table.{UserTable, User}
import scalikejdbc._

/**
 * Created by oshikawatakashi on 2015/12/05.
 */
object UserDAO extends DBAccess {


  // ユーザが存在するかチェック
  def isExistUserID(user_name: String): List[User] = {
    DB readOnly { implicit session =>
      val user = sql"SELECT * FROM users WHERE user_name = ${user_name}"
        .map(UserTable.allColumn).list.apply()
      user
    }
  }


  def createUser(user_name: String): User = {
    DB localTx { implicit session =>
      val user_id = sql"INSERT INTO users (user_name) values (${user_name})"
        .updateAndReturnGeneratedKey.apply()
      User(user_id, user_name)
    }
  }

}
