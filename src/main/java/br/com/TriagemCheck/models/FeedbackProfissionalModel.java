package br.com.TriagemCheck.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TB_FEEDBACKPROFISSIONAL")
public class FeedbackProfissionalModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID feedbackprofissionalId;

    @Column(nullable = false)
    private UUID profissionalId;

    @Column(nullable = false)
    private UUID triagemId;

    @Column(nullable = false, length = 250)
    private String comentario;

    @Column(nullable = false)
    private Integer avaliacaoseveridade;

    @Column(nullable = false)
    private Integer avaliacaoeficacia;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;


    public UUID getFeedbackprofissionalId() {
        return feedbackprofissionalId;
    }

    public void setFeedbackprofissionalId(UUID feedbackprofissionalId) {
        this.feedbackprofissionalId = feedbackprofissionalId;
    }

    public UUID getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(UUID profissionalId) {
        this.profissionalId = profissionalId;
    }

    public UUID getTriagemId() {
        return triagemId;
    }

    public void setTriagemId(UUID triagemId) {
        this.triagemId = triagemId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getAvaliacaoseveridade() {
        return avaliacaoseveridade;
    }

    public void setAvaliacaoseveridade(Integer avaliacaoseveridade) {
        this.avaliacaoseveridade = avaliacaoseveridade;
    }

    public Integer getAvaliacaoeficacia() {
        return avaliacaoeficacia;
    }

    public void setAvaliacaoeficacia(Integer avaliacaoeficacia) {
        this.avaliacaoeficacia = avaliacaoeficacia;
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
}
