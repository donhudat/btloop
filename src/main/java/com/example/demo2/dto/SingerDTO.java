package com.example.demo2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Data
public class SingerDTO {
    private Long id;

    private String name;

    private String birth_day;

    private String description;

    private Set<SongDTO> songs;
}
