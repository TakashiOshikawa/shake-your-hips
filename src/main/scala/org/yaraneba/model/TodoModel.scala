package org.yaraneba.model

import org.yaraneba.model.DAO.{TodoDAO, UserDAO}
import org.yaraneba.model.table.{InsertTodoResponse, TodoTable, User}
import play.api.libs.json.{JsValue, Json}

/**
 * Created by oshikawatakashi on 2015/12/05.
 */
object TodoModel extends TodoTable {


  /*
  'todo_title, 'user_name, 'description, 'deadline
   */
  def createTodo(todo_title: String, posted_user_name: String, description: String, deadline: String): JsValue = {
    // TODO user_nameからusersテーブルにユーザが存在するか判定
    val user = UserDAO.isExistUserID(posted_user_name)
    val user_id = user match {
      case List(User(_,_), _*) => user.headOption.get.user_id
      case _              => UserDAO.createUser(posted_user_name).user_id
    }

    val todo = TodoDAO.insertTodo(user_id, todo_title, Option(description), deadline)

    val todo_json = Json.toJson( InsertTodoResponse(todo.todo_id, user.headOption.get.user_name, todo_title, Option(description), todo.deadline, todo.progres_status, 200) )
    todo_json

  }


}
