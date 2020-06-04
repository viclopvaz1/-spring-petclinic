package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FindCitaOperacionByTipoOperacion extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(16)
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
			.headers(headers_3)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "${stoken}"))
		.pause(100)
	}

	object FindCitaByTipoOperacion {
		val findCitaByTipoOperacion = exec(http("FindCitaByTipoOperacionForm")
			.get("/citasOperaciones/find")
			.headers(headers_0))
		.pause(5)
		.exec(http("FindOneCitaByTipoOperacion")
			.get("/citasOperaciones?tipoOperacion.name=Cirugia+basica")
			.headers(headers_0))
		.pause(30)
	}

	object FindCitaByTipoOperacion2 {
		val findCitaByTipoOperacion2 = exec(http("FindCitaByTipoOperacionForm")
			.get("/citasOperaciones/find")
			.headers(headers_0))
		.pause(5)
		.exec(http("FindListCitaByTipoOperacion")
			.get("/citasOperaciones?tipoOperacion.name=")
			.headers(headers_0))
		.pause(30)
	}

	val citasScn = scenario("citas").exec(Home.home,
											Login.login,
											FindCitaByTipoOperacion.findCitaByTipoOperacion,
											FindCitaByTipoOperacion2.findCitaByTipoOperacion2)

	setUp(
		citasScn.inject(rampUsers(15000) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1500),
        global.successfulRequests.percent.gt(95)
     )

}