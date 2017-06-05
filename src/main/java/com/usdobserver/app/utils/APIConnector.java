package com.usdobserver.app.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Component
public class APIConnector {

	private Logger logger = LoggerFactory.getLogger(APIConnector.class);

	private static final int STATUS_OK = 200;
	private static final String BASE_NBP_URL = "http://api.nbp.pl/api/exchangerates/tables/A/";
	private static final String END_NBP_URL = "/?format=xml";
	private static final String URL_SEPARATOR = "/";
	private OkHttpClient client = new OkHttpClient();

	public Optional<String> getResponseFromAPI(String URL) {
		Response response;
		Optional<String> body;
		long startTime, endTime, processResponseTime;

		Request request = new Request.Builder().url(URL).get().build();

		try {
			logger.info("Date and time of NBP API call: " + LocalDateTime.now());
			startTime = System.nanoTime();
			response = client.newCall(request).execute();
			endTime = System.nanoTime();

			if (response.code() == STATUS_OK) {
				body = Optional.of(response.body().string());
				processResponseTime = endTime - startTime;
				logger.info("Time to process API response in milliseconds: "
						+ TimeUnit.MILLISECONDS.convert(processResponseTime, TimeUnit.NANOSECONDS));
			} else {
				logger.error("Response was with error: " + response.code());
				return Optional.empty();
			}

			logger.info("Response from API: " + response.toString());

		} catch (IOException e) {
			logger.error("Connection to API failed: " + e);
			return Optional.empty();
		}

		return body;
	}

	public String constructNBPURL(String startDate, String endDate) {
		String nbpurl = BASE_NBP_URL + startDate + URL_SEPARATOR + endDate + END_NBP_URL;
		logger.info("NBP URL constructed: " + nbpurl);
		return nbpurl;
	}

}