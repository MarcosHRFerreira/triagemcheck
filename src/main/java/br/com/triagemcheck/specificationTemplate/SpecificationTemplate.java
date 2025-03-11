package br.com.triagemcheck.specificationTemplate;

import br.com.triagemcheck.models.*;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "cpf",  spec = Equal.class),
            @Spec(path = "dataFeedback",  spec = Equal.class),
            @Spec(path = "nome",  spec = LikeIgnoreCase.class)
    })

    public interface PacienteSpec extends Specification<PacienteModel> {}

    public interface FeedbackpacienteSpec extends Specification<FeedbackPacienteModel> {}

    public interface FeedbackprofissionalSpec extends Specification<FeedbackProfissionalModel> {}

    public interface ProfissionalSpec extends Specification<ProfissionalModel> {}

    public interface ResultClinicoSpec extends Specification<ResultClinicosModel> {}

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface TriagemSpec extends Specification<TriagemModel> {}

    public static Specification<PacienteModel> pacienteUrseId(final UUID pacienteId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("pacienteId"), pacienteId);
        };
    }

    public static Specification<FeedbackPacienteModel> feedbackpacienteId(final UUID feedbackpacienteId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("feedbackpacienteId"), feedbackpacienteId);
        };
    }

    public static Specification<FeedbackProfissionalModel> feedbackprofissionalId(final UUID feedbackprofissionalId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("feedbackprofissionalId"), feedbackprofissionalId);
        };
    }

    public static Specification<ProfissionalModel> profissionalId(final UUID profissionalId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("profissionalId"), profissionalId);
        };
    }

    public static Specification<ResultClinicosModel> resultadoId(final UUID resultadoId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("resultadoId"), resultadoId);
        };
    }

    public static Specification<TriagemModel> triagemId(final UUID triagemId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("triagemId"), triagemId);
        };
    }





}
