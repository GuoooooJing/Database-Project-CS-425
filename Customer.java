// Customer class for flight management application.

package flightApp;

public class Customer {
	
	private String email;
	private String name;
	private String homeAirport;
	private String address;
	private String creditCard;

	public Customer () {
		email = "default";
		name = "default";
		homeAirport = "default";
	}
	
	public Customer (String e, String n) {
		email = e;
		name = n;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String nemail) {
		this.email = nemail;
	}

	public String getName() {
		return name;
	}

	public void setName(String nname) {
		this.name = nname;
	}

	public String getHomeAirport() {
		return homeAirport;
	}

	public void setHomeAirport(String homeAirport) {
		this.homeAirport = homeAirport;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String naddress) {
		this.address = naddress;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String ncreditCard) {
		this.creditCard = ncreditCard;
	}
	
}
