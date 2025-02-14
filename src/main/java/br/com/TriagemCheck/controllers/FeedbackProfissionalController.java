package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.FeedbackProfissionalService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.FeedbackProfissionalValidator;
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

import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/feedbackprofissionais")
public class FeedbackProfissionalController {

    Logger logger = LogManager.getLogger(FeedbackProfissionalController.class);

    final FeedbackProfissionalService feedbackProfissionalService;
    final FeedbackProfissionalValidator feedbackProfissionalValidator;
    final ProfissionalService profissionalService;
    final TriagemService triagemService;

    public FeedbackProfissionalController(FeedbackProfissionalService feedbackProfissionalService, FeedbackProfissionalValidator feedbackProfissionalValidator, ProfissionalService profissionalService, TriagemService triagemService) {
        this.feedbackProfissionalService = feedbackProfissionalService;
        this.feedbackProfissionalValidator = feedbackProfissionalValidator;
        this.profissionalService = profissionalService;
        this.triagemService = triagemService;
    }
    @Operation(summary = "Salvar feedback do profissional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação do feedback")
    })
    @PostMapping("/profissionais/{profissionalId}/triagens/{triagemId}/feedbackprofissional")
    public ResponseEntity<Object>saveFeedbackProfissional(@Parameter(description = "ID do profissional") @PathVariable(value = "profissionalId") UUID profissionalId,
                                                          @Parameter(description = "ID da triagem") @PathVariable(value="triagemId") UUID triagemId,
                                                          @RequestBody @Valid FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, Errors errors){

        logger.debug("POST saveFeedbackProfissional feedbackProfissionalRecordDto received {} ", feedbackProfissionalRecordDto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(feedbackProfissionalService.save(feedbackProfissionalRecordDto, profissionalId, triagemId));
    }



    @Operation(summary = "Obter todos os feedbacks dos profissionais")
    @ApiResponse(responseCode = "200", description = "Feedbacks obtidos com sucesso")
    @GetMapping
    public ResponseEntity<Page<FeedbackProfissionalModel>> getAll( Pageable pageable,
                                                                  @RequestParam(required = false) UUID feedbackprofissionalId){
        Page<FeedbackProfissionalModel>  feedbackProfissionalModelPage = feedbackProfissionalService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackProfissionalModelPage);
    }

    @Operation(summary = "Obter um feedback do profissional pelo ID")
    @ApiResponse(responseCode = "200", description = "Feedback obtido com sucesso")
    @GetMapping("/{feedbackprofissionalId}")
    public ResponseEntity<Object> getOne(@Parameter(description = "ID do feedback do profissional") @PathVariable(value = "feedbackprofissionalId") UUID feedbackprofissionalId){

        logger.debug("GET getOne feedbackprofissionalId received {} ", feedbackprofissionalId);

        return ResponseEntity.status(HttpStatus.OK).body(feedbackProfissionalService.findById(feedbackprofissionalId));

    }
    @Operation(summary = "Atualizar feedback do profissional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    })
    @PutMapping("/{feedbackprofissionalId}/profissionais/{profissionalId}/triagens/{triagemId}/feedbackprofissional")
    public ResponseEntity<Object> updateFeedBackProfissional(@Parameter(description = "ID do feedback do profissional") @PathVariable(value ="profissionalId") UUID profissionalId,
                                                             @Parameter(description = "ID da triagem")  @PathVariable(value="triagemId") UUID triagemId,
                                                             @Parameter(description = "ID do feedback do profissional") @PathVariable(value="feedbackprofissionalId") UUID feedbackprofissionalId,
                                                         @RequestBody FeedbackProfissionalRecordDto feedbackProfissionalRecordDto){

        logger.debug("PUT updateFeedBackProfissional FeedbackProfissionalRecordDto received {} ", feedbackProfissionalRecordDto);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(feedbackProfissionalService.update(feedbackProfissionalRecordDto, triagemId, profissionalId, feedbackprofissionalId));

    }

    @Operation(summary = "Deletar feedback do profissional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    })
    @Transactional
    @DeleteMapping("/{feedbackprofissionalId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "feedbackprofissionalId") UUID feedbackprofissionalId){

        logger.debug("DELETE delete FeedbackProfissionais feedbackprofissionalId received {} ", feedbackprofissionalId);
        feedbackProfissionalService.delete(feedbackprofissionalId);
        return ResponseEntity.status(HttpStatus.OK).body("Feedback deletado com sucesso.");
    }

}
