package org.yaraneba.model

import org.yaraneba.model.DAO.{TodoDAO, UserDAO}
import org.yaraneba.model.table.{TodoUpdateResponse, TodoResponse, TodoTable, User}
import play.api.libs.json.{JsValue, Json}

/**
 * Created by oshikawatakashi on 2015/12/05.
 */
object TodoModel extends TodoTable {


  /**
   *  POSTされてきた値からTODOを作成するメソッド
   *
   *  @param todo_title        TODOに付けるタイトル名
   *  @param posted_user_name  TODOを作成したユーザのニックネーム(ログイン機能はないので誰でも好きな名前で投稿できる)
   *  @param description       TODOの説明文 付けるか付けないかはユーザの任意
   *  @param deadline          TODOの締め切り期限 POST値で受け取る形式は YYYY-MM-DD
   *
   *  @return                  JSON化されたTODOのcase class 参照はこちら -> org.yaraneba.model.table.TodoResponse
   */
  def createTodo(todo_title: String, posted_user_name: String, description: String, deadline: String): JsValue = {

    // user_nameからusersテーブルにユーザを取得
    val user = UserDAO.findUserByUserName(posted_user_name)

    // userデータが取得できなかった場合入力されたユーザ名でユーザを作成
    val user_id = user match {
      case List(User(_,_), _*) => user.headOption.get.user_id
      case _                   => UserDAO.createUser(posted_user_name).user_id
    }

    val todo = TodoDAO.insertTodo(user_id, todo_title, Option(description), deadline)

    val todo_json = Json.toJson(
      TodoResponse(
        todo.todo_id,
        user.headOption.get.user_name,
        todo_title,
        Option(description),
        todo.deadline,
        todo.progress_status,
        200
      )
    )

    todo_json
  }



  /**
   *  TODOを投稿順に指定件数づつ取得するメソッド
   *
   *  @param num_of_articles  取得したいTODOの件数 取得側は普通この値は固定する
   *  @param start_num        TODOの取得を開始する位置 1〜20まで取得した後はこの値は21になる
   *
   *  @return                 JSON化されたListのTODOのcase class ListTodos
   */
  def getTodosSpecifiedNumber(num_of_articles: Long, start_num: Long): JsValue = {

    val ls_todos: List[TodoResponse] = TodoDAO.selectTodosBySpecifiedNumber(num_of_articles, start_num)

    val ls_todos_json: JsValue = Json.toJson( ls_todos )

    ls_todos_json
  }


  /**
   *  TODOの進行状況を更新するメソッド
   *
   *  @param todo_id          状態を変更したいTODOのID todosテーブルのtodo_id
   *  @param progress_status  変更したい状態 ( 1: 未着手, 2: 進行中, 3: 完了)
   *
   *  @return                 JSON化されたTODOのcase class
   */
  def changeProgressStatus(todo_id: Long, progress_status: Int): JsValue = {

    val updated_todo: TodoUpdateResponse = TodoDAO.updateProgressStatus(todo_id, progress_status)

    val updated_todo_json: JsValue = Json.toJson( updated_todo )

    updated_todo_json
  }


}
