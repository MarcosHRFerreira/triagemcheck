package br.com.TriagemCheck.validations;


import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResultClinicoValidator implements Validator {

    Logger logger = LogManager.getLogger(ResultClinicoValidator.class);

    private  final Validator validator;

    public ResultClinicoValidator(@Qualifier("defaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ResultClinicoRecordDto resultClinicoRecordDto = (ResultClinicoRecordDto) o;
        validator.validate(resultClinicoRecordDto, errors);

    }

}
