package com.property.tax.controller;

import com.property.tax.model.SelfAssessmentForm;
import com.property.tax.model.ZonalReport;
import com.property.tax.service.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PropertyTaxController {

    @Autowired
    PropertyTaxService propertyTaxService;

    @RequestMapping("/selfAssessment")
    public String selfAssessment(Model model){
        SelfAssessmentForm selfAssessmentForm = new SelfAssessmentForm();
        model.addAttribute("selfAssessmentForm", selfAssessmentForm);
        model.addAttribute("zones", propertyTaxService.getAllZones());
        model.addAttribute("propertyTypes", propertyTaxService.getAllPropertyType());
        model.addAttribute("statusTypes", propertyTaxService.getAllStatus());
        return "selfAssessment";
    }

    @RequestMapping("/zonalReport")
    public String zonalReport(Model model){
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
        System.out.println("The value of zonal class -->"+selfAssessmentForm.getZonalClassification());
        System.out.println("The value property type -->"+selfAssessmentForm.getPropertyDescription());
        System.out.println("The value status -->"+selfAssessmentForm.getStatus());
        System.out.println("The value construction year -->"+selfAssessmentForm.getBuildingConstructionYear());
        System.out.println("The value buildup area -->"+selfAssessmentForm.getBuildUpArea());
        double payableTax = propertyTaxService.calculatePayableTax(selfAssessmentForm);
        return new ResponseEntity(payableTax, HttpStatus.OK);
    }

    @RequestMapping("/payTax")
    public String payTax(SelfAssessmentForm selfAssessmentForm, BindingResult result, Model model){
        propertyTaxService.saveSelfAssessment(selfAssessmentForm);
        return "index";
    }


}
