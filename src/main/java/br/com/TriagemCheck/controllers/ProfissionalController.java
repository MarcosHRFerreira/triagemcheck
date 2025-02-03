package br.com.TriagemCheck.controllers;


import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.specificationTemplate.SpecificationTemplate;
import br.com.TriagemCheck.validations.ProfissionalValidator;
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
@RequestMapping("/profissionais")
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
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.save(profissionalRecordDto));
    }

    @GetMapping
    public ResponseEntity<Page<ProfissionalModel>> getAll(SpecificationTemplate.ProfissionalSpec spec,
                                                          Pageable pageable,
                                                          @RequestParam(required = false) UUID profissionalId){
        Page<ProfissionalModel>  profissionalModelPage = (profissionalId != null)
                ? profissionalService.findAll(SpecificationTemplate.profissionalId(profissionalId).and(spec), pageable)
                : profissionalService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(profissionalModelPage);
    }


}
