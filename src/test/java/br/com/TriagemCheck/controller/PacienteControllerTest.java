package br.com.TriagemCheck.controller;

import br.com.TriagemCheck.controllers.PacienteController;
import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.enums.Sexo;
import br.com.TriagemCheck.enums.UnidadeFederativa;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.services.PacienteService;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PacienteControllerTest {

    @InjectMocks
    PacienteController pacienteController;

    @Mock
    PacienteService pacienteService;

    @Mock
    Errors errors;

    UUID pacienteId;
    PacienteRecordDto pacienteRecordDto;
    Page<PacienteModel> pacienteModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pacienteId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        pacienteRecordDto = new PacienteRecordDto(
                "05623656056",
                "Pedro",
                LocalDate.of(2025, 2, 21),
                Sexo.MASCULINO,
                "pedro@teste.com",
                "rua do Pedro",
                "341",
                "BARRA FUNDA",
                "13565565",
                "SÃO PAULO",
                UnidadeFederativa.SP,
                "119932323",
                "119932323",
                "alérgico a penicilina",
                "remédio para pressão");

        pacienteModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        when(pacienteService.save(pacienteRecordDto))
                .thenReturn(new PacienteModel());

        ResponseEntity<Object> response = pacienteController.save(pacienteRecordDto, errors);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(pacienteService, times(1)).save(pacienteRecordDto);
    }

    @Test
    void testGetAll() {
        when(pacienteService.findAll(any(PageRequest.class)))
                .thenReturn(pacienteModelPage);

        ResponseEntity<Page<PacienteModel>> response = pacienteController.getAll(PageRequest.of(0, 10), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pacienteModelPage, response.getBody());
        verify(pacienteService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetOne() {
        when(pacienteService.findById(pacienteId))
                .thenReturn(Optional.of(new PacienteModel()));

        ResponseEntity<Object> response = pacienteController.getOne(pacienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pacienteService, times(1)).findById(pacienteId);
    }

    @Test
    void testGetOneCpf() {
        when(pacienteService.findByCpf("cpf"))
                .thenReturn(Optional.of(new PacienteModel()));

        ResponseEntity<Object> response = pacienteController.getOneCpf("cpf");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pacienteService, times(1)).findByCpf("cpf");
    }

    @Test
    void testUpdate() {
        when(pacienteService.update(pacienteRecordDto, pacienteId))
                .thenReturn(new PacienteModel());

        ResponseEntity<Object> response = pacienteController.update(pacienteId, pacienteRecordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pacienteService, times(1)).update(pacienteRecordDto, pacienteId);
    }

    @Test
    void testDelete() {
        doNothing().when(pacienteService).delete(pacienteId);

        ResponseEntity<Object> response = pacienteController.delete(pacienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pacienteService, times(1)).delete(pacienteId);
    }
}
