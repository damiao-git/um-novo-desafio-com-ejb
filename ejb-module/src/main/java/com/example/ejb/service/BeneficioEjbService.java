package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Stateless
public class BeneficioEjbService implements BeneficioServiceRemote{

    @PersistenceContext(unitName = "beneficioPU")
    private EntityManager em;

    @Override
    public Beneficio criar(Beneficio beneficio) {
        beneficio.setVersion(0L);
        em.persist(beneficio);
        return beneficio;
    }

    @Override
    public Beneficio atualizar(Beneficio beneficio) {
        Beneficio existente = em.find(Beneficio.class, beneficio.getId());
        if (existente.getVersion() == null) {
            existente.setVersion(0L);
        }
        existente.setNome(beneficio.getNome());
        existente.setDescricao(beneficio.getDescricao());
        existente.setValor(beneficio.getValor());

        return em.merge(existente);
    }

    @Override
    public Beneficio buscar(Long id) {
        return em.find(Beneficio.class, id);
    }

    @Override
    public List<Beneficio> listar() {
        return em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)
                .getResultList();
    }

    @Override
    public void deletar(Long id) {
        Beneficio beneficio = em.find(Beneficio.class, id);
        em.remove(beneficio);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferir(Long fromId, Long toId, BigDecimal amount) {

        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

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

        em.merge(from);
        em.merge(to);
    }
}