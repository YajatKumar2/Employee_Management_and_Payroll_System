//here we will record the money payments done, i am using local date
//becuz it will be easy for the user

package model;

import java.time.LocalDate;

public class PaymentRecord {
	private LocalDate date;
	private double gross;
    private double deductions;
    private double net;
    
    //constructorr
    public PaymentRecord(LocalDate date, double gross, double deductions, double net) {
        this.date = date;
        this.gross = gross;
        this.deductions = deductions;
        this.net = net;
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
    
    //we will be using here only the get but no set 
   //the code is going up, dont care about that 
    

}
