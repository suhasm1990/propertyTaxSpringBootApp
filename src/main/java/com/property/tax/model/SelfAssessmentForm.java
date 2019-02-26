package com.property.tax.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelfAssessmentForm {

    @Id
    @GeneratedValue
    private Long id;

    @Min(value = 1900, message = "Building constructed year cannot be less than Year 1900")
    private int yearOfAssessment;

    @NotEmpty(message = "Name cannot be empty")
    private String owner;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Address of the Property cannot be empty")
    private String propertyAddress;

    @NotEmpty(message = "Zonal Classification cannot be empty")
    private String zonalClassification;

    @NotEmpty(message = "Description of the Property cannot be empty")
    private String propertyDescription;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    @Min(value = 1900, message = "Building constructed year cannot be less than Year 1900")
    private int buildingConstructionYear;

    @Positive(message = "Total Tax Payable cannot be zero or less")
    private int buildUpArea;

    @Positive(message = "Total Tax Payable cannot be zero or less")
    private double totalTaxPayable;

}