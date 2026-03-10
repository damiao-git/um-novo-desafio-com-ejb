//package com.example.backend.domain.dto;
//
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//@Entity
//public class Beneficio implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    private Long id;
//
//    private String nome;
//
//    private String descricao;
//
//    private BigDecimal valor;
//
//    private Boolean ativo = true;
//
//    private Long version;
//
//    public Beneficio() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public BigDecimal getValor() {
//        return valor;
//    }
//
//    public Boolean getAtivo() {
//        return ativo;
//    }
//
//    public Long getVersion() {
//        return version;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao = descricao;
//    }
//
//    public void setValor(BigDecimal valor) {
//        this.valor = valor;
//    }
//
//    public void setAtivo(Boolean ativo) {
//        this.ativo = ativo;
//    }
//
//    public void setVersion(Long version) {
//        this.version = version;
//    }
//
//    @Override
//    public String toString() {
//        return "Beneficio{" +
//                "id=" + id +
//                ", nome='" + nome + '\'' +
//                ", descricao='" + descricao + '\'' +
//                ", valor=" + valor +
//                ", ativo=" + ativo +
//                ", version=" + version +
//                '}';
//    }
//}