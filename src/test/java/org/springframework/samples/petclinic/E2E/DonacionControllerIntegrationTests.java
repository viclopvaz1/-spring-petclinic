package org.springframework.samples.petclinic.E2E;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.web.DonacionController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class DonacionControllerIntegrationTests {
	
	@Autowired
	private DonacionController donacionController;

	@Autowired
	private DonacionService donacionService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private CausaService causaService;

	@Autowired
	private VetService vetService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private AdiestradorService adiestradorService;

	@Autowired
	private AuthoritiesService authoritiesService;

	private static final int TEST_CAUSA_ID = 1;
	
	private Causa causa;
	
	private Donacion donacion;
	
	private User user;
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testNewDonacionHtml() throws Exception {
		mockMvc.perform(get("/donacion/{causaId}/new", TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("donacion"))
				.andExpect(MockMvcResultMatchers.view().name("donaciones/createOrUpdateDonacion"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testNotExistNewDonacionHtml() throws Exception {
		mockMvc.perform(get("/donacion/{causaId}/new", 151)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("donacion"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

}
