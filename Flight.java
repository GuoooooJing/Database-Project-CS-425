// Flight class for flight management application.

package flightApp;

import java.sql.Date;

public class Flight {
	
	private String airlineCode;
	private int flightNum;
	private Date flightDate;
	
	public Flight () {
		airlineCode = "AA";
		flightNum = 0000;
		flightDate = new Date(0);
	}
	
	public Flight (String ac, int fn, Date fd) {
		airlineCode = ac;
		flightNum = fn;
		flightDate = fd;
	}
	
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String nairlineCode) {
		this.airlineCode = nairlineCode;
	}

	public int getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(int flightNum) {
		this.flightNum = flightNum;
	}

	public Date getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
}
