package com.property.tax.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResedentialPropertyTax {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "propertyTypeId")
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusType statusType;

    @ManyToOne
    @JoinColumn(name = "zoneId")
    private Zone zone;

    private double rate;

}
