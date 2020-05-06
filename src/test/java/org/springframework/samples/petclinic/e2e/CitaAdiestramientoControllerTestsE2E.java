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
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TipoOperacionService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.web.CitaOperacionController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class CitaAdiestramientoControllerTestsE2E {

	private static final int TEST_CITA_ADIESTRAMIENTO_ID = 1;

	private static final int TEST_PET_ID = 1;

	private static final int TEST_ADIESTRADOR_ID = 1;

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_TIPO_ADIESTRAMIENTO_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testShowAllCitasOperacion() throws Exception {
		mockMvc.perform(get("/citasAdiestramiento/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("citasAdiestramiento"))
				.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramiento"));
	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testListadoCitasAdiestramientoPorOwnerId() throws Exception {
		mockMvc.perform(get("/citasAdiestramiento/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("citasAdiestramiento"))
				.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramientoOwnersId"));
	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void initFindForm() throws Exception {
		this.mockMvc.perform(get("/citasAdiestramiento/find")).andExpect(status().isOk())
				.andExpect(model().attributeExists("citaAdiestramiento"))
				.andExpect(view().name("/citasAdiestramiento/findCitasAdiestramiento"));
	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void processFindFormFail() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/citasAdiestramiento").param("tipoAdiestramiento",
						"Unknown Tipo adiestramiento"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasAdiestramiento"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })

	@Test
	void testProcessFindFormByTipoPet() throws Exception {
		// given(this.clinicService.findCitaAdiestramientoByPet(pet.getType().getName())).willReturn(Lists.newArrayList(citaAdiestramiento));
		mockMvc.perform(get("/citasAdiestramiento").param("pet.type.name", "cat")).andExpect(status().isOk())
				.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramientoFiltrado"));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void processFindFormByTipoPetNotFound() throws Exception {
		// given(this.clinicService.findCitaAdiestramientoByPet(pet.getType().getName())).willReturn(Lists.newArrayList(cita));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasAdiestramiento").param("pet.type.name", "Unknown Tipo"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasAdiestramiento"))
				.andExpect(MockMvcResultMatchers.view().name("citasAdiestramiento/findCitasAdiestramiento"));
	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testInitUpdateCitaForm() throws Exception {
		mockMvc.perform(get("/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}",
				TEST_CITA_ADIESTRAMIENTO_ID, TEST_OWNER_ID, TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("citaAdiestramiento"))
				.andExpect(model().attribute("citaAdiestramiento", Matchers.hasProperty("duracion", Matchers.is(30))))
				.andExpect(model().attribute("citaAdiestramiento",
						Matchers.hasProperty("fechaInicio",
								Matchers.is(LocalDate.parse("2020/12/12", DateTimeFormatter.ofPattern("yyyy/MM/dd"))))))
				.andExpect(model().attribute("citaAdiestramiento",
						Matchers.hasProperty("hora", Matchers.is(LocalTime.parse("17:00")))))
				// .andExpect(model().attribute("citaAdiestramiento", hasProperty("pagado",
				// is(false))))
				.andExpect(model().attribute("citaAdiestramiento", Matchers.hasProperty("precio", Matchers.is(50.0))))
//				.andExpect(model().attribute("citaAdiestramiento",
//						Matchers.hasProperty("tipoAdiestramiento", Matchers.is("Adiestramiento deportivo"))))
				.andExpect(view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testProcessUpdateCitaFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}", TEST_CITA_ADIESTRAMIENTO_ID,
						TEST_OWNER_ID, TEST_PET_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("fechaInicio", "2020/12/11")
				.param("hora", "15:30")
				.param("duracion", "45")
				.param("precio", "75.0")
				.param("tipoAdiestramiento", "Adiestramiento deportivo"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/citaAdiestramiento/" + TEST_CITA_ADIESTRAMIENTO_ID));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}", TEST_CITA_ADIESTRAMIENTO_ID,
						TEST_OWNER_ID, TEST_PET_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("hora", "").param("duracion", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("citaAdiestramiento"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("citaAdiestramiento", "hora"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("citaAdiestramiento", "duracion"))
				.andExpect(
						MockMvcResultMatchers.view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testInitCreationCitaForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/citasAdiestramiento/new/{ownerId}/{petId}", TEST_OWNER_ID,
						TEST_PET_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("citaAdiestramiento")).andExpect(
						MockMvcResultMatchers.view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));
	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testProcessCreationCitaFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/citasAdiestramiento/new/{ownerId}/{petId}", TEST_OWNER_ID, TEST_PET_ID)//
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("id", "1")
						.param("fechaInicio", "2020/12/11").param("tipoAdiestramiento", "Adiestramiento deportivo")//
						.param("precio", "100").param("hora", "15:00").param("duracion", "20"))// 1
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/citaAdiestramiento/" + TEST_CITA_ADIESTRAMIENTO_ID));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/citasAdiestramiento/new/{ownerId}/{petId}", TEST_OWNER_ID, TEST_PET_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("fechaInicio", "2020/12/11")
						.param("duracion", "30").param("precio", "100").param("tipoAdiestramiento", "hola"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("citaAdiestramiento"))
				.andExpect(model().attributeHasFieldErrors("citaAdiestramiento", "hora"))
				.andExpect(view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));
	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testDeleteCitaForm() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/citaAdiestramiento/{citaAdiestramientoId}/delete",
						TEST_CITA_ADIESTRAMIENTO_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("welcome"));

	}

	@WithMockUser(username = "adiestrador1", authorities = { "adiestrador" })
	@Test
	void testDeleteCitaNotFound() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaAdiestramiento/{citaAdiestramientoId}/delete", 200))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "owner1", authorities = { "owner" })
	@Test
	void testPayCitaOperacionSuccess() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/citaAdiestramiento/{citaAdiestramientoId}/pay",
						TEST_CITA_ADIESTRAMIENTO_ID))
				.andExpect(MockMvcResultMatchers.model().attributeExists("citasAdiestramiento"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pagado"))
				.andExpect(MockMvcResultMatchers.view().name("citasAdiestramiento/listadoCitasAdiestramientoOwnersId"));
	}
}
