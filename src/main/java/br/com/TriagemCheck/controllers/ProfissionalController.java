package br.com.TriagemCheck.controllers;


import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.validations.ProfissionalValidator;
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
@RequestMapping("/profissionalsaude")
public class ProfissionalController {

    Logger logger = LogManager.getLogger(ProfissionalController.class);

    final ProfissionalService profissionalService;
    final ProfissionalValidator profissionalValidator;

    public ProfissionalController(ProfissionalService profissionalService, ProfissionalValidator profissionalValidator) {
        this.profissionalService = profissionalService;
        this.profissionalValidator = profissionalValidator;
    }
    @PostMapping
    public ResponseEntity<Object>salvaMedico(@RequestBody ProfissionalRecordDto profissionalRecordDto, Errors errors){

        logger.debug("POST salvaMedico medicoRecordDto received {} ", profissionalRecordDto);

        profissionalValidator.validate(profissionalRecordDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.salvar(profissionalRecordDto));
    }
}
