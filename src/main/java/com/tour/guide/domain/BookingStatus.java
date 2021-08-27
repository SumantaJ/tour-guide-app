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
@Schema(name = "BookingStatus", description = "Data object for book status", oneOf = BookingStatus.class)
public class BookingStatus extends BaseEntity{
	
	@Size(max = 100)
	private String name;
	
}
