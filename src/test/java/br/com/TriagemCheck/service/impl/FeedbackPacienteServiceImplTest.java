package br.com.TriagemCheck.service.impl;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.FeedbackPacienteRepository;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.services.impl.FeedbackPacienteServiceImpl;
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
class FeedbackPacienteServiceImplTest {

    @Mock
    private FeedbackPacienteRepository feedbackPacienteRepository;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private TriagemService triagemService;

    @InjectMocks
    private FeedbackPacienteServiceImpl feedbackPacienteService;

    private FeedbackPacienteRecordDto feedbackPacienteRecordDto;
    private FeedbackPacienteModel feedbackPacienteModel;
    private PacienteModel pacienteModel;
    private TriagemModel triagemModel;
    private UUID pacienteId;
    private UUID triagemId;

    @BeforeEach
    void setUp() {
        pacienteId = UUID.randomUUID();
        triagemId = UUID.randomUUID();

        pacienteModel = new PacienteModel();
        pacienteModel.setPacienteId(pacienteId);

        triagemModel = new TriagemModel();
        triagemModel.setTriagemId(triagemId);
        triagemModel.setPaciente(pacienteModel);

        feedbackPacienteRecordDto = new FeedbackPacienteRecordDto(
                "Excelente atendimento",
                5
        );

        feedbackPacienteModel = new FeedbackPacienteModel();
        feedbackPacienteModel.setPaciente(pacienteModel);
        feedbackPacienteModel.setTriagem(triagemModel);
        feedbackPacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        feedbackPacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    void testSaveFeedbackSuccessfully() {
        when(triagemService.findById(triagemId)).thenReturn(Optional.of(triagemModel));
        when(pacienteService.findById(pacienteId)).thenReturn(Optional.of(pacienteModel));
        when(feedbackPacienteRepository.findPacienteTriagem(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());
        when(feedbackPacienteRepository.save(any(FeedbackPacienteModel.class))).thenReturn(feedbackPacienteModel);

        FeedbackPacienteModel result = feedbackPacienteService.save(feedbackPacienteRecordDto, triagemId);

        assertNotNull(result);
        verify(feedbackPacienteRepository, times(1)).save(any(FeedbackPacienteModel.class));
    }

    @Test
    void testSaveFeedbackTriagemNotFound() {
        when(triagemService.findById(triagemId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackPacienteService.save(feedbackPacienteRecordDto, triagemId);
        });

        assertEquals("Erro: Triagem não encontrada.", exception.getMessage());
    }

    @Test
    void testSaveFeedbackPacienteNotFound() {
        when(triagemService.findById(triagemId)).thenReturn(Optional.of(triagemModel));
        when(pacienteService.findById(pacienteId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackPacienteService.save(feedbackPacienteRecordDto, triagemId);
        });

        assertEquals("Erro: Paciente não encontrado.", exception.getMessage());
    }


    @Test
    void testFindByIdSuccessfully() {
        when(feedbackPacienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(feedbackPacienteModel));

        Optional<FeedbackPacienteModel> result = feedbackPacienteService.findById(UUID.randomUUID());

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        when(feedbackPacienteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            feedbackPacienteService.findById(UUID.randomUUID());
        });

        assertEquals("Error: FeedbackPaciente não encontrado.", exception.getMessage());
    }

    @Test
    void testFindAllFeedbacks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FeedbackPacienteModel> page = new PageImpl<>(List.of(feedbackPacienteModel));
        when(feedbackPacienteRepository.findAll(pageable)).thenReturn(page);

        Page<FeedbackPacienteModel> result = feedbackPacienteService.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
