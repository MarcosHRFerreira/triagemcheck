package br.com.TriagemCheck.controller;


import br.com.TriagemCheck.controllers.ProfissionalController;
import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.enums.Especialidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.services.ProfissionalService;
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

class ProfissionalControllerTest {

    @InjectMocks
    ProfissionalController profissionalController;

    @Mock
    ProfissionalService profissionalService;

    @Mock
    Errors errors;

    UUID profissionalId;
    ProfissionalRecordDto profissionalRecordDto;
    Page<ProfissionalModel> profissionalModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profissionalId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        profissionalRecordDto = new ProfissionalRecordDto(
                "CRM33232",
                "Dr Roberto",
                Especialidade.PEDIATRA,
                StatusOperacional.ATIVO,
                "1199882233",
                "roberto@gmail.com");

        profissionalModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        when(profissionalService.save(profissionalRecordDto))
                .thenReturn(new ProfissionalModel());

        ResponseEntity<Object> response = profissionalController.save(profissionalRecordDto, errors);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(profissionalService, times(1)).save(profissionalRecordDto);
    }

    @Test
    void testGetAll() {
        when(profissionalService.findAll(any(PageRequest.class)))
                .thenReturn(profissionalModelPage);

        ResponseEntity<Page<ProfissionalModel>> response = profissionalController.getAll(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profissionalModelPage, response.getBody());
        verify(profissionalService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetOne() {
        when(profissionalService.findById(profissionalId))
                .thenReturn(Optional.of(new ProfissionalModel()));

        ResponseEntity<Object> response = profissionalController.getOne(profissionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profissionalService, times(1)).findById(profissionalId);
    }

    @Test
    void testUpdate() {
        when(profissionalService.update(profissionalRecordDto, profissionalId))
                .thenReturn(new ProfissionalModel());

        ResponseEntity<Object> response = profissionalController.update(profissionalId, profissionalRecordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profissionalService, times(1)).update(profissionalRecordDto, profissionalId);
    }

    @Test
    void testDelete() {
        doNothing().when(profissionalService).delete(profissionalId);

        ResponseEntity<Object> response = profissionalController.delete(profissionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profissionalService, times(1)).delete(profissionalId);
    }
}

