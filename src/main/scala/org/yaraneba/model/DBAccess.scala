package org.yaraneba.model

import scalikejdbc.ConnectionPool

/**
 * Created by oshikawatakashi on 2015/12/05.
 */

trait DBAccess {

  //DB接続設定
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost/yaraneba", "scaler", "scaler")

}