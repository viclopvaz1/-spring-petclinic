package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CrudCitas extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.ico""", """.*.js""", """.*.css"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_10 = Map(
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-WNS/10.0")

    val uri2 = "http://tile-service.weather.microsoft.com/es-ES/livetile/preinstall"

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
			.headers(headers_3)
			.formParam("username", "adiestrador1")
			.formParam("password", "adiestrador")
			.formParam("_csrf", "${stoken}"))
		.pause(6)
	}
object FindOwners {
		val findOwners = exec(http("FindOwners")
			.get("/owners/find")
			.headers(headers_0))
		.pause(16)
	}
object OwnerFind {
		val ownerFind = exec(http("OwnerFind")
		.get("/owners?lastName=franklin")
			.headers(headers_0))
		.pause(21)
	}

object CreateCita {
		val createCita = exec(http("CreateCita")
	.get("/citasAdiestramiento/new/1/1")
			.headers(headers_0))
		.pause(48).exec(http("CitaCreated")
		.post("/citasAdiestramiento/new/1/1")
			.headers(headers_3)
			.formParam("fechaInicio", "2020/05/30")
			.formParam("hora", "15:00")
			.formParam("duracion", "60")
			.formParam("precio", "50.0")
			.formParam("tipoAdiestramiento", "Adiestramiento deportivo")
			.formParam("_csrf", "cdd9d551-0334-46c2-93e8-e00e7be2d80b"))
		.pause(15)
	}

object EditCita {
		val editCita = exec(http("EditCita")
			.get("/citaAdiestramiento/6/edit/1/1")
			.headers(headers_0))
		.pause(15).exec(http("CitaUpdated")
				.post("/citaAdiestramiento/6/edit/1/1")
			.headers(headers_3)
			.formParam("fechaInicio", "2020/05/29")
			.formParam("hora", "15:00")
			.formParam("duracion", "60")
			.formParam("precio", "50.0")
			.formParam("tipoAdiestramiento", "Adiestramiento en obediencia basica")
			.formParam("_csrf", "cdd9d551-0334-46c2-93e8-e00e7be2d80b"))
		    .pause(4)
		    .exec(http("request_10")
			.get(uri2 + "?region=ES&appid=C98EA5B0842DBB9405BBF071E1DA76512D21FE36&FORM=Threshold")
			.headers(headers_10))
		.pause(52)
	}
object CitaDelete {
		val citaDelete = exec(http("CitaDelete")
			.get("/citaAdiestramiento/6/delete")
			.headers(headers_0))
		.pause(15)
	}


	val crudCitas = scenario("CrudCitas").exec(Home.home,Login.login,FindOwners.findOwners,
	OwnerFind.ownerFind,CreateCita.createCita, EditCita.editCita, CitaDelete.citaDelete)
		

	setUp(
		crudCitas.inject(rampUsers(5000) during (100 seconds))
	
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}