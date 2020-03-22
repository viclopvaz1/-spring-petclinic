
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
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

	private static final int	TEST_CAUSA_ID	= 1;

	@Autowired
	private CausaController		causaController;

	@MockBean
	private CausaService		causaService;

	@MockBean
	private UserService			userService;

	@MockBean
	private OwnerService		ownerService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Causa				causa;

	private User				user;


	@BeforeEach
	void setup() {
		List<Donacion> donaciones = new ArrayList<Donacion>();
		LocalDate fechaFin = LocalDate.parse("2020-05-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate fechaInicio = LocalDate.parse("2020-01-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.causa = new Causa();
		this.user = new User();
		this.user.setUsername("paco");
		this.user.setPassword("12345");
		this.user.setEnabled(true);
		Donacion donacion = new Donacion();
		donacion.setId(1);
		donacion.setCantidad(120);
		donacion.setCausa(this.causa);
		donacion.setUser(this.user);
		donaciones.add(donacion);
		this.causa.setId(1);
		this.causa.setDineroRecaudado(120);
		this.causa.setDonaciones(donaciones);
		this.causa.setFechaFin(fechaFin);
		this.causa.setFechaInicio(fechaInicio);
		this.causa.setObjetivo(1200);
		this.causa.setOng("ONG");
		this.causa.setValido(true);
		BDDMockito.given(this.causaService.findCausaById(CausaControllerTests.TEST_CAUSA_ID)).willReturn(this.causa);
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
			.perform(MockMvcRequestBuilders.post("/causa/new").param("fechaFin", "2020/05/11").param("fechaInicio", "2020/01/11").with(SecurityMockMvcRequestPostProcessors.csrf()).param("ong", "ONG").param("objetivo", "1200").param("dineroRecaudado", "0"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//		@WithMockUser(value = "spring")
	//		@Test
	//		void testProcessCreationFormHasErrors() throws Exception {
	//			this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London")).andExpect(MockMvcResultMatchers.status().isOk())
	//				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "address")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "telephone"))
	//				.andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	//		}
	//
	//		@WithMockUser(value = "spring")
	//		@Test
	//		void testInitFindForm() throws Exception {
	//			this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner")).andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
	//		}
	//
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testProcessFindFormSuccess() throws Exception {
	//		BDDMockito.given(this.clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(this.causa, new Owner()));
	//
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("owners/ownersList"));
	//	}
	//
	//	@WithMockUser(value = "spring")
	//	@Test
	//	void testProcessFindFormByLastName() throws Exception {
	//		BDDMockito.given(this.clinicService.findOwnerByLastName(this.causa.getLastName())).willReturn(Lists.newArrayList(this.causa));
	//
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", "Franklin")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + TEST_OWNER_ID));
	//	}
	//
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
