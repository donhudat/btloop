package com.example.demo2.dto;

import com.example.demo2.model.Category;
import com.example.demo2.model.Singer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
public class SongDTO {
    private Long id;

    private String name;

    private Singer singer;

    private Category category;

    private String description;

    private int amountListen;

    private int quantityLike;

    private String url;
}
