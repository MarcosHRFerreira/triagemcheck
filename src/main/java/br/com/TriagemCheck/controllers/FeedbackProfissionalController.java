package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.services.FeedbackProfissionalService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.specificationTemplate.SpecificationTemplate;
import br.com.TriagemCheck.validations.FeedbackProfissionalValidator;
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
    @PostMapping("/{profissionais}/{profissionalId}/triagens/{triagemId}/feedbackprofissional")
    public ResponseEntity<Object>saveFeedbackProfissional(@PathVariable(value = "profissionalId") UUID profissionalId,
                                                          @PathVariable(value="triagemId") UUID triagemId,
            @RequestBody FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, Errors errors){

        logger.debug("POST saveFeedbackProfissional feedbackProfissionalRecordDto received {} ", feedbackProfissionalRecordDto);

        feedbackProfissionalValidator.validate(feedbackProfissionalRecordDto,errors);

        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackProfissionalService.save(feedbackProfissionalRecordDto,
                profissionalService.findById(profissionalId).get(),
                triagemService.findById(triagemId).get()));
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackProfissionalModel>> getAll(SpecificationTemplate.FeedbackprofissionalSpec spec,
                                                                  Pageable pageable,
                                                                  @RequestParam(required = false) UUID feedbackprofissionalId){
        Page<FeedbackProfissionalModel>  feedbackProfissionalModelPage = (feedbackprofissionalId != null)
                ? feedbackProfissionalService.findAll(SpecificationTemplate.feedbackprofissionalId(feedbackprofissionalId).and(spec), pageable)
                : feedbackProfissionalService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackProfissionalModelPage);
    }

    @GetMapping("/{feedbackprofissionalId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "feedbackprofissionalId") UUID feedbackprofissionalId){
        return ResponseEntity.status(HttpStatus.OK).body(feedbackProfissionalService.findById(feedbackprofissionalId).get());
    }



    @PutMapping("/{feedbackprofissionalId}/profissionais/{profissionalId}/triagens/{triagemId}/feedbackprofissional")
    public ResponseEntity<Object> updateFeedBackProfissional(@PathVariable(value ="profissionalId") UUID profissionalId,
                                                         @PathVariable(value="triagemId") UUID triagemId,
                                                         @PathVariable(value="feedbackprofissionalId") UUID feedbackprofissionalId,
                                                         @RequestBody FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, Errors errors){

        logger.debug("PUT updateFeedBackProfissional FeedbackProfissionalRecordDto received {} ", feedbackProfissionalRecordDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(feedbackProfissionalService.update(feedbackProfissionalRecordDto, feedbackProfissionalService.
                        findProfissionalTriagemInFeedback(profissionalId, triagemId,feedbackprofissionalId).get()));
    }




}
