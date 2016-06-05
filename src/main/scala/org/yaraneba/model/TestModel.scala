package org.yaraneba.model

import org.yaraneba.model.table._
import play.api.libs.json.Json
import play.api.libs.json.{JsValue, Json}
import org.joda.time.DateTime

case class Article(
  articleId: Int,
  siteName: String,
  title: String,
  articleUrl: String,
  date: String
)

object TestModel {

  def getArtiles() = {
    implicit def jsonWrites = Json.writes[Article]
    implicit def jsonReads  = Json.reads[Article]

    val articles: List[Article] = List(
      Article(64, "ジャニ速V", "【速報】ジュニアboysが歌う「勇気100％」がCD化される模様", "http://janinare.blog.fc2.com/blog-entry-1239.html", "2016-06-04 23:21:26"),
      Article(65, "ジャニーズ通信", "ジャニーズ出演情報6月1日（水）", "http://ticketcamp.net/johnnys-blog/johnnys-tickets-0601", "2016-06-04 22:46:54"),
      Article(66, "Jnews1", "6/10放送「タモリ倶楽部」に堂本剛出演！みうらじゅんのナイブームを振り返る", "http://jnews1.com/archives/52734145.html", "2016-06-03 21:11:36"),
      Article(67, "ITmediaニュース", "【SMAP解散騒動　ジャニーズ事務所・メリー喜多川副社長の解任求める署名始まる", "http://www.itmedia.co.jp/news/articles/1601/20/news139.html", "2016-01-17 23:21:26"),
      Article(68, "朝日新聞デジタル", "『THE MUSIC DAY』出演者発表", "http://www.asahi.com/and_w/interest/entertainment/CORI2072783.html", "2016-06-04 23:21:26")
    )

    Json.toJson(articles)
  }

}
