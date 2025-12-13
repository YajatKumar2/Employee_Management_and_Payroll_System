//Here we will record employees attendence

package model;

import java.time.LocalDate;

public class AttendanceRecord {
	private int employeeId;
    private LocalDate date;
    private AttendanceStatus attendstatus;
    
    //constructor
    public AttendanceRecord(int employeeId, LocalDate date, AttendanceStatus status) {
        this.employeeId = employeeId;
        this.date = date;
        this.attendstatus = status;
    }

	public int getEmployeeId() {
		return employeeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public AttendanceStatus getAttendstatus() {
		return attendstatus;
	}
    
    //here also only we use get
    
    


}
