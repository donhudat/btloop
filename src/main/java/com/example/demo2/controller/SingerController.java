package com.example.demo2.controller;

import com.example.demo2.dto.SingerDTO;
import com.example.demo2.model.Singer;
import com.example.demo2.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/singers")
public class SingerController {
    @Autowired
    private SingerService singerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Singer>> getAllSinger() {
        return ResponseEntity.ok(singerService.findAll());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Long>> createSingers(@RequestBody List<SingerDTO> listSingers) {
        List<Long> result = new ArrayList<>();
        for (SingerDTO singer: listSingers) {
            result.add(singerService.create(singer));
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Singer> updateSinger(@RequestBody SingerDTO singerReq, @PathVariable Long id) {
        return ResponseEntity.ok(singerService.update(id, singerReq));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteSinger(@PathVariable Long id) {
        return ResponseEntity.ok(singerService.delete(id));
    }
}