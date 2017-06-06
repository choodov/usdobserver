package com.usdobserver.app.web;

import com.usdobserver.app.entity.USDRate;
import com.usdobserver.app.service.USDRateService;
import com.usdobserver.app.utils.XLSGenerator;
import com.usdobserver.app.web.dto.DataTablesRenderDTO;
import com.usdobserver.app.web.dto.DataTablesSettingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@RestController
@RequestMapping("/api/rates")
public class RESTController {

	@Autowired
	private USDRateService usdRateService;

	@Autowired
	private XLSGenerator xlsGenerator;

	private static final String XLSX_MEDIA_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

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

	@RequestMapping(value = "/generateExcel", method = RequestMethod.POST)
	public String generateExcel(@RequestBody String tableData) {
		return xlsGenerator.generateXLS(tableData);
	}

	@RequestMapping(value = "/getExcel", method = RequestMethod.GET, produces = XLSX_MEDIA_TYPE)
	public ResponseEntity<byte[]> getExcel(@RequestParam("fileName") String fileName) {
		byte[] data = new byte[]{};
		try {
			data = Files.readAllBytes(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(XLSX_MEDIA_TYPE));
		headers.setContentDispositionFormData(fileName, fileName);

		return new ResponseEntity<>(data, headers, HttpStatus.OK);
	}
}
