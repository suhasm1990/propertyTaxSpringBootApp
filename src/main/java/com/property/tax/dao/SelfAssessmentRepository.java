package com.property.tax.dao;

import com.property.tax.model.SelfAssessmentForm;
import com.property.tax.model.ZonalReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SelfAssessmentRepository extends CrudRepository<SelfAssessmentForm, Long> {

    @Query("select s.yearOfAssessment as yearOfAssessment, s.status as status, s.zonalClassification as zonalClassification, sum(s.totalTaxPayable) as amountCollected\n" +
            "from SelfAssessmentForm s\n" +
            "group by s.yearOfAssessment, s.status, s.zonalClassification\n" +
            "order by s.yearOfAssessment desc, s.zonalClassification, s.status")
    List<ZonalReport> getZonalReport();
}
