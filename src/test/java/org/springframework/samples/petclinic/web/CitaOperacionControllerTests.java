package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
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
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TipoOperacionService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = CitaOperacionController.class,
includeFilters = @ComponentScan.Filter(value = TipoOperacionFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CitaOperacionControllerTests {
	
	private static final int TEST_VET_ID = 1;

	private static final int TEST_PET_ID = 1;
	
//	private static final int TEST_OWNER_ID = 1;
	
	private static final int TEST_CITAOPERACION_ID = 1;
	
	@Autowired
	private CitaOperacionController citaOperacioncontroller;
	
	@MockBean
	private AuthoritiesService	authoritiesService;
	
	@MockBean
	private CitaOperacionService citaOperacionService;
	
	@MockBean
	private PetService petService;
	
//	@MockBean
//	private OwnerService ownerService;
	
	@MockBean
	private VetService vetService;
	
	@MockBean
	private TipoOperacionService tipoOperacionService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private CitaOperacion citaOperacion1;
	
	@BeforeEach
	void setup() {
		this.citaOperacion1 = new CitaOperacion();
		this.citaOperacion1.setId(TEST_CITAOPERACION_ID);
		this.citaOperacion1.setFechaInicio(LocalDate.parse("2020/12/29", DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		this.citaOperacion1.setHora(LocalTime.parse("17:00"));
		this.citaOperacion1.setDuracion(30);
		this.citaOperacion1.setPrecio(100.0);
		this.citaOperacion1.setPagado(false);
		this.citaOperacion1.setCantidadPersonal(2.0);
		TipoOperacion cirugiaVisual = new TipoOperacion();
		cirugiaVisual.setId(1);
		cirugiaVisual.setName("hola");
		this.citaOperacion1.setTipoOperacion(cirugiaVisual);
		BDDMockito.given(this.tipoOperacionService.findAll()).willReturn(Lists.newArrayList(cirugiaVisual));
		BDDMockito.given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
		BDDMockito.given(this.vetService.findVetById(TEST_VET_ID)).willReturn(new Vet());
		BDDMockito.given(this.citaOperacionService.findCitaOperacionById(TEST_CITAOPERACION_ID)).willReturn(Optional.of(this.citaOperacion1));
	}
	
	//								Find By Tipo Operacion
		
	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones/find"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.view().name("/citasOperaciones/findCitasOperaciones"));
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessFindFormByTipoOperacionMoreThanOne() throws Exception {
//		BDDMockito.given(this.citaOperacionService.findCitaOperacionByTipoOperacion("")).willReturn(Lists.newArrayList(this.citaOperacion1, new CitaOperacion()));
//		
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones"))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/listadoCitasOperacionesFiltrado"));
//
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByTipoOperacion() throws Exception {
		BDDMockito.given(this.citaOperacionService.findCitaOperacionByTipoOperacion("hola")).willReturn(Lists.newArrayList(this.citaOperacion1));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
		.param("tipoOperacion", "hola"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/citaOperacion/" + CitaOperacionControllerTests.TEST_CITAOPERACION_ID));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoCitasOperacionesFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
				.param("tipoOperacion", "Unknown Tipo Operacion"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasOperaciones"))
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	
	//								Create Tipo Operacion

	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/citasOperaciones/new/{petId}", TEST_PET_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"))
		.andExpect(model().attributeExists("citaOperacion"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
				.with(csrf())
				.param("id", "1")
				.param("fechaInicio", "2020/03/27")
				.param("hora", "15:00")
				.param("duracion", "30")
				.param("precio", "100")
				.param("tipoOperacion", "hola")
				.param("cantidadPersonal", "2"))
				.andExpect(status().is3xxRedirection())
			  .andExpect(view().name("redirect:/citaOperacion/" + TEST_CITAOPERACION_ID));
	}
	
	@WithMockUser(value = "spring")
	  @Test
	  void testProcessCreationFormHasErrors() throws Exception {
			mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
					.with(csrf())
					.param("fechaInicio", "2020/03/27")
					.param("duracion", "30")
					.param("precio", "100")
					.param("cantidadPersonal", "2"))
					.andExpect(model().attributeHasErrors("citaOperacion"))
					.andExpect(model().attributeHasFieldErrors("citaOperacion", "hora"))
					.andExpect(status().isOk())
					.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	//								Update Tipo Operacion

	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		TipoOperacion hola = new TipoOperacion();
		hola.setId(1);
		hola.setName("hola");
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{citaOperacionId}/edit/{petId}",TEST_CITAOPERACION_ID, TEST_PET_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("fechaInicio", Matchers.is(LocalDate.parse("2020/12/29", DateTimeFormatter.ofPattern("yyyy/MM/dd"))))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("hora", Matchers.is(LocalTime.parse("17:00")))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("duracion", Matchers.is(30))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("precio", Matchers.is(100.0))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("tipoOperacion", Matchers.is(hola))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("cantidadPersonal", Matchers.is(2.0))))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/citaOperacion/{citaOperacionId}/edit/{petId}",TEST_CITAOPERACION_ID, TEST_PET_ID)
							.with(csrf())
							.param("id", "1")
							.param("fechaInicio", "2020/03/27")
							.param("hora", "15:00")
							.param("duracion", "30")
							.param("precio", "100")
							.param("tipoOperacion", "hola")
							.param("cantidadPersonal", "2"))
							.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/citaOperacion/" + TEST_CITAOPERACION_ID));
	}
	
	@WithMockUser(value = "spring")
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
	
	//			Show Cita Operacion
	
	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{id}", TEST_CITAOPERACION_ID))
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("fechaInicio", Matchers.is(LocalDate.parse("2020/12/29", DateTimeFormatter.ofPattern("yyyy/MM/dd"))))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("hora", Matchers.is(LocalTime.parse("17:00")))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("duracion", Matchers.is(30))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("precio", Matchers.is(100.0))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("tipoOperacion", Matchers.is(hola))))
		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("cantidadPersonal", Matchers.is(2.0))))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/citaOperacionDetails"));
	}
}
