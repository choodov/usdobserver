package com.usdobserver.app.service;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.repository.USDRateRepository;
import com.usdobserver.app.utils.APIConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Service
public class USDRateServiceImpl implements USDRateService {

	@Autowired
	private USDRateRepository usdRateRepository;
	@Autowired
	private APIConnector apiConnector;

	@Override
	public List<USDRate> getAllRates() {
		return usdRateRepository.findAll();
	}
}
