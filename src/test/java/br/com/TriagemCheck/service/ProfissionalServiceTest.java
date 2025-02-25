package br.com.TriagemCheck.service;


import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.enums.Especialidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.services.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfissionalServiceTest {

    @Mock
    private ProfissionalService profissionalService;

    private UUID profissionalId;
    private ProfissionalRecordDto profissionalRecordDto;
    private Page<ProfissionalModel> profissionalModelPage;

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
                "roberto@gmail.com"
        );

        profissionalModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSave() {
        ProfissionalModel profissionalModel = new ProfissionalModel();
        when(profissionalService.save(profissionalRecordDto)).thenReturn(profissionalModel);

        ProfissionalModel result = profissionalService.save(profissionalRecordDto);

        assertEquals(profissionalModel, result);
        verify(profissionalService, times(1)).save(profissionalRecordDto);
    }

    @Test
    void testExistsByCrm() {
        when(profissionalService.existsBycrm("crm")).thenReturn(true);

        boolean result = profissionalService.existsBycrm("crm");

        assertEquals(true, result);
        verify(profissionalService, times(1)).existsBycrm("crm");
    }

    @Test
    void testFindById() {
        ProfissionalModel profissionalModelOptional = new ProfissionalModel();
        when(profissionalService.findById(profissionalId)).thenReturn(profissionalModelOptional);

        ProfissionalModel result = profissionalService.findById(profissionalId);

        assertEquals(profissionalModelOptional, result);
        verify(profissionalService, times(1)).findById(profissionalId);
    }

    @Test
    void testFindAll() {
        when(profissionalService.findAll(any(PageRequest.class))).thenReturn(profissionalModelPage);

        Page<ProfissionalModel> result = profissionalService.findAll(PageRequest.of(0, 10));

        assertEquals(profissionalModelPage, result);
        verify(profissionalService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testUpdate() {
        ProfissionalModel profissionalModel = new ProfissionalModel();
        when(profissionalService.update(profissionalRecordDto, profissionalId)).thenReturn(profissionalModel);

        ProfissionalModel result = profissionalService.update(profissionalRecordDto, profissionalId);

        assertEquals(profissionalModel, result);
        verify(profissionalService, times(1)).update(profissionalRecordDto, profissionalId);
    }

    @Test
    void testDelete() {
        doNothing().when(profissionalService).delete(profissionalId);

        profissionalService.delete(profissionalId);

        verify(profissionalService, times(1)).delete(profissionalId);
    }
}
