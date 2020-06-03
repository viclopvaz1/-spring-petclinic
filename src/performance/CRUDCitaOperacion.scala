package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CRUDCitaOperacion extends Simulation {

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
		.pause(11)
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

	object FranklinFind {
		val franklinFind = exec(http("Franklin Find")
			.get("/owners/find")
			.headers(headers_0))
		.pause(4)
		.exec(http("Franklin Show")
			.get("/owners?lastName=Franklin")
			.headers(headers_0))
		.pause(23)
	}

	object CreatedCitaOperacion {
		val createdCitaOperacion = exec(http("Boton Create")
			.get("/citasOperaciones/new/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(23)
		.exec(http("CreatedCitaOperacion")
			.post("/citasOperaciones/new/1")
			.headers(headers_3)
			.formParam("fechaInicio", "2020/05/30")
			.formParam("hora", "11:00")
			.formParam("duracion", "300")
			.formParam("precio", "100")
			.formParam("tipoOperacion", "Cirugia dental")
			.formParam("cantidadPersonal", "2")
			.formParam("_csrf", "${stoken}"))
		.pause(20)
	}

	object UpdateCitaOperacion {
		val updateCitaOperacion = exec(http("Update boton")
			.get("/citaOperacion/6/edit/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8)
		.exec(http("Update Cita Operacion")
			.post("/citaOperacion/6/edit/1")
			.headers(headers_3)
			.formParam("fechaInicio", "2020/05/30")
			.formParam("hora", "11:00")
			.formParam("duracion", "30")
			.formParam("precio", "100.0")
			.formParam("tipoOperacion", "Cirugia de emergencia")
			.formParam("cantidadPersonal", "2.0")
			.formParam("_csrf", "${stoken}"))
		.pause(29)
	}

	object DeleteCitaOperacion {
		val deleteCitaOperacion = exec(http("Delete Cita Operacion")
			.get("/citaOperacion/6/delete")
			.headers(headers_0))
		.pause(10)
	}

	val vetsScn = scenario("Vets").exec(Home.home,
									  Login.login,
									  FranklinFind.franklinFind,
									  CreatedCitaOperacion.createdCitaOperacion,
									  UpdateCitaOperacion.updateCitaOperacion,
									  DeleteCitaOperacion.deleteCitaOperacion)
		
		// Home
		
		// Login

		// Franklin Show
		
		// CreatedCitaOperacion
		
		// Update Cita Operacion
		
		// Delete Cita Operacion

	//setUp(
//		vetsScn.inject(atOnceUsers(1)))
//		.protocols(httpProtocol)

	setUp(
			vetsScn.inject(rampUsers(8000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),    
			global.responseTime.mean.lt(1500),
			global.successfulRequests.percent.gt(95)
		)

}