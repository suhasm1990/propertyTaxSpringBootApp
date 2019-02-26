package com.property.tax.controller;

import com.property.tax.TaxApplication;
import com.property.tax.model.SelfAssessmentForm;
import com.property.tax.model.ZonalReport;
import com.property.tax.service.PropertyTaxService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PropertyTaxController {

    @Autowired
    PropertyTaxService propertyTaxService;
    private static final Logger logger = LogManager.getLogger(PropertyTaxController.class);

    @RequestMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping("/international")
    public String getInternationalPage() {
        return "index";
    }

    @RequestMapping("/selfAssessment")
    public String selfAssessment(Model model){
        logger.info("Inside  selfAssessment method");
        SelfAssessmentForm selfAssessmentForm = new SelfAssessmentForm();
        model.addAttribute("selfAssessmentForm", selfAssessmentForm);
        model.addAttribute("zones", propertyTaxService.getAllZones());
        model.addAttribute("propertyTypes", propertyTaxService.getAllPropertyType());
        model.addAttribute("statusTypes", propertyTaxService.getAllStatus());
        return "selfAssessment";
    }

    @RequestMapping("/zonalReport")
    public String zonalReport(Model model){
        logger.info("Inside  zonalReport method");
        List<ZonalReport> zonalReportList = propertyTaxService.getZonalReport();
        List<Integer> yearsList =  zonalReportList.stream()
                                .map(ZonalReport::getYearOfAssessment)
                                .distinct()
                                .collect(Collectors.toList());
        model.addAttribute("yearsList", yearsList);
        model.addAttribute("zonalReportList", zonalReportList);

        return "zonalReport";
    }

    @RequestMapping(value = "/calculateTax", method = RequestMethod.POST)
    public ResponseEntity calculateTax(@RequestBody SelfAssessmentForm selfAssessmentForm){

        logger.info("The value of zonal class -->"+selfAssessmentForm.getZonalClassification());
        logger.info("The value property type -->"+selfAssessmentForm.getPropertyDescription());
        logger.info("The value status -->"+selfAssessmentForm.getStatus());
        logger.info("The value construction year -->"+selfAssessmentForm.getBuildingConstructionYear());
        logger.info("The value buildup area -->"+selfAssessmentForm.getBuildUpArea());
        double payableTax = propertyTaxService.calculatePayableTax(selfAssessmentForm);
        return new ResponseEntity(payableTax, HttpStatus.OK);
    }

    @RequestMapping("/payTax")
    public String payTax(@Valid SelfAssessmentForm selfAssessmentForm, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("zones", propertyTaxService.getAllZones());
            model.addAttribute("propertyTypes", propertyTaxService.getAllPropertyType());
            model.addAttribute("statusTypes", propertyTaxService.getAllStatus());
            return "selfAssessment";
        }
        propertyTaxService.saveSelfAssessment(selfAssessmentForm);
        return "index";
    }


}
