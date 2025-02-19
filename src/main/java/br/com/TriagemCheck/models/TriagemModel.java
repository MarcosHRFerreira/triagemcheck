package br.com.TriagemCheck.models;

import br.com.TriagemCheck.enums.Severidade;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.TriagemCheck.enums.CorProtocolo;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_TRIAGENS")
public class TriagemModel  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID triagemId;

    @Column(nullable = false, length = 250)
    private String sintomas;

    @Enumerated( EnumType.STRING)
    private Severidade severidade;

    @Enumerated( EnumType.STRING)
    private CorProtocolo corProtocolo;

    @Column(nullable = false)
    UUID enfermagemId;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PacienteModel paciente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProfissionalModel profissional;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "triagem", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ResultClinicosModel> resultclinico;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "triagem", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<FeedbackProfissionalModel> feedbackprofissional;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "triagem", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<FeedbackPacienteModel> feedbackpaciente;


    public UUID getTriagemId() {
        return triagemId;
    }

    public void setTriagemId(UUID triagemId) {
        this.triagemId = triagemId;
    }


    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public Severidade getSeveridade() {
        return severidade;
    }

    public void setSeveridade(Severidade severidade) {
        this.severidade = severidade;
    }

    public CorProtocolo getCorProtocolo() {
        return corProtocolo;
    }

    public void setCorProtocolo(CorProtocolo corProtocolo) {
        this.corProtocolo = corProtocolo;
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

    public PacienteModel getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModel paciente) {
        this.paciente = paciente;
    }


    public ProfissionalModel getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalModel profissional) {
        this.profissional = profissional;
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


    public Set<FeedbackPacienteModel> getFeedbackpaciente() {
        return feedbackpaciente;
    }

    public void setFeedbackpaciente(Set<FeedbackPacienteModel> feedbackpaciente) {
        this.feedbackpaciente = feedbackpaciente;
    }

    public UUID getEnfermagemId() {
        return enfermagemId;
    }

    public void setEnfermagemId(UUID enfermagemId) {
        this.enfermagemId = enfermagemId;
    }
}
