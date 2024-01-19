package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
