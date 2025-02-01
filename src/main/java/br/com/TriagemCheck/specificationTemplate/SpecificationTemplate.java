package br.com.TriagemCheck.specificationTemplate;

import br.com.TriagemCheck.models.PacienteModel;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "nome", spec = LikeIgnoreCase.class)
    })
    public interface PacienteSpec extends Specification<PacienteModel> {}

    public static Specification<PacienteModel> pacienteUrseId(final UUID pacienteId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("pacienteId"), pacienteId);
        };
    }
}
