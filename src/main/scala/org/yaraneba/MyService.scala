package org.yaraneba

import akka.actor.Actor
import org.yaraneba.model.{TodoModel, TestModel}
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes._
import spray.routing._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class YaranebaServiceActor extends Actor with YaranebaService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(yaraneba)
}


// this trait defines our service behavior independently from the service actor
trait YaranebaService extends HttpService {

  val yaraneba =
    path( "v1" / "todos" ) {
      post {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          formFields('todo_title, 'user_name, 'description, 'deadline) {
            (todo_title: String, user_name: String, description: String, deadline: String) =>
            validate(todo_title.nonEmpty && user_name.nonEmpty && description.length <= 2048 && deadline.length <= 10, s"Invalid Request") {
              respondWithMediaType(`application/json`) {
                complete {
                  val todo = TodoModel.createTodo(todo_title, user_name, description, deadline)
                  "" + todo
                }
              }
            }
          }
        }
      }
    } ~
    path( "v1" / "todos" / IntNumber / IntNumber ) { (num_of_articles, start_num) =>
      get {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          validate(num_of_articles > 0 && start_num > 0, s"Invalid Request!") {
            respondWithMediaType(`application/json`) {
              complete {
                val todos = TodoModel.getTodosSpecifiedNumber(num_of_articles, start_num)
                "" + todos
              }
            }
          }
        }
      }
    } ~
    path( "v1" / "todos" / IntNumber / "progress" ) { todo_id =>
      put {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          formFields('progress_status.as[Int]) { progress_status =>
            validate(todo_id > 0 && (progress_status >= 1 &&  progress_status <= 3), s"Invalid Request!") {
              respondWithMediaType(`application/json`) {
                complete {
                  val todo = TodoModel.changeProgressStatus(todo_id, progress_status)
                  "" + todo
                }
              }
            }
          }
        }
      }
    } ~
    path( "v1" / "todos" / IntNumber ) { todo_id =>
      delete {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          validate(todo_id > 0, s"Invalid Request!") {
            respondWithMediaType(`application/json`) {
              complete {
                val todo = TodoModel.deleteTodo(todo_id)
                "" + todo
              }
            }
          }
        }
      }
    } ~
    path( "v1" / "todos" / IntNumber / "contents" ) { todo_id =>
      put {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          formFields('todo_title, 'description, 'deadline) { (todo_title: String, description: String, deadline: String) =>
            validate(todo_id > 0, s"Invalid Request!") {
              respondWithMediaType(`application/json`) {
                complete {
                  val todo = TodoModel.changeTodoContents(todo_id, todo_title, description, deadline)
                  "" + todo
                }
              }
            }
          }
        }
      }
    } ~
    path( "v1" / "test" / "articles" ) {
      get {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          respondWithMediaType(`application/json`) {
            complete {
              // val todo = TodoModel.changeTodoContents(todo_id, todo_title, description, deadline)
              val todo = TestModel.getArtiles()
              "" + todo
            }
          }
        }
      }
    }
}
