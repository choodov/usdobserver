package com.usdobserver.app.web.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"sEcho", "iTotalRecords", "iTotalDisplayRecords", "aaData"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
		getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DataTablesRenderDTO<E> {

	@JsonProperty("sEcho")
	private String sEcho;

	@JsonProperty("iTotalRecords")
	private Integer iTotalRecords;

	@JsonProperty("iTotalDisplayRecords")
	private Integer iTotalDisplayRecords;

	@JsonProperty("aaData")
	private List<E> aaData;
}
