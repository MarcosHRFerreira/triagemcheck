package br.com.triagemcheck.validations;

import br.com.triagemcheck.dtos.FeedbackProfissionalRecordDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FeedbackProfissionalValidator implements Validator {

    Logger logger = LogManager.getLogger(FeedbackProfissionalValidator.class);

    private  final Validator validator;

    public FeedbackProfissionalValidator(@Qualifier("defaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        FeedbackProfissionalRecordDto feedbackProfissionalRecordDto = (FeedbackProfissionalRecordDto) o;
        validator.validate(feedbackProfissionalRecordDto, errors);
        if(!errors.hasErrors()){
            validateSeveridadeEficacia(feedbackProfissionalRecordDto, errors);
        }
    }

    private void validateSeveridadeEficacia(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, Errors errors) {
        if(feedbackProfissionalRecordDto.avaliacaoeficacia()<=0 || feedbackProfissionalRecordDto.avaliacaoeficacia()>6 ){
            errors.rejectValue("avaliacaoeficacia", "FeedbackEficacia" +
                    "SeveridadeConflict", "Avaliacao tem que ser entre 1 e 5.");
            logger.error("Error validation Eficacia: {} ", feedbackProfissionalRecordDto.avaliacaoeficacia());
        }
        if(feedbackProfissionalRecordDto.avaliacaoseveridade()<=0 || feedbackProfissionalRecordDto.avaliacaoseveridade()>6 ){
            errors.rejectValue("avaliacaoseveridade", "FeedbackEficacia" +
                    "SeveridadeConflict", "Severidade tem que ser entre 1 e 5.");
            logger.error("Error validation Eficacia: {} ", feedbackProfissionalRecordDto.avaliacaoseveridade());
        }
    }
}
