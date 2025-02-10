package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.FeedbackPacienteValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/feedbackpacientes")
public class FeedbackPacienteController {

    Logger logger = LogManager.getLogger(FeedbackPacienteController.class);

    final FeedbackPacienteService feedbackPacienteService;
    final FeedbackPacienteValidator feedbackPacienteValidator;
    final TriagemService triagemService;
    final PacienteService pacienteService;

    public FeedbackPacienteController(FeedbackPacienteService feedbackPacienteService, Validator validator, FeedbackPacienteValidator feedbackPacienteValidator, TriagemService triagemService, PacienteService pacienteService) {
        this.feedbackPacienteService = feedbackPacienteService;
        this.feedbackPacienteValidator = feedbackPacienteValidator;
        this.triagemService = triagemService;
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Salvar feedback do paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação do feedback")
    })
    @PostMapping("/pacientes/{pacienteId}/triagens/{triagemId}/feedbackpaciente")

    public ResponseEntity<Object> save(@Parameter(description = "ID do paciente")@PathVariable(value ="pacienteId") UUID pacienteId,
                                       @Parameter(description = "ID da triagem")@PathVariable(value="triagemId") UUID triagemId,
            @RequestBody FeedbackPacienteRecordDto feedbackPacienteRecordDto, Errors errors){

        logger.debug("POST salvarFeedbackPaciente feedbackPacienteRecordDto recebido {} ", feedbackPacienteRecordDto);

        feedbackPacienteValidator.validate(feedbackPacienteRecordDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        Optional<PacienteModel> pacienteOptional = pacienteService.findById(pacienteId);
        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);

        if (pacienteOptional.isPresent() && triagemOptional.isPresent()) {
            PacienteModel paciente = pacienteOptional.get();
            TriagemModel triagem = triagemOptional.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackPacienteService.save(feedbackPacienteRecordDto, paciente, triagem));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente ou triagem não encontrados.");
        }
    }
    @Operation(summary = "Obter todos os feedbacks dos pacientes")
    @ApiResponse(responseCode = "200", description = "Feedbacks obtidos com sucesso")
    @GetMapping
    public ResponseEntity<Page<FeedbackPacienteModel>> getAll(Pageable pageable,
                                            @Parameter(description = "ID do feedback do paciente") @RequestParam(required = false) UUID feedbackpacienteId){
        Page<FeedbackPacienteModel>  feedbackpacienteModelPage = feedbackPacienteService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackpacienteModelPage);
    }

    @Operation(summary = "Obter um feedback do paciente pelo ID")
    @ApiResponse(responseCode = "200", description = "Feedback obtido com sucesso")
    @GetMapping("/{feedbackpacienteId}")
    public ResponseEntity<Object> getOne(@Parameter(description = "ID do feedback do paciente") @PathVariable(value = "feedbackpacienteId") UUID feedbackpacienteId){

        Optional<FeedbackPacienteModel> feedbackOptional = feedbackPacienteService.findById(feedbackpacienteId);
        if (feedbackOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(feedbackOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualizar feedback do paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    })
    @PutMapping("/{feedbackpacienteId}/pacientes/{pacienteId}/triagens/{triagemId}/feedbackpaciente")
    public ResponseEntity<Object> update(@Parameter(description = "ID do paciente") @PathVariable(value ="pacienteId") UUID pacienteId,
                                         @Parameter(description = "ID da triagem") @PathVariable(value="triagemId") UUID triagemId,
                                         @Parameter(description = "ID do feedback do paciente") @PathVariable(value="feedbackpacienteId") UUID feedbackpacienteId,
                                         @RequestBody FeedbackPacienteRecordDto feedbackPacienteRecordDto){

        logger.debug("PUT updateFeedBackPaciente FeedbackPacienteRecordDto received {} ", feedbackPacienteRecordDto);

        Optional<FeedbackPacienteModel> feedbackOptional = feedbackPacienteService.findPacienteTriagemInFeedback(pacienteId, triagemId, feedbackpacienteId);
        if (feedbackOptional.isPresent()) {
            FeedbackPacienteModel feedbackPaciente = feedbackOptional.get();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(feedbackPacienteService.update(feedbackPacienteRecordDto, feedbackPaciente));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback não encontrado.");
        }
    }
    @Operation(summary = "Deletar feedback do paciente")
    @ApiResponse(responseCode = "200", description = "Feedback deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    @Transactional
    @DeleteMapping("/{feedbackpacienteId}")
    public ResponseEntity<Object> delete(@Parameter(description = "ID do feedback do paciente") @PathVariable(value = "feedbackpacienteId") UUID feedbackpacienteId){
        logger.debug("DELETE delete FeedbackPacientes feedbackpacienteId received {} ", feedbackpacienteId);

        Optional<FeedbackPacienteModel> feedbackOptional = feedbackPacienteService.findById(feedbackpacienteId);
        if (feedbackOptional.isPresent()) {
            feedbackPacienteService.delete(feedbackOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Feedback Paciente deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback não encontrado.");
        }

    }

}
