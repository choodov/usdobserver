package com.usdobserver.app.service;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.repository.USDRateRepository;
import com.usdobserver.app.utils.APIConnector;
import com.usdobserver.app.utils.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Service
public class USDRateServiceImpl implements USDRateService {

	@Autowired
	private USDRateRepository usdRateRepository;
	@Autowired
	private APIConnector apiConnector;
	@Autowired
	private XMLParser xmlParser;

	private Logger logger = LoggerFactory.getLogger(USDRateServiceImpl.class);

	@Override
	public List<USDRate> getAllRates() {
		return usdRateRepository.findAll();
	}

	@Override
	public void updateDBFromAPI(LocalDate startDate, LocalDate endDate) {
		logger.info("NBP API dates range: From " + startDate + " to " + endDate);

		String NBPURL = apiConnector.constructNBPURL(startDate, endDate);
		Optional<String> response = apiConnector.getResponseFromAPI(NBPURL);
		logger.info("NBP response: " + response.get());

		if (response.isPresent()) {
			List<USDRate> USDRateList = xmlParser.getNBPRates(response.get());
			List<USDRate> savedUSDRateList = usdRateRepository.save(USDRateList);
			usdRateRepository.flush();
			logger.info("List of saved USDRates: " + savedUSDRateList);
		}
	}
}
