package com.usdobserver.app.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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
	@SequenceGenerator(name="usdrate_idusdrate_seq", sequenceName="usdrate_idusdrate_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="usdrate_idusdrate_seq")
	@Column(name = "usdrate_id", updatable=false)
	private Long id;

	@Column(name = "effective_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;

	@Column(name = "rate_mid")
	private Double rate;

}
