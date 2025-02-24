package br.com.TriagemCheck.service.impl;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.FeedbackProfissionalRepository;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.services.impl.FeedbackProfissionalServiceImpl;
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
        when(triagemService.findById(triagemId)).thenReturn(Optional.of(triagemModel));
        when(profissionalService.findById(profissionalId)).thenReturn(Optional.of(profissionalModel));
        when(feedbackProfissionalRepository.findProfissionalTriagem(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());
        when(feedbackProfissionalRepository.save(any(FeedbackProfissionalModel.class))).thenReturn(feedbackProfissionalModel);

        FeedbackProfissionalModel result = feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId);

        assertNotNull(result);
        verify(feedbackProfissionalRepository, times(1)).save(any(FeedbackProfissionalModel.class));
    }

    @Test
    void testSaveFeedbackTriagemNotFound() {
        when(triagemService.findById(triagemId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId);
        });

        assertEquals("Erro: Triagem não encontrada.", exception.getMessage());
    }

    @Test
    void testSaveFeedbackProfissionalNotFound() {
        when(triagemService.findById(triagemId)).thenReturn(Optional.of(triagemModel));
        when(profissionalService.findById(profissionalId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackProfissionalService.save(feedbackProfissionalRecordDto, triagemId);
        });

        assertEquals("Erro: Profissional não encontrado.", exception.getMessage());
    }


    @Test
    void testFindByIdSuccessfully() {
        when(feedbackProfissionalRepository.findById(any(UUID.class))).thenReturn(Optional.of(feedbackProfissionalModel));

        Optional<FeedbackProfissionalModel> result = feedbackProfissionalService.findById(UUID.randomUUID());

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        when(feedbackProfissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackProfissionalService.findById(UUID.randomUUID());
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

