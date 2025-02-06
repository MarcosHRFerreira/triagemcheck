package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.specificationTemplate.SpecificationTemplate;
import br.com.TriagemCheck.validations.TriagemValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/triagens")
public class TriagemController {

    Logger logger = LogManager.getLogger(TriagemController.class);

    final TriagemService triagemService;
    final TriagemValidator triagemValidator;
    final PacienteService pacienteService;
    final ProfissionalService profissionalService;

    public TriagemController(TriagemService triagemService, TriagemValidator triagemValidator, PacienteService pacienteService, ProfissionalService profissionalService ) {
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

          return ResponseEntity.status(HttpStatus.CREATED).body(triagemService.save(triagemRecordDto,
                  pacienteService.findById(pacienteId).get(),
                  profissionalService.findById(profissionalId).get()));

    }

    @GetMapping
    public ResponseEntity<Page<TriagemModel>> getAll(SpecificationTemplate.TriagemSpec spec,
                                                     Pageable pageable,
                                                     @RequestParam(required = false) UUID triagemId){
        Page<TriagemModel>  triagemModelPage = (triagemId != null)
                ? triagemService.findAll(SpecificationTemplate.triagemId(triagemId).and(spec), pageable)
                : triagemService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(triagemModelPage);
    }

    @GetMapping("/{triagemId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "triagemId") UUID triagemId){
        return ResponseEntity.status(HttpStatus.OK).body(triagemService.findById(triagemId).get());
    }

    @PutMapping("/{triagemId}/pacientes/{pacienteId}/profissionais/{profissionalId}/triagem")
    public ResponseEntity<Object> update(@PathVariable(value ="pacienteId") UUID pacienteId,
                                         @PathVariable(value="profissionalId") UUID profissionalId,
                                         @PathVariable(value="triagemId") UUID triagemId,
                                         @RequestBody TriagemRecordDto triagemRecordDto){

        logger.debug("PUT triagens triagemRecordDto received {} ", triagemRecordDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(triagemService.update(triagemRecordDto, triagemService.
                        findPacienteProfissionalInTriagem(pacienteId,profissionalId, triagemId).get()));
    }
    @GetMapping("/completa")
    public ResponseEntity<Page<TriagemCompletaRecordDto>> getTriagemCompleta(SpecificationTemplate.TriagemSpec spec,
                                                                             Pageable pageable){
        Page<TriagemCompletaRecordDto> triagemCompleta = triagemService.findTriagemCompleta(spec, pageable );
        return ResponseEntity.status(HttpStatus.OK).body(triagemCompleta);
    }




}
