package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService implements BeneficioServiceRemote{

    @PersistenceContext(unitName = "beneficioPU")
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        Beneficio from = em.find(
                Beneficio.class,
                fromId,
                LockModeType.PESSIMISTIC_WRITE
        );

        Beneficio to = em.find(
                Beneficio.class,
                toId,
                LockModeType.PESSIMISTIC_WRITE
        );

        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }
}