package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.validations.PacienteValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Validated
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
    @Operation(summary = "Salvar um novo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação do paciente")
    })
    @PostMapping
    public ResponseEntity<Object>save(@Parameter(description = "Dados do paciente") @RequestBody @Valid PacienteRecordDto pacienteRecordDto, Errors errors){

        logger.debug("POST savePaciente pacienteRecordDto recebido {} ", pacienteRecordDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.save(pacienteRecordDto));

    }

    @Operation(summary = "Obter todos os pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacientes encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<PacienteModel>> getAll(Pageable pageable, @RequestParam(required = false) UUID pacienteId){
        Page<PacienteModel> pacienteModelPage = pacienteService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteModelPage);
    }

    @Operation(summary = "Atualizar um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @PutMapping("/{pacienteId}")
    public ResponseEntity<Object> update(@Parameter(description = "ID do paciente") @PathVariable(value = "pacienteId") UUID pacienteId,
                                         @Parameter(description = "Dados do paciente") @RequestBody  PacienteRecordDto pacienteRecordDto){
        logger.debug("PUT alterarPaciente  pacienteRecordDto received {} ", pacienteRecordDto);

            return ResponseEntity.status(HttpStatus.OK).body(pacienteService.update(pacienteRecordDto, pacienteId));

    }

    @Operation(summary = "Deletar um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")

    })
    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<Object> delete(@Parameter(description = "ID do paciente") @PathVariable(value = "pacienteId") UUID pacienteId){

            pacienteService.delete(pacienteId);
            return ResponseEntity.status(HttpStatus.OK).body("Paciente deleted successfully.");
    }

    @Operation(summary = "Obter um paciente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> getOneCpf(@Parameter(description = "CPF do paciente") @PathVariable(value = "cpf") String cpf ){

        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.findByCpf(cpf));
    }

    @Operation(summary = "Obter um paciente por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @GetMapping("/id/{pacienteId}")
    public ResponseEntity<Object> getOne(@Parameter(description = "ID do paciente") @PathVariable(value = "pacienteId") UUID pacienteId){

        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.findById(pacienteId));

    }


}
