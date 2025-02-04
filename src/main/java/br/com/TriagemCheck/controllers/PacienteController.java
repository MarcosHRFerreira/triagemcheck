package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.specificationTemplate.SpecificationTemplate;
import br.com.TriagemCheck.validations.PacienteValidator;
import jakarta.validation.Valid;
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
@RequestMapping("/pacientes")
public class PacienteController {

    Logger logger = LogManager.getLogger(PacienteController.class);

    final PacienteService pacienteService;
    final PacienteValidator pacienteValidator;


    public PacienteController(PacienteService pacienteService, PacienteValidator pacienteValidator) {
        this.pacienteService = pacienteService;
        this.pacienteValidator = pacienteValidator;
    }
    @PostMapping
    public ResponseEntity<Object>save(@RequestBody PacienteRecordDto pacienteRecordDto, Errors errors){

        logger.debug("POST savePaciente pacienteRecordDto recebido {} ", pacienteRecordDto);

        pacienteValidator.validate(pacienteRecordDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.save(pacienteRecordDto));

    }
    @GetMapping
    public ResponseEntity<Page<PacienteModel>> getAll(SpecificationTemplate.PacienteSpec spec,
                                                                       Pageable pageable,
                                                                       @RequestParam(required = false) UUID pacienteId){
        Page<PacienteModel> pacienteModelPage = (pacienteId != null)
                ? pacienteService.findAll(SpecificationTemplate.pacienteUrseId(pacienteId).and(spec), pageable)
                : pacienteService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteModelPage);
    }
    @PutMapping("/{pacienteId}")
    public ResponseEntity<Object> update(@PathVariable(value = "pacienteId") UUID pacienteId,
                                                  @RequestBody @Valid PacienteRecordDto pacienteRecordDto){
        logger.debug("PUT alterarPaciente  pacienteRecordDto received {} ", pacienteRecordDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pacienteService.update(pacienteRecordDto, pacienteService.findById(pacienteId).get()));

    }
    @GetMapping("/{pacienteId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "pacienteId") UUID pacienteId){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.findById(pacienteId).get());
    }

    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "pacienteId") UUID pacienteId){
        logger.debug("Delete Paciente received {} ", pacienteId);
        pacienteService.delete(pacienteService.findById(pacienteId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Paciente deleted successfully.");
    }


}
