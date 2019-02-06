package com.property.tax.service;

import com.property.tax.model.*;

import java.util.List;

public interface PropertyTaxService {

    Iterable<PropertyType> getAllPropertyType();

    Iterable<StatusType> getAllStatus();

    Iterable<Zone> getAllZones();

    void saveSelfAssessment(SelfAssessmentForm selfAssessmentForm);

    List<ZonalReport> getZonalReport();

    double calculatePayableTax(SelfAssessmentForm selfAssessmentForm);

}
