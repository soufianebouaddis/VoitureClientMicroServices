package com.vc.VoitureService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vc.VoitureService.entity.Voiture;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {

}