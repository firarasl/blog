package com.example.portfolio.repository;

import com.example.portfolio.model.Subfite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SubfiteRepository extends JpaRepository<Subfite, Long> {
    Optional<Subfite> findByName(String subfiteName);

}
