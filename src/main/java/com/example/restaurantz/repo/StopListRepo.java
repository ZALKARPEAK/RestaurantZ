package com.example.restaurantz.repo;

import com.example.restaurantz.dto.StopList.StopListResponse;
import com.example.restaurantz.entity.StopList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StopListRepo extends JpaRepository<StopList, Long> {
    @Query("select new com.example.restaurantz.dto.StopList.StopListResponse(s.id,s.reason,s.menuItem.name,s.date) from StopList s")
    Page<StopListResponse> findAllStopLists(Pageable pageable);
}
