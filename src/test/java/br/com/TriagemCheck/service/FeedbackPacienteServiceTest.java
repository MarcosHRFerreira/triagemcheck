package br.com.TriagemCheck.service;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.services.FeedbackPacienteService;
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

class FeedbackPacienteServiceTest {

    @Mock
    private FeedbackPacienteService feedbackPacienteService;

    private UUID triagemId;
    private UUID feedbackPacienteId;
    private FeedbackPacienteRecordDto feedbackPacienteRecordDto;
    private Page<FeedbackPacienteModel> feedbackPacienteModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triagemId = UUID.randomUUID();
        feedbackPacienteId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        feedbackPacienteRecordDto = new FeedbackPacienteRecordDto(
                "Excelente atendimento",
                5);

        feedbackPacienteModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        FeedbackPacienteModel feedbackPacienteModel = new FeedbackPacienteModel();
        when(feedbackPacienteService.save(feedbackPacienteRecordDto, triagemId)).thenReturn(feedbackPacienteModel);

        FeedbackPacienteModel result = feedbackPacienteService.save(feedbackPacienteRecordDto, triagemId);

        assertEquals(feedbackPacienteModel, result);
        verify(feedbackPacienteService, times(1)).save(feedbackPacienteRecordDto, triagemId);
    }

    @Test
    void testFindAll() {
        when(feedbackPacienteService.findAll(any(PageRequest.class))).thenReturn(feedbackPacienteModelPage);

        Page<FeedbackPacienteModel> result = feedbackPacienteService.findAll(PageRequest.of(0, 10));

        assertEquals(feedbackPacienteModelPage, result);
        verify(feedbackPacienteService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindById() {
        Optional<FeedbackPacienteModel> feedbackPacienteModelOptional = Optional.of(new FeedbackPacienteModel());
        when(feedbackPacienteService.findById(feedbackPacienteId)).thenReturn(feedbackPacienteModelOptional);

        Optional<FeedbackPacienteModel> result = feedbackPacienteService.findById(feedbackPacienteId);

        assertEquals(feedbackPacienteModelOptional, result);
        verify(feedbackPacienteService, times(1)).findById(feedbackPacienteId);
    }

    @Test
    void testFindPacienteTriagemInFeedback() {
        Optional<FeedbackPacienteModel> feedbackPacienteModelOptional = Optional.of(new FeedbackPacienteModel());
        UUID pacienteId = UUID.randomUUID();
        when(feedbackPacienteService.findPacienteTriagemInFeedback(pacienteId, triagemId, feedbackPacienteId))
                .thenReturn(feedbackPacienteModelOptional);

        Optional<FeedbackPacienteModel> result = feedbackPacienteService.findPacienteTriagemInFeedback(pacienteId, triagemId, feedbackPacienteId);

        assertEquals(feedbackPacienteModelOptional, result);
        verify(feedbackPacienteService, times(1)).findPacienteTriagemInFeedback(pacienteId, triagemId, feedbackPacienteId);
    }

    @Test
    void testUpdate() {
        FeedbackPacienteModel feedbackPacienteModel = new FeedbackPacienteModel();
        when(feedbackPacienteService.update(feedbackPacienteId, feedbackPacienteRecordDto)).thenReturn(feedbackPacienteModel);

        FeedbackPacienteModel result = feedbackPacienteService.update(feedbackPacienteId, feedbackPacienteRecordDto);

        assertEquals(feedbackPacienteModel, result);
        verify(feedbackPacienteService, times(1)).update(feedbackPacienteId, feedbackPacienteRecordDto);
    }

    @Test
    void testDelete() {
        doNothing().when(feedbackPacienteService).delete(feedbackPacienteId);

        feedbackPacienteService.delete(feedbackPacienteId);

        verify(feedbackPacienteService, times(1)).delete(feedbackPacienteId);
    }
}

