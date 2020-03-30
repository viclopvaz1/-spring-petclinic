package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
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

@WebMvcTest(controllers = DonacionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class DonacionControllerTests {

	@Autowired
	private DonacionController donacionController;

	@MockBean
	private DonacionService donacionService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private CausaService causaService;

	@MockBean
	private VetService vetService;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private AdiestradorService adiestradorService;

	@MockBean
	private AuthoritiesService authoritiesService;

	private static final int TEST_CAUSA_ID = 1;

	@BeforeEach
	void setup() {

		Donacion donacion = new Donacion();

		User user = new User();
		user.setUsername("ksldjslk");
		user.setPassword("fsdgkjdf");

		donacion.setId(1);

		Causa causa = new Causa();
		causa.setId(TEST_CAUSA_ID);
		causa.setDineroRecaudado(10000);
		List<Donacion> donaciones = new ArrayList<Donacion>();
		donaciones.add(donacion);
		causa.setDonaciones(donaciones);
		causa.setObjetivo(100);
		causa.setValido(true);

		donacion.setCantidad(100);
		donacion.setCausa(causa);
		donacion.setUser(user);

		BDDMockito.given(this.donacionService.findById(TEST_CAUSA_ID)).willReturn(donacion);
		BDDMockito.given(this.causaService.findCausaById(TEST_CAUSA_ID)).willReturn(causa);

	}

	@WithMockUser(value = "spring")
	@Test
	void testNewDonacionHtml() throws Exception {
		mockMvc.perform(get("/donacion/{causaId}/new", TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(model().attributeExists("donacion"))
				.andExpect(MockMvcResultMatchers.view().name("donaciones/createOrUpdateDonacion"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotExistNewDonacionHtml() throws Exception {
		mockMvc.perform(get("/donacion/{causaId}/new", 151)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(model().attributeDoesNotExist("donacion"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}


}
