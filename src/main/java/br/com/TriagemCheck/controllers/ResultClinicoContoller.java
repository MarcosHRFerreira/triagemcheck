package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.ResultClinicoService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.ResultClinicoValidator;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
    @PostMapping("/triagens/{triagemId}/profissionais/{profissionalId}/resultclinico")
    public ResponseEntity<Object> salvarResultClinico(@Parameter(description = "ID da triagem") @PathVariable(value = "triagemId") UUID triagemId,
                                                      @Parameter(description = "ID do profissional") @PathVariable(value = "profissionalId") UUID profissionalId,
            @RequestBody ResultClinicoRecordDto resultClinicoRecordDto, Errors errors){

        logger.debug("POST saveResultClinico resultClinicoRecordDto received {} ", resultClinicoRecordDto);

        resultClinicoValidator.validate(resultClinicoRecordDto, errors);

        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);
        Optional<ProfissionalModel> profissionalOptional = profissionalService.findById(profissionalId);

        if (triagemOptional.isPresent() && profissionalOptional.isPresent()) {
            TriagemModel triagem = triagemOptional.get();
            ProfissionalModel profissional = profissionalOptional.get();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(resultClinicoService.save(resultClinicoRecordDto, triagem, profissional));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Triagem ou profissional não encontrados.");
        }
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

        Optional<ResultClinicosModel> resultOptional = resultClinicoService.findById(resultadoId);
        if (resultOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(resultOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resultado não encontrado.");
        }
    }

    @Operation(summary = "Atualizar ResultClinico", description = "Atualiza um resultado clínico existente.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização bem-sucedida")})
    @PutMapping("/{resultadoId}/profissionais/{profissionalId}/triagens/{triagemId}/resultado")
    public ResponseEntity<Object> update(@Parameter(description = "ID do profissional")   @PathVariable(value ="profissionalId") UUID profissionalId,
                                         @Parameter(description = "ID da triagem") @PathVariable(value="triagemId") UUID triagemId,
                                         @Parameter(description = "ID do resultado") @PathVariable(value="resultadoId") UUID resultadoId,
                                         @RequestBody ResultClinicoRecordDto resultClinicoRecordDto) {

        logger.debug("PUT resultClinico resultClinicoRecordDto received {} ", resultClinicoRecordDto);

        Optional<ResultClinicosModel> resultOptional = resultClinicoService.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId);

        if (resultOptional.isPresent()) {
            ResultClinicosModel resultClinico = resultOptional.get();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(resultClinicoService.update(resultClinicoRecordDto, resultClinico));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resultado não encontrado.");

        }
    }
}
