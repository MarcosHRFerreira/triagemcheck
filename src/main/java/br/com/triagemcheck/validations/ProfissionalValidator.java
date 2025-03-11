package br.com.triagemcheck.validations;

import br.com.triagemcheck.dtos.ProfissionalRecordDto;
import br.com.triagemcheck.enums.Especialidade;
import br.com.triagemcheck.enums.StatusOperacional;
import br.com.triagemcheck.services.ProfissionalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfissionalValidator implements Validator{

    Logger logger = LogManager.getLogger(ProfissionalValidator.class);

    private final Validator validator;
    final ProfissionalService profissionalService;


    public ProfissionalValidator(@Qualifier("defaultValidator") Validator validator, ProfissionalService profissionalService) {
        this.validator = validator;
        this.profissionalService = profissionalService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


    @Override
    public void validate(Object o, Errors errors) {
        ProfissionalRecordDto profissionalRecordDto = (ProfissionalRecordDto) o;
        validator.validate(profissionalRecordDto, errors);
        if(!errors.hasErrors()){
            validateMedicoCrm(profissionalRecordDto, errors);
            validateVerificaStatusProfissional(profissionalRecordDto,errors);
        }
    }

    public void validateVerificaStatusProfissional(ProfissionalRecordDto profissionalRecordDto, Errors errors) {
        if (profissionalRecordDto.statusOperacional().equals(StatusOperacional.INATIVO)){
            errors.rejectValue("statusOperacional", "statusOperacionalConflict", "O Status Operacional deve estar Ativo.");
            logger.error("Error validation Status Operacional: O Status Operacional deve estar Ativo.");
        }

    }

    public void validateMedicoCrm(ProfissionalRecordDto profissionalRecordDto, Errors errors) {
        String crm = profissionalRecordDto.crm();
        if (!profissionalRecordDto.especialidade().equals(Especialidade.ENFERMAGEM)) {
            if (crm.isEmpty()) {
                errors.rejectValue("crm", "medicoCRMConflict", "CRM deve ser preenchido.");
                logger.error("Error validation CRM: CRM deve ser preenchido.");
            } else if (profissionalService.existsBycrm(crm)) {
                errors.rejectValue("crm", "medicoCRMConflict", "CRM já existe.");
                logger.error("Error validation CRM: {} ", crm);
            }
        }
    }
}
