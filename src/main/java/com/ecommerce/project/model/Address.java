package com.ecommerce.project.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	@NotBlank
	@Size(min = 5, message = "Street name must be atleast 5 characters")
	private String street;

	@NotBlank
	@Size(min = 5, message = "Building name must be atleast 5 characters")
	private String builingName;

	@NotBlank
	@Size(min = 4, message = "City name must be atleast 4 characters")
	private String city;

	@NotBlank
	@Size(min = 2, message = "Cistatety name must be atleast 2 characters")
	private String state;

	@NotBlank
	@Size(min = 2, message = "country name must be atleast 2 characters")
	private String country;

	@NotBlank
	@Size(min = 6, message = "pincode name must be atleast 6 characters")
	private String pincode;

	public Address(@NotBlank @Size(min = 5, message = "Street name must be atleast 5 characters") String street,
			@NotBlank @Size(min = 5, message = "Building name must be atleast 5 characters") String builingName,
			@NotBlank @Size(min = 4, message = "City name must be atleast 4 characters") String city,
			@NotBlank @Size(min = 2, message = "Cistatety name must be atleast 2 characters") String state,
			@NotBlank @Size(min = 2, message = "country name must be atleast 2 characters") String country,
			@NotBlank @Size(min = 6, message = "pincode name must be atleast 6 characters") String pincode) {
		super();
		this.street = street;
		this.builingName = builingName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}

	@ToString.Exclude
	@ManyToMany(mappedBy = "address")
	private List<User> users = new ArrayList<>();

}