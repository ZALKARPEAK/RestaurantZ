package com.example.restaurantz.repo;

import com.example.restaurantz.dto.AverageSum.AverageSumResponse;
import com.example.restaurantz.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ChequeRepo extends JpaRepository<Cheque, Long> {
    @Query("SELECT new com.example.restaurantz.dto.AverageSum.AverageSumResponse(SUM(c.priceAverage)) FROM Cheque c WHERE c.createdAt = :date")
    AverageSumResponse getChequeByCreatedAtAndAndPriceAverage(@Param("date") LocalDate date);

    @Query("SELECT new com.example.restaurantz.dto.AverageSum.AverageSumResponse(SUM(c.priceAverage)) FROM Cheque c WHERE c.createdAt = :date AND c.user.id = :walterId")
    AverageSumResponse getChequeByCreatedAtAndPriceAverageAndUserId(@Param("date") LocalDate date, @Param("walterId") Long walterId);

}
