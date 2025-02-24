package br.com.TriagemCheck.controller;

import br.com.TriagemCheck.controllers.FeedbackProfissionalController;
import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.services.FeedbackProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FeedbackProfissionalControllerTest {

    @InjectMocks
    FeedbackProfissionalController feedbackProfissionalController;

    @Mock
    FeedbackProfissionalService feedbackProfissionalService;

    @Mock
    Errors errors;

    UUID triagemId;
    UUID feedbackProfissionalId;
    FeedbackProfissionalRecordDto feedbackProfissionalRecordDto;
    Page<FeedbackProfissionalModel> feedbackProfissionalModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triagemId = UUID.randomUUID();
        feedbackProfissionalId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        feedbackProfissionalRecordDto = new FeedbackProfissionalRecordDto("Otimo Atendimento", 5,5);

        feedbackProfissionalModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        when(feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId))
                .thenReturn(new FeedbackProfissionalModel());

        ResponseEntity<Object> response = feedbackProfissionalController.saveFeedbackProfissional(triagemId, feedbackProfissionalRecordDto, errors);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(feedbackProfissionalService, times(1)).save(feedbackProfissionalRecordDto, triagemId);
    }

    @Test
    void testGetAll() {
        when(feedbackProfissionalService.findAll(any(PageRequest.class)))
                .thenReturn(feedbackProfissionalModelPage);

        ResponseEntity<Page<FeedbackProfissionalModel>> response = feedbackProfissionalController.getAll(PageRequest.of(0, 10), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(feedbackProfissionalModelPage, response.getBody());
        verify(feedbackProfissionalService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetOne() {
        when(feedbackProfissionalService.findById(feedbackProfissionalId))
                .thenReturn(Optional.of(new FeedbackProfissionalModel()));

        ResponseEntity<Object> response = feedbackProfissionalController.getOne(feedbackProfissionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedbackProfissionalService, times(1)).findById(feedbackProfissionalId);
    }

    @Test
    void testUpdate() {
        when(feedbackProfissionalService.update(feedbackProfissionalRecordDto, feedbackProfissionalId))
                .thenReturn(new FeedbackProfissionalModel());

        ResponseEntity<Object> response = feedbackProfissionalController.updateFeedBackProfissional(feedbackProfissionalId, feedbackProfissionalRecordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedbackProfissionalService, times(1)).update(feedbackProfissionalRecordDto, feedbackProfissionalId);
    }

    @Test
    void testDelete() {
        doNothing().when(feedbackProfissionalService).delete(feedbackProfissionalId);

        ResponseEntity<Object> response = feedbackProfissionalController.delete(feedbackProfissionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedbackProfissionalService, times(1)).delete(feedbackProfissionalId);
    }
}
