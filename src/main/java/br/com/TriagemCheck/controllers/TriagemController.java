package br.com.TriagemCheck.controllers;


import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.TriagemValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TriagemController {

    Logger logger = LogManager.getLogger(TriagemController.class);

    final TriagemService triagemService;
    final TriagemValidator triagemValidator;
    final PacienteService pacienteService;
    final ProfissionalService profissionalService;

    public TriagemController(TriagemService triagemService, TriagemValidator triagemValidator, PacienteService pacienteService, ProfissionalService profissionalService) {
        this.triagemService = triagemService;
        this.triagemValidator = triagemValidator;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
    }
    @PostMapping("/pacientes/{pacienteId}/profissionais/{profissionalId}/triagens")
    public ResponseEntity<Object> savarTriagem(@PathVariable(value = "pacienteId") UUID pacienteId,
                                               @PathVariable(value="profissionalId") UUID profissionalId,
            @RequestBody TriagemRecordDto triagemRecordDto, Errors errors){

        logger.debug("POST savarTriagem triagemRecordDto received {} ", triagemRecordDto);

        triagemValidator.validate(triagemRecordDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

          return ResponseEntity.status(HttpStatus.CREATED).body(triagemService.salvar(triagemRecordDto,
                  pacienteService.findById(pacienteId).get(),
                  profissionalService.findById(profissionalId).get()));

    }
}
