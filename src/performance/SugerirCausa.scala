package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class SugerirCausa extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.png""", """.*.css""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,la;q=0.7")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")



	object Home {
		val home = exec(http("Home")
				.get("/")
				.headers(headers_0))
				.pause(4)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
//			.resources(http("request_2"))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(12)
		.exec(http("Logged")
			.post("/login")
//			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(100)
	}

	object CrearCausaOwner {
		val crearCausaOwner = exec(http("crearCausaForm")
			.get("/causa/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(25)
		.exec(http("crearCausaOwner")
			.post("/causa/new")
			.headers(headers_2)
			.formParam("fechaInicio", "2020/08/08")
			.formParam("fechaFin", "2020/09/09")
			.formParam("ong", "Mi mascota Owner1")
			.formParam("objetivo", "1000")
			.formParam("dineroRecaudado", "0")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	val ownerScn = scenario("Owner").exec(Home.home,
									Login.login,
									CrearCausaOwner.crearCausaOwner)

	setUp(
			ownerScn.inject(rampUsers(20000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),    
			global.responseTime.mean.lt(1500),
			global.successfulRequests.percent.gt(95)
		)
}