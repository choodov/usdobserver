package com.usdobserver.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataTablesSettingsDTO {
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	private Integer iColumns;
	private String sSearch;
	private Boolean bRegex;

	List<ColumnSettings> columnSettings = new ArrayList<>();
	List<SortSettings> sortSettings = new ArrayList<>();

	private Integer iSortingCols;
	private String sEcho;

	public void populateColumnData(HttpServletRequest request) {

		if (iColumns == null) return;

		for (int i = 0; i < iColumns; i++) {
			columnSettings.add(new ColumnSettings(
					Boolean.valueOf(request.getParameter("bSearchable_" + i)),
					request.getParameter("sSearch_" + i),
					Boolean.valueOf(request.getParameter("bRegex_" + i)),
					Boolean.valueOf(request.getParameter("bSortable_" + i)),
					request.getParameter("mDataProp_" + i)
			));
		}

		if (iSortingCols == null) return;

		for (Integer i = 0; i < iSortingCols; i++) {
			Integer sortCol = Integer.valueOf(request.getParameter("iSortCol_" + i));
			sortSettings.add(new SortSettings(
					sortCol,
					Sort.Direction.fromString(request.getParameter("sSortDir_" + i)),
					request.getParameter("mDataProp_" + sortCol)
			));
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ColumnSettings {
		private Boolean bSearchable;
		private String sSearch;
		private Boolean bRegex;
		private Boolean bSortable;
		private String mDataProp;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SortSettings {
		private Integer iSortCol;
		private Sort.Direction sSortDir;
		private String mDataProp;
	}
}