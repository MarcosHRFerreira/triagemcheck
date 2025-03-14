package br.com.triagemcheck.service.impl;

import br.com.triagemcheck.dtos.FeedbackProfissionalRecordDto;
import br.com.triagemcheck.exceptions.NotFoundException;
import br.com.triagemcheck.models.FeedbackProfissionalModel;
import br.com.triagemcheck.models.ProfissionalModel;
import br.com.triagemcheck.models.TriagemModel;
import br.com.triagemcheck.repositories.FeedbackProfissionalRepository;
import br.com.triagemcheck.services.ProfissionalService;
import br.com.triagemcheck.services.TriagemService;
import br.com.triagemcheck.services.impl.FeedbackProfissionalServiceImpl;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackProfissionalServiceImplTest {

    @Mock
    private FeedbackProfissionalRepository feedbackProfissionalRepository;

    @Mock
    private ProfissionalService profissionalService;

    @Mock
    private TriagemService triagemService;

    @InjectMocks
    private FeedbackProfissionalServiceImpl feedbackProfissionalService;

    private FeedbackProfissionalRecordDto feedbackProfissionalRecordDto;
    private FeedbackProfissionalModel feedbackProfissionalModel;
    private ProfissionalModel profissionalModel;
    private TriagemModel triagemModel;
    private UUID profissionalId;
    private UUID triagemId;

    @BeforeEach
    void setUp() {
        profissionalId = UUID.randomUUID();
        triagemId = UUID.randomUUID();

        profissionalModel = new ProfissionalModel();
        profissionalModel.setProfissionalId(profissionalId);

        triagemModel = new TriagemModel();
        triagemModel.setTriagemId(triagemId);
        triagemModel.setProfissional(profissionalModel);

        feedbackProfissionalRecordDto = new FeedbackProfissionalRecordDto(
                "Ótimo profissional",
                5, // Avaliação de severidade válida
                5  // Avaliação de eficácia válida
        );

        feedbackProfissionalModel = new FeedbackProfissionalModel();
        feedbackProfissionalModel.setProfissional(profissionalModel);
        feedbackProfissionalModel.setTriagem(triagemModel);
        feedbackProfissionalModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        feedbackProfissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    void testSaveFeedbackSuccessfully() {
        when(triagemService.findById(triagemId)).thenReturn(triagemModel);
        when(profissionalService.findById(profissionalId)).thenReturn(profissionalModel);
        when(feedbackProfissionalRepository.findProfissionalTriagem(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());
        when(feedbackProfissionalRepository.save(any(FeedbackProfissionalModel.class))).thenReturn(feedbackProfissionalModel);

        FeedbackProfissionalModel result = feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId);

        assertNotNull(result);
        verify(feedbackProfissionalRepository, times(1)).save(any(FeedbackProfissionalModel.class));
    }

    @Test
    void testSaveFeedbackTriagemNotFound() {
         triagemId = UUID.randomUUID();
        when(triagemService.findById(triagemId)).thenThrow(new NotFoundException("Erro: Triagem não encontrada."));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId);
        });

        assertEquals("Erro: Triagem não encontrada.", exception.getMessage());
    }


    @Test
    void testFindByIdNotFound() {
        UUID feedbackId = UUID.randomUUID();
        when(feedbackProfissionalRepository.findByfeedbackprofissionalId(any(UUID.class))).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackProfissionalService.findById(feedbackId);
        });

        assertEquals("Error: FeedbackProfissional não encontrado.", exception.getMessage());
    }

    @Test
    void testFindAllFeedbacks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FeedbackProfissionalModel> page = new PageImpl<>(List.of(feedbackProfissionalModel));
        when(feedbackProfissionalRepository.findAll(pageable)).thenReturn(page);

        Page<FeedbackProfissionalModel> result = feedbackProfissionalService.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}

