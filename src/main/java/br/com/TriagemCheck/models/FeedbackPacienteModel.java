package br.com.TriagemCheck.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TB_FEEDBACKPACIENTES")
public class FeedbackPacienteModel  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID feedbackpacienteId;


    @Column( nullable = false, length = 250)
    private String comentario;

    private Integer avaliacao;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PacienteModel paciente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TriagemModel triagem;


    public UUID getFeedbackpacienteId() {
        return feedbackpacienteId;
    }

    public void setFeedbackpacienteId(UUID feedbackpacienteId) {
        this.feedbackpacienteId = feedbackpacienteId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
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

    public TriagemModel getTriagem() {
        return triagem;
    }

    public void setTriagem(TriagemModel triagem) {
        this.triagem = triagem;
    }
}
