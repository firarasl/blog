package com.example.portfolio.controller;

import com.example.portfolio.dto.SubfiteDto;
import com.example.portfolio.service.SubfiteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subfite")
@AllArgsConstructor
@Slf4j
public class SubfiteController {

    private final SubfiteService subFiteService;

    @PostMapping
    public ResponseEntity<SubfiteDto> createSubfite(@RequestBody SubfiteDto subfiteDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subFiteService.save(subfiteDto));
    }
    @GetMapping
    public ResponseEntity<List<SubfiteDto>> getAllSubfites(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(subFiteService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubfiteDto> getSubfite(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(subFiteService.getSubfite(id));
    }
}
