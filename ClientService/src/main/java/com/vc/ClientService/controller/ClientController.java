package com.vc.ClientService.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.vc.ClientService.entity.Client;
import com.vc.ClientService.entity.Voiture;
import com.vc.ClientService.repository.ClientRepository;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private ClientRepository clientRepository;
    private RestTemplate restTemplate;

    public ClientController(ClientRepository clientRepository, RestTemplate restTemplate) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok().body(clientRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) throws Exception {
        Client c = clientRepository.findById(id).orElseThrow(() -> new Exception("Client inexistant"));
        return ResponseEntity.ok().body(c);
    }

    @PostMapping("/save/{userid}")
    public String addVoitureForClient(@PathVariable Long userid, Voiture v) throws Exception {
        Client c = clientRepository.findById(userid)
                .orElseThrow(() -> new Exception("User not found with id: " + userid));
        Voiture testV = restTemplate.postForEntity("http://VOITURE-SERVICES/api/voiture/save", v,
                Voiture.class).getBody();
        c.getVoitures().add(testV.getId());
        if (!c.getVoitures().isEmpty()) {
            clientRepository.save(c);
            return "inside if";
        }
        return "Voiture added";
    }

    @PostMapping("/affect/{userid}/{vid}")
    public String affectVoitureToClient(@PathVariable Long userid, @PathVariable Long vid) throws Exception {
        try {
            String url = "http://VOITURE-SERVICES/api/voiture/" + vid;
            Client c = clientRepository.findById(userid)
                    .orElseThrow(() -> new Exception("User not found with id: " + userid));
            Voiture testV = restTemplate.getForEntity(url, Voiture.class).getBody();
            c.getVoitures().add(testV.getId());
            if (!c.getVoitures().isEmpty()) {
                clientRepository.save(c);
            }
            return "Voiture affected";
        } catch (ResourceAccessException e) {
            e.printStackTrace();
            return "Error accessing VOITURE-SERVICES";
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Client> save(@RequestBody Client c) {
        return ResponseEntity.ok().body(this.clientRepository.save(c));
    }
}