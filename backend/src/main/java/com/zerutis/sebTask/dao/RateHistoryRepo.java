package com.zerutis.sebTask.dao;

import com.zerutis.sebTask.model.RateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateHistoryRepo extends JpaRepository<RateHistory,Integer> {
}
