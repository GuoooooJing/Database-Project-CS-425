// Booking class for flight management application.

package flightApp;

import java.sql.Date;

public class Booking {
	private int flightNumber;
	private Date flightDate;
	private String airlineCode;
	private String classOfSeat;
	
	public Booking (int fn, Date fd, String ac, String s) {
		flightNumber = fn;
		flightDate = fd;
		airlineCode = ac;
		setClassOfSeat(s);
	}
	
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(int nflightNumber) {
		flightNumber = nflightNumber;
	}
	public Date getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(Date nflightDate) {
		flightDate = nflightDate;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String nairlineCode) {
		airlineCode = nairlineCode;
	}

	public String getClassOfSeat() {
		return classOfSeat;
	}

	public void setClassOfSeat(String nclassOfSeat) {
		classOfSeat = nclassOfSeat;
	}
	
}
