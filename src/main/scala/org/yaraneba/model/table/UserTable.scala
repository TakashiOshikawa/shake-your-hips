package org.yaraneba.model.table

import play.api.libs.json.Json
import scalikejdbc.WrappedResultSet

/**
 * Created by oshikawatakashi on 2015/12/05.
 */
trait UserTable {

  implicit def jsonWrites = Json.writes[User]
  implicit def jsonReads  = Json.reads[User]

}

case class User
  (
    user_id: Long,
    user_name: String
  )


object UserTable {

  val allColumn = (res: WrappedResultSet) => User(
    user_id = res.long("user_id"),
    user_name = res.string("user_name")
  )

}