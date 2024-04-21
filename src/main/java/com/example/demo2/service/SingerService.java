package com.example.demo2.service;

import com.example.demo2.dto.SingerDTO;
import com.example.demo2.dto.SongDTO;
import com.example.demo2.model.Singer;
import com.example.demo2.model.Song;
import com.example.demo2.repos.SingerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SingerService {

    private final SingerRepository singerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SingerService(final SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    public List<Singer> findAll() {
        List<Singer> singers = singerRepository.findAll();
//        for(Singer singer: singers) {
//            for (Song song : singer.getSongs()) {
//                SongDTO songDTO = new SongDTO();
//                songDTO.setId(song.getId());
//                songDTO.setName(song.getName());
//                // Sao chép các trường khác của songDTO nếu cần
//                System.out.println(songDTO);
//            }
//        }
        return singers;
    }

    public Singer get(final Long id) {
        return singerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final SingerDTO singerDTO) {
        final Singer singer = modelMapper.map(singerDTO, Singer.class);
        return singerRepository.save(singer).getId();
    }

    public Singer update(Long id, final SingerDTO singerDTO) {
        final Singer singer = singerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        singer.setName(singerDTO.getName());
        singer.setDescription(singerDTO.getDescription());
        singer.setBirth_day(singerDTO.getBirth_day());
        return singerRepository.save(singer);
    }

    public Integer delete(final Long id) {
        try {
            singerRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}