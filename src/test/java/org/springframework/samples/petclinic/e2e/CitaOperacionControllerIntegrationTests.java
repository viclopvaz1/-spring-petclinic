package org.springframework.samples.petclinic.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class CitaOperacionControllerIntegrationTests {
	
	private static final int TEST_PET_ID = 1;
		
	private static final int TEST_CITAOPERACION_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;
	
		
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones/find"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.view().name("/citasOperaciones/findCitasOperaciones"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessFindFormByTipoOperacionMoreThanOne() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
		.param("tipoOperacion", "Cirugia dental"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/listadoCitasOperacionesFiltrado"));

	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessFindFormByTipoOperacion() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
		.param("tipoOperacion", "Cirugia de emergencia"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/citaOperacion/" + 5));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessFindFormNoCitasOperacionesFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
				.param("tipoOperacion", "Unknown Tipo Operacion"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasOperaciones"))
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	

	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/citasOperaciones/new/{petId}", TEST_PET_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"))
		.andExpect(model().attributeExists("citaOperacion"));
}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
				.with(csrf())
				.param("id", "6")
				.param("fechaInicio", "2020/12/11")
				.param("hora", "15:00")
				.param("duracion", "30")
				.param("precio", "100")
				.param("tipoOperacion", "Cirugia dental")
				.param("cantidadPersonal", "2"))
				.andExpect(status().is3xxRedirection())
			  .andExpect(view().name("redirect:/citaOperacion/" + 6));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	  void testProcessCreationFormHasErrors() throws Exception {
			mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
					.with(csrf())
					.param("fechaInicio", "2020/12/27")
					.param("duracion", "30")
					.param("precio", "100")
					.param("cantidadPersonal", "2"))
					.andExpect(model().attributeHasErrors("citaOperacion"))
					.andExpect(model().attributeHasFieldErrors("citaOperacion", "hora"))
					.andExpect(status().isOk())
					.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
    void testProcessCreationFormHasErrorsInFechaInicio() throws Exception {
		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
				.with(csrf())
				.param("fechaInicio", "2019/05/11")
				.param("hora", "15:00")
				.param("duracion", "30")
				.param("precio", "100")
				.param("tipoOperacion", "Cirugia dental")
				.param("cantidadPersonal", "2"))
			.andExpect(status().isOk())
			.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"))
			.andExpect(model().attributeExists("mensaje"));
	}
	

	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testInitUpdateForm() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{citaOperacionId}/edit/{petId}",TEST_CITAOPERACION_ID, TEST_PET_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("fechaInicio", Matchers.is(LocalDate.parse("2020/12/29", DateTimeFormatter.ofPattern("yyyy/MM/dd"))))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("hora", Matchers.is(LocalTime.parse("17:00")))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("duracion", Matchers.is(30))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("precio", Matchers.is(50.0))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("cantidadPersonal", Matchers.is(3.0))))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/citaOperacion/{citaOperacionId}/edit/{petId}",TEST_CITAOPERACION_ID, TEST_PET_ID)
							.with(csrf())
							.param("fechaInicio", "2020/12/27")
							.param("hora", "15:00")
							.param("duracion", "30")
							.param("precio", "100")
							.param("tipoOperacion", "Cirugia dental")
							.param("cantidadPersonal", "2"))
							.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/citaOperacion/" + TEST_CITAOPERACION_ID));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
							.with(csrf())
							.param("hora", "")
							.param("cantidadPersonal", ""))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeHasErrors("citaOperacion"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("citaOperacion", "hora"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("citaOperacion", "cantidadPersonal"))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testShowCitaOperacion() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{id}", TEST_CITAOPERACION_ID))
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("fechaInicio", Matchers.is(LocalDate.parse("2020/12/29", DateTimeFormatter.ofPattern("yyyy/MM/dd"))))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("hora", Matchers.is(LocalTime.parse("17:00")))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("duracion", Matchers.is(30))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("precio", Matchers.is(50.0))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("cantidadPersonal", Matchers.is(3.0))))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/citaOperacionDetails"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testPayCitaOperacionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{citaOperacionId}/pay", TEST_CITAOPERACION_ID))
		.andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("pagado"))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/listadoCitasOperacionesPets"));
	}
}
