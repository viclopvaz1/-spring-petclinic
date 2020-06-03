package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CausaCrud extends Simulation {

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
			.headers(headers_2)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "${stoken}"))
		.pause(100)
	}

	object CrearCausa {
		val crearCausa =exec(http("crearCausa")
			.get("/causa/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(51)
	.exec(http("createCausa")
			.post("/causa/new")
			.headers(headers_2)
			.formParam("fechaInicio", "2020/07/05")
			.formParam("fechaFin", "2020/08/05")
			.formParam("ong", "Mi Lola")
			.formParam("objetivo", "100")
			.formParam("dineroRecaudado", "0")
			.formParam("_csrf", "${stoken}"))
		.pause(19)
	}
	object EditCausa {
		val editCausa =exec(http("editCausa")
			.get("/causa/4/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(23)
	.exec(http("updateCausa")
			.post("/causa/4/edit")
			.headers(headers_2)
			.formParam("fechaInicio", "2020/07/05")
			.formParam("fechaFin", "2020/08/05")
			.formParam("ong", "Mi Lola")
			.formParam("objetivo", "1000")
			.formParam("dineroRecaudado", "0")
			.formParam("valido", "true")
			.formParam("_csrf", "${stoken}"))
		.pause(10)
	}
	object DeleteCausa {
		val deleteCausa =exec(http("deleteCausa")
			.get("/causa/4/delete")
			.headers(headers_0))
		.pause(5)
	}
	val vetScn = scenario("Vet").exec(Home.home,
									Login.login,
									CrearCausa.crearCausa,
									EditCausa.editCausa,
									DeleteCausa.deleteCausa)
		setUp(
		vetScn.inject(rampUsers(5000) during (10 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95)
		)
}