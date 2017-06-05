package com.usdobserver.app.repository;

import com.usdobserver.app.entity.USDRate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
public interface USDRateRepository extends JpaRepository<USDRate,Long> {
}
