package br.com.TriagemCheck.service;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.services.ResultClinicoService;
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

class ResultClinicoServiceTest {

    @Mock
    private ResultClinicoService resultClinicoService;

    private UUID triagemId;
    private UUID resultadoId;
    private ResultClinicoRecordDto resultClinicoRecordDto;
    private Page<ResultClinicosModel> resultClinicosModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triagemId = UUID.randomUUID();
        resultadoId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        resultClinicoRecordDto = new ResultClinicoRecordDto(
                "Pneumonia",
                "Antibióticos",
                "Em Tratamento"
        );

        resultClinicosModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        ResultClinicosModel resultClinicosModel = new ResultClinicosModel();
        when(resultClinicoService.save(resultClinicoRecordDto, triagemId)).thenReturn(resultClinicosModel);

        ResultClinicosModel result = resultClinicoService.save(resultClinicoRecordDto, triagemId);

        assertEquals(resultClinicosModel, result);
        verify(resultClinicoService, times(1)).save(resultClinicoRecordDto, triagemId);
    }

    @Test
    void testFindAll() {
        when(resultClinicoService.findAll(any(PageRequest.class))).thenReturn(resultClinicosModelPage);

        Page<ResultClinicosModel> result = resultClinicoService.findAll(PageRequest.of(0, 10));

        assertEquals(resultClinicosModelPage, result);
        verify(resultClinicoService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindById() {
        Optional<ResultClinicosModel> resultClinicosModelOptional = Optional.of(new ResultClinicosModel());
        when(resultClinicoService.findById(resultadoId)).thenReturn(resultClinicosModelOptional);

        Optional<ResultClinicosModel> result = resultClinicoService.findById(resultadoId);

        assertEquals(resultClinicosModelOptional, result);
        verify(resultClinicoService, times(1)).findById(resultadoId);
    }

    @Test
    void testFindProfissionalTriagemInResultClinico() {
        Optional<ResultClinicosModel> resultClinicosModelOptional = Optional.of(new ResultClinicosModel());
        UUID profissionalId = UUID.randomUUID();
        when(resultClinicoService.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId))
                .thenReturn(resultClinicosModelOptional);

        Optional<ResultClinicosModel> result = resultClinicoService.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId);

        assertEquals(resultClinicosModelOptional, result);
        verify(resultClinicoService, times(1)).findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId);
    }

    @Test
    void testUpdate() {
        ResultClinicosModel resultClinicosModel = new ResultClinicosModel();
        when(resultClinicoService.update(resultClinicoRecordDto, resultadoId)).thenReturn(resultClinicosModel);

        ResultClinicosModel result = resultClinicoService.update(resultClinicoRecordDto, resultadoId);

        assertEquals(resultClinicosModel, result);
        verify(resultClinicoService, times(1)).update(resultClinicoRecordDto, resultadoId);
    }
}
