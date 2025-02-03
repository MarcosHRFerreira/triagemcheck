package br.com.TriagemCheck.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TB_RESULTCLINICOS")
public class ResultClinicosModel  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID resultadoId;

    @Column(length = 250)
    private String diagnostico;
    @Column(length = 250)
    private String tratamento;
    @Column(length = 250)
    private String desfecho;
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TriagemModel triagem;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProfissionalModel profissional;


    public UUID getResultadoId() {
        return resultadoId;
    }

    public void setResultadoId(UUID resultadoId) {
        this.resultadoId = resultadoId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getDesfecho() {
        return desfecho;
    }

    public void setDesfecho(String desfecho) {
        this.desfecho = desfecho;
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

    public TriagemModel getTriagem() {
        return triagem;
    }

    public void setTriagem(TriagemModel triagem) {
        this.triagem = triagem;
    }

    public ProfissionalModel getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalModel profissional) {
        this.profissional = profissional;
    }
}
