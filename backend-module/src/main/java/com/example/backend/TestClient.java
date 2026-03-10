package com.example.backend;

import com.example.ejb.service.BeneficioServiceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class TestClient {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        props.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory");

        props.put(Context.PROVIDER_URL,
                "http-remoting://localhost:8080");

        Context context = new InitialContext(props);

        BeneficioServiceRemote service =
                (BeneficioServiceRemote) context.lookup(
                        "ejb:/ejb-module-0.0.1-SNAPSHOT/BeneficioEjbService!com.example.ejb.service.BeneficioServiceRemote"
                );

        System.out.println(service.listar());
    }
}