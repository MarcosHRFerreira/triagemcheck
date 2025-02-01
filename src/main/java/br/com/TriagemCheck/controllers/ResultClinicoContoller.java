package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.ResultClinicoService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.ResultClinicoValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/resultclinicos")
public class ResultClinicoContoller {

    Logger logger = LogManager.getLogger(ResultClinicoContoller.class);

     final ResultClinicoService resultClinicoService;
     final ResultClinicoValidator resultClinicoValidator;

     final ProfissionalService profissionalService;
     final TriagemService triagemService;


    public ResultClinicoContoller(ResultClinicoService resultClinicoService, ResultClinicoValidator resultClinicoValidator, ProfissionalService profissionalService, TriagemService triagemService) {
        this.resultClinicoService = resultClinicoService;
        this.resultClinicoValidator = resultClinicoValidator;
        this.profissionalService = profissionalService;
        this.triagemService = triagemService;
    }
    @PostMapping("/triagens/{triagemId}/profissionais/{profissionalId}/resultclinico")
    public ResponseEntity<Object> salvarResultClinico(@PathVariable(value = "triagemId") UUID triagemId,
                                                      @PathVariable(value = "profissionalId") UUID profissionalId,
            @RequestBody ResultClinicoRecordDto resultClinicoRecordDto, Errors errors){

        logger.debug("POST saveResultClinico resultClinicoRecordDto received {} ", resultClinicoRecordDto);

        resultClinicoValidator.validate(resultClinicoRecordDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultClinicoService.save(resultClinicoRecordDto,
                triagemService.findById(triagemId).get(),
                profissionalService.findById(profissionalId).get()));
    }
}
