package br.com.TriagemCheck.service.impl;

import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.enums.CorProtocolo;
import br.com.TriagemCheck.enums.Severidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ProfissionalRepository;
import br.com.TriagemCheck.repositories.TriagemRepository;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.impl.TriagemServiceImpl;
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
class TriagemServiceImplTest {

    @Mock
    private TriagemRepository triagemRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ProfissionalService profissionalService;

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private TriagemServiceImpl triagemService;

    private TriagemRecordDto triagemRecordDto;
    private TriagemModel triagemModel;
    private PacienteModel pacienteModel;
    private ProfissionalModel profissionalModel;
    private UUID pacienteId;
    private UUID profissionalId;

    @BeforeEach
    void setUp() {
        pacienteId = UUID.randomUUID();
        profissionalId = UUID.randomUUID();

        pacienteModel = new PacienteModel();
        pacienteModel.setPacienteId(pacienteId);

        profissionalModel = new ProfissionalModel();
        profissionalModel.setProfissionalId(profissionalId);
        profissionalModel.setCrm("12345");
        profissionalModel.setStatusOperacional(StatusOperacional.ATIVO);

        triagemRecordDto = new TriagemRecordDto(
                "Dor de cabeça",
                Severidade.EMERGENCIA,
                CorProtocolo.VERMELHO,
                UUID.fromString("58b4e98d-13af-44e8-9357-f3301f17eef6")
        );

        triagemModel = new TriagemModel();
        triagemModel.setPaciente(pacienteModel);
        triagemModel.setProfissional(profissionalModel);
        triagemModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        triagemModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    void testSaveTriagemSuccessfully() {
        when(pacienteService.findById(pacienteId)).thenReturn(Optional.of(pacienteModel));
        when(profissionalService.findById(profissionalId)).thenReturn(Optional.of(profissionalModel));
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.of(profissionalModel));
        when(triagemRepository.save(any(TriagemModel.class))).thenReturn(triagemModel);

        TriagemModel result = triagemService.save(triagemRecordDto, pacienteId, profissionalId);

        assertNotNull(result);
        verify(triagemRepository, times(1)).save(any(TriagemModel.class));
    }

    @Test
    void testSaveTriagemPacienteNotFound() {
        when(pacienteService.findById(pacienteId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            triagemService.save(triagemRecordDto, pacienteId, profissionalId);
        });

        assertEquals("Erro: Paciente não encontrado.", exception.getMessage());
    }

    @Test
    void testSaveTriagemProfissionalNotFound() {
        when(pacienteService.findById(pacienteId)).thenReturn(Optional.of(pacienteModel));
        when(profissionalService.findById(profissionalId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            triagemService.save(triagemRecordDto, pacienteId, profissionalId);
        });

        assertEquals("Erro: Profissional não encontrado.", exception.getMessage());
    }

    @Test
    void testSaveTriagemNoValidException() {
        profissionalModel.setCrm("");
        when(pacienteService.findById(pacienteId)).thenReturn(Optional.of(pacienteModel));
        when(profissionalService.findById(profissionalId)).thenReturn(Optional.of(profissionalModel));
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.of(profissionalModel));

        NoValidException exception = assertThrows(NoValidException.class, () -> {
            triagemService.save(triagemRecordDto, pacienteId, profissionalId);
        });

        assertEquals("Erro: Profissional deve possuir um CRM válido.", exception.getMessage());
    }

    @Test
    void testFindByIdSuccessfully() {
        when(triagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(triagemModel));

        Optional<TriagemModel> result = triagemService.findById(UUID.randomUUID());

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        when(triagemRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            triagemService.findById(UUID.randomUUID());
        });

        assertEquals("Erro: Triagem não existe.", exception.getMessage());
    }

    @Test
    void testFindAllTriagens() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TriagemModel> page = new PageImpl<>(List.of(triagemModel));
        when(triagemRepository.findAll(pageable)).thenReturn(page);

        Page<TriagemModel> result = triagemService.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
