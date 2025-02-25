package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.ResultClinicoService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.ResultClinicoValidator;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/resultclinicos")
public class ResultClinicoContoller {

    Logger logger = LogManager.getLogger(ResultClinicoContoller.class);

     final ResultClinicoService resultClinicoService;
     final ResultClinicoValidator resultClinicoValidator;

     final ProfissionalService profissionalService;
     final TriagemService triagemService;


    public ResultClinicoContoller(ResultClinicoService resultClinicoService, ResultClinicoValidator resultClinicoValidator, ProfissionalService profissionalService, TriagemService triagemService) {
        this.resultClinicoService = resultClinicoService;
        this.resultClinicoValidator = resultClinicoValidator;
        this.profissionalService = profissionalService;
        this.triagemService = triagemService;
    }
    @Operation(summary = "Salvar ResultClinico", description = "Salva um novo resultado clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping("/{triagemId}")
    public ResponseEntity<Object> salvarResultClinico(@Parameter(description = "ID da triagem") @PathVariable(value = "triagemId") UUID triagemId,
                                                      @RequestBody @Valid ResultClinicoRecordDto resultClinicoRecordDto){

        logger.debug("POST saveResultClinico resultClinicoRecordDto received {} ", resultClinicoRecordDto);


            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(resultClinicoService.save(resultClinicoRecordDto, triagemId));

    }

    @Operation(summary = "Obter todos os ResultClinicos", description = "Obtém uma lista paginada de todos os resultados clínicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem bem-sucedida")
    })
    @GetMapping
    public ResponseEntity<Page<ResultClinicosModel>> getAll(Pageable pageable){
        Page<ResultClinicosModel>  resultClinicosModelPage = resultClinicoService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(resultClinicosModelPage);
    }

    @Operation(summary = "Obter um ResultClinico", description = "Obtém um resultado clínico específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Resultado não encontrado")
    })
    @GetMapping("/{resultadoId}")
    public ResponseEntity<Object> getOne(@Parameter(description = "ID do resultado") @PathVariable(value = "resultadoId") UUID resultadoId){

        return ResponseEntity.status(HttpStatus.OK).body(resultClinicoService.findById(resultadoId));

    }

    @Operation(summary = "Atualizar ResultClinico", description = "Atualiza um resultado clínico existente.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização bem-sucedida")})
    @PutMapping("/{resultadoId}")
    public ResponseEntity<Object> update(@Parameter(description = "ID do resultado") @PathVariable(value="resultadoId") UUID resultadoId,
                                         @RequestBody ResultClinicoRecordDto resultClinicoRecordDto) {

        logger.debug("PUT resultClinico resultClinicoRecordDto received {} ", resultClinicoRecordDto);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(resultClinicoService.update(resultClinicoRecordDto,  resultadoId));

    }
}
