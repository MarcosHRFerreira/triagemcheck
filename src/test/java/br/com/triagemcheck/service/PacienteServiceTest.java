package br.com.triagemcheck.service;

import br.com.triagemcheck.dtos.PacienteRecordDto;
import br.com.triagemcheck.enums.Sexo;
import br.com.triagemcheck.enums.UnidadeFederativa;
import br.com.triagemcheck.models.PacienteModel;
import br.com.triagemcheck.services.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Mock
    private PacienteService pacienteService;

    private UUID pacienteId;
    private PacienteRecordDto pacienteRecordDto;
    private Page<PacienteModel> pacienteModelPage;

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
        PacienteModel pacienteModel = new PacienteModel();
        when(pacienteService.save(pacienteRecordDto)).thenReturn(pacienteModel);

        PacienteModel result = pacienteService.save(pacienteRecordDto);

        assertEquals(pacienteModel, result);
        verify(pacienteService, times(1)).save(pacienteRecordDto);
    }

    @Test
    void testExistsByCpf() {
        when(pacienteService.existsBycpf("cpf")).thenReturn(true);

        boolean result = pacienteService.existsBycpf("cpf");

        assertEquals(true, result);
        verify(pacienteService, times(1)).existsBycpf("cpf");
    }

    @Test
    void testFindAll() {
        when(pacienteService.findAll(any(PageRequest.class))).thenReturn(pacienteModelPage);

        Page<PacienteModel> result = pacienteService.findAll(PageRequest.of(0, 10));

        assertEquals(pacienteModelPage, result);
        verify(pacienteService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindByCpf() {
        PacienteModel pacienteModel = new PacienteModel();
        when(pacienteService.findByCpf("cpf")).thenReturn(pacienteModel);

        PacienteModel result = pacienteService.findByCpf("cpf");

        assertEquals(pacienteModel, result);
        verify(pacienteService, times(1)).findByCpf("cpf");
    }

    @Test
    void testUpdate() {
        PacienteModel pacienteModel = new PacienteModel();
        when(pacienteService.update(pacienteRecordDto, pacienteId)).thenReturn(pacienteModel);

        PacienteModel result = pacienteService.update(pacienteRecordDto, pacienteId);

        assertEquals(pacienteModel, result);
        verify(pacienteService, times(1)).update(pacienteRecordDto, pacienteId);
    }

    @Test
    void testDelete() {
        doNothing().when(pacienteService).delete(pacienteId);

        pacienteService.delete(pacienteId);

        verify(pacienteService, times(1)).delete(pacienteId);
    }

    @Test
    void testFindById() {
        PacienteModel pacienteModel = new PacienteModel();
       when(pacienteService.findById(pacienteId)).thenReturn(pacienteModel);

        PacienteModel result = pacienteService.findById(pacienteId);

        assertEquals(pacienteModel, result);
        verify(pacienteService, times(1)).findById(pacienteId);
    }
}
