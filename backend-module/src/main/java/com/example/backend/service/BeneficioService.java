package com.example.backend.service;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.service.BeneficioServiceRemote;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

@Service
public class BeneficioService {

    private BeneficioServiceRemote ejbService;

    @PostConstruct
    public void init() {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

            Context context = new InitialContext(props);

            ejbService = (BeneficioServiceRemote) context.lookup(
                    "ejb:/ejb-module-0.0.1-SNAPSHOT/BeneficioEjbService!com.example.ejb.service.BeneficioServiceRemote"
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar com o EJB remoto", e);
        }
    }

    public List<Beneficio> listar() {
        return ejbService.listar();
    }

    public Beneficio criar(Beneficio beneficio) {
        return ejbService.criar(beneficio);
    }

    public Beneficio atualizar(Beneficio beneficio) {
        return ejbService.atualizar(beneficio);
    }

    public void deletar(Long id) {
        ejbService.deletar(id);
    }

    public Beneficio buscarPorId(Long id) {
        return ejbService.buscar(id);
    }

    public void transferir(Long origemId, Long destinoId, BigDecimal valor){
        ejbService.transferir(origemId, destinoId, valor);
    }
}