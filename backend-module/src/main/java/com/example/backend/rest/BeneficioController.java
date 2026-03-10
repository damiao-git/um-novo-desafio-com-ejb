package com.example.backend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @GetMapping
    public List<String> list() {
        return Arrays.asList("Beneficio A", "Beneficio B");
    }

    @PostMapping("transferir")
    public ResponseEntity<?> transferir(){
        return null;
    }

}
