package com.usdobserver.app;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.repository.USDRateRepository;
import com.usdobserver.app.service.USDRateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsdobserverApplicationTests {

	@Autowired	private USDRateRepository usdRateRepository;
	@Autowired	private USDRateService usdRateService;

	@Before
	public void dbInit() {
		USDRate rate = new USDRate();
		rate.setDate(LocalDate.parse("2017-06-05"));
		rate.setRate(3.9995D);
		usdRateRepository.saveAndFlush(rate);
	}

	@Test
	public void dbTest() {
		List<USDRate> usdRateList = usdRateService.getAllRates();
		System.out.println("USD rate list: " + usdRateList);
		assertEquals(1, usdRateList.size());

		usdRateRepository.delete(usdRateList);
		List<USDRate> emptyUsdRateList = usdRateService.getAllRates();
		System.out.println("Empty USD rate list: " + emptyUsdRateList);
		assertEquals(1, usdRateList.size());
	}

}
