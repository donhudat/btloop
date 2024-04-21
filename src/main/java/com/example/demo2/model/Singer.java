package com.example.demo2.model;

import com.example.demo2.dto.SongDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Data
public class Singer {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column()
    private String birth_day;

    @Column()
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "singer")
    private Set<Song> songs;
}
