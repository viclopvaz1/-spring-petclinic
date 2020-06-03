package org.springframework.samples.petclinic.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class CausaControllerIntegrationTests {
	
	private static final int	TEST_CAUSA_ID			= 1;


	@Autowired
	private MockMvc				mockMvc;
	
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testInitCreationFormOwner() throws Exception {
		mockMvc.perform(post("/causa/new")
		.with(csrf())
		.param("fechaInicio", "2020/02/02")
		.param("fechaFin", "2020/03/03")
		.param("ong", "Mi perrito Paco")
		.param("objetivo", "1000")
		.param("dineroRecaudado", "0"))
		.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testInitCreationFormVet() throws Exception {
		mockMvc.perform(post("/causa/new")
		.with(csrf())
		.param("fechaInicio", "2020/02/02")
		.param("fechaFin", "2020/03/03")
		.param("ong", "Mi perrito Paco")
		.param("objetivo", "1000")
		.param("dineroRecaudado", "0"))
		.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testInitCreationFormGetVet() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/causa/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("causa"))
		.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testInitCreationFormGetOwner() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/causa/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("causa"))
		.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testProcessCreationFormHasErrorsInFechaFin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/causa/new")
		.with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("fechaInicio", "2020/01/11")
		.param("ong", "ONG")
		.param("objetivo", "1200")
		.param("dineroRecaudado", "0"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("causa", "fechaFin"))
		.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessUpdateFormHasErrorInObjetivoMenorARecaudado() throws Exception {
		mockMvc
		.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerIntegrationTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("fechaFin", "2020/01/11")
		.param("fechaInicio", "2020/01/05")
		.param("ong", "ONG")
		.param("objetivo", "120")
		.param("dineroRecaudado", "130"))
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
		.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessUpdateFormHasErrorInFechas() throws Exception {
		mockMvc
		.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerIntegrationTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("fechaFin", "2020/01/11")
		.param("fechaInicio", "2020/01/12")
		.param("ong", "ONG")
		.param("objetivo", "1200")
		.param("dineroRecaudado", "0"))
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("causa")).andExpect(MockMvcResultMatchers.model().hasErrors())
		.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testListadoCausas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causas")).andExpect(MockMvcResultMatchers.view().name("causas/listadoCausas"));
	}

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testInitUpdeteForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/{causaId}/edit", CausaControllerIntegrationTests.TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causa"))
			.andExpect(MockMvcResultMatchers.view().name("causas/createOrUpdateCausaForm"));
	}

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causa/{causaId}/edit", CausaControllerIntegrationTests.TEST_CAUSA_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaFin", "2020/06/11").param("fechaInicio", "2020/01/10").param("ong", "ONG2")
			.param("objetivo", "12000").param("dineroRecaudado", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/causa/" + CausaControllerIntegrationTests.TEST_CAUSA_ID));
		;
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testListadoCausasNoValidas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causa/noValidas", CausaControllerIntegrationTests.TEST_CAUSA_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("causas"))
			.andExpect(MockMvcResultMatchers.view().name("causas/listadoCausas"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testInitDeleteCausa() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/causa/{causaId}/delete", CausaControllerIntegrationTests.TEST_CAUSA_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/causa"));
	}

}
