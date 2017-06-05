package com.usdobserver.app.service;

import com.usdobserver.app.entity.USDRate;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
public interface USDRateService {

	List<USDRate> getAllRates();

	void updateDBFromAPI(LocalDate startDate, LocalDate endDate);
}
