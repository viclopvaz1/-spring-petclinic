package org.springframework.samples.petclinic.E2E;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.web.AdiestradorController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdiestradorControllerIntegrationTests {
	
	@Autowired
	private AdiestradorController adiestradorController;

	@Autowired
	private AdiestradorService adiestradorService;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private UserService userService;
    
	@Autowired
    private AuthoritiesService authoritiesService;
    
    private static final int TEST_ADIESTRADORES_ESTRELLAS = 5;
    
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testShowAdiestradorListHtml() throws Exception {
		mockMvc.perform(get("/adiestradores")).andExpect(status().isOk())
				.andExpect(model().attributeExists("adiestradores"))
				.andExpect(view().name("adiestradores/listadoAdiestradores"));
	}
	
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testShowBestAdiestradorListHtml() throws Exception {
		mockMvc.perform(get("/adiestradores/{estrellas}", TEST_ADIESTRADORES_ESTRELLAS)).andExpect(status().isOk())
				.andExpect(model().attributeExists("adiestradores"))
				.andExpect(view().name("adiestradores/listadoBestAdiestradores"));
	}

}
