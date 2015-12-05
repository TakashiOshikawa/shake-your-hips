package org.yaraneba.model.DAO


import org.joda.time.DateTime
import org.yaraneba.model.DBAccess
import org.yaraneba.model.table._
import scalikejdbc._


/**
 * Created by oshikawatakashi on 2015/12/05.
 */

object TodoDAO extends DBAccess {


  /*
   * 'todo_title, 'user_name, 'description, 'deadline
   * TodoDAO.insertTodo(user_id, todo_title, Option(description), DateTime.parse(deadline))
   */
  def insertTodo(user_id: Long, todo_title: String, description: Option[String], deadline: String): Todo = {
    DB localTx { implicit session =>
      val todo_id =
        sql"INSERT INTO todos (todo_title, user_id, description, deadline) values (${todo_title}, ${user_id}, ${description.getOrElse("")}, ${deadline})"
          .updateAndReturnGeneratedKey.apply()

      Todo(todo_id, todo_title, user_id, description, DateTime.parse(deadline), 1)
    }
  }


}
