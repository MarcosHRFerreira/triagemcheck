package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.FeedbackPacienteValidator;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/feedbackpacientes")
public class FeedbackPacienteController {

    Logger logger = LogManager.getLogger(FeedbackPacienteController.class);

    final FeedbackPacienteService feedbackPacienteService;
    final FeedbackPacienteValidator feedbackPacienteValidator;
    final TriagemService triagemService;
    final PacienteService pacienteService;

    public FeedbackPacienteController(FeedbackPacienteService feedbackPacienteService, FeedbackPacienteValidator feedbackPacienteValidator, TriagemService triagemService, PacienteService pacienteService) {
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
    @PostMapping("/{triagemId}")
    public ResponseEntity<Object> save(@Parameter(description = "ID da triagem")@PathVariable(value="triagemId") UUID triagemId,
                                       @RequestBody @Valid FeedbackPacienteRecordDto feedbackPacienteRecordDto, Errors errors){

        logger.debug("POST salvarFeedbackPaciente feedbackPacienteRecordDto recebido {} ", feedbackPacienteRecordDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackPacienteService.save(feedbackPacienteRecordDto,  triagemId));

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

         return ResponseEntity.status(HttpStatus.OK).body(feedbackPacienteService.findById(feedbackpacienteId));
    }

    @Operation(summary = "Atualizar feedback do paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado"),
            @ApiResponse(responseCode = "409", description = "Avaliacao tem que ser entre 1 e 5.")
    })
    @PutMapping("/{feedbackpacienteId}")
    public ResponseEntity<Object> update(@Parameter(description = "ID do feedback do paciente") @PathVariable(value="feedbackpacienteId") UUID feedbackpacienteId,
                                         @RequestBody FeedbackPacienteRecordDto feedbackPacienteRecordDto){

        logger.debug("PUT updateFeedBackPaciente FeedbackPacienteRecordDto received {} ", feedbackPacienteRecordDto);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(feedbackPacienteService.update(feedbackpacienteId,feedbackPacienteRecordDto));

    }

    @Operation(summary = "Deletar feedback do paciente")
    @ApiResponse(responseCode = "200", description = "Feedback deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    @Transactional
    @DeleteMapping("/{feedbackpacienteId}")
    public ResponseEntity<Object> delete(@Parameter(description = "ID do feedback do paciente") @PathVariable(value = "feedbackpacienteId") UUID feedbackpacienteId){
        logger.debug("DELETE delete FeedbackPacientes feedbackpacienteId received {} ", feedbackpacienteId);

        feedbackPacienteService.delete(feedbackpacienteId);
        return ResponseEntity.status(HttpStatus.OK).body("Feedback deletado com sucesso.");
    }
}
