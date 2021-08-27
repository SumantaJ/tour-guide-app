package com.tour.guide.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
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
@Schema(name = "Booking", description = "Data object for hike tour booking", oneOf = Booking.class)
public class Booking extends BaseEntity{
	
	@Size(max = 100)
    @Schema(description = "Name of the hiker", example = "Sumanta Chakraborty", required = true)
	private String name;
	
    @Schema(description = "Selected id of the hike trail", example = "{\"id\": 1}", required = true)
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hikeTrailId", referencedColumnName = "id", nullable = false)
	private HikeTrail hikeTrail;
	
	@Pattern(message = "age must be a valid", regexp="^[0-9]*$")
    @Schema(description = "Age of hiker", example = "15", required = true)
	private Integer age;

    @Schema(description = "Booking refernce number", required = false)
	private Long bookingRefNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BookingStatusId", referencedColumnName = "id", nullable = false)
    @Schema(description = "Booking status", implementation = BookingStatus.class, example = "{}", required = false)
	private BookingStatus bookingStatus;

	@Transient
    @Schema(description = "Companion list", implementation = List.class, required = false)
	private List<Companion> companion = new ArrayList<Companion>();
}
