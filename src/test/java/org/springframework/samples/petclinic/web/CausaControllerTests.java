
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link CausaController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = CausaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class CausaControllerTests {


	private static final int	TEST_CAUSA_ID			= 1;

	private static final int	TEST_CAUSA_NO_VALIDA_ID	= 2;

	private static final int	TEST_OWNER_ID			= 1;

	private static final int	TEST_VET_ID				= 1;

	private static final int	TEST_ADIESTRADOR_ID		= 1;


	@Autowired
	private CausaController		causaController;

	@MockBean
	private CausaService		causaService;

	@MockBean
	private UserService			userService;

	@MockBean
	private DonacionService		donacionService;

	@MockBean
	private OwnerService		ownerService;

	@MockBean
	private VetService			vetService;

	@MockBean
	private AdiestradorService	adiestradorService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Causa				causa;

	private Causa				causaNoValida;

	private Authorities			authorities1;

	private Authorities			authorities2;

	private Authorities			authorities3;

	private User				user1;

	private User				user2;

	private User				user3;

	private Owner				owner;

	private Vet					vet;

	private Adiestrador			adiestrador;

	private Donacion			donacion;


	@BeforeEach
	void setup() {
		this.causa = new Causa();
		this.causaNoValida = new Causa();

		this.user1 = new User();
		this.user1.setUsername("Mary");
		this.user1.setPassword("12345");
		this.user1.setEnabled(true);

		this.user2 = new User();
		this.user2.setUsername("Jon");
		this.user2.setPassword("12345");
		this.user2.setEnabled(true);

		this.user3 = new User();
		this.user3.setUsername("George");
		this.user3.setPassword("12345");
		this.user3.setEnabled(true);

		this.authorities1 = new Authorities();
		this.authorities1.setAuthority("veterinarian");
		this.authorities1.setUsername("Mary");

		this.authorities2 = new Authorities();
		this.authorities2.setAuthority("adiestrador");
		this.authorities2.setUsername("Jon");

		this.authorities3 = new Authorities();
		this.authorities3.setAuthority("owner");
		this.authorities3.setUsername("George");

		this.vet = new Vet();
		this.vet.setFirstName("Mary");
		this.vet.setLastName("Carter");
		this.vet.setId(CausaControllerTests.TEST_VET_ID);
		this.vet.setUser(this.user1);

		this.adiestrador = new Adiestrador();
		this.adiestrador.setFirstName("jon");
		this.adiestrador.setLastName("Carter");
		this.adiestrador.setId(CausaControllerTests.TEST_ADIESTRADOR_ID);
		this.adiestrador.setEstrellas(5);
		this.adiestrador.setMonedero(1000);
		this.adiestrador.setUser(this.user2);

		this.owner = new Owner();
		this.owner.setId(CausaControllerTests.TEST_OWNER_ID);
		this.owner.setFirstName("George");
		this.owner.setLastName("Franklin");
		this.owner.setAddress("110 W. Liberty St.");
		this.owner.setCity("Madison");
		this.owner.setTelephone("6085551023");
		this.owner.setMonedero(1000);
		this.owner.setUser(this.user3);

		this.donacion = new Donacion();
		this.donacion.setId(1);
		this.donacion.setCantidad(120);
		this.donacion.setCausa(this.causa);
		this.donacion.setUser(this.user1);

		List<Donacion> donaciones = new ArrayList<Donacion>();
		LocalDate fechaFin = LocalDate.parse("2020-05-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate fechaInicio = LocalDate.parse("2020-01-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		donaciones.add(this.donacion);
		this.causa.setId(CausaControllerTests.TEST_CAUSA_ID);
		this.causa.setDineroRecaudado(120);
		this.causa.setDonaciones(donaciones);
		this.causa.setFechaFin(fechaFin);
		this.causa.setFechaInicio(fechaInicio);
		this.causa.setObjetivo(1200);
		this.causa.setOng("ONG");
		this.causa.setValido(true);


		this.causaNoValida.setId(CausaControllerTests.TEST_CAUSA_NO_VALIDA_ID);
		this.causaNoValida.setDineroRecaudado(120);
		this.causaNoValida.setFechaFin(fechaFin);
		this.causaNoValida.setFechaInicio(fechaInicio);
		this.causaNoValida.setObjetivo(1200);
		this.causaNoValida.setOng("ONG2");
		this.causaNoValida.setValido(false);

		List<Causa> causasAll = new ArrayList<Causa>();
		causasAll.add(this.causa);
		causasAll.add(this.causaNoValida);

		List<Causa> causasNoValidas = new ArrayList<Causa>();
		causasNoValidas.add(this.causaNoValida);

		BDDMockito.given(this.causaService.findCausaById(CausaControllerTests.TEST_CAUSA_ID)).willReturn(this.causa);
		BDDMockito.given(this.causaService.findCausaByUsername(this.user1.getUsername())).willReturn(Lists.newArrayList(this.causa));
		BDDMockito.given(this.authoritiesService.findAll()).willReturn(Lists.newArrayList(this.authorities1, this.authorities2, this.authorities3));
		BDDMockito.given(this.causaService.findAll()).willReturn(causasAll);
		BDDMockito.given(this.causaService.findCausaById(CausaControllerTests.TEST_CAUSA_ID)).willReturn(this.causa);
		BDDMockito.given(this.causaService.findCausaByValido()).willReturn(causasNoValidas);
		BDDMockito.given(this.ownerService.findOwnerById(CausaControllerTests.TEST_OWNER_ID)).willReturn(this.owner);

	}


	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causa"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/05/11").param("fechaInicio", "2020/01/11").param("ong", "ONG").param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	// Los siguientes errores comprueban que ningun atributo, excepto valido, pueden ser nulos al crear la causa
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsInFechaFin() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaInicio", "2020/01/11").param("ong", "ONG").param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "fechaFin"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}



	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsInFechaInicio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("ong", "ONG").param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "fechaInicio"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsInOng() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "ong"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsInObjetivo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "objetivo"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsInDineroRecaudado() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("objetivo", "1200").param("ong", "ONG"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "dineroRecaudado"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	//Prueba que no se pueden insertar unas fechas en las que la de inicio es anterior a la de final
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorInFechas() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/03/11").param("ong", "ONG").param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	//Pureba que el obtivo de diero a reacaudar no puede ser menor que el dinero ya recaudado
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorInObjetivoMenorARecaudado() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG").param("objetivo", "120").param("dineroRecaudado", "130"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorInObjetivoIgualARecaudado() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG").param("objetivo", "120").param("dineroRecaudado", "120"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasNotErrorInObjetivo() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/causa/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG").param("objetivo", "120").param("dineroRecaudado", "119"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	//preguntar al profe por esta
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testProcessFindFormByLastName() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/propias")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.model().attributeExists("causas"))
	//			.andExpect(MockMvcResultMatchers.view().name("causas/listadoCausas"));
	//	}
	//
	@WithMockUser(value = "spring")
	@Test
	void testListadoCausas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causas")).andExpect(MockMvcResultMatchers.view().name("causas/listadoCausas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdeteForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causa"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/06/11").param("fechaInicio", "2020/01/10").param("ong", "ONG2")
			.param("objetivo", "12000").param("dineroRecaudado", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/causa/" + CausaControllerTests.TEST_CAUSA_ID));
		;
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorsInFechaFin() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaInicio", "2020/01/11").param("ong", "ONG").param("objetivo", "1200")
				.param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "fechaFin"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorsInFechaInicio() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("ong", "ONG").param("objetivo", "1200")
				.param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "fechaInicio"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorsInOng() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("objetivo", "1200")
				.param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "ong"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorsInObjetivo() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG")
				.param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "objetivo"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorsInDineroRecaudado() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("objetivo", "1200")
				.param("ong", "ONG"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "dineroRecaudado"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	//Prueba que no se pueden actualizar unas fechas a otras en las que la de inicio es anterior a la de final
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorInFechas() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/12").param("ong", "ONG")
				.param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasNotErrorInFechas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/10").param("ong", "ONG")
			.param("objetivo", "1200").param("dineroRecaudado", "0")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/causa/" + CausaControllerTests.TEST_CAUSA_ID));
	}

	//Prueba que el obtivo de dinero a reacaudar no puede ser menor o igual que el dinero ya recaudado al actualizar una causa, pero si que pueda ser mayor en un valor de 1
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorInObjetivoMenorARecaudado() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG")
				.param("objetivo", "120").param("dineroRecaudado", "130"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrorInObjetivoIgualARecaudado() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG")
				.param("objetivo", "120").param("dineroRecaudado", "120"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasNotErrorInObjetivo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/01/11").param("fechaInicio", "2020/01/05").param("ong", "ONG")
			.param("objetivo", "120").param("dineroRecaudado", "119")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/causa/" + CausaControllerTests.TEST_CAUSA_ID));
	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testShowCausa() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/{id}", CausaControllerTests.TEST_CAUSA_ID))
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.model().attributeExists("causa"))
//			.andExpect(MockMvcResultMatchers.view().name("causas/causaDetails"));
//	}

	@WithMockUser(value = "spring")
	@Test
	void testListadoCausasNoValidas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/noValidas", CausaControllerTests.TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causas"))
			.andExpect(MockMvcResultMatchers.view().name("causas/listadoCausas"));
	}
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testProcessFindFormNoOwnersFound() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", "Unknown Surname")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "lastName"))
	//			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("owner", "lastName", "notFound")).andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
	//	}
	//
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testInitUpdateOwnerForm() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/edit", TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
	//			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is("George"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is("110 W. Liberty St.")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is("Madison"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is("6085551023")))).andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	//	}
	//
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testProcessUpdateOwnerFormSuccess() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("address", "123 Caramel Street")
	//			.param("city", "London").param("telephone", "01616291589")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"));
	//	}
	//
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testProcessUpdateOwnerFormHasErrors() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London"))
	//			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "address"))
	//			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "telephone")).andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	//	}
	//
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testShowOwner() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}", TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
	//			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is("George"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is("110 W. Liberty St.")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is("Madison"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is("6085551023")))).andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"));
	//	}

}
