package com.example.demo2.controller;

import com.example.demo2.dto.SingerDTO;
import com.example.demo2.dto.SongDTO;
import com.example.demo2.model.Singer;
import com.example.demo2.model.Song;
import com.example.demo2.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Song>> getAllSong() {
        return ResponseEntity.ok(songService.findAll());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Integer>> createSongs(@RequestBody List<SongDTO> listSongs) {
        List<Integer> result = new ArrayList<>();
        for (SongDTO song : listSongs) {
//            Set<Long> singerSet = song.getSingers().stream().map(singer -> singer.getId()).collect(Collectors.toSet());
            result.add(songService.create(song));
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Song> updateSong(@RequestBody SongDTO songReq, @PathVariable Long id) {
        return ResponseEntity.ok(songService.update(id, songReq));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.delete(id));
    }

    @PutMapping("/{id}/like")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Song> likeSong(@RequestParam(value = "liked", defaultValue = "true") boolean liked, @PathVariable Long id) {
        return ResponseEntity.ok(songService.likeSong(id, liked));
    }
    @GetMapping("/statistic-by-likes")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Song>> ratingSong() {
        List<Song> sortedSongs = songService.findAllOrderByQuantitySongDesc();
        return ResponseEntity.ok(sortedSongs);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Song>> searchSong(@RequestParam(value = "key") String key) {
        List<Song> songs = songService.searchSongByKeyWord(key);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search-by-singer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Song>> searchSongBySinger(@RequestParam(value = "key") String key) {
        List<Song> songs = songService.searchSongBySinger(key);
        return ResponseEntity.ok(songs);
    }
}
