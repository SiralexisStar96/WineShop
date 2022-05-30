package com.example.wineshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long>{

    @Query(value = "SELECT * FROM wine ORDER BY rating DESC LIMIT ?1", nativeQuery = true)
    List<Wine> findAllByBestRating(int limit);

    @Query(value = "SELECT * FROM wine ORDER BY price DESC LIMIT ?1", nativeQuery = true)
    List<Wine> findAllByBestPrice(int limit);

    @Query(value="SELECT * FROM wine ORDER BY rating/price DESC LIMIT ?1 ", nativeQuery = true)
    List<Wine> findAllByRatingPriceRatio(int limit);

    @Query(value="SELECT * FROM wine GROUP BY year ORDER BY avg(rating) DESC LIMIT ?1 ", nativeQuery = true)
    List<Wine> findAllByVintage(int limit);
}
