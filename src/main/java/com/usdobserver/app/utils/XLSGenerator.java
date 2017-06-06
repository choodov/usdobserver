package com.usdobserver.app.utils;

import com.google.gson.Gson;
import com.usdobserver.app.entity.USDRate;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Chudov A.V. on 6/6/2017.
 */
@Component
public class XLSGenerator {

	private Logger logger = LoggerFactory.getLogger(XLSGenerator.class);
	private static final String SHEET_NAME = "USD Rates";
	private static final String ROW_HEADER_DATES = "Dates";
	private static final String ROW_HEADER_RATES = "USD Rates";
	private static final String FILE_NAME_XLSX = "USDRates.xlsx";
	private static final int FIRST_COLUMN = 0;
	private static final int SECOND_COLUMN = 1;

	public String generateXLS(String inputJSON) {

		logger.info("TABLE DATA: " + inputJSON);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(SHEET_NAME);

		USDRate[] usdRates = parseJSON(inputJSON);

		int rowCount = 0;

		XSSFRow rowHeader = sheet.createRow(rowCount);
		XSSFCell cellDates = rowHeader.createCell(FIRST_COLUMN);
		XSSFCell cellRates = rowHeader.createCell(SECOND_COLUMN);
		cellDates.setCellValue(ROW_HEADER_DATES);
		cellRates.setCellValue(ROW_HEADER_RATES);

		for (USDRate rate : usdRates) {
			XSSFRow row = sheet.createRow(++rowCount);
			cellDates = row.createCell(FIRST_COLUMN);
			cellRates = row.createCell(SECOND_COLUMN);
			cellDates.setCellValue(rate.getDate());
			cellRates.setCellValue(rate.getRate());
		}

		// Write the output to a file
		try (FileOutputStream outputStream = new FileOutputStream(FILE_NAME_XLSX)) {
			workbook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return FILE_NAME_XLSX;
	}

	private USDRate[] parseJSON(String inputJSON) {
		Gson gson = new Gson();
		return gson.fromJson(inputJSON, USDRate[].class);
	}

}
