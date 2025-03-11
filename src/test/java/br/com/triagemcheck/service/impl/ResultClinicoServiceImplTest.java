package br.com.triagemcheck.service.impl;

import br.com.triagemcheck.dtos.ResultClinicoRecordDto;
import br.com.triagemcheck.models.ProfissionalModel;
import br.com.triagemcheck.models.ResultClinicosModel;
import br.com.triagemcheck.models.TriagemModel;
import br.com.triagemcheck.repositories.ResultClinicoRepository;
import br.com.triagemcheck.services.ProfissionalService;
import br.com.triagemcheck.services.TriagemService;
import br.com.triagemcheck.services.impl.ResultClinicoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultClinicoServiceImplTest {

    @Mock
    private ResultClinicoRepository resultClinicoRepository;

    @Mock
    private TriagemService triagemService;

    @Mock
    private ProfissionalService profissionalService;

    @InjectMocks
    private ResultClinicoServiceImpl resultClinicoService;

    private UUID resultadoId;
    private UUID triagemId;
    private UUID profissionalId;
    private ResultClinicoRecordDto resultClinicoRecordDto;
    private ResultClinicosModel resultClinicosModel;
    private ProfissionalModel profissionalModel;
    private TriagemModel triagemModel;

    @BeforeEach
    void setUp() {
        resultadoId = UUID.randomUUID();
        triagemId = UUID.randomUUID();
        profissionalId = UUID.randomUUID();
        resultClinicoRecordDto = new ResultClinicoRecordDto(
                "Pneumonia",
                "Antibióticos",
                "Em Tratamento"
        );
        resultClinicosModel = new ResultClinicosModel();
        profissionalModel = new ProfissionalModel();
        triagemModel = new TriagemModel();
        profissionalModel.setProfissionalId(profissionalId);
        triagemModel.setTriagemId(triagemId);
        triagemModel.setProfissional(profissionalModel);
        resultClinicosModel.setProfissional(profissionalModel);
        resultClinicosModel.setTriagem(triagemModel);
    }

    @Test
    void testSave() {
        when(triagemService.findById(triagemId)).thenReturn(triagemModel);
        when(profissionalService.findById(profissionalId)).thenReturn(profissionalModel);
        when(resultClinicoRepository.findByIdTriagem(triagemId)).thenReturn(Optional.empty());
        when(resultClinicoRepository.save(any(ResultClinicosModel.class))).thenReturn(resultClinicosModel);

        ResultClinicosModel result = resultClinicoService.save(resultClinicoRecordDto, triagemId);

        assertNotNull(result);
        verify(triagemService).findById(triagemId);
        verify(profissionalService).findById(profissionalId);
        verify(resultClinicoRepository).findByIdTriagem(triagemId);
        verify(resultClinicoRepository).save(any(ResultClinicosModel.class));
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ResultClinicosModel> page = new PageImpl<>(Collections.singletonList(resultClinicosModel));
        when(resultClinicoRepository.findAll(pageable)).thenReturn(page);

        Page<ResultClinicosModel> result = resultClinicoService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(resultClinicoRepository).findAll(pageable);
    }

    @Test
    void testFindById() {
        when(resultClinicoRepository.findByIdresultadoId(resultadoId)).thenReturn((resultClinicosModel));

        ResultClinicosModel result = resultClinicoService.findById(resultadoId);

        assertNotNull(result);
        verify(resultClinicoRepository).findByIdresultadoId(resultadoId);
    }

    @Test
    void testFindProfissionalTriagemInResultClinico() {
        when(resultClinicoRepository.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId))
                .thenReturn(Optional.of(resultClinicosModel));

        Optional<ResultClinicosModel> result = resultClinicoService.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId);

        assertTrue(result.isPresent());
        verify(resultClinicoRepository).findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId);
    }

    @Test
    void testUpdate() {
        when(resultClinicoRepository.findByIdresultadoId(resultadoId)).thenReturn(resultClinicosModel);
        when(resultClinicoRepository.save(any(ResultClinicosModel.class))).thenReturn(resultClinicosModel);

        ResultClinicosModel result = resultClinicoService.update(resultClinicoRecordDto, resultadoId);

        assertNotNull(result);
        verify(resultClinicoRepository).findByIdresultadoId(resultadoId);
        verify(resultClinicoRepository).save(any(ResultClinicosModel.class));
    }
}
