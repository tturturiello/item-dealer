package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.utils.Fakes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class RESTControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;
    private int id;
    private Item stubItem;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.id = 42;
        this.stubItem = Fakes.item( this.id );
        doReturn( this.stubItem ).when( this.itemService ).get( this.id);
        doReturn( this.stubItem ).when( this.itemService ).save( this.stubItem );
    }

    @Test
    void testIndexHasSupplyAndDemandInModel() throws Exception {
        this.mockMvc
                .perform( get("/").accept( "application/json" ))
                .andExpect( model().attributeExists( "demandItems" ))
                .andExpect( model().attributeExists( "supplyItems" ))
                .andExpect( model().hasNoErrors() );
    }

    @Test
    void testDetailHasFilledInModel() throws Exception {
        this.mockMvc
                .perform( get( "/item/" + this.id ).accept( MediaType.APPLICATION_JSON ))
                .andExpect( model().attributeExists( "item" ))
                .andExpect( model().hasNoErrors() );
    }

    @Test
    void testGetNewPostShouldFillInModel() throws Exception {
        this.mockMvc
                .perform( get("/item/new").accept( MediaType.APPLICATION_JSON ))
                .andExpect( model().attributeExists( "item" ))
                .andExpect( model().attributeExists( "categories" ))
                .andExpect( model().attributeExists( "authors" ))
                .andExpect( model().hasNoErrors() );
    }

    @Test
    void testEditHasFilledInModel() throws Exception {
        this.mockMvc
                .perform( get("/item/" + this.id + "/edit").accept( MediaType.APPLICATION_JSON ))
                .andExpect( model().attributeExists( "item" ))
                .andExpect( model().attributeExists( "categories" ))
                .andExpect( model().attributeExists( "authors" ))
                .andExpect( model().hasNoErrors() );
    }

    @Test
    void testShouldFilterHasFilledInModel() throws Exception {
        this.mockMvc
                .perform( get("/filter/category_name").accept(MediaType.APPLICATION_JSON))
                .andExpect( model().attributeExists( "supplyItems" ))
                .andExpect( model().attributeExists( "demandItems" ))
                .andExpect( model().hasNoErrors() );
    }
}