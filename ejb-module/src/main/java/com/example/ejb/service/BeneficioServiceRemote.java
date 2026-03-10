package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import jakarta.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface BeneficioServiceRemote {

    Beneficio criar(Beneficio beneficio);

    Beneficio atualizar(Long id, Beneficio beneficio);

    Beneficio buscar(Long id);

    List<Beneficio> listar();

    void deletar(Long id);

    void transferir(Long origemId, Long destinoId, BigDecimal valor);
}