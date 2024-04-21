package com.example.demo2.repos;

import com.example.demo2.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByName(String name);

    List<Song> findAllByOrderByQuantityLikeDesc();

    List<Song> findAllBySingerId(Long id);

    @Query("SELECT s FROM Song s WHERE LOWER(s.name) LIKE %:keyword% OR LOWER(s.description) LIKE %:keyword%")
    List<Song> findByKeyword(String keyword);
}