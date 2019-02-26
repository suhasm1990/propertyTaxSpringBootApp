package com.property.tax.service.impl;

import com.property.tax.controller.PropertyTaxController;
import com.property.tax.dao.*;
import com.property.tax.model.*;
import com.property.tax.service.PropertyTaxService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class PropertyTaxServiceImpl implements PropertyTaxService {

    @Autowired
    PropertyTypeRepository propertyTypeRepository;

    @Autowired
    StatusTypeRepository statusTypeRepository;

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    SelfAssessmentRepository selfAssessmentRepository;

    @Autowired
    ResedentialPropertyTaxRepository resedentialPropertyTaxRepository;

    private static final Logger logger = LogManager.getLogger(PropertyTaxServiceImpl.class);

    @Override
    public Iterable<PropertyType> getAllPropertyType() {
        return propertyTypeRepository.findAll();
    }

    @Override
    public Iterable<StatusType> getAllStatus() {
        return statusTypeRepository.findAll();
    }

    @Override
    public Iterable<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @Override
    public void saveSelfAssessment(SelfAssessmentForm selfAssessmentForm){
        selfAssessmentRepository.save(selfAssessmentForm);
    }

    @Override
    public List<ZonalReport> getZonalReport(){
        return  selfAssessmentRepository.getZonalReport();
    }

    @Override
    public double calculatePayableTax(SelfAssessmentForm selfAssessmentForm) {
        Iterable<ResedentialPropertyTax> resedentialPropertyTaxes = resedentialPropertyTaxRepository.findAll();

        double unitAreaValue = 1;

        for(ResedentialPropertyTax resedentialPropertyTax: resedentialPropertyTaxes){
            if(resedentialPropertyTax.getPropertyType().getPropertyType().equalsIgnoreCase(selfAssessmentForm.getPropertyDescription())
                    && resedentialPropertyTax.getStatusType().getStatus().equalsIgnoreCase(selfAssessmentForm.getStatus())
                    && resedentialPropertyTax.getZone().getZone().equalsIgnoreCase(selfAssessmentForm.getZonalClassification())){

                unitAreaValue = resedentialPropertyTax.getRate();

            }

        }

        /* Resedential Property Tax Calculation
                Total_1 =Built Up area [Square feet] * unit area Value * 10 months
                Total_2 = Total_1 – Applicable depreciation
                Total_3 = Total_2 * 20% [Property tax for residential area]
                Total_4 = Total_3 * 24% [ CESS ==> Health cess + beggary cess + Library cess ]
                Total_5 = Total_3 + Total_4 */

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int ageOfBuilding = currentYear - selfAssessmentForm.getBuildingConstructionYear();

        int depreciationValue = 0;

        if(ageOfBuilding>=60){
            depreciationValue = 60;
        }else{
            depreciationValue = ageOfBuilding;
        }

        double total1 = selfAssessmentForm.getBuildUpArea() * unitAreaValue * 10;
        double total2 = total1 - ((total1*depreciationValue)/100);
        double total3 = (total2*20)/100;
        double total4 = (total3*24)/100;
        double total5 = total3+total4;

        logger.info("UnitArea value-->"+unitAreaValue);
        logger.info("DepreciationValue value-->"+depreciationValue);
        logger.info("Total1 value-->"+total1);
        logger.info("Total2 value-->"+total2);
        logger.info("Total3 value-->"+total3);
        logger.info("Total4 value-->"+total4);
        logger.info("Total5 value-->"+total5);

        return total5;
    }

}
