package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Reading reporting structure with employee id [{}]", id);

        Employee employeeToReport = employeeRepository.findByEmployeeId(id);

        if (employeeToReport == null) {
            // I just followed the precedent set in EmployeeServiceImpl for handling this error,
            // though it seems it would be cleaner to handle it by returning an HTTP status of 404
            // instead of throwing an internal error.
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // list of employee ids we have processed as a direct report under this employee
        Set<String> reportsList = new HashSet<>();

        if (employeeToReport.getDirectReports() != null) {
            for (Employee emp : employeeToReport.getDirectReports()) {
                // process the employee's direct reports and, recursively, all underlying direct reports
                findReports(emp.getEmployeeId(), reportsList);
            }
        }

        return new ReportingStructure(employeeToReport, reportsList.size());
    }

    //
    // See if we have checked this employeeId yet by checking the list of reports.
    // If we haven't then add this id to the list of reports and then process its direct reports.
    //
    // Input : employeeId - id of the employee we are processing
    //         reportsList - list of employee ids we have already processed
    // Output : reportsList may be updated with new employee ids
    //
    // NOTE: As with any recursive solution, we must consider if the line of direct reports may be
    // too long to allow recursive computation on it.  This seems unlikely given that we are dealing
    // with a chain of direct reports, not likely to be significantly long.
    //
    // NOTE 2:  This solution was chosen to allow for the possibility that an employee is a direct
    // report of more than one other employee, and that we would only want to count them once in
    // the reporting structure.
    //
    // NOTE 3 : This solution ends up reading the employee record for each employee in the reporting
    // structure one by one.  If we are supporting extremely large companies, we may want to rework
    // the solution to use MongoDB stream processing.
    //
    private void findReports(String employeeId, Set<String> reportsList) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // unable to find employee id, even though it is used as a direct report
        if (employee == null) {
            // TODO how do we want to handle this error case?  It basically indicates
            //  an internal database inconsistency.
            LOG.debug("Missing direct report with id [{}]", employeeId);
            return;
        }

        if (!reportsList.contains(employeeId)) {
            reportsList.add(employeeId);
            if (employee.getDirectReports() != null) {
                for (Employee emp : employee.getDirectReports()) {
                    findReports(emp.getEmployeeId(), reportsList);
                }
            }
        }
    }
}
