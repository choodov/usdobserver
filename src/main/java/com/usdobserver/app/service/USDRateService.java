package com.usdobserver.app.service;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.web.dto.DataTablesSettingsDTO;

import java.util.List;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
public interface USDRateService {

	List<USDRate> getAllRates();

	void updateDBFromAPI(String startDate, String endDate);

	Long countTotalRates();

	List<USDRate> getRatesPage(DataTablesSettingsDTO settings);
}
