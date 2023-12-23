package com.vc.ClientService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Voiture {
    private Long id;
    private String marque;
    private String matricule;
    private String model;
}
