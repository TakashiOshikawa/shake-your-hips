package com.example

import org.specs2.mutable.Specification
import org.yaraneba.YaranebaService
import spray.testkit.Specs2RouteTest

class MyServiceSpec extends Specification with Specs2RouteTest with YaranebaService {
  def actorRefFactory = system
  
//  "MyService" should {
//
//    "return a greeting for GET requests to the root path" in {
//      Get() ~> yaraneba ~> check {
//        responseAs[String] must contain("Say hello")
//      }
//    }
//
//    "leave GET requests to other paths unhandled" in {
//      Get("/kermit") ~> yaraneba ~> check {
//        handled must beFalse
//      }
//    }
//
//    "return a MethodNotAllowed error for PUT requests to the root path" in {
//      Put() ~> sealRoute(yaraneba) ~> check {
//        status === MethodNotAllowed
//        responseAs[String] === "HTTP method not allowed, supported methods: GET"
//      }
//    }
//  }
}
