package br.com.TriagemCheck.validations;

import br.com.TriagemCheck.dtos.TriagemRecordDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TriagemValidator implements Validator {

    Logger logger = LogManager.getLogger(TriagemValidator.class);

    private  final Validator validator;

    public TriagemValidator(@Qualifier("defaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        TriagemRecordDto triagemRecordDto = (TriagemRecordDto) o;
        validator.validate(triagemRecordDto, errors);
//        if(!errors.hasErrors()){
//            validatePacientecpf(pacienteRecordDto, errors);
//
//        }
    }

}
