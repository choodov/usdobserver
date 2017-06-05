package com.usdobserver.app.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Controller
public class MainController {

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	@GetMapping("/")
	public String mainPage() {
		return "index";
	}

}
