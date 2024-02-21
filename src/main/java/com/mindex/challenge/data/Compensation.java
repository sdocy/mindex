package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Compensation {
    // NOTE : Unlike ReportingStructure, here I chose to interpret employee
    // as just the employee id, since this document will be stored in the
    // database, it did not make sense to me to waste space re-storing what
    // is already available in the "employee" document.  If this was correct,
    // then the field might be better named "employeeId" for consistency with
    // the "employee" document, however I followed the naming in the README.
    private String employee;
    private Float salary;
    private Date effectiveDate;

    Compensation() {
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
