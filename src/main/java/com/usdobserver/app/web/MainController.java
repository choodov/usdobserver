package com.usdobserver.app.web;

import com.usdobserver.app.service.USDRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Controller
public class MainController {

	@Autowired
	private USDRateService usdRateService;

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	@GetMapping("/")
	public String mainPage() {
		usdRateService.updateDBFromAPI("2017-05-01", "2017-06-02");
		return "index";
	}

}
