package org.yaraneba.model.table

import org.joda.time.DateTime
import play.api.libs.json.Json
import scalikejdbc.WrappedResultSet

/**
 * Created by oshikawatakashi on 2015/12/05.
 */
trait TodoTable {

  implicit def jsonWrites = Json.writes[Todo]
  implicit def jsonReads  = Json.reads[Todo]

  implicit def jsonInsResWrites = Json.writes[TodoResponse]
  implicit def jsonInsResReads  = Json.reads[TodoResponse]

  implicit def jsonListTodosWrites = Json.writes[ListTodos]
  implicit def jsonListTodosReads  = Json.reads[ListTodos]

}


case class Todo
  (
    todo_id: Long,
    todo_title: String = "",
    user_id: Long,
    description: Option[String] = None,
    deadline: DateTime,
    progres_status: Int
  )


case class TodoResponse
  (
    todo_id: Long,
    user_name: String,
    todo_title: String,
    description: Option[String] = None,
    deadline: DateTime,
    progres_status: Int,
    request_status: Int
  )


case class ListTodos
  (
    todos: List[TodoResponse]
  )


object TodoTable {

  val allColumn = (res: WrappedResultSet) => Todo(
    todo_id = res.long("todo_id"),
    todo_title = res.string("todo_title"),
    user_id = res.long("user_id"),
    description = res.stringOpt("description"),
    deadline = res.jodaDateTime("deadline"),
    progres_status = res.int("progres_status")
  )

  val userJoinColumn = (res: WrappedResultSet) => TodoResponse(
    todo_id = res.long("todo_id"),
    user_name = res.string("user_name"),
    todo_title = res.string("todo_title"),
    description = res.stringOpt("description"),
    deadline = res.jodaDateTime("deadline"),
    progres_status = res.int("progres_status"),
    request_status = res.int("request_status")
  )

}