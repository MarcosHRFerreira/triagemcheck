package br.com.TriagemCheck.service.impl;


import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.enums.Sexo;
import br.com.TriagemCheck.enums.UnidadeFederativa;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.repositories.PacienteRepository;
import br.com.TriagemCheck.services.impl.PacienteServiceImpl;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private PacienteRecordDto pacienteRecordDto;
    private PacienteModel pacienteModel;
    private UUID pacienteId;

    @BeforeEach
    void setUp() {
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
                "remédio para pressão"
        );

        pacienteModel = new PacienteModel();
        pacienteModel.setPacienteId(UUID.randomUUID());
        pacienteModel.setCpf("12345678900");
        pacienteModel.setNome("Pedro");
        pacienteModel.setEmail("pedro@teste.com");
        pacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
        pacienteId = pacienteModel.getPacienteId();
    }

    @Test
    void testSavePacienteInvalidoCPF() {
        PacienteRecordDto pacienteRecordDtoComCpfInvalido = new PacienteRecordDto(
                "12345678910",
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
                "remédio para pressão"
        );

        assertThrows(NoValidException.class, () -> pacienteService.save(pacienteRecordDtoComCpfInvalido));
    }

    @Test
    void testSavePacienteInvalidoEmail() {
        PacienteRecordDto pacienteRecordDtoComEmailInvalido = new PacienteRecordDto(
                "12345678900",
                "Pedro",
                LocalDate.of(2025, 2, 21),
                Sexo.MASCULINO,
                "email_invalido",
                "rua do Pedro",
                "341",
                "BARRA FUNDA",
                "13565565",
                "SÃO PAULO",
                UnidadeFederativa.SP,
                "119932323",
                "119932323",
                "alérgico a penicilina",
                "remédio para pressão"
        );

        assertThrows(NoValidException.class, () -> pacienteService.save(pacienteRecordDtoComEmailInvalido));
    }

    @Test
    void testSavePacienteJaExistenteCpf() {
        when(pacienteRepository.existsBycpf(anyString())).thenReturn(true);

        assertThrows(NoValidException.class, () -> pacienteService.save(pacienteRecordDto));
    }

    @Test
    void testSavePacienteJaExistenteEmail() {
        when(pacienteRepository.existsByemail(anyString())).thenReturn(true);

        assertThrows(NoValidException.class, () -> pacienteService.save(pacienteRecordDto));
    }

    @Test
    void testSavePacienteComSucesso() {
        when(pacienteRepository.save(any(PacienteModel.class))).thenReturn(pacienteModel);

        PacienteModel savedPaciente = pacienteService.save(pacienteRecordDto);

        assertNotNull(savedPaciente);
        assertEquals(pacienteModel.getCpf(), savedPaciente.getCpf());
    }

    @Test
    void testFindByCpfPacienteNaoEncontrado() {
        when(pacienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pacienteService.findByCpf("12345678900"));
    }

    @Test
    void testFindByCpfPacienteEncontrado() {
        when(pacienteRepository.findByCpf(anyString())).thenReturn(Optional.of(pacienteModel));

        Optional<PacienteModel> foundPaciente = pacienteService.findByCpf("12345678900");

        assertTrue(foundPaciente.isPresent());
        assertEquals(pacienteModel.getCpf(), foundPaciente.get().getCpf());
    }

    @Test
    void testFindAllPacientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PacienteModel> page = new PageImpl<>(List.of(pacienteModel));

        when(pacienteRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<PacienteModel> allPacientes = pacienteService.findAll(pageable);

        assertNotNull(allPacientes);
        assertEquals(1, allPacientes.getTotalElements());
    }

    @Test
    void testUpdatePacienteNaoEncontrado() {
        when(pacienteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pacienteService.update(pacienteRecordDto, pacienteId));
    }

    @Test
    void testUpdatePacienteComSucesso() {
        when(pacienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(pacienteModel));
        when(pacienteRepository.save(any(PacienteModel.class))).thenReturn(pacienteModel);

        PacienteModel updatedPaciente = pacienteService.update(pacienteRecordDto, pacienteId);

        assertNotNull(updatedPaciente);
        assertEquals(pacienteModel.getCpf(), updatedPaciente.getCpf());
    }

    @Test
    void testDeletePacienteNaoEncontrado() {
        when(pacienteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pacienteService.delete(pacienteId));
    }

    @Test
    void testDeletePacienteComSucesso() {
        when(pacienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(pacienteModel));
        doNothing().when(pacienteRepository).deleteById(any(UUID.class));

        pacienteService.delete(pacienteId);

        verify(pacienteRepository, times(1)).deleteById(pacienteId);
    }
}

