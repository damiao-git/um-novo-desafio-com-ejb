package com.example.backend.service;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.service.BeneficioServiceRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Serviço de Benefícios")
class BeneficioServiceTest {

    @Mock
    private BeneficioServiceRemote ejbServiceMock;

    @InjectMocks
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
    @DisplayName("Deve listar todos os benefícios")
    void deveListarTodosBeneficos() {

        List<Beneficio> beneficiosList = Arrays.asList(beneficioMock, beneficioMock2);
        when(ejbServiceMock.listar()).thenReturn(beneficiosList);

        List<Beneficio> resultado = beneficioService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Benefício A", resultado.get(0).getNome());
        assertEquals("Benefício B", resultado.get(1).getNome());
        
        verify(ejbServiceMock, times(1)).listar();
    }

    @Test
    @DisplayName("Deve criar um novo benefício")
    void deveCriarNovoBeneficio() {

        Beneficio novoBeneficio = new Beneficio();
        novoBeneficio.setNome("Novo Benefício");
        novoBeneficio.setValor(new BigDecimal("500.00"));
        novoBeneficio.setAtivo(true);

        Beneficio beneficioCriado = new Beneficio();
        beneficioCriado.setId(3L);
        beneficioCriado.setNome("Novo Benefício");
        beneficioCriado.setValor(new BigDecimal("500.00"));
        beneficioCriado.setAtivo(true);

        when(ejbServiceMock.criar(any(Beneficio.class))).thenReturn(beneficioCriado);

        Beneficio resultado = beneficioService.criar(novoBeneficio);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Novo Benefício", resultado.getNome());
        assertEquals(new BigDecimal("500.00"), resultado.getValor());
        
        verify(ejbServiceMock, times(1)).criar(any(Beneficio.class));
    }

    @Test
    @DisplayName("Deve atualizar um benefício existente")
    void deveAtualizarBeneficio() {

        Beneficio beneficioAtualizado = new Beneficio();
        beneficioAtualizado.setId(1L);
        beneficioAtualizado.setNome("Benefício A Atualizado");
        beneficioAtualizado.setValor(new BigDecimal("1500.00"));
        beneficioAtualizado.setAtivo(true);

        when(ejbServiceMock.atualizar(any(Beneficio.class))).thenReturn(beneficioAtualizado);


        Beneficio resultado = beneficioService.atualizar(beneficioAtualizado);

        assertNotNull(resultado);
        assertEquals("Benefício A Atualizado", resultado.getNome());
        assertEquals(new BigDecimal("1500.00"), resultado.getValor());
        
        verify(ejbServiceMock, times(1)).atualizar(any(Beneficio.class));
    }

    @Test
    @DisplayName("Deve deletar um benefício por ID")
    void deveDeletarBeneficio() {

        doNothing().when(ejbServiceMock).deletar(1L);

        beneficioService.deletar(1L);

        verify(ejbServiceMock, times(1)).deletar(1L);
    }

    @Test
    @DisplayName("Deve buscar um benefício por ID")
    void deveBuscarBeneficioPorId() {
        when(ejbServiceMock.buscar(1L)).thenReturn(beneficioMock);

        Beneficio resultado = beneficioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Benefício A", resultado.getNome());
        assertEquals(new BigDecimal("1000.00"), resultado.getValor());
        
        verify(ejbServiceMock, times(1)).buscar(1L);
    }

    @Test
    @DisplayName("Deve transferir valor entre dois benefícios")
    void deveTransferirValorEntreBeneficos() {

        BigDecimal valorTransferencia = new BigDecimal("100.00");
        doNothing().when(ejbServiceMock).transferir(1L, 2L, valorTransferencia);

        beneficioService.transferir(1L, 2L, valorTransferencia);

        verify(ejbServiceMock, times(1)).transferir(1L, 2L, valorTransferencia);
    }

    @Test
    @DisplayName("Deve transferir com valores corretos")
    void deveTransferirComValoresCorretos() {

        Long origemId = 1L;
        Long destinoId = 2L;
        BigDecimal valorTransferencia = new BigDecimal("250.00");

        doNothing().when(ejbServiceMock).transferir(origemId, destinoId, valorTransferencia);

        beneficioService.transferir(origemId, destinoId, valorTransferencia);

        verify(ejbServiceMock, times(1)).transferir(
            eq(origemId),
            eq(destinoId),
            eq(valorTransferencia)
        );
    }

    @Test
    @DisplayName("Deve transferir com valor fracionado (centavos)")
    void deveTransferirComValorFracionado() {

        Long origemId = 1L;
        Long destinoId = 2L;
        BigDecimal valorTransferencia = new BigDecimal("123.45");

        doNothing().when(ejbServiceMock).transferir(origemId, destinoId, valorTransferencia);

        beneficioService.transferir(origemId, destinoId, valorTransferencia);

        verify(ejbServiceMock).transferir(origemId, destinoId, valorTransferencia);
    }

    @Test
    @DisplayName("Deve chamar EJB com parâmetros corretos ao buscar")
    void devePassarParametrosCorretosPraBuscar() {

        when(ejbServiceMock.buscar(25L)).thenReturn(beneficioMock);

        beneficioService.buscarPorId(25L);

        verify(ejbServiceMock).buscar(eq(25L));
    }
}
