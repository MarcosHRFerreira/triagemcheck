package br.com.triagemcheck.validations;

import br.com.triagemcheck.dtos.PacienteRecordDto;
import br.com.triagemcheck.services.PacienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PacienteValidator implements Validator {

    Logger logger = LogManager.getLogger(PacienteValidator.class);

    private  final Validator validator;
    final PacienteService pacienteService;


    public PacienteValidator(@Qualifier("defaultValidator") Validator validator, PacienteService pacienteService) {
        this.validator = validator;
        this.pacienteService = pacienteService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        PacienteRecordDto pacienteRecordDto = (PacienteRecordDto) o;
        validator.validate(pacienteRecordDto, errors);
        if(!errors.hasErrors()){
            validatePacientecpf(pacienteRecordDto, errors);

        }
    }

    private void validatePacientecpf(PacienteRecordDto pacienteRecordDto, Errors errors){
        if(pacienteService.existsBycpf(pacienteRecordDto.cpf())){
            errors.rejectValue("cpf", "courseCPFConflict", "CPF ja existe.");
            logger.error("Error validation CPF: {} ", pacienteRecordDto.cpf());
        }
    }



}
