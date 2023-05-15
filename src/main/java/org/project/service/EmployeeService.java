package org.project.service;

import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.project.exception.ValidationException;
import org.project.model.Employee;
import org.project.model.JobPosition;
import org.project.model.LastEducation;
import org.project.model.User;
import org.project.model.dto.EmployeeRequest;
import org.project.model.dto.EmployeeResponse;
import org.project.util.DateUtil;
import org.project.util.FormatUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmployeeService {
    @Inject
    EntityManager entityManager;
    public Response post(EmployeeRequest request, String userId) throws ParseException {
        if (!FormatUtil.isAlphabet(request.fullName) ||
                !FormatUtil.isPhoneNumber(request.mobilePhoneNumber) ||
                !FormatUtil.isEmail(request.email) ||
                !FormatUtil.isGender(request.gender) ||
                !FormatUtil.isDateFormat(request.dob) ||
                !FormatUtil.isAlphabet(request.pob)) {
            throw new ValidationException("BAD_REQUEST");
        }
        if (request.jobPositionId == null ||
                request.lastEducationId == null ||
                request.jobPositionId.isBlank() ||
                request.lastEducationId.isBlank()){
            throw new ValidationException("BAD_REQUEST");
        }

        Optional<JobPosition> jobPositionOptional = JobPosition.findByIdOptional(request.jobPositionId);
        Optional<LastEducation> lastEducationOptional = LastEducation.findByIdOptional(request.lastEducationId);
        if (jobPositionOptional.isEmpty() || lastEducationOptional.isEmpty()){
            throw new ValidationException("BAD_REQUEST");
        }

        User user = User.findById(userId);

        persistEmployee(request, jobPositionOptional.get(), lastEducationOptional.get(), user, null);

        return Response.ok(new HashMap<>()).build();
    }

    public Response list(Integer page, String jobPositionId){
        if (page < 1){
            throw new ValidationException("BAD_REQUEST");
        }
        Long count = Employee.countByJobPosition(jobPositionId);
        Double totalPage = Math.ceil(count/10);

        List<Employee> list = Employee.findByJobPositionPagination(jobPositionId, page);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.totalPage = totalPage.longValue();
        employeeResponse.dataList = list;
        return Response.ok(employeeResponse).build();
    }

    public Response detail(String employeeId){
        Optional<Employee> employeeOptional = Employee.findByIdOptional(employeeId);
        if (employeeOptional.isEmpty()){
            throw new ValidationException("EMPLOYEE_NOT_FOUND");
        }
        return Response.ok(employeeOptional.get()).build();
    }
    public Response put(EmployeeRequest request, String userId, String employeeId) throws ParseException {
        if (employeeId == null || employeeId.isBlank()){
            throw new ValidationException("BAD_REQUEST");
        }
        if (!FormatUtil.isAlphabet(request.fullName) ||
                !FormatUtil.isPhoneNumber(request.mobilePhoneNumber) ||
                !FormatUtil.isEmail(request.email) ||
                !FormatUtil.isGender(request.gender) ||
                !FormatUtil.isDateFormat(request.dob) ||
                !FormatUtil.isAlphabet(request.pob)) {
            throw new ValidationException("BAD_REQUEST");
        }
        if (request.jobPositionId == null || request.lastEducationId == null ||
                request.jobPositionId.isBlank() || request.lastEducationId.isBlank()){
            throw new ValidationException("BAD_REQUEST");
        }

        Optional<JobPosition> jobPositionOptional = JobPosition.findByIdOptional(request.jobPositionId);
        Optional<LastEducation> lastEducationOptional = LastEducation.findByIdOptional(request.lastEducationId);
        if (jobPositionOptional.isEmpty() || lastEducationOptional.isEmpty()){
            throw new ValidationException("BAD_REQUEST");
        }

        User user = User.findById(userId);

        persistEmployee(request, jobPositionOptional.get(), lastEducationOptional.get(), user, employeeId);

        return Response.ok(new HashMap<>()).build();
    }

    public Response delete(String userId, String employeeId) throws ParseException {
        User user = User.findById(userId);
        deleteEmployee(user, employeeId);
        return Response.ok(new HashMap<>()).build();
    }

        @Transactional
        @TransactionConfiguration(timeout = 30)
        public Employee persistEmployee(EmployeeRequest request, JobPosition jobPosition, LastEducation lastEducation, User user, String employeeId) throws ParseException {

            Employee employee;
            if (employeeId == null){
                employee = new Employee();
                employee.setActive(true);
                employee.setCreatedBy(user);
            }else {
                Optional<Employee> employeeOptional = Employee.findByIdOptional(employeeId);
                if (employeeOptional.isEmpty()){
                    throw new ValidationException("EMPLOYEE_NOT_FOUND");
                }
                employee = employeeOptional.get();
            }
            employee.setFullName(request.fullName);
            employee.setDob(DateUtil.stringToDate(request.dob));
            employee.setPob(request.pob);
            employee.setJobPosition(jobPosition);
            employee.setLastEducation(lastEducation);
            employee.getUpdatedBy(user);
            employee.setEmail(request.email);
            employee.setMobilePhoneNumber(request.mobilePhoneNumber);
            employee.setGender(request.gender);

            Employee.persist(employee);
            return employee;
    }

    @Transactional
    @TransactionConfiguration(timeout = 30)
    public void deleteEmployee(User user, String employeeId) throws ParseException {
        Optional<Employee> employeeOptional = Employee.findByIdOptional(employeeId);

        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setActive(false);
            employee.setCreatedBy(user);

            Employee.persist(employee);
        }
    }

}
