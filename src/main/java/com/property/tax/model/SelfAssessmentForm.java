package com.property.tax.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private int yearOfAssessment;

    private String owner;

    private String email;

    private String propertyAddress;

    private String zonalClassification;

    private String propertyDescription;

    private String status;

    private int buildingConstructionYear;

    private int buildUpArea;

    private double totalTaxPayable;

}