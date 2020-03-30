package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdiestradorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdiestradorControllerTests {

	@Autowired
	private AdiestradorController adiestradorController;

	@MockBean
	private AdiestradorService adiestradorService;

	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    private UserService userService;
    
    @MockBean
    private AuthoritiesService authoritiesService;
    
    private static final int TEST_ADIESTRADORES_ESTRELLAS = 5;
    
    
	

	@BeforeEach
	void setup() {

		Adiestrador alberto = new Adiestrador();
		alberto.setFirstName("Alberto");
		alberto.setLastName("Cordon");
		alberto.setId(1);
		alberto.setEstrellas(TEST_ADIESTRADORES_ESTRELLAS);
		Adiestrador vicente = new Adiestrador();
		vicente.setFirstName("Helen");
		vicente.setLastName("Leary");
		vicente.setId(2);
		vicente.setEstrellas(TEST_ADIESTRADORES_ESTRELLAS);
		given(this.adiestradorService.findAll()).willReturn(Lists.newArrayList(alberto, vicente));
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAdiestradorListHtml() throws Exception {
		mockMvc.perform(get("/adiestradores")).andExpect(status().isOk())
				.andExpect(model().attributeExists("adiestradores"))
				.andExpect(view().name("adiestradores/listadoAdiestradores"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testShowBestAdiestradorListHtml() throws Exception {
		mockMvc.perform(get("/adiestradores/{estrellas}", TEST_ADIESTRADORES_ESTRELLAS)).andExpect(status().isOk())
				.andExpect(model().attributeExists("adiestradores"))
				.andExpect(view().name("adiestradores/listadoBestAdiestradores"));
	}

}
