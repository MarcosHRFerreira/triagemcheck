package br.com.TriagemCheck.repositories;


import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TriagemRepository extends JpaRepository<TriagemModel, UUID> {
}
