package com.usdobserver.app.repository;

import com.usdobserver.app.entity.USDRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
public interface USDRateRepository extends JpaRepository<USDRate,Long> {
	Page<USDRate> findByDateContaining(String search, Pageable pageable);
	List<USDRate> findByDateGreaterThanEqualAndDateLessThanEqual(String dateFrom, String dateTo);
	Page<USDRate> findByDateGreaterThanEqualAndDateLessThanEqual(String dateFrom, String dateTo, Pageable pageable);
	long countByDateGreaterThanEqualAndDateLessThanEqual(String dateFrom, String dateTo);

}
