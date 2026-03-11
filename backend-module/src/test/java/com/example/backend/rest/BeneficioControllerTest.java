package com.example.backend.rest;

import com.example.backend.service.BeneficioService;
import com.example.ejb.entity.Beneficio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controller de Benefícios")
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeneficioService beneficioService;

    private Beneficio beneficioMock;
    private Beneficio beneficioMock2;

    @BeforeEach
    void setup() {
        beneficioMock = new Beneficio();
        beneficioMock.setId(1L);
        beneficioMock.setNome("Benefício A");
        beneficioMock.setDescricao("Descrição do benefício A");
        beneficioMock.setValor(new BigDecimal("1000.00"));
        beneficioMock.setAtivo(true);

        beneficioMock2 = new Beneficio();
        beneficioMock2.setId(2L);
        beneficioMock2.setNome("Benefício B");
        beneficioMock2.setDescricao("Descrição do benefício B");
        beneficioMock2.setValor(new BigDecimal("2000.00"));
        beneficioMock2.setAtivo(true);
    }

    @Test
    @DisplayName("Deve retornar lista de benefícios quando GET /api/beneficios")
    void deveRetornarListaBeneficos() throws Exception {

        List<Beneficio> beneficios = Arrays.asList(beneficioMock, beneficioMock2);
        when(beneficioService.listar()).thenReturn(beneficios);

        mockMvc.perform(get("/api/beneficios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Benefício A")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Benefício B")));

        verify(beneficioService, times(1)).listar();
    }

    @Test
    @DisplayName("Deve buscar um benefício por ID quando GET /api/beneficios/{id}")
    void deveBuscarBeneficioPorId() throws Exception {

        when(beneficioService.buscarPorId(1L)).thenReturn(beneficioMock);

        mockMvc.perform(get("/api/beneficios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Benefício A")))
                .andExpect(jsonPath("$.valor", is(1000.00)));

        verify(beneficioService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 quando benefício não é encontrado")
    void deveRetornar404QuandoBeneficioNaoEncontrado() throws Exception {

        when(beneficioService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/beneficios/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(beneficioService, times(1)).buscarPorId(999L);
    }

    @Test
    @DisplayName("Deve criar um novo benefício quando POST /api/beneficios")
    void deveCriarNovoBeneficio() throws Exception {

        Beneficio novoBeneficio = new Beneficio();
        novoBeneficio.setNome("Novo Benefício");
        novoBeneficio.setDescricao("Descrição novo");
        novoBeneficio.setValor(new BigDecimal("500.00"));
        novoBeneficio.setAtivo(true);

        Beneficio beneficioCriado = new Beneficio();
        beneficioCriado.setId(3L);
        beneficioCriado.setNome("Novo Benefício");
        beneficioCriado.setDescricao("Descrição novo");
        beneficioCriado.setValor(new BigDecimal("500.00"));
        beneficioCriado.setAtivo(true);

        when(beneficioService.criar(any(Beneficio.class))).thenReturn(beneficioCriado);

        MvcResult result = mockMvc.perform(post("/api/beneficios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoBeneficio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Novo Benefício")))
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        verify(beneficioService, times(1)).criar(any(Beneficio.class));
    }

    @Test
    @DisplayName("Deve atualizar um benefício quando PUT /api/beneficios/{id}")
    void deveAtualizarBeneficio() throws Exception {
        Beneficio beneficioAtualizado = new Beneficio();
        beneficioAtualizado.setId(1L);
        beneficioAtualizado.setNome("Benefício A Atualizado");
        beneficioAtualizado.setDescricao("Descrição atualizada");
        beneficioAtualizado.setValor(new BigDecimal("1500.00"));
        beneficioAtualizado.setAtivo(true);

        when(beneficioService.atualizar(any(Beneficio.class))).thenReturn(beneficioAtualizado);

        mockMvc.perform(put("/api/beneficios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beneficioAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Benefício A Atualizado")))
                .andExpect(jsonPath("$.valor", is(1500.00)));

        verify(beneficioService, times(1)).atualizar(any(Beneficio.class));
    }

    @Test
    @DisplayName("Deve deletar um benefício quando DELETE /api/beneficios/{id}")
    void deveDeletarBeneficio() throws Exception {

        doNothing().when(beneficioService).deletar(1L);

        mockMvc.perform(delete("/api/beneficios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beneficioService, times(1)).deletar(1L);
    }

    @Test
    @DisplayName("Deve transferir valor entre benefícios quando POST /api/beneficios/transferir")
    void deveTransferirValor() throws Exception {

        Beneficio origem = new Beneficio();
        origem.setId(1L);
        origem.setNome("Benefício A");
        origem.setValor(new BigDecimal("900.00"));

        Beneficio destino = new Beneficio();
        destino.setId(2L);
        destino.setNome("Benefício B");
        destino.setValor(new BigDecimal("2100.00"));

        doNothing().when(beneficioService).transferir(1L, 2L, new BigDecimal("100.00"));
        when(beneficioService.buscarPorId(1L)).thenReturn(origem);
        when(beneficioService.buscarPorId(2L)).thenReturn(destino);

        mockMvc.perform(post("/api/beneficios/transferir")
                .param("origemId", "1")
                .param("destinoId", "2")
                .param("valor", "100.00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origem.id", is(1)))
                .andExpect(jsonPath("$.origem.valor", is(900.00)))
                .andExpect(jsonPath("$.destino.id", is(2)))
                .andExpect(jsonPath("$.destino.valor", is(2100.00)));

        verify(beneficioService, times(1)).transferir(1L, 2L, new BigDecimal("100.00"));
        verify(beneficioService, times(1)).buscarPorId(1L);
        verify(beneficioService, times(1)).buscarPorId(2L);
    }
}
