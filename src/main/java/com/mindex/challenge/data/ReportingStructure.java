package com.mindex.challenge.data;

public class ReportingStructure {
    // NOTE : I wasn't 100% sure if this was meant to be the entire employee record,
    // or just the employee id.  Since the README specifically said "employee", that
    // is how I structured it.
    private Employee employee;
    private Integer numberOfReports;

    public ReportingStructure() {
    }

    public ReportingStructure(Employee employee, Integer numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(Integer numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
