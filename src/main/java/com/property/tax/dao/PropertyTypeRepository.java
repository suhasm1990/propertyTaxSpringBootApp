package com.property.tax.dao;

import com.property.tax.model.PropertyType;
import org.springframework.data.repository.CrudRepository;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, Long> {
}
