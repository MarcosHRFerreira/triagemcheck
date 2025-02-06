package br.com.TriagemCheck.validations;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.repositories.FeedbackPacienteRepository;
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
    private final FeedbackPacienteRepository feedbackPacienteRepository;

    public FeedbackPacienteValidator(@Qualifier("defaultValidator") Validator validator,
                                     FeedbackPacienteRepository feedbackPacienteRepository) {
        this.validator = validator;
        this.feedbackPacienteRepository = feedbackPacienteRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        FeedbackPacienteRecordDto feedbackPacienteRecordDto = (FeedbackPacienteRecordDto) o;
        validator.validate(feedbackPacienteRecordDto, errors);
        if(!errors.hasErrors()){
            validateAvaliacao(feedbackPacienteRecordDto, errors);
        }
    }
    private void validateAvaliacao(FeedbackPacienteRecordDto feedbackPacienteRecordDto, Errors errors) {
        if(feedbackPacienteRecordDto.avaliacao()<=0 || feedbackPacienteRecordDto.avaliacao()>6 ){
            errors.rejectValue("avaliacao", "FeedbackPacienteAvaliacaoConflict", "Avaliacao tem que ser entre 1 e 5.");
            logger.error("Error validation Avaliacao: {} ", feedbackPacienteRecordDto.avaliacao());
        }
    }
}
