package com.usdobserver.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Controller
public class MainController {

	@GetMapping("/")
	public String mainPage() {
		return "index";
	}

}
