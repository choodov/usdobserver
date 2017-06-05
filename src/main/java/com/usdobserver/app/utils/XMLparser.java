package com.usdobserver.app.utils;

import com.usdobserver.app.entity.USDRate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.usdobserver.app.utils.InputXMLKeys.*;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Component
public class XMLParser {

	private Logger logger = LoggerFactory.getLogger(XMLParser.class);
	public static final String CODING = "UTF-8";

	public List<USDRate> getNBPRates(String response) {
		List<USDRate> resultList = new ArrayList<>();
		Document parsedResponse = Jsoup.parse(response, CODING);

		resultList.addAll(parsedResponse.getElementsByTag(EXCHANGE_RATES_TABLE).stream()
				.map(this::getNewUSDRate)
				.collect(Collectors.toList()));

		return resultList;
	}

	private USDRate getNewUSDRate(Element exchangeRatesTable) {
		USDRate usdRate = new USDRate();
		usdRate.setDate(LocalDate.parse(exchangeRatesTable.getElementsByTag(EFFECTIVE_DATE).text()));

		exchangeRatesTable.select(RATES_TO_RATE).stream()
				.filter(element -> element.select(CODE).text().equals(USD))
				.forEach(element -> usdRate.setRate(Double.valueOf(element.select(MID).text())));

		logger.debug("New parsed USDRate: " + usdRate);

		return usdRate;
	}

}
