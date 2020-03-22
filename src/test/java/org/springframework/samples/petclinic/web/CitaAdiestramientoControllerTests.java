package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = CitaAdiestramientoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

class CitaAdiestramientoControllerTests {

//	private static final int TEST_CITA_OPERACION_ID = 1;

	@Autowired
	private CitaAdiestramientoController CitaAdiestramientoController;

	@MockBean
	private CitaAdiestramientoService clinicService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		CitaAdiestramiento cita = new CitaAdiestramiento();
		cita.setDuracion(45);
		cita.setId(2);
		cita.setPrecio(75.0);
		CitaAdiestramiento cita2 = new CitaAdiestramiento();
		cita.setDuracion(55);
		cita.setId(3);
		cita.setPrecio(85.0);
		TipoAdiestramiento to = new TipoAdiestramiento();
		to.setId(2);
		
		given(this.clinicService.findAll()).willReturn(Lists.newArrayList(cita, cita2));

//		(TEST_CITA_OPERACION_ID)).willReturn(george);

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowCitasOperacion() throws Exception {
		mockMvc.perform(get("/citasAdiestramiento/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("citasAdiestramiento"))
				.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramiento"));
   
	}

}