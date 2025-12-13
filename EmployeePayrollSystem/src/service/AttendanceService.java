package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.AttendanceRecord;
import model.AttendanceStatus;

public class AttendanceService {
	private List<AttendanceRecord> records = new ArrayList<>();
	
	//marking attendance
    public void markAttendance(int empId, LocalDate date, AttendanceStatus status) {
        records.add(new AttendanceRecord(empId, date, status));
    }

    public List<AttendanceRecord> getRecordsForEmployeeMonth(int empId, int year, int month) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord r : records) {
            LocalDate d = r.getDate();
            if (r.getEmployeeId() == empId &&
                d.getYear() == year &&
                d.getMonthValue() == month) {
                result.add(r);
            }
        }
        return result;
    }
    //calculate lost money becuz of attendance
    public double calculateLossOfPay(int empId, int year, int month, double perDaySalary) {
        List<AttendanceRecord> list = getRecordsForEmployeeMonth(empId, year, month);
        int absents = 0;
        int halfDays = 0;

        for (AttendanceRecord r : list) {
            if (r.getAttendstatus() == AttendanceStatus.ABSENT) {
                absents++;
            } else if (r.getAttendstatus() == AttendanceStatus.HALF_DAY) {
                halfDays++;
            }
        }

        //this is important logic,
        //we are cutting the money by the no of absents, we are even doing half day absents
        //for that we wil need everyday salary
        //i dont think we will do loss of payment for leave, beacuase that will be done in absentees
        double loss = absents * perDaySalary + halfDays * perDaySalary * 0.5;
        return loss;
    }

}
