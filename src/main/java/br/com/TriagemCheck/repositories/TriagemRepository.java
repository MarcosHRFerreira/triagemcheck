package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.TriagemCompletaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TriagemRepository extends JpaRepository<TriagemModel, UUID> {

    @Query(value="select * from TB_TRIAGENS where triagem_id = :triagemId and  paciente_paciente_id = :pacienteId and" +
            " profissional_profissional_id = :profissionalId", nativeQuery = true)
    Optional<TriagemModel> findPacienteProfissionalInTriagem(UUID pacienteId , UUID profissionalId, UUID triagemId);

    @Query(value="select * from TB_TRIAGENS where paciente_paciente_id = :pacienteId LIMIT 1", nativeQuery = true)
    Optional<TriagemModel> findPacienteIntoTriagem(UUID pacienteId);

    @Query(value="select * from TB_TRIAGENS where profissional_profissional_id = :profissionalId LIMIT 1", nativeQuery = true)
    Optional<TriagemModel> findProficionalIntoTriagem(UUID profissionalId);

    @Query(value = "SELECT t.triagem_id AS triagemId, t.cor_protocolo AS corProtocolo, t.data_alteracao AS dataAlteracao, t.data_criacao AS dataCriacao, t.severidade AS severidade, t.sintomas AS sintomas, " +
            "t.paciente_paciente_id AS paciente_id, " +
            "p.nome AS nomePaciente, p.cpf AS cpfPaciente, " +
            "t.profissional_profissional_id AS profissional_id, pr.nome AS nomeProfissional, pr.especialidade AS especialidadeProfissional, pr.crm AS crmProfissional, " +
            "r.desfecho AS desfecho, r.diagnostico AS diagnostico, r.tratamento AS tratamento, " +
            "fp.avaliacaoeficacia AS avaliacaoEficacia, fp.avaliacaoseveridade AS avaliacaoSeveridade, fp.comentario AS comentarioProfissional, " +
            "fpa.avaliacao AS avaliacaoPaciente, fpa.comentario AS comentarioPaciente, " +
            "pr2.nome AS nomeEnfermagem, pr2.profissional_id AS profissionalIdEnfermagem " +
            "FROM tb_triagens t " +
            "LEFT JOIN tb_pacientes p ON t.paciente_paciente_id = p.paciente_id " +
            "LEFT JOIN tb_profissionais pr ON t.profissional_profissional_id = pr.profissional_id " +
            "LEFT JOIN tb_profissionais pr2 ON t.enfermagem_id = pr2.profissional_id " +
            "LEFT JOIN tb_resultclinicos r ON t.triagem_id = r.triagem_triagem_id " +
            "LEFT JOIN tb_feedbackprofissional fp ON t.triagem_id = fp.triagem_triagem_id " +
            "LEFT JOIN tb_feedbackpacientes fpa ON t.triagem_id = fpa.triagem_triagem_id " +
            "WHERE (:cpf IS NULL OR p.cpf = :cpf)",
            nativeQuery = true)
    Page<TriagemCompletaProjection> findTriagemCompleta(@Param("cpf") String cpf, Pageable pageable);

    @Query(value="select * from TB_TRIAGENS where triagem_id = :triagemId LIMIT 1", nativeQuery = true)
    TriagemModel findTriagemId(UUID triagemId);
}
