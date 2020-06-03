package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ListTypePet extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.ico""", """.*.js""", """.*.css"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}
object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
//			.resources(http("request_2"))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("Logged")
			.post("/login")
			//.headers(headers_3)
			.formParam("username", "adiestrador1")
			.formParam("password", "adiestrador")
			.formParam("_csrf", "${stoken}"))
		.pause(6)
	}
	

object FindType {
		val findType = exec(http("request_0")
			.get("/citasAdiestramiento/find")
			.headers(headers_0))
		.pause(10)
	}
	object CitaType {
		val citaType = exec(http("CitaType")
			.get("/citasAdiestramiento?pet.type.name=cat")
			.headers(headers_0))
		.pause(21)
	}
	val list = scenario("ListTypePet").exec(Home.home,Login.login,FindType.findType,CitaType.citaType)
	
	setUp(
		list.inject(rampUsers(8000) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1500),
        global.successfulRequests.percent.gt(95)
     )
}