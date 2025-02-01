package br.com.TriagemCheck.validations;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FeedbackPacienteValidator implements Validator {

    Logger logger = LogManager.getLogger(FeedbackPacienteValidator.class);

    private  final Validator validator;

    public FeedbackPacienteValidator(@Qualifier("defaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        FeedbackPacienteRecordDto feedbackPacienteRecordDto = (FeedbackPacienteRecordDto) o;
        validator.validate(feedbackPacienteRecordDto, errors);
//        if(!errors.hasErrors()){
//            validatePacientecpf(pacienteRecordDto, errors);
//
//        }
    }


}
