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

  implicit def jsonTodoUpdateWrites = Json.writes[TodoModifyResponse]
  implicit def jsonTodoUpdateReads  = Json.reads[TodoModifyResponse]

  implicit def jsonTodoContentsUpdateWrites = Json.writes[TodoContentsModifyResponse]
  implicit def jsonTodoContentsUpdateReads  = Json.reads[TodoContentsModifyResponse]

}


case class Todo
  (
    todo_id: Long,
    todo_title: String = "",
    user_id: Long,
    description: Option[String] = None,
    deadline: DateTime,
    progress_status: Int
  )


case class TodoResponse
  (
    todo_id: Long,
    user_name: String,
    todo_title: String,
    description: Option[String] = None,
    deadline: DateTime,
    progress_status: Int,
    request_status: Int
  )


case class ListTodos
  (
    todos: List[TodoResponse]
  )


case class TodoModifyResponse
  (
    todo_id: Long,
    request_status: Int
  )

case class TodoContentsModifyResponse
  (
    todo_id: Long,
    todo_title: String,
    description: String,
    deadline: DateTime,
    request_status: Int
  )


object TodoTable {

  val allColumn = (res: WrappedResultSet) => Todo(
    todo_id = res.long("todo_id"),
    todo_title = res.string("todo_title"),
    user_id = res.long("user_id"),
    description = res.stringOpt("description"),
    deadline = res.jodaDateTime("deadline"),
    progress_status = res.int("progress_status")
  )

  val userJoinColumn = (res: WrappedResultSet) => TodoResponse(
    todo_id = res.long("todo_id"),
    user_name = res.string("user_name"),
    todo_title = res.string("todo_title"),
    description = res.stringOpt("description"),
    deadline = res.jodaDateTime("deadline"),
    progress_status = res.int("progress_status"),
    request_status = res.int("request_status")
  )

}