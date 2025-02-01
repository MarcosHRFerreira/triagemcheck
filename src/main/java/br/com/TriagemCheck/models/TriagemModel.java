package br.com.TriagemCheck.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.TriagemCheck.enums.CorProtocolo;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_TRIAGENS")
public class TriagemModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID triagemId;

//    private UUID pacienteId;

    @Column(nullable = false, length = 250)
    private String sintomas;

    @Column(nullable = false, length = 250)
    private String severidade;

    @Enumerated(EnumType.STRING)
    private CorProtocolo corProtocolo;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;

    @Column(nullable = false)
    private UUID profissionalId;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PacienteModel paciente;



    public UUID getTriagemId() {
        return triagemId;
    }

    public void setTriagemId(UUID triagemId) {
        this.triagemId = triagemId;
    }

//    public UUID getPacienteId() {
//        return pacienteId;
//    }
//
//    public void setPacienteId(UUID pacienteId) {
//        this.pacienteId = pacienteId;
//    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getSeveridade() {
        return severidade;
    }

    public void setSeveridade(String severidade) {
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

    public UUID getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(UUID profissionalId) {
        this.profissionalId = profissionalId;
    }

    public PacienteModel getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModel paciente) {
        this.paciente = paciente;
    }
}
