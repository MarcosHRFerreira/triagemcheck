package br.com.TriagemCheck.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProfissionalModel feedbackprofissional;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TriagemModel triagem;


    public UUID getFeedbackprofissionalId() {
        return feedbackprofissionalId;
    }

    public void setFeedbackprofissionalId(UUID feedbackprofissionalId) {
        this.feedbackprofissionalId = feedbackprofissionalId;
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

    public ProfissionalModel getFeedbackprofissional() {
        return feedbackprofissional;
    }

    public void setFeedbackprofissional(ProfissionalModel feedbackprofissional) {
        this.feedbackprofissional = feedbackprofissional;
    }

    public TriagemModel getTriagem() {
        return triagem;
    }

    public void setTriagem(TriagemModel triagem) {
        this.triagem = triagem;
    }
}
