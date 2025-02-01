package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.services.FeedbackProfissionalService;
import br.com.TriagemCheck.validations.FeedbackProfissionalValidator;
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
@RequestMapping("/feedbackprofissionais")
public class FeedbackProfissionalController {

    Logger logger = LogManager.getLogger(FeedbackProfissionalController.class);

    final FeedbackProfissionalService feedbackProfissionalService;
    final FeedbackProfissionalValidator feedbackProfissionalValidator;

    public FeedbackProfissionalController(FeedbackProfissionalService feedbackProfissionalService, FeedbackProfissionalValidator feedbackProfissionalValidator) {
        this.feedbackProfissionalService = feedbackProfissionalService;
        this.feedbackProfissionalValidator = feedbackProfissionalValidator;
    }
    @PostMapping
    public ResponseEntity<Object>saveFeedbackProfissional(@RequestBody FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, Errors errors){

        logger.debug("POST saveFeedbackProfissional feedbackProfissionalRecordDto received {} ", feedbackProfissionalRecordDto);

        feedbackProfissionalValidator.validate(feedbackProfissionalRecordDto,errors);

        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackProfissionalService.salvar(feedbackProfissionalRecordDto));
    }
}
