package org.yaraneba.model.DAO


import org.joda.time.DateTime
import org.yaraneba.model.DBAccess
import org.yaraneba.model.table._
import scalikejdbc._


/**
 * Created by oshikawatakashi on 2015/12/05.
 */

object TodoDAO extends DBAccess {


  /**
   *  POSTされた値をtodosテーブルにINSERTするメソッド
   *
   *  @param user_id      usersテーブルのuser_id
   *  @param todo_title   TODOのタイトル名
   *  @param description  TODOの説明文 付けるか付けないかはユーザの任意
   *  @param deadline     TODOの締め切り期限 POST値で受け取る形式は YYYY-MM-DD
   *
   *  @return             TODOのcase class
   */
  def insertTodo(user_id: Long, todo_title: String, description: Option[String], deadline: String): Todo = {

    DB localTx { implicit session =>
      val todo_id =
        sql"INSERT INTO todos (todo_title, user_id, description, deadline) values (${todo_title}, ${user_id}, ${description.getOrElse("")}, ${deadline})"
          .updateAndReturnGeneratedKey.apply()

      Todo(todo_id, todo_title, user_id, description, DateTime.parse(deadline), 1)
    }
  }


  /**
   *  TODOを投稿順に指定件数づつSELECTするメソッド
   *
   *  @param num_of_articles  TODOを取得する件数
   *  @param start_num        TODOを取得する開始位置 1〜20まで取得した後はこの値は21になる
   *
   *  @return
   */
  def selectTodosBySpecifiedNumber(num_of_articles: Long, start_num: Long): List[TodoResponse] = {

    val todos = DB readOnly { implicit session =>
      sql"SELECT todo_id, user_name, todo_title, description, deadline, progress_status, 200 as request_status FROM todos LEFT JOIN users USING (user_id) ORDER BY todo_id DESC LIMIT ${start_num-1}, ${num_of_articles}"
        .map(TodoTable.userJoinColumn).list.apply()
    }
    todos

  }


  /**
   *  TODOの状態をUPDATEするメソッド
   *
   *  @param todo_id          進捗状況を更新したいTODOのID
   *  @param progress_status  TODOの進捗状態
   *
   *  @return                 TODOのIDとstatus codeを持つcase class
   */
  def updateProgressStatus(todo_id: Long, progress_status: Int): TodoModifyResponse = {

    DB localTx { implicit session =>
      val update_todo_id =
        sql"UPDATE todos SET progress_status = ${progress_status} WHERE todo_id = ${todo_id}"
          .update().apply()

      TodoModifyResponse(todo_id, 200)
    }

  }


  /**
   *   TODOをtodosテーブルから削除するメソッド
   *
   *   @param todo_id  削除したいTODOのID
   *
   *   @return         TODOのIDとstatus codeを持つcase class
   */
  def deleteTodoByTodoID(todo_id: Long): TodoModifyResponse = {

    DB localTx { implicit session =>
      val update_todo_id =
        sql"DELETE FROM todos WHERE todo_id = ${todo_id}"
          .update().apply()

      TodoModifyResponse(todo_id, 200)
    }

  }



}
