package br.com.triagemcheck.controllers;

import br.com.triagemcheck.dtos.ProfissionalRecordDto;
import br.com.triagemcheck.models.ProfissionalModel;
import br.com.triagemcheck.services.ProfissionalService;
import br.com.triagemcheck.validations.ProfissionalValidator;
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
@RequestMapping("/profissionais")
public class ProfissionalController {

    Logger logger = LogManager.getLogger(ProfissionalController.class);

    final ProfissionalService profissionalService;
    final ProfissionalValidator profissionalValidator;

    public ProfissionalController(ProfissionalService profissionalService, ProfissionalValidator profissionalValidator) {
        this.profissionalService = profissionalService;
        this.profissionalValidator = profissionalValidator;
    }

    @Operation(summary = "Salva um novo Profissional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })

    @PostMapping
    public ResponseEntity<Object>save(@RequestBody @Valid ProfissionalRecordDto profissionalRecordDto, Errors errors){

        logger.debug("POST salvaProfissional profissionalRecordDto received {} ", profissionalRecordDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.save(profissionalRecordDto));
    }

    @Operation(summary = "Busca todos os profissionais")
    @ApiResponse(responseCode = "200", description = "Lista de profissionais retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ProfissionalModel>> getAll(Pageable pageable){
        Page<ProfissionalModel>  profissionalModelPage = profissionalService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(profissionalModelPage);
    }
    @GetMapping("/{profissionalId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "profissionalId") UUID profissionalId){
        logger.debug("Get getOne  profissionalId received {} ",profissionalId);


       ProfissionalModel profissional = profissionalService.findById(profissionalId);
        return ResponseEntity.status(HttpStatus.OK).body(profissional);

    }
    @Operation(summary = "Busca um profissional pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @PutMapping("/{profissionalId}")
    public ResponseEntity<Object> update(@Parameter(description = "ID do profissional") @PathVariable(value = "profissionalId") UUID profissionalId,
                                         @RequestBody  ProfissionalRecordDto profissionalRecordDto){
        logger.debug("PUT update  profissionalRecordDto received {} ", profissionalRecordDto);

            return ResponseEntity.status(HttpStatus.OK).body(profissionalService.update(profissionalRecordDto, profissionalId));
    }

    @Operation(summary = "Deleta um profissional pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @Transactional
    @DeleteMapping("/{profissionalId}")
    public ResponseEntity<Object> delete(@Parameter(description = "ID do profissional") @PathVariable(value = "profissionalId") UUID profissionalId){
        logger.debug("Delete Profissional profissionalId received {} ", profissionalId);

            profissionalService.delete(profissionalId);
            return ResponseEntity.status(HttpStatus.OK).body("Profissional deleted successfully.");

    }

}
