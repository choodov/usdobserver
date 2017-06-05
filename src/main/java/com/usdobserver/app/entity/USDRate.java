package com.usdobserver.app.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Chudov A.V. on 6/5/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "USDRATES")
@Table(name = "usdrates")
public class USDRate {

	@Id
	@Column(name = "effective_date")
	private String date;

	@Column(name = "rate_mid")
	private Double rate;

}
