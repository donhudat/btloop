package com.example.demo2.service;

import com.example.demo2.dto.SingerDTO;
import com.example.demo2.dto.SongDTO;
import com.example.demo2.model.Category;
import com.example.demo2.model.Singer;
import com.example.demo2.model.Song;
import com.example.demo2.repos.CategoryRepository;
import com.example.demo2.repos.SingerRepository;
import com.example.demo2.repos.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SongService {

    private final SongRepository songRepository;
    private final SingerRepository singerRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

    public SongService(final SongRepository songRepository, final SingerRepository singerRepository, final CategoryRepository categoryRepository) {
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Song> findAll() {
        List<Song> songs = songRepository.findAll();
        return songs;
    }

    public Song get(final Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(SongDTO request) {
        Song song = new Song();
        Singer singer = request.getSinger();
        Singer singerDB = singerRepository.findByName(singer.getName());
        if (singerDB == null) {
            Singer newSinger = singerRepository.save(singer);
            singer.setId(newSinger.getId());
        } else {
            singer.setId(singerDB.getId());
        }
        Category cate = request.getCategory();
        Category cateDB = categoryRepository.findByName(cate.getName());
        if (cateDB == null) {
            Category newCate = categoryRepository.save(cate);
            cate.setId(newCate.getId());
        } else {
            cate.setId(cateDB.getId());
        }
        song.setSinger(singer);
        song.setCategory(cate);
        song.setName(request.getName());
        song.setUrl(request.getUrl());
        song.setDescription(request.getDescription());
        song.setQuantityLike(request.getQuantityLike());
        song.setAmountListen(request.getAmountListen());
        try {
            System.out.println(song);
            songRepository.save(song);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Song update(final Long id, final SongDTO songDTO) {
        final Song song = songRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        song.setUrl(songDTO.getUrl());
        song.setName(songDTO.getName());
        song.setDescription(songDTO.getDescription());
        return songRepository.save(song);
    }

    public Integer delete(final Long id) {
        try {
            songRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Song likeSong(final Long id, boolean liked) {
        final Song song = songRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        int newLike;
        try {
            if (song.getQuantityLike() > 0) {
                newLike = liked ? song.getQuantityLike()+1 : song.getQuantityLike()-1;
            } else {
                newLike = liked ? song.getQuantityLike()+1 : 0;
            }
        } catch (Exception e) {
            newLike = liked ? 1 : 0;
        }
        song.setQuantityLike(newLike);
        return songRepository.save(song);
    }

    public List<Song> findAllOrderByQuantitySongDesc() {
        return songRepository.findAllByOrderByQuantityLikeDesc();
    }

    public List<Song> searchSongByKeyWord(String key) {
        return songRepository.findByKeyword(key);
    }

    public List<Song> searchSongBySinger(String key) {
        List<Song> result = new ArrayList<>();
        List<Singer> singers = singerRepository.findByKeyword(key);
        for (Singer singer: singers) {
            result.addAll(songRepository.findAllBySingerId(singer.getId()));
        }
        return result;
    }
}
