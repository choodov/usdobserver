package com.usdobserver.app.service;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.repository.USDRateRepository;
import com.usdobserver.app.utils.APIConnector;
import com.usdobserver.app.utils.XMLParser;
import com.usdobserver.app.web.dto.DataTablesSettingsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
	public List<USDRate> getRatesByPeriod(String dateFrom, String dateTo) {
		return usdRateRepository.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
	}

	@Override
	public boolean updateDBFromAPI(String startDate, String endDate) {
		logger.info("NBP API dates range: From " + startDate + " to " + endDate);

		String NBPURL = apiConnector.constructNBPURL(startDate, endDate);
		Optional<String> response = apiConnector.getResponseFromAPI(NBPURL);

		if (response.isPresent()) {
			logger.info("NBP response: " + response.get());
			List<USDRate> USDRateList = xmlParser.getNBPRates(response.get());
			List<USDRate> savedUSDRateList = usdRateRepository.save(USDRateList);
			usdRateRepository.flush();
			logger.info("List of saved USDRates: " + savedUSDRateList);
			return true;
		}
		return false;
	}

	@Override
	public Long countTotalRates() {
		return usdRateRepository.count();
	}

	@Override
	public List<USDRate> getRatesPage(DataTablesSettingsDTO settings) {
		Integer startFrom = settings.getIDisplayStart();
		Integer productsOnPage = settings.getIDisplayLength();

		Sort sort = null;
		for (DataTablesSettingsDTO.SortSettings sortSettings : settings.getSortSettings()) {
			if (sort == null) {
				sort = new Sort(new Sort.Order(sortSettings.getSSortDir(), sortSettings.getMDataProp()));
			} else {
				sort = sort.and(new Sort(new Sort.Order(sortSettings.getSSortDir(), sortSettings.getMDataProp())));
			}
		}

		PageRequest pageRequest = new PageRequest(startFrom / productsOnPage, productsOnPage, sort);

		if (settings.getSSearch() == null || settings.getSSearch().isEmpty()) {
			return usdRateRepository.findAll(pageRequest).getContent();
		}
		return usdRateRepository.findByDateContaining(settings.getSSearch(), pageRequest).getContent();
	}
}
