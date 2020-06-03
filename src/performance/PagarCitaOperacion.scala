package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PagarCitaOperacion extends Simulation {

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
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
	}

  	object Login {
    	val login = exec(
    	  http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(20)
		.exec(
		http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")        
			.formParam("_csrf", "${stoken}")
		).pause(142)
	}

	object FranklinShow {
			val franklinShow = exec(http("FindOwnerForm")
			.get("/owners/find")
			.headers(headers_0))
		.pause(3)
		.exec(http("FranklinShow")
			.get("/owners?lastName=Franklin")
			.headers(headers_0))
		.pause(14)
	}

	object CitasOperaciones {
			val citasOperaciones = 	exec(http("CitasOperaciones")
			.get("/citasOperacionesPet/1")
			.headers(headers_0))
		.pause(13)
	}

	object PayCitaOperaciones {
			val payCitaOperaciones = exec(http("Pay")
			.get("/citaOperacion/3/pay")
			.headers(headers_0))
		.pause(8)
	}

	val ownersScn = scenario("Owners").exec(Home.home,
									  Login.login,
									  FranklinShow.franklinShow,
									  CitasOperaciones.citasOperaciones,
									  PayCitaOperaciones.payCitaOperaciones)
		
		// Home
		
		// Login
		
		// FranklinShow

		// CitasOperaciones
		
		// PayCitaOperaciones

	//setUp(ownersScn.inject(atOnceUsers(1))).protocols(httpProtocol)

	setUp(
			ownersScn.inject(rampUsers(20000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),    
			global.responseTime.mean.lt(1500),
			global.successfulRequests.percent.gt(95)
		)
}