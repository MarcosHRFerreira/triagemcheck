package br.com.TriagemCheck.service;

import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.enums.CorProtocolo;
import br.com.TriagemCheck.enums.Severidade;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.services.TriagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TriagemServiceTest {

    @Mock
    private TriagemService triagemService;

    private UUID pacienteId;
    private UUID profissionalId;
    private UUID triagemId;
    private TriagemRecordDto triagemRecordDto;
    private Page<TriagemModel> triagemModelPage;
    private Page<TriagemCompletaRecordDto> triagemCompletaRecordDtoPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pacienteId = UUID.randomUUID();
        profissionalId = UUID.randomUUID();
        triagemId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        triagemRecordDto = new TriagemRecordDto(
                "Dor de cabeça",
                Severidade.EMERGENCIA,
                CorProtocolo.VERMELHO,
                UUID.fromString("58b4e98d-13af-44e8-9357-f3301f17eef6")
        );

        triagemModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        triagemCompletaRecordDtoPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        TriagemModel triagemModel = new TriagemModel();
        when(triagemService.save(triagemRecordDto, pacienteId, profissionalId)).thenReturn(triagemModel);

        TriagemModel result = triagemService.save(triagemRecordDto, pacienteId, profissionalId);

        assertEquals(triagemModel, result);
        verify(triagemService, times(1)).save(triagemRecordDto, pacienteId, profissionalId);
    }

    @Test
    void testFindById() {
        Optional<TriagemModel> triagemModelOptional = Optional.of(new TriagemModel());
        when(triagemService.findById(triagemId)).thenReturn(triagemModelOptional);

        Optional<TriagemModel> result = triagemService.findById(triagemId);

        assertEquals(triagemModelOptional, result);
        verify(triagemService, times(1)).findById(triagemId);
    }

    @Test
    void testFindAll() {
        when(triagemService.findAll(any(PageRequest.class))).thenReturn(triagemModelPage);

        Page<TriagemModel> result = triagemService.findAll(PageRequest.of(0, 10));

        assertEquals(triagemModelPage, result);
        verify(triagemService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindPacienteProfissionalInTriagem() {
        Optional<TriagemModel> triagemModelOptional = Optional.of(new TriagemModel());
        when(triagemService.findPacienteProfissionalInTriagem(pacienteId, profissionalId, triagemId))
                .thenReturn(triagemModelOptional);

        Optional<TriagemModel> result = triagemService.findPacienteProfissionalInTriagem(pacienteId, profissionalId, triagemId);

        assertEquals(triagemModelOptional, result);
        verify(triagemService, times(1)).findPacienteProfissionalInTriagem(pacienteId, profissionalId, triagemId);
    }

    @Test
    void testUpdate() {
        TriagemModel triagemModel = new TriagemModel();
        when(triagemService.update(triagemRecordDto, triagemId)).thenReturn(triagemModel);

        TriagemModel result = triagemService.update(triagemRecordDto, triagemId);

        assertEquals(triagemModel, result);
        verify(triagemService, times(1)).update(triagemRecordDto, triagemId);
    }

    @Test
    void testFindTriagemCompleta() {
        when(triagemService.findTriagemCompleta(any(PageRequest.class), anyString()))
                .thenReturn(triagemCompletaRecordDtoPage);

        Page<TriagemCompletaRecordDto> result = triagemService.findTriagemCompleta(PageRequest.of(0, 10), "cpf");

        assertEquals(triagemCompletaRecordDtoPage, result);
        verify(triagemService, times(1)).findTriagemCompleta(any(PageRequest.class), anyString());
    }
}
