package br.com.TriagemCheck.models;

import br.com.TriagemCheck.enums.UnidadeFederativa;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.TriagemCheck.enums.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TB_PACIENTES")
public class PacienteModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pacienteId;

    @Column(nullable = false, unique = true)
  	private String cpf;
    @Column(nullable = false, length = 150)
	private String  nome;
    @Column(nullable = false)
    private LocalDate dtnascimento;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Column(length = 150)
    private String  email;
    @Column(nullable = false, length = 250)
    private String logradouro;
    @Column(nullable = false, length = 20)
    private String  numero;
    @Column(nullable = false, length = 150)
    private String  bairro;
    @Column(nullable = false,length = 10)
    private String  cep;
    @Column(nullable = false,length = 150)
    private String  cidade;
    @Column(nullable = false,length = 2)

    @Enumerated(EnumType.STRING)
    private UnidadeFederativa uf;

    @Column(length = 15)
    private String  telefone1;
    @Column(length = 15)
    private String  telefone2;
    @Column(nullable = false)
    private LocalDateTime  dataCriacao;
    @Column(nullable = false)
    private LocalDateTime  dataAlteracao;
    @Column(length = 250)
    private String observacao;
    @Column(length = 250)
    private String  medicacaoContinua;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<TriagemModel> triagens;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<FeedbackPacienteModel> feedbackpaciente;


    public LocalDate getDtnascimento() {
        return dtnascimento;
    }

    public void setDtnascimento(LocalDate dtnascimento) {
        this.dtnascimento = dtnascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public UUID getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(UUID pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
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

    public String getMedicacaoContinua() {
        return medicacaoContinua;
    }

    public void setMedicacaoContinua(String medicacaoContinua) {
        this.medicacaoContinua = medicacaoContinua;
    }

    public UnidadeFederativa getUf() {
        return uf;
    }

    public void setUf(UnidadeFederativa uf) {
        this.uf = uf;
    }

    public Set<TriagemModel> getTriagens() {
        return triagens;
    }

    public void setTriagens(Set<TriagemModel> triagens) {
        this.triagens = triagens;
    }

    public Set<FeedbackPacienteModel> getFeedbackpaciente() {
        return feedbackpaciente;
    }

    public void setFeedbackpaciente(Set<FeedbackPacienteModel> feedbackpaciente) {
        this.feedbackpaciente = feedbackpaciente;
    }


}
