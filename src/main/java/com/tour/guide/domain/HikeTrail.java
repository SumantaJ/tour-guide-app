package com.tour.guide.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Schema(name = "HikeTrail", description = "Data object for hike trail", oneOf = HikeTrail.class)
public class HikeTrail extends BaseEntity{

	@Size(max = 50)
	private String name;

	@Size(max = 50)
	private String startAt;

	@Size(max = 50)
	private String endAt;

	@Size(max = 50)
	private Integer minimumAge;

	@Size(max = 50)
	private Integer maximumAge;

	@Size(max = 50)
	private Float unitPrice;

}
