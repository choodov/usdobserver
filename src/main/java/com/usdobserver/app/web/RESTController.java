package com.usdobserver.app.web;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.service.USDRateService;
import com.usdobserver.app.web.dto.DataTablesRenderDTO;
import com.usdobserver.app.web.dto.DataTablesSettingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@RestController
@RequestMapping("/api/rates")
public class RESTController {

	@Autowired
	private USDRateService usdRateService;

	@RequestMapping(value = "/getAjax", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<DataTablesRenderDTO> getRates(DataTablesSettingsDTO settings, HttpServletRequest request) {

		settings.populateColumnData(request);

		Integer totalHeadings = Math.toIntExact(usdRateService.countTotalRates());

		DataTablesRenderDTO<USDRate> renderDTO = new DataTablesRenderDTO<>();
		renderDTO.setSEcho(settings.getSEcho());
		renderDTO.setITotalRecords(totalHeadings);
		renderDTO.setITotalDisplayRecords(totalHeadings);
		renderDTO.setAaData(usdRateService.getRatesPage(settings));

		return new ResponseEntity<>(renderDTO, HttpStatus.OK);
	}
}
