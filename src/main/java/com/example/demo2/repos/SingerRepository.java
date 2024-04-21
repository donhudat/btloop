package com.example.demo2.repos;

import com.example.demo2.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SingerRepository extends JpaRepository<Singer, Long> {
    Singer findByName(String name);

    @Query("SELECT s FROM Singer s WHERE LOWER(s.name) LIKE %:keyword% OR LOWER(s.description) LIKE %:keyword%")
    List<Singer> findByKeyword(String keyword);
}



