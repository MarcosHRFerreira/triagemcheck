package br.com.TriagemCheck.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import br.com.TriagemCheck.enums.Especialidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TB_PROFISSIONAIS")
public class ProfissionalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID profissionalId;
    @Column(nullable = false)
    private String crm;
    @Column(nullable = false,length = 150)
    private String nome;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOperacional statusOperacional;
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String email;

    public UUID getMedicoId() {
        return profissionalId;
    }

    public void setMedicoId(UUID medicoId) {
        this.profissionalId = medicoId;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }


    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusOperacional getStatusOperacional() {
        return statusOperacional;
    }

    public void setStatusOperacional(StatusOperacional statusOperacional) {
        this.statusOperacional = statusOperacional;
    }
}
