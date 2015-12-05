package org.yaraneba

import akka.actor.Actor
import org.yaraneba.model.TodoModel
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
    path( "v1" / "todo" / "create" ) {
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
    path( "v1" / "todo" / "timeline" / IntNumber / IntNumber ) { (numb_of_articles, start_num) =>
      get {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
          validate(numb_of_articles > 0 && start_num > 0, s"Invalid Request!") {
            respondWithMediaType(`application/json`) {
              complete {
                val todos = "ddd"
                "" + todos
              }
            }
          }
        }
      }
    }
}