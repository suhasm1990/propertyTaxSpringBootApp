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
public class Zone {

    @Id
    @GeneratedValue
    private Long zoneId;

    private String zone;

}
