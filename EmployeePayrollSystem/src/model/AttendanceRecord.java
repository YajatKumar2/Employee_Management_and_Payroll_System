//Here we will record employees attendence

package model;

import java.time.LocalDate;

public class AttendanceRecord {
	private int employeeId;
    private LocalDate date;
    private AttendanceStatus status;
    
    //constructor
    public AttendanceRecord(int employeeId, LocalDate date, AttendanceStatus status) {
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
    }

	public int getEmployeeId() {
		return employeeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public AttendanceStatus getStatus() {
		return status;
	}
    
    //here also only we use get
    
    


}
