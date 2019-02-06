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
public class StatusType {

    @Id
    @GeneratedValue
    private Long statusId;

    private String status;

}
