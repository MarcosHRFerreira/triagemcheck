package br.com.TriagemCheck.service.impl;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.enums.Especialidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ProfissionalRepository;
import br.com.TriagemCheck.repositories.TriagemRepository;
import br.com.TriagemCheck.services.impl.ProfissionalServiceImpl;
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
class ProfissionalServiceImplTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private TriagemRepository triagemRepository;

    @InjectMocks
    private ProfissionalServiceImpl profissionalService;

    private ProfissionalRecordDto profissionalRecordDto;
    private ProfissionalModel profissionalModel;
    private UUID profissionalId;

    @BeforeEach
    void setUp() {
        profissionalRecordDto = new ProfissionalRecordDto(
                "CRM33232",
                "Dr Roberto",
                Especialidade.PEDIATRA,
                StatusOperacional.ATIVO,
                "1199882233",
                "roberto@gmail.com"
        );

        profissionalModel = new ProfissionalModel();
        profissionalModel.setProfissionalId(UUID.randomUUID());
        profissionalModel.setCrm("CRM33232");
        profissionalModel.setNome("Dr Roberto");
        profissionalModel.setEspecialidade(Especialidade.PEDIATRA);
        profissionalModel.setStatusOperacional(StatusOperacional.ATIVO);
        profissionalModel.setTelefone("1199882233");
        profissionalModel.setEmail("roberto@gmail.com");
        profissionalModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        profissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
        profissionalId = profissionalModel.getProfissionalId();
    }

    @Test
    void testSaveProfissionalInvalidoEmail() {
        ProfissionalRecordDto profissionalRecordDtoComEmailInvalido = new ProfissionalRecordDto(
                "CRM33232",
                "Dr Roberto",
                Especialidade.PEDIATRA,
                StatusOperacional.ATIVO,
                "1199882233",
                "email_invalido"
        );

        assertThrows(NoValidException.class, () -> profissionalService.save(profissionalRecordDtoComEmailInvalido));
    }

    @Test
    void testSaveProfissionalJaExistenteEmail() {
        when(profissionalRepository.existsByemail(anyString())).thenReturn(true);

        assertThrows(NoValidException.class, () -> profissionalService.save(profissionalRecordDto));
    }

    @Test
    void testSaveProfissionalJaExistenteCrm() {
        when(profissionalRepository.existsBycrm(anyString())).thenReturn(true);

        assertThrows(NoValidException.class, () -> profissionalService.save(profissionalRecordDto));
    }

    @Test
    void testSaveProfissionalComSucesso() {
        when(profissionalRepository.save(any(ProfissionalModel.class))).thenReturn(profissionalModel);

        ProfissionalModel savedProfissional = profissionalService.save(profissionalRecordDto);

        assertNotNull(savedProfissional);
        assertEquals(profissionalModel.getEmail(), savedProfissional.getEmail());
    }

    @Test
    void testFindByIdProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> profissionalService.findById(profissionalId));
    }

    @Test
    void testFindByIdProfissionalEncontrado() {
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.of(profissionalModel));

        Optional<ProfissionalModel> foundProfissional = profissionalService.findById(profissionalId);

        assertTrue(foundProfissional.isPresent());
        assertEquals(profissionalModel.getEmail(), foundProfissional.get().getEmail());
    }

    @Test
    void testFindAllProfissionais() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProfissionalModel> page = new PageImpl<>(List.of(profissionalModel));

        when(profissionalRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ProfissionalModel> allProfissionais = profissionalService.findAll(pageable);

        assertNotNull(allProfissionais);
        assertEquals(1, allProfissionais.getTotalElements());
    }

    @Test
    void testUpdateProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> profissionalService.update(profissionalRecordDto, profissionalId));
    }

    @Test
    void testUpdateProfissionalComSucesso() {
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.of(profissionalModel));
        when(profissionalRepository.save(any(ProfissionalModel.class))).thenReturn(profissionalModel);

        ProfissionalModel updatedProfissional = profissionalService.update(profissionalRecordDto, profissionalId);

        assertNotNull(updatedProfissional);
        assertEquals(profissionalModel.getEmail(), updatedProfissional.getEmail());
    }


    @Test
    void testDeleteProfissionalExistenteTriagem() {

        TriagemModel triagemModel = new TriagemModel();

        when(triagemRepository.findProficionalIntoTriagem(any(UUID.class))).thenReturn(Optional.of(triagemModel));

        assertThrows(NoValidException.class, () -> profissionalService.delete(profissionalId));

        verify(triagemRepository, times(1)).findProficionalIntoTriagem(profissionalId);
    }


}


