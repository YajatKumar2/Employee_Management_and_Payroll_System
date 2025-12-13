//here we will record the money payments done, i am using local date
//becuz it will be easy for the user

package model;

import java.time.LocalDate;

public class PaymentRecord {
	private LocalDate date;
	private double gross;
    private double deductions;
    private double net;
    private int salaryMonth;
    private int salaryYear;
    
    //constructorr
    public PaymentRecord(double gross, double deductions, double net, int salaryMonth,
    		int salaryYear) {
        this.date = LocalDate.now();
        this.gross = gross;
        this.deductions = deductions;
        this.net = net;
        this.salaryMonth = salaryMonth;
        this.salaryYear = salaryYear;
    }

	public LocalDate getDate() {
		return date;
	}

	public double getGross() {
		return gross;
	}

	public double getDeductions() {
		return deductions;
	}

	public double getNet() {
		return net;
	}
	
	public int getSalaryMonth() {
	    return salaryMonth;
	}

	public int getSalaryYear() {
	    return salaryYear;
	}

    
    //we will be using here only the get but no set 
   //the code is going up, dont care about that 
    

}
