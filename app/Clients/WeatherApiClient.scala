package Clients

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import play.api.libs.json._
import play.api.Configuration

import javax.inject.Inject
import scala.io.Source

class WeatherApiClient @Inject()(config: Configuration){
  val baseUrl: String = config.get[String]("baseUrl")
  val apiKey: String = config.get[String]("apiKey")

  def getWeather(location: String) = {
    val httpClient = HttpClients.createDefault()
    val url = s"$baseUrl?q=$location&APPID=$apiKey"
    val request = new HttpGet(url)

    val response = httpClient.execute(request)
    val entity = response.getEntity

    try {
      val inputStream = entity.getContent
      val content = Source.fromInputStream(inputStream).mkString
      Json.parse(content)
    } finally {
      response.close()
    }
  }
}