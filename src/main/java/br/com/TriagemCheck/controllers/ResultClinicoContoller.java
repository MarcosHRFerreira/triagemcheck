package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.services.ResultClinicoService;
import br.com.TriagemCheck.validations.ResultClinicoValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resultclinicos")
public class ResultClinicoContoller {

    Logger logger = LogManager.getLogger(ResultClinicoContoller.class);

     final ResultClinicoService resultClinicoService;
     final ResultClinicoValidator resultClinicoValidator;

    public ResultClinicoContoller(ResultClinicoService resultClinicoService, ResultClinicoValidator resultClinicoValidator) {
        this.resultClinicoService = resultClinicoService;
        this.resultClinicoValidator = resultClinicoValidator;
    }
    @PostMapping
    public ResponseEntity<Object> salvarResultClinico(@RequestBody ResultClinicoRecordDto resultClinicoRecordDto, Errors errors){

        logger.debug("POST saveResultClinico resultClinicoRecordDto received {} ", resultClinicoRecordDto);

        resultClinicoValidator.validate(resultClinicoRecordDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultClinicoService.save(resultClinicoRecordDto));
    }
}
