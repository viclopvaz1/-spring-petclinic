package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(value = CitaOperacionController.class,
includeFilters = @ComponentScan.Filter(value = TipoOperacionFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CitaOperacionControllerTests2 {
	
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
	
	@BeforeEach
	void setup() {
		TipoOperacion cirugiaVisual = new TipoOperacion();
		cirugiaVisual.setId(1);
		cirugiaVisual.setName("Cirugia Visual");
		LocalTime hora = LocalTime.parse("15:00");
		given(this.tipoOperacionService.findAll()).willReturn(Lists.newArrayList(cirugiaVisual));
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
		given(this.vetService.findVetById(TEST_VET_ID)).willReturn(new Vet());
		given(this.citaOperacionService.findCitaOperacionById(TEST_CITAOPERACION_ID)).willReturn(Optional.of(new CitaOperacion()));
//		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(new Owner());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/citasOperaciones/new/{petId}", TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm")).andExpect(model().attributeExists("citaOperacion"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/citasOperaciones/new/{petId}", TEST_PET_ID)
				.with(csrf())
				.param("fechaInicio", "2020/03/27")
//				.param("hora", "15:00")
				.param("duracion", "30")
				.param("precio", "100")
//				.param("tipoOperacion", "Cirugia Visual")
				.param("cantidadPersonal", "2"))
				.andExpect(status().isOk())
				.andExpect(view().name("redirect:/citaOperacion/{citaOperacionId}"));
			  //.andExpect(view().name("redirect:/citaOperacion/" + TEST_CITAOPERACION_ID));
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
	//				.andExpect(model().attributeHasFieldErrors("citaOperacion", "tipoOperacion"))
					.andExpect(status().isOk())
					.andExpect(view().name("citasOperaciones/createOrUpdateCitaOperacionForm"));
	}	
}
