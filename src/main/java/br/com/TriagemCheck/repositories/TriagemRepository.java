package br.com.TriagemCheck.repositories;


import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.TriagemCompletaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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


    @Query(value="SELECT t.triagem_id AS triagemId, t.cor_protocolo AS corProtocolo, t.data_alteracao AS dataAlteracao, t.data_criacao AS dataCriacao, t.severidade AS severidade, t.sintomas AS sintomas, " +
            "p.nome AS nomePaciente, p.cpf AS cpfPaciente, " +
            "pr.nome AS nomeProfissional, pr.especialidade AS especialidadeProfissional, " +
            "r.desfecho AS desfecho, r.diagnostico AS diagnostico, r.tratamento AS tratamento, " +
            "fp.avaliacaoeficacia AS avaliacaoEficacia, fp.avaliacaoseveridade AS avaliacaoSeveridade, fp.comentario AS comentarioProfissional, " +
            "fpa.avaliacao AS avaliacaoPaciente, fpa.comentario AS comentarioPaciente " +
            "FROM tb_triagens t " +
            "JOIN tb_pacientes p ON t.paciente_paciente_id = p.paciente_id " +
            "JOIN tb_profissionais pr ON t.profissional_profissional_id = pr.profissional_id " +
            "JOIN tb_resultclinicos r ON t.triagem_id = r.triagem_triagem_id " +
            "JOIN tb_feedbackprofissional fp ON t.triagem_id = fp.triagem_triagem_id " +
            "JOIN tb_feedbackpacientes fpa ON t.triagem_id = fpa.triagem_triagem_id",
            countQuery = "SELECT count(*) FROM tb_triagens t " +
                    "JOIN tb_pacientes p ON t.paciente_paciente_id = p.paciente_id " +
                    "JOIN tb_profissionais  pr ON t.profissional_profissional_id = pr.profissional_id " +
                    "JOIN tb_resultclinicos r ON t.triagem_id = r.triagem_triagem_id " +
                    "JOIN tb_feedbackprofissional fp ON t.triagem_id = fp.triagem_triagem_id " +
                    "JOIN tb_feedbackpacientes fpa ON t.triagem_id = fpa.triagem_triagem_id",
            nativeQuery = true)
    Page<TriagemCompletaProjection> findTriagemCompleta(Pageable pageable);
}
