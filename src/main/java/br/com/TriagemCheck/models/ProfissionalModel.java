package br.com.TriagemCheck.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import br.com.TriagemCheck.enums.Especialidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TB_PROFISSIONAIS")
public class ProfissionalModel implements Serializable  {
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
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<TriagemModel> triagens;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ResultClinicosModel> resultclinico;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<FeedbackProfissionalModel> feedbackprofissional;


    public UUID getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(UUID profissionalId) {
        this.profissionalId = profissionalId;
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

    public StatusOperacional getStatusOperacional() {
        return statusOperacional;
    }

    public void setStatusOperacional(StatusOperacional statusOperacional) {
        this.statusOperacional = statusOperacional;
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

    public Set<TriagemModel> getTriagens() {
        return triagens;
    }

    public void setTriagens(Set<TriagemModel> triagens) {
        this.triagens = triagens;
    }

    public Set<ResultClinicosModel> getResultclinico() {
        return resultclinico;
    }

    public void setResultclinico(Set<ResultClinicosModel> resultclinico) {
        this.resultclinico = resultclinico;
    }

    public Set<FeedbackProfissionalModel> getFeedbackprofissional() {
        return feedbackprofissional;
    }

    public void setFeedbackprofissional(Set<FeedbackProfissionalModel> feedbackprofissional) {
        this.feedbackprofissional = feedbackprofissional;
    }
}
