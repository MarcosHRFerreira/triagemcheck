package br.com.triagemcheck.controller;

import br.com.triagemcheck.controllers.FeedbackPacienteController;
import br.com.triagemcheck.dtos.FeedbackPacienteRecordDto;
import br.com.triagemcheck.models.FeedbackPacienteModel;
import br.com.triagemcheck.services.FeedbackPacienteService;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FeedbackPacienteControllerTest {

    @InjectMocks
    FeedbackPacienteController feedbackPacienteController;

    @Mock
    FeedbackPacienteService feedbackPacienteService;

    @Mock
    Errors errors;

    UUID triagemId;
    UUID feedbackPacienteId;
    FeedbackPacienteRecordDto feedbackPacienteRecordDto;
    Page<FeedbackPacienteModel> feedbackPacienteModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triagemId = UUID.randomUUID();
        feedbackPacienteId = UUID.randomUUID();
        feedbackPacienteRecordDto = new FeedbackPacienteRecordDto("Teste",1);
        feedbackPacienteModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        when(feedbackPacienteService.save(feedbackPacienteRecordDto, triagemId))
                .thenReturn(new FeedbackPacienteModel());

        ResponseEntity<Object> response = feedbackPacienteController.save(triagemId, feedbackPacienteRecordDto, errors);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(feedbackPacienteService, times(1)).save(feedbackPacienteRecordDto, triagemId);
    }

    @Test
    void testGetAll() {
        when(feedbackPacienteService.findAll(any(PageRequest.class)))
                .thenReturn(feedbackPacienteModelPage);

        ResponseEntity<Page<FeedbackPacienteModel>> response = feedbackPacienteController.getAll(PageRequest.of(0, 10), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(feedbackPacienteModelPage, response.getBody());
        verify(feedbackPacienteService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetOne() {
        when(feedbackPacienteService.findById(feedbackPacienteId))
                .thenReturn(new FeedbackPacienteModel());

        ResponseEntity<Object> response = feedbackPacienteController.getOne(feedbackPacienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedbackPacienteService, times(1)).findById(feedbackPacienteId);
    }

    @Test
    void testUpdate() {
        when(feedbackPacienteService.update(feedbackPacienteId, feedbackPacienteRecordDto))
                .thenReturn(new FeedbackPacienteModel());

        ResponseEntity<Object> response = feedbackPacienteController.update(feedbackPacienteId, feedbackPacienteRecordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedbackPacienteService, times(1)).update(feedbackPacienteId, feedbackPacienteRecordDto);
    }

    @Test
    void testDelete() {
        doNothing().when(feedbackPacienteService).delete(feedbackPacienteId);

        ResponseEntity<Object> response = feedbackPacienteController.delete(feedbackPacienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(feedbackPacienteService, times(1)).delete(feedbackPacienteId);
    }
}


