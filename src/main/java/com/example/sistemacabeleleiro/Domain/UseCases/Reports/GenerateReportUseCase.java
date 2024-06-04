package com.example.sistemacabeleleiro.Domain.UseCases.Reports;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.EmployeeDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenerateReportUseCase {
    private SchedulingDAO schedulingDAO;
    private EmployeeDAO employeeDAO;

    public GenerateReportUseCase(SchedulingDAO schedulingDAO, EmployeeDAO employeeDAO) {
        this.schedulingDAO = schedulingDAO;
        this.employeeDAO = employeeDAO;
    }

    public List<Scheduling> findSchedulesByFilters(){
        return schedulingDAO.findAll();
    }
    public List<Scheduling> findSchedulesByFilters(Integer employeeId){
        return schedulingDAO.findByEmployee(employeeId);

    }

    public List<Scheduling> findSchedulesByFilters(LocalDate startDate) {
        return schedulingDAO.findByTimePeriod(startDate, LocalDate.MAX);
    }

    public List<Scheduling> findSchedulesByFilters(LocalDate endDate, boolean isEndDate) {
        return schedulingDAO.findByTimePeriod(LocalDate.MIN, endDate);
    }

    public List<Scheduling> findSchedulesByFilters(LocalDate startDate, Integer employeeId) {

        List<Scheduling> schedulesByDate = schedulingDAO.findByTimePeriod(startDate,LocalDate.MAX);
        List<Scheduling> schedulesList = new ArrayList<>();
        for (Scheduling s:schedulesByDate){
            if (s.getEmployee().getId().equals(employeeId)){
                schedulesList.add(s);
            }
        }
        return schedulesList;
    }

    public List<Scheduling> findSchedulesByFilters(LocalDate endDate,boolean isEndDate, Integer employeeId) {

        List<Scheduling> schedulesByDate = schedulingDAO.findByTimePeriod(LocalDate.MIN,endDate);
        List<Scheduling> schedulesList = new ArrayList<>();
        for (Scheduling s:schedulesByDate){
            if (s.getEmployee().getId().equals(employeeId)){
                schedulesList.add(s);
            }
        }
        return schedulesList;
    }

    public List<Scheduling> findSchedulesByFilters(LocalDate startDate, LocalDate endDate){

        if (startDate.isAfter(endDate) || endDate.isBefore(startDate)){
            throw new RuntimeException("Start date can't be after than the end date");
        }

        return schedulingDAO.findByTimePeriod(startDate,endDate);

    }
    public List<Scheduling> findSchedulesByFilters(LocalDate startDate, LocalDate endDate,Integer employeeId){
        if (startDate.isAfter(endDate) || endDate.isBefore(startDate)){
            throw new RuntimeException("Start date can't be after than the end date");
        }
        if (employeeDAO.findOne(employeeId).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        List<Scheduling> schedulesByDate = schedulingDAO.findByTimePeriod(startDate,endDate);
        List<Scheduling> schedulesList = new ArrayList<>();
        for (Scheduling s:schedulesByDate){
            if (s.getEmployee().getId().equals(employeeId)){
                schedulesList.add(s);
            }
        }
        return schedulesList;
    }


}
