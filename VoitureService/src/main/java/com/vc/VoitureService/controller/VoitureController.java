package com.vc.VoitureService.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vc.VoitureService.entity.Voiture;
import com.vc.VoitureService.repository.VoitureRepository;

@RestController
@RequestMapping("/api/voiture")
public class VoitureController {
    private VoitureRepository voitureRepository;

    public VoitureController(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Voiture> add(@RequestBody Voiture voiture) {
        return ResponseEntity.ok().body(this.voitureRepository.save(voiture));
    }

    @GetMapping
    public ResponseEntity<List<Voiture>> all() {
        return ResponseEntity.ok().body(this.voitureRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> one(@PathVariable Long id) throws Exception {
        Voiture v = this.voitureRepository.findById(id).orElseThrow(() -> new Exception("Voiture Not Found"));
        return ResponseEntity.ok().body(v);
    }
}