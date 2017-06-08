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

	private static final String KEY_SEARCHABLE = "bSearchable_";
	private static final String KEY_SEARCH = "sSearch_";
	private static final String KEY_REGEX = "bRegex_";
	private static final String KEY_SORTABLE = "bSortable_";
	private static final String KEY_DATA_PROP = "mDataProp_";
	private static final String KEY_SORT_COL = "iSortCol_";
	private static final String KEY_SORT_DIR = "sSortDir_";

	public void populateColumnData(HttpServletRequest request) {

		if (iColumns == null) return;

		for (int i = 0; i < iColumns; i++) {
			columnSettings.add(new ColumnSettings(
					Boolean.valueOf(request.getParameter(KEY_SEARCHABLE + i)),
					request.getParameter(KEY_SEARCH + i),
					Boolean.valueOf(request.getParameter(KEY_REGEX + i)),
					Boolean.valueOf(request.getParameter(KEY_SORTABLE + i)),
					request.getParameter(KEY_DATA_PROP + i)
			));
		}

		if (iSortingCols == null) return;

		for (Integer i = 0; i < iSortingCols; i++) {
			Integer sortCol = Integer.valueOf(request.getParameter(KEY_SORT_COL + i));
			sortSettings.add(new SortSettings(
					sortCol,
					Sort.Direction.fromString(request.getParameter(KEY_SORT_DIR + i)),
					request.getParameter(KEY_DATA_PROP + sortCol)
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