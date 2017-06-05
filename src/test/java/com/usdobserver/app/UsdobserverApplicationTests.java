package com.usdobserver.app;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.repository.USDRateRepository;
import com.usdobserver.app.service.USDRateService;
import com.usdobserver.app.utils.APIConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsdobserverApplicationTests {

	@Autowired
	private USDRateRepository usdRateRepository;
	@Autowired
	private USDRateService usdRateService;
	@Autowired
	private APIConnector apiConnector;

	private static final String TEST_URL
			= "http://api.nbp.pl/api/exchangerates/tables/A/2017-06-01/2017-06-01/?format=xml";
	private static final String BAD_TEST_URL
			= "http://badapi.nbp.pl/api/exchangerates/tables/A/2017-06-01/2017-06-01/?format=xml";

	@Test
	public void dbTest() {
		USDRate rate = new USDRate();
		rate.setDate("2017-06-05");
		rate.setRate(3.9995D);
		usdRateRepository.saveAndFlush(rate);

		List<USDRate> usdRateList = usdRateService.getAllRates();
		System.out.println("USD rate list: " + usdRateList);
		assertThat(usdRateList.size(), is(1));

		usdRateRepository.delete(usdRateList);
		List<USDRate> emptyUsdRateList = usdRateService.getAllRates();
		assertThat(emptyUsdRateList.size(), is(0));
	}

	@Test
	public void APIConnectionTest() {
		Optional<String> responseFromADI = apiConnector.getResponseFromAPI(TEST_URL);
		System.out.println("Response from http://api.nbp.pl: " + responseFromADI);
		assertThat(responseFromADI, notNullValue());

		Optional<String> badResponseFromADI = apiConnector.getResponseFromAPI(BAD_TEST_URL);
		assertThat(badResponseFromADI, is(Optional.empty()));
	}

	@Test
	public void updateDBFromAPITest(){
		usdRateService.updateDBFromAPI("2017-06-01", "2017-06-05");
		List<USDRate> usdRateList = usdRateService.getAllRates();
		assertThat(usdRateList.size(), is(3));
	}

}
