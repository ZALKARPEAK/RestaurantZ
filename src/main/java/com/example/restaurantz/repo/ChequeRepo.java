package com.example.restaurantz.repo;

import com.example.restaurantz.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeRepo extends JpaRepository<Cheque, Long> {
}
