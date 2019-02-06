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
public class PropertyType {

    @Id
    @GeneratedValue
    private Long propertyTypeId;

    private String propertyType;

}
