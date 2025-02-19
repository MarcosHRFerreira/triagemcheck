package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.validations.TriagemValidator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/triagens")
public class TriagemController {

    Logger logger = LogManager.getLogger(TriagemController.class);

    final TriagemService triagemService;
    final TriagemValidator triagemValidator;
    final PacienteService pacienteService;
    final ProfissionalService profissionalService;

    public TriagemController(TriagemService triagemService, TriagemValidator triagemValidator, PacienteService pacienteService, ProfissionalService profissionalService ) {
        this.triagemService = triagemService;
        this.triagemValidator = triagemValidator;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;

    }
    @Operation(summary = "Salvar Triagem", description = "Salva uma nova triagem para um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Triagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })

    @PostMapping("/pacientes/{pacienteId}/profissionais/{profissionalId}/triagens")
    public ResponseEntity<Object> savarTriagem(@Parameter(description = "ID do paciente", required = true) @PathVariable(value = "pacienteId") UUID pacienteId,
                                               @Parameter(description = "ID do profissional", required = true) @PathVariable(value="profissionalId") UUID profissionalId,
                                               @Parameter(description = "Dados da triagem", required = true) @RequestBody @Valid TriagemRecordDto triagemRecordDto, Errors errors){

        logger.debug("POST savarTriagem triagemRecordDto received {} ", triagemRecordDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(triagemService.save(triagemRecordDto, pacienteId, profissionalId));

    }

    @Operation(summary = "Listar Todas as Triagens", description = "Retorna uma lista paginada de todas as triagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de triagens retornada com sucesso")
    })
    @GetMapping("/todastriagens")
    public ResponseEntity<Page<TriagemModel>> getAll( Pageable pageable)                                                     {

        return ResponseEntity.status(HttpStatus.OK).body(triagemService.findAll(pageable));
    }

    @Operation(summary = "Buscar Triagem por ID", description = "Retorna os detalhes de uma triagem específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes da triagem retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Triagem não encontrada")
    })
    @GetMapping("/{triagemId}")
    public ResponseEntity<Object> getOne(@Parameter(description = "ID da triagem", required = true) @PathVariable(value = "triagemId") UUID triagemId){

        logger.debug("Get triagens getOne received {} ", triagemId);

        return ResponseEntity.status(HttpStatus.OK).body( triagemService.findById(triagemId));

    }

    @Operation(summary = "Atualizar Triagem", description = "Atualiza os dados de uma triagem específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Triagem atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Triagem não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @PutMapping("/{triagemId}/pacientes/{pacienteId}/profissionais/{profissionalId}/triagem")
    public ResponseEntity<Object> update(@Parameter(description = "ID do paciente", required = true) @PathVariable(value ="pacienteId") UUID pacienteId,
                                         @Parameter(description = "ID do profissional", required = true) @PathVariable(value="profissionalId") UUID profissionalId,
                                         @Parameter(description = "ID da triagem", required = true)  @PathVariable(value="triagemId") UUID triagemId,
                                         @Parameter(description = "Dados atualizados da triagem", required = true) @RequestBody TriagemRecordDto triagemRecordDto){

        logger.debug("PUT triagens triagemRecordDto received {} ", triagemRecordDto);

            return ResponseEntity.status(HttpStatus.OK).body(triagemService.update(triagemRecordDto, triagemId, pacienteId, profissionalId));
    }
    @Operation(summary = "Listar Triagens Completas", description = "Retorna uma lista paginada de triagens com informações detalhdas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de triagens completas retornada com sucesso")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Página a ser carregada", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Tamanho da página", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Critério de ordenação no formato campo,asc ou campo,desc")
    })
    @GetMapping("/completa")
    public ResponseEntity<Page<TriagemCompletaRecordDto>> getTriagemCompleta(@Parameter(description = "CPF do Paciente") @RequestParam(required = false)  String cpf, Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body( triagemService.findTriagemCompleta(pageable,cpf ));
    }

}
