package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.model.User;
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
	
	private static final int TEST_OWNER_ID = 1;
	
	private static final int TEST_CITAOPERACION_ID_1 = 1;
	
	private static final int TEST_CITAOPERACION_ID_2 = 2;
	
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
	
	private Pet pet;
	
	private Vet vet;
	
	private CitaOperacion citaOperacion1;
	
	private CitaOperacion citaOperacion2;
	
//	private Owner owner;
	
	@BeforeEach
	void setup() {
		
//		this.owner = new Owner();
//		this.owner.setId(TEST_OWNER_ID);
//		this.owner.setFirstName("George");
//		this.owner.setLastName("Franklin");
//		this.owner.setMonedero(1000);
//		this.owner.setAddress("110 W. Liberty St.");
//		this.owner.setCity("Madison");
//		this.owner.setTelephone("6085551023");
//		User george = new User();
//		george.setUsername("george");
//		this.owner.setUser(george);
//		this.owner.setId(1);
		
		this.pet = new Pet();
		this.pet.setId(TEST_PET_ID);
		this.pet.setName("Leo");
		this.pet.setBirthDate(LocalDate.parse("2010-09-07"));
		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		this.pet.setType(cat);
		
		this.vet = new Vet();
		this.vet.setId(TEST_VET_ID);
		this.vet.setFirstName("James");
		this.vet.setLastName("Carter");
		this.vet.setEstrellas(4);;
		User james = new User();
		james.setUsername("james");
		this.vet.setUser(james);
		this.vet.setId(1);
		
		this.citaOperacion1 = new CitaOperacion();
		this.citaOperacion1.setPet(this.pet);
		this.citaOperacion1.setFechaInicio(LocalDate.parse("2020-12-29", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		this.citaOperacion1.setHora(LocalTime.parse("17:00"));
		this.citaOperacion1.setDuracion(30);
		this.citaOperacion1.setPrecio(100.0);
		this.citaOperacion1.setPagado(false);
		this.citaOperacion1.setVet(this.vet);
		TipoOperacion cirugiaVisual = new TipoOperacion();
		cirugiaVisual.setId(1);
		cirugiaVisual.setName("Cirugia Visual");
		this.citaOperacion1.setTipoOperacion(cirugiaVisual);
		this.citaOperacion1.setCantidadPersonal(2.0);
//		this.citaOperacion1.setPet(this.pet);
//		this.citaOperacion1.setVet(this.vet);
		
		this.citaOperacion2 = new CitaOperacion();
		this.citaOperacion2.setPet(this.pet);
		this.citaOperacion2.setFechaInicio(LocalDate.parse("2020-12-09", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		this.citaOperacion2.setHora(LocalTime.parse("17:00"));
		this.citaOperacion2.setDuracion(50);
		this.citaOperacion2.setPrecio(200.0);
		this.citaOperacion2.setPagado(false);
		this.citaOperacion2.setVet(this.vet);
		TipoOperacion cirugiaDental = new TipoOperacion();
		cirugiaDental.setId(2);
		cirugiaDental.setName("Cirugia Dental");
		this.citaOperacion2.setTipoOperacion(cirugiaDental);
		this.citaOperacion2.setCantidadPersonal(3.0);
//		this.citaOperacion2.setPet(this.pet);
//		this.citaOperacion2.setVet(this.vet);
		
		BDDMockito.given(this.citaOperacionService.findCitaOperacionByTipoOperacion("Cirugia Visual")).willReturn(Lists.newArrayList(this.citaOperacion1));
		BDDMockito.given(this.citaOperacionService.findCitaOperacionById(TEST_CITAOPERACION_ID_1)).willReturn(Optional.of(this.citaOperacion1));
		BDDMockito.given(this.citaOperacionService.findAll()).willReturn(Lists.newArrayList(this.citaOperacion1, this.citaOperacion2));
		BDDMockito.given(this.petService.findPetById(TEST_PET_ID)).willReturn(this.pet);
		BDDMockito.given(this.vetService.findVetById(TEST_VET_ID)).willReturn(this.vet);
		
	}
	
	//									Find By Tipo Operacion
	
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
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones"))
////		.param("tipoOperacion", "Cirugia Visual"))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/listadoCitasOperacionesFiltrado"));
//	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessFindFormByTipoOperacion() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
//		.param("tipoOperacion", "Cirugia Visual"))
//		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//		.andExpect(MockMvcResultMatchers.view().name("redirect:/citaOperacion/" + CitaOperacionControllerTests.TEST_CITAOPERACION_ID_1));
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoCitasOperacionesFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones")
				.param("tipoOperacion", "Unknown Tipo Operacion"))
		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.model().attributeExists("conjuntoVacio"))
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasOperaciones"))
		.andExpect(MockMvcResultMatchers.view().name("exception"));
//		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/listadoCitasOperacionesFiltrado"));
	}
	
	//									Create Cita Operacion
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasOperaciones/new/{petId}", TEST_PET_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	@WithMockUser(value = "spring")
	 @Test
	 void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
				.with(csrf())
				.param("fechaInicio", ""))
				.andExpect(model().attributeHasErrors("citaOperacion"))
				.andExpect(model().attributeHasFieldErrors("citaOperacion", "fechaInicio"))
				.andExpect(status().isOk())
				.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}
	
	//									Update Cita Operacion
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{citaOperacionId}/edit/{petId}",TEST_CITAOPERACION_ID_1, TEST_PET_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("citaOperacion"))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("fechaInicio", Matchers.is("2020-12-29"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("hora", Matchers.is("17:00"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("duracion", Matchers.is("30"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("precio", Matchers.is("100.0"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("tipoOperacion", Matchers.is("Cirugia Visual"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("cantidadPersonal", Matchers.is("2.0"))))
		.andExpect(MockMvcResultMatchers.view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateFormSuccess() throws Exception {
//		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
//							.with(csrf())
////							.param("hora", "15:00")
////							.param("tipoOperacion", "Cirugia Dental")
//							.param("cantidadPersonal", "5.0"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("redirect:/citaOperacion/" + TEST_CITAOPERACION_ID_1));
//	}
	
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

	//									Show Cita Operacion

//	@WithMockUser(value = "spring")
//	@Test
//	void testShowOwner() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaOperacion/{id}", TEST_CITAOPERACION_ID_1))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("fechaInicio", Matchers.is("2020-12-29"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("hora", Matchers.is("17:00"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("duracion", Matchers.is("30"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("precio", Matchers.is("100.0"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("tipoOperacion", Matchers.is("Cirugia Visual"))))
//		.andExpect(MockMvcResultMatchers.model().attribute("citaOperacion", Matchers.hasProperty("cantidadPersonal", Matchers.is("2.0"))))
//		.andExpect(MockMvcResultMatchers.view().name("owners/citaOperacionDetails"));
//	}
	
	//									Delete Cita Operacion

	
}
