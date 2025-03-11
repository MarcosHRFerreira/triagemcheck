package br.com.triagemcheck.service;

import br.com.triagemcheck.dtos.FeedbackProfissionalRecordDto;
import br.com.triagemcheck.models.FeedbackProfissionalModel;
import br.com.triagemcheck.services.FeedbackProfissionalService;
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

class FeedbackProfissionalServiceTest {

    @Mock
    private FeedbackProfissionalService feedbackProfissionalService;

    private UUID triagemId;
    private UUID feedbackProfissionalId;
    private FeedbackProfissionalRecordDto feedbackProfissionalRecordDto;
    private Page<FeedbackProfissionalModel> feedbackProfissionalModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triagemId = UUID.randomUUID();
        feedbackProfissionalId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        feedbackProfissionalRecordDto = new FeedbackProfissionalRecordDto(
                "Ótimo profissional",
                5,
                5);

        feedbackProfissionalModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        FeedbackProfissionalModel feedbackProfissionalModel = new FeedbackProfissionalModel();
        when(feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId)).thenReturn(feedbackProfissionalModel);

        FeedbackProfissionalModel result = feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId);

        assertEquals(feedbackProfissionalModel, result);
        verify(feedbackProfissionalService, times(1)).save(feedbackProfissionalRecordDto, triagemId);
    }

    @Test
    void testFindAll() {
        when(feedbackProfissionalService.findAll(any(PageRequest.class))).thenReturn(feedbackProfissionalModelPage);

        Page<FeedbackProfissionalModel> result = feedbackProfissionalService.findAll(PageRequest.of(0, 10));

        assertEquals(feedbackProfissionalModelPage, result);
        verify(feedbackProfissionalService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindById() {
        FeedbackProfissionalModel feedbackProfissionalModelOptional = new FeedbackProfissionalModel();
        when(feedbackProfissionalService.findById(feedbackProfissionalId)).thenReturn(feedbackProfissionalModelOptional);

        FeedbackProfissionalModel result = feedbackProfissionalService.findById(feedbackProfissionalId);

        assertEquals(feedbackProfissionalModelOptional, result);
        verify(feedbackProfissionalService, times(1)).findById(feedbackProfissionalId);
    }

    @Test
    void testFindProfissionalTriagemInFeedback() {
        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional = Optional.of(new FeedbackProfissionalModel());
        UUID profissionalId = UUID.randomUUID();
        when(feedbackProfissionalService.findProfissionalTriagemInFeedback(profissionalId, triagemId, feedbackProfissionalId))
                .thenReturn(feedbackProfissionalModelOptional);

        Optional<FeedbackProfissionalModel> result = feedbackProfissionalService.findProfissionalTriagemInFeedback(profissionalId, triagemId, feedbackProfissionalId);

        assertEquals(feedbackProfissionalModelOptional, result);
        verify(feedbackProfissionalService, times(1)).findProfissionalTriagemInFeedback(profissionalId, triagemId, feedbackProfissionalId);
    }

    @Test
    void testUpdate() {
        FeedbackProfissionalModel feedbackProfissionalModel = new FeedbackProfissionalModel();
        when(feedbackProfissionalService.update(feedbackProfissionalRecordDto, feedbackProfissionalId)).thenReturn(feedbackProfissionalModel);

        FeedbackProfissionalModel result = feedbackProfissionalService.update(feedbackProfissionalRecordDto, feedbackProfissionalId);

        assertEquals(feedbackProfissionalModel, result);
        verify(feedbackProfissionalService, times(1)).update(feedbackProfissionalRecordDto, feedbackProfissionalId);
    }

    @Test
    void testDelete() {
        doNothing().when(feedbackProfissionalService).delete(feedbackProfissionalId);

        feedbackProfissionalService.delete(feedbackProfissionalId);

        verify(feedbackProfissionalService, times(1)).delete(feedbackProfissionalId);
    }
}