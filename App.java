/* CS 425 Database Organization
 * Final Project: Flight Management Application
 * Group 25: Moueed Salameh, Jiarui Jiang, Yifeng Xie
 * This is a command line application.
 */
package flightApp;

// Import statements.
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

public class App {

	// Constants for connecting to database.
	public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/FinalProject";
	public static final String DBUSER = "postgres";
	public static final String DBPASSWD = "973606";
	
	public static void main(String[] args) throws Exception{
		System.out.println("Welcome to the flight booking management app");
		initialPrompt();	// Text prompt showing the user commands.
		Customer user = new Customer();
		Scanner in = new Scanner (System.in);
		String cmd = "";	// Command character for knowing the user's choice.
		Boolean signedIn = false;	// Indicates if user is signed in or not.
		
		// Loop until the user registers, signs in, or quits the application
		do {
			cmd = in.nextLine();
			if (cmd.equals("1")) {		// User wishes to register. Ask for new email and a name.
				System.out.println("Please enter your email address:");
				user.setEmail((in.nextLine()));
				System.out.println("Please enter your name:");
				user.setName((in.nextLine()));
				try {
				signedIn = registerCustomer(user);	// Handle the registration. Method returns true if the registration succeeded, meaning customer is signed in.
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("2")) {	// User wishes to sign in. Ask for existing email.
				System.out.println("Please enter your email to sign in:");
				user.setEmail((in.nextLine()));
				signedIn = signIn(user);	// Handle the sign in. Method returns true if sign in succeeded, meaning customer's email was found in the database.
			} else if (cmd.equals("q")) {	// Handle the exit command q. Prints an exit message and breaks the loop.
				System.out.println("Command 'q' entered. Quitting the Application.");
				break;
			} else {	// Handle incorrect commands. Complain that the command entered is not valid and loop.
				System.out.println("'"+ cmd + "'" + " is not a command.");
				initialPrompt();
			}
		} while (!cmd.equals("q") && !signedIn);	
		
		// After the sign in was successful, print a welcome message and enter the main menu.
		if (signedIn) {
			System.out.println("Welcome! Thank you for signing in.");
			signedinPrompt();	// Print the main menu user commands.
		}
		
		/* 
		 * Loop until the user quits the application.
		 * Ask the user for a command to execute.
		 */
		while (!cmd.equals("q")) {
			cmd = in.nextLine();
			if (cmd.equals("1")) {	// User wishes to modify personal information.
				System.out.println("Entering Personal Information Modification Menu:");
				cmd = modifyPersonalInfo(user);	// Handle personal information modifications.
			} else if (cmd.equals("2")) {	// User wishes to search flights.
				System.out.println("Entering Flight Search Menu:");
				cmd = flightSearch(user);	// Handle flight searches.
			} else if (cmd.equals("3")) {	// User wishes to manage bookings
				System.out.println("Entering Booking Management Menu.");
				cmd = bookingManage(user);	// Handle booking management
			} else if (cmd.equals("q")) {	// User wishes to quit. Print an exit message and break loop.
				System.out.println("Command 'q' entered. Quitting the Application.");
				break;
			} else {	// Handle incorrect commands. Print a message complaining about invalid command and loop.
				System.out.println("'"+ cmd + "'" + " is not a command.");
				signedinPrompt();	// Print the main menu user commands again for the loop.
			}
		}
		in.close();
	}
	
	/*
	 * Method for handling customer registration.
	 * Takes a Customer object to check the email and name.
	 * Accesses the database to add new customer or complain if email address already exists.
	 * Returns true if the registration was a success.
	 */
	public static boolean registerCustomer(Customer newUser) throws Exception{
		try {
			boolean sigin = false;
			// create a connection
			Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
			// create a statement object to execute commands
			// Create query statement to find the customer's entered email.
			PreparedStatement s = c.prepareStatement("SELECT email_address FROM customer WHERE email_address = ?;");
			s.setString(1, newUser.getEmail());
			ResultSet r = s.executeQuery();
			// If the result set is empty, meaning the email does not exist, we can add the customer.
			if (!r.next()) {
				// Register the customer using an INSERT statement.
				s = c.prepareStatement("INSERT INTO Customer VALUES (?,?)");
				s.setString(1, newUser.getEmail());
				s.setString(2, newUser.getName());
				s.executeUpdate();
				sigin = true;		// Set sign in/registration success to true.
			} else {	// Result set was not empty, meaning the email was found, customer registration failed.
				System.out.println("That email already exists. Please sign in using it or register with another email");
				initialPrompt();	// Print initial prompt again to continue asking the customer to sign in or register.
			}
			r.close();
			c.close();
			s.close();
			return sigin;	// Return false if not registered/signed in.
		}
		catch (Exception e) {
			System.err.println("An error occurred: " + e.toString());
			return false;
		}
	}
	
	/*
	 * Method for handling customer sign in.
	 * Takes a Customer object to check the email.
	 * Accesses the database to check if the customer email exists.
	 * Return true if sign in was a success.
	 */
	public static Boolean signIn(Customer newUser) throws Exception{
		try {
			Boolean successfulSignin = false;
			// create a connection
			Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
			// create a statement object to execute commands
			// Create query for the email of the customer
			PreparedStatement s = c.prepareStatement("SELECT email_address FROM customer WHERE email_address = ?;");
			s.setString(1, newUser.getEmail());
			ResultSet r = s.executeQuery();
			// If the result set is empty, the customer's email was not found. Sign in failed.
			if (!r.next()) {
				System.out.println("This email does not exist. Please either register with a new email or enter a valid email.");
				initialPrompt();
			} else {	// If the result set is not empty, the customer's email was found. Sign in success.
				System.out.println("Sign in successful.");
				successfulSignin = true;	// Set return value to true since sign in was successful.
			}
			return successfulSignin;
		}
		catch (Exception e) {
			System.err.println("An error occurred: " + e.toString());
			return false;
		}
	}
	
	/*
	 * Method for handling personal information modifications.
	 * Takes a Customer object to carry queries and update/insert statements.
	 * Accesses the database to modify customer information.
	 * Returns a string. If the returned string is 'q' then quit the application.
	 */
	public static String modifyPersonalInfo(Customer user) throws Exception{
		Scanner in = new Scanner (System.in);
		String cmd = "";	// User's command input.
		// Loop until user quits with 'q' or returns to main menu with 'b'.
		do {	
			modPIPrompt();	// Prints out the modification menu's user commands.
			cmd = in.nextLine();
			if (cmd.equals("1")) {	// User wishes to enter a new address.
				System.out.println("Please enter the address you would like to add:");
				user.setAddress(in.nextLine());	// Get the new address.
				// Insert new address value into the database with the user's email.
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					PreparedStatement s = c.prepareStatement("INSERT INTO Customer_ADCR VALUES (?,?)");
					s.setString(1, user.getEmail());
					s.setString(2, user.getAddress());
					s.executeUpdate();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("2")) {	// User wishes to add a payment method. Ask for both an address and a new credit card.	
				System.out.println("Please enter the payment method you would like to add:");
				user.setCreditCard(in.nextLine());
				System.out.println("Please enter the address you would like to link the payment method to:");
				user.setAddress(in.nextLine());
				// Insert the new payment method tied to the address.
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create a query to see if the credit card already exists.
					PreparedStatement s = c.prepareStatement("SELECT credit_card FROM customer_adcr WHERE credit_card = ?;");
					s.setString(1, user.getCreditCard());
					ResultSet r = s.executeQuery();
					// If the result set is empty, the credit card does not exist. Add the credit card tied to the address.
					if (!r.next()) {
						s = c.prepareStatement("INSERT INTO Customer_ADCR VALUES (?,?,?)");
						s.setString(1, user.getEmail());
						s.setString(2, user.getAddress());
						s.setString(3, user.getCreditCard());
						s.executeUpdate();
					} else {	// Result set not empty. Credit car exists. Complain and do not add the payment method.
						System.out.println("That payment method already exists.");
					}
					r.close();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("3")) {	// User wishes to modify an address.
				System.out.println("Please enter the address you would like to modify:");
				String temp = (in.nextLine());	// Address to modify.
				System.out.println("Please enter the new address:");
				user.setAddress(in.nextLine());	// Address to replace the old.
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create query to see if the address to replace exists.
					PreparedStatement s = c.prepareStatement("SELECT address FROM customer_adcr WHERE address = ? AND email_address = ?;");
					s.setString(1, temp);
					s.setString(2, user.getEmail());
					ResultSet r = s.executeQuery();
					// If the result set is empty, the address does not exists. Complain and do not change anything.
					if (!r.next()) {
						System.out.println("The address you want to replace does not exist.");
					} else {	// Result set is not empty. Address exists. Replace it with new address.
						s = c.prepareStatement("UPDATE Customer_ADCR SET address = ? WHERE address = ?");
						s.setString(1, user.getAddress());
						s.setString(2, temp);
						s.executeUpdate();
					}
					r.close();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("4")) {	// User wishes to modify a payment method. Ask for a credit card to replace and a new credit card.
				System.out.println("Please enter the payment method you would like to modify:");
				String temp = (in.nextLine());
				System.out.println("Please enter the new payment method:");
				user.setCreditCard(in.nextLine());
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create query to check if the credit card to replace exists.
					PreparedStatement s = c.prepareStatement("SELECT credit_card FROM customer_adcr WHERE credit_card = ?;");
					s.setString(1, temp);
					ResultSet r = s.executeQuery();
					if (!r.next()) {	// Result set is empty. Credit card does not exist. Complain and do not change anything.
						System.out.println("The payment method you want to replace does not exist.");
					} else {	// Result set is not empty. Credit card exists. Replace it with new credit card.
						s = c.prepareStatement("UPDATE Customer_ADCR SET credit_card = ? WHERE credit_card = ?");
						s.setString(1, user.getCreditCard());
						s.setString(2, temp);
						s.executeUpdate();
					}
					r.close();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("5")) {	// User wishes to delete an address. Ask for address to delete. Cannot delete addresses with a credit card.
				System.out.println("Please enter the address you would like to delete:");
				user.setAddress(in.nextLine());
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create a query to find the address and any credit cards tied to it.
					PreparedStatement s = c.prepareStatement("SELECT DISTINCT address FROM customer_adcr WHERE address = ? AND email_address = ? AND credit_card IS NOT NULL;");
					s.setString(1, user.getAddress());
					s.setString(2, user.getEmail());
					ResultSet r = s.executeQuery();
					// If the result set is empty, no credit cards are tied to this address. Safe to delete the address.
					if (!r.next()) {
						s = c.prepareStatement("DELETE FROM Customer_ADCR WHERE address = ? AND email_address = ?");
						s.setString(1, user.getAddress());
						s.setString(2, user.getEmail());
						s.executeUpdate();
						
					} else {	// Result set is not empty. The address is tied to one or more credit cards. Complain and do not delete the address.
						System.out.println("The address you want to delete is linked to a payment method and cannot be deleted.");
					}
					r.close();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("6")) {	// User wishes to delete a payment method. Ask for a credit card to delete.
				System.out.println("Please enter the payment method you would like to delete:");
				user.setCreditCard(in.nextLine());
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create a query to delete the credit card.
					PreparedStatement s = c.prepareStatement("DELETE FROM Customer_ADCR WHERE credit_card = ?");
					s.setString(1, user.getCreditCard());
					s.executeUpdate();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("7")) {	// User wishes to modify/add a home airport
				System.out.println("Please enter the new home airport you would like:");
				String temp = (in.nextLine());
				try {
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create an update statement to change the customer's home airport.
					PreparedStatement s = c.prepareStatement("UPDATE Customer SET home_airport = ? WHERE email_address = ?");
					s.setString(1, temp);
					s.setString(2, user.getEmail());
					s.executeUpdate();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("8")) {	// User wishes to delete home airport.
				try {
					String temp = null;
					// create a connection
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create update statement to set home airport of the customer to null.
					PreparedStatement s = c.prepareStatement("UPDATE Customer SET home_airport = ? WHERE email_address = ?");
					s.setString(1, temp);
					s.setString(2, user.getEmail());
					s.executeUpdate();
					c.close();
					s.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("q")) {	// User wishes to quit. Print exit message and quit by returning 'q' to the main method.
				System.out.println("Command 'q' entered. Quitting the Application.");
				return ("q");
			} else if (cmd.equals("b")) {	// User wishes to go back to the main menu. Do so by returning a string other than 'q'.
				System.out.println("Command 'b' entered. Returning to menu.");
				signedinPrompt();
				return ("");
			} else {	// Handle incorrect commands. Complain about invalid command and loop.
				System.out.println("'"+ cmd + "'" + " is not a command.");
			}
		} while (!cmd.equals("q") || (!cmd.equals("b")));
		in.close();
		return ("");	// Return anything other than 'q' by default.
	}
	
	/*
	 * Method for handling flight searches.
	 * Takes in a Customer object to check the user email in queries.
	 * Uses the Flight class to create array lists of flights for the results of queries.
	 * Accesses the database to query flights.
	 * Customers specify search criteria and are presented with a list of flights that fit said criteria.
	 * Calls the method inpectFlight to provide more detailed information about flight connections.
	 * Returns a string. If the returned string is 'q' then quit the application.
	 */
	public static String flightSearch(Customer user) throws Exception {
		String destination ="";	// Destination airport for the flight. Required.
		String departure ="";	// Departure airport for the flight. Required.
		boolean returnFlight = false;	// Indicates if the user wants to search for a return flight as well. Optional.
		int maxCon = 50;	// Maximum number of connections the user allows. Optional.
		int maxPrice = 1000000;	// Maximum price of entire trip (one way). Optional.
		String orderby = "flight_number";	// The way in which the query is ordered. Optional. 
		String maxTime = "99:59:59";	// The maximum total time of entire trip the user allows (one way). Optional.
		Date departureDate;	// Departure date for flight. Required.
		Date returnDate = new Date(0);	// Return dat for flight. Required only if returnFlight is true.
		Scanner in = new Scanner (System.in);
		String cmd = "";
		// Loop until the user quits application with 'q' or returns to main menu with 'b'.
		do {
			flightSearchPrompt();	// Print the flight search menu user commands.
			cmd = in.next();
			if (cmd.equals("a")) {	// User wishes to display the list of IATAs, airports, and countries.
				System.out.println("Printing out the airports:");
				printAirports();
			} else if (cmd.equals("b")) {	// User wishes to return to main menu. Do so by returning a string other than 'q' to go back to main method.
				System.out.println("Command 'b' entered. Returning to menu.");
				signedinPrompt();
				return ("");
			} else if (cmd.equals("q")) {	// User wishes to quit application. Do so by returning 'q'.
				System.out.println("Command 'q' entered. Quitting the Application.");
				return ("q");
			} else {	// User begins to enter the specifications for the flight search. 
				departure = (cmd);
				System.out.println("Please enter your destination airport code:");
				destination = (in.next());
				System.out.println("Please enter your desired departure date (format: YYYY-MM-DD):");
				departureDate = (Date.valueOf(in.next()));
				System.out.println("Would you like a return flight (YES or NO)?:");
				cmd = in.next();
				if (cmd.equals("YES")) {
					returnFlight = true;
					System.out.println("Please enter your desired return date (format: YYYY-MM-DD):");
					returnDate = (Date.valueOf(in.next()));
				}
				System.out.println("Would you like to set a maximum number of connections? (YES or NO)?:");
				cmd = in.next();
				if (cmd.equals("YES")) {
					System.out.println("Please enter your a maximum number of connections:");
					maxCon = in.nextInt();
				}
				System.out.println("Would you like to set a maximum price for the trip? (YES or NO)?:");
				cmd = in.next();
				if (cmd.equals("YES")) {
					System.out.println("Please enter your desired maximum price:");
					maxPrice = in.nextInt();
				}
				System.out.println("Would you like to set a maximum time for the trip? (YES or NO)?:");
				cmd = in.next();
				if (cmd.equals("YES")) {
					System.out.println("Please enter your desired maximum time (format: HH:MM:SS in 24 hours):");
					maxTime = (in.next());
				} 
				System.out.println("Would you like to order the search results in a specific way? (YES or NO)?:");
				cmd = in.next();
				if (cmd.equals("YES")) {
					System.out.println("Please enter 'e' to sort by economy class price.\nPlease enter 'f' to order by first class price.\nPlease enter 't' to order by total time.");
					cmd = in.next();
					if (cmd.equals("e")) {
						orderby = "economy_class_prices";
					} else if (cmd.equals("f")) {
						orderby = "first_class_prices";
					} else if (cmd.equals("t")) {
						orderby = "total_time";
					}
				}
				// All specifications are filled out. Begin to execute the query.
				int index = -1;	// Index used for the array list of Flight objects that will make up the results our query.
				// Loop until the user wishes to start a new query by entering '-1'.
				do {
					int i = 1;	// Index to display next to each item of the query.
					ArrayList<Flight> flightList = new ArrayList<Flight>();	// Create array list of Flight objects to display and use for further inspection queries.
					try {
						// create a connection
						Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
						// create a statement object to execute commands
						// Create a recursive query to get all the flights that satisfy the customer's conditions.
						PreparedStatement s = c.prepareStatement("WITH RECURSIVE one AS (\r\n" + 
								"	SELECT 	airline_code, flight_number, flight_date,\r\n" + 
								"        departure_airport, destination_airport, \r\n" + 
								"        0 number_connections, ARRAY[departure_airport,destination_airport]::text[] AS connections,\r\n" + 
								"        departure_time, arrival_time, ((arrival_time - departure_time) :: TIME) :: INTERVAL AS total_time, \r\n" + 
								"        first_class_price AS first_class_prices, economy_class_price AS economy_class_prices\r\n" + 
								"    FROM flight \r\n" + 
								"    WHERE departure_airport = ? AND flight_date = ? AND (max_economy_class_seats > 0 OR max_first_class_seats > 0)\r\n" + 
								"    UNION\r\n" + 
								"    SELECT 	one.airline_code, one.flight_number, one.flight_date,\r\n" + 
								"            one.departure_airport, two.destination_airport, \r\n" + 
								"            (one.number_connections + 1) AS number_connections, one.connections || ARRAY[two.destination_airport]::text[] AS connections,\r\n" + 
								"            one.departure_time, one.arrival_time, one.total_time + ((two.arrival_time - two.departure_time) :: TIME) :: INTERVAL AS total_time,\r\n" + 
								"            one.first_class_prices + two.first_class_price AS first_class_prices, one.economy_class_prices + two.economy_class_price AS economy_class_prices  \r\n" + 
								"    FROM one, flight two\r\n" + 
								"    WHERE one.destination_airport = two.departure_airport AND one.flight_date = two.flight_date AND NOT connections @> ARRAY[two.destination_airport]::text[]\r\n" + 
								")\r\n" + 
								"SELECT * \r\n" + 
								"FROM one\r\n" + 
								"WHERE 	destination_airport = ?\r\n" + 
								"        AND number_connections <= ?\r\n" + 
								"        AND (first_class_prices <= ? OR economy_class_prices <= ?)\r\n" + 
								"        AND total_time <= ((? :: TEXT) :: INTERVAL)\r\n" + 
								"ORDER BY (?);");
						s.setString(1, departure);
						s.setDate(2, departureDate);
						s.setString(3, destination);
						s.setInt(4, maxCon);
						s.setInt(5, maxPrice);
						s.setInt(6, maxPrice);
						s.setString(7, maxTime);
						s.setString(8, orderby);
						ResultSet r = s.executeQuery();
						boolean flightsFound = false;	// Indicates whether flights from the departure airport to the destination airport were found.
						// If the result set is not empty, the query was a success and we found flights that satisfy the customer's conditions.
						if (r.next()) {
							flightsFound = true;	// Set to true because the query found flights.
							// Begin printing the flights found.
							System.out.println("These are the flights from your departure airport to your destination airport:");
							System.out.format("...|%-15s||%-14s||%-11s||%-20s||%-20s||%-15s||%-30s||%-15s||%-15s||%-15s||%-20s||%-20s|\n", 
									"Airport Code", "Flight Number", "Flight Date", "Departure Airport", "Destination Airport", 
									"No. Connections", "Connections", "Departure Time", "Arrival Time", "Total Time", 
									"First Class Prices", "Economy Class Prices");
							// Loop while there are still tuples in the result set, meaning there are still flights that were found.
							do {
								Flight f = new Flight(r.getString(1), r.getInt(2), r.getDate(3));	// Create Flight objects from each tuple in the query.
								flightList.add(f);	// Add the created Flight objects to the Flight array list.
								// Print the tuples.
								System.out.format("%d. |%-15s||%-14d||%-11s||%-20s||%-20s||%-15d||%-30s||%-15s||%-15s||%-15s||%-20d||%-20d|\n", i, 
										r.getString(1), r.getInt(2), r.getDate(3), r.getString(4), r.getString(5), 
										r.getInt(6), r.getString(7), r.getTime(8), r.getTime(9), r.getTime(10), 
										r.getInt(11), r.getInt(12));
								i++;	// Increment the display index.
							} while (r.next());
						} else {	// No flights were found by our query that satisfy the customer's criteria.
							System.out.println("No flights were found.");
						}
						// If the customer had specified that they wish to search for a return flight, create a new query and print the results.
						// This time, use the return date specified instead of the departure date.
						// Also switch the departure airport with the destination airport and vice versa.
						if (returnFlight = true) {
							System.out.println("These are the return flights:");
							s = c.prepareStatement("WITH RECURSIVE one AS (\r\n" + 
									"	SELECT 	airline_code, flight_number, flight_date,\r\n" + 
									"        departure_airport, destination_airport, \r\n" + 
									"        0 number_connections, ARRAY[departure_airport,destination_airport]::text[] AS connections,\r\n" + 
									"        departure_time, arrival_time, ((arrival_time - departure_time) :: TIME) :: INTERVAL AS total_time, \r\n" + 
									"        first_class_price AS first_class_prices, economy_class_price AS economy_class_prices\r\n" + 
									"    FROM flight \r\n" + 
									"    WHERE departure_airport = ? AND flight_date = ? AND (max_economy_class_seats > 0 OR max_first_class_seats > 0)\r\n" + 
									"    UNION\r\n" + 
									"    SELECT 	one.airline_code, one.flight_number, one.flight_date,\r\n" + 
									"            one.departure_airport, two.destination_airport, \r\n" + 
									"            (one.number_connections + 1) AS number_connections, one.connections || ARRAY[two.destination_airport]::text[] AS connections,\r\n" + 
									"            one.departure_time, one.arrival_time, one.total_time + ((two.arrival_time - two.departure_time) :: TIME) :: INTERVAL AS total_time,\r\n" + 
									"            one.first_class_prices + two.first_class_price AS first_class_prices, one.economy_class_prices + two.economy_class_price AS economy_class_prices  \r\n" + 
									"    FROM one, flight two\r\n" + 
									"    WHERE one.destination_airport = two.departure_airport AND one.flight_date = two.flight_date AND NOT connections @> ARRAY[two.destination_airport]::text[]\r\n" + 
									")\r\n" + 
									"SELECT * \r\n" + 
									"FROM one\r\n" + 
									"WHERE 	destination_airport = ?\r\n" + 
									"        AND number_connections <= ?\r\n" + 
									"        AND (first_class_prices <= ? OR economy_class_prices <= ?)\r\n" + 
									"        AND total_time <= ((? :: TEXT) :: INTERVAL)\r\n" + 
									"ORDER BY (?);");
							s.setString(1, destination);
							s.setDate(2, returnDate);
							s.setString(3, departure);
							s.setInt(4, maxCon);
							s.setInt(5, maxPrice);
							s.setInt(6, maxPrice);
							s.setString(7, maxTime);
							s.setString(8, orderby);
							r = s.executeQuery();
							if (r.next()) {
								System.out.format("...|%-15s||%-14s||%-11s||%-20s||%-20s||%-15s||%-30s||%-15s||%-15s||%-15s||%-20s||%-20s|\n", 
										"Airport Code", "Flight Number", "Flight Date", "Departure Airport", "Destination Airport", 
										"No. Connections", "Connections", "Departure Time", "Arrival Time", "Total Time", 
										"First Class Prices", "Economy Class Prices");
								do {
									Flight f = new Flight(r.getString(1), r.getInt(2), r.getDate(3));
									flightList.add(f);
									System.out.format("%d. |%-15s||%-14d||%-11s||%-20s||%-20s||%-15d||%-30s||%-15s||%-15s||%-15s||%-20d||%-20d|\n", i, 
											r.getString(1), r.getInt(2), r.getDate(3), r.getString(4), r.getString(5), 
											r.getInt(6), r.getString(7), r.getTime(8), r.getTime(9), r.getTime(10), 
											r.getInt(11), r.getInt(12));
									i++;
								} while (r.next());
							} else {
								System.out.println("No return flights were found.");
							}
						}
						// If we found flights from the departure airport to the destination airport, continue the process.
						if (flightsFound) {
							flightInspectPrompt();	// Print out the flight inspection prompt.
							index = in.nextInt();	// Get the index of the flight that the customer wishes to inspect.
							// If the index entered is '-1' then exit inspection menu.
							if (index == -1) {
								System.out.println("Command '-1' entered. Exiting inspection.");
							} else {	// If index is not '-1'
								inspectFlight(user, flightList.get(index-1));	// Call inspectFlight to handle flight inspections and booking.
							}
						} else {	// No flights were found. Ask the customer for another query.
							System.out.println("No flights from the departure airport to the destination airport were found. Please try another search.");
						}
						c.close();
						s.close();
						r.close();
					} 
					catch (Exception e) {
						System.err.println("An error occurred: " + e.toString());
					}
				} while (index != -1);
			}
		} while (!cmd.equals("q") || (!cmd.equals("b")));
		in.close();
		return ("");	// Return a string other than 'q' by default.
	}

	/*
	 * Method for handling flight inspections.
	 * Called by flightSearch for further inspection of found flight connections.
	 * Takes in a Customer object to check user email in queries.
	 * Takes in a Flight object from flightSearch and uses it to expand all connections and provide detail on each flight.
	 * Accesses the database to query flights and booking as well as create bookings for customers.
	 * Creates an array list of Flight objects from the query like flightSearch.
	 */
	public static void inspectFlight (Customer user, Flight iFlight) throws Exception {
		int i = 1;	// Index displayed next to query results.
		Scanner in = new Scanner(System.in);
		int index = -1;	// Index to be used to select the Flight object in the array list.
		// Loop until the user exits the inspection menu by entering '-1'.
		do {
			try {
				// create a connection
				Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
				// create a statement object to execute commands
				// Create a query to find all the flights involved in the flight connection that is under inspection by flightSearch.
				PreparedStatement s = c.prepareStatement("WITH RECURSIVE one AS(\r\n" + 
						"    SELECT 	airline_code, flight_number, flight_date, departure_airport, destination_airport, departure_time, arrival_time, first_class_price, economy_class_price\r\n" + 
						"    FROM	flight\r\n" + 
						"    WHERE	airline_code = ? AND flight_number = ? AND flight_date = ?\r\n" + 
						"    UNION\r\n" + 
						"    SELECT two.airline_code, two.flight_number, two.flight_date, two.departure_airport, two.destination_airport, two.departure_time, two.arrival_time, two.first_class_price, two.economy_class_price\r\n" + 
						"	FROM one, flight AS two\r\n" + 
						"    WHERE one.destination_airport = two.departure_airport AND one.flight_date = two.flight_date\r\n" + 
						")\r\n" + 
						"SELECT * FROM one;");
				s.setString(1, iFlight.getAirlineCode());
				s.setInt(2, iFlight.getFlightNum());
				s.setDate(3, iFlight.getFlightDate());
				ResultSet r = s.executeQuery();
				ArrayList<Flight> flightList = new ArrayList<Flight>();	// Create array list of Flight objects.
				// Print out the column names for the result set.
				System.out.format("...|%-15s||%-20s||%-20s||%-20s||%-20s||%-20s||%-20s||%-20s||%-20s|\n", 
						"Airport Code", "Flight Number", "Flight Date", "Departure Airport", "Destination Airport",
						"Departure Time", "Arrival Time", "First Class Price", "Economy Class Price");
				// Loop while there are still tuples in the result set.
				while (r.next()) {
					Flight f = new Flight(r.getString(1), r.getInt(2), r.getDate(3));	// Create a Flight object for each tuple in the result set.
					flightList.add(f);	// Add the created Flight objects to the array list.
					// Print out the result set tuples.
					System.out.format("%d. |%-15s||%-20d||%-20s||%-20s||%-20s||%-20s||%-20s||%-20d||%-20d|\n", i, 
							r.getString(1), r.getInt(2), r.getDate(3), r.getString(4), r.getString(5), 
							r.getTime(6), r.getTime(7),	r.getInt(8), r.getInt(9));
					i++;	// Increment the display index.
				}
				// After printing the details of the flights, ask the customer to either book a flight or exit inspection menu by entering '-1'
				System.out.println("Please enter a number to book flights (Enter -1 to exit booking):");
				index = in.nextInt();
				if (index < 0) {	// Customer wishes to exit inspection menu. Break the loop.
					break;
				} else {	// Customer wishes to book a flight. They selected a displayed index. Begin to book that flight.
					index--;	// Decrement the index provided by the customer since the display index starts counting from 1 not 0.
					System.out.println("Please enter a seat class (Economy or First):");	// Ask the customer for the class of seat.
					String seatClass = in.next();
					System.out.println("Please enter a payment method:");	// Ask the customer for a credit card.
					String creditCard = in.next();
					// Create a query to find the payment method that the customer wishes to use.
					s = c.prepareStatement("SELECT * FROM customer_adcr WHERE email_address = ? AND credit_card = ?;");
					s.setString(1, user.getEmail());
					s.setString(2, creditCard);
					r = s.executeQuery();
					// If the result set is not empty, then we found the credit card entered by the user and may continue the booking process.
					if (r.next()) {
						// Create a query to find the flight the customer wishes to book in the booking relation.
						s = c.prepareStatement("SELECT * FROM booking WHERE email_address = ? AND flight_number = ? AND flight_date = ? AND airline_code = ?;");
						s.setString(1, user.getEmail());
						s.setInt(2, flightList.get(index).getFlightNum());
						s.setDate(3, flightList.get(index).getFlightDate());
						s.setString(4, flightList.get(index).getAirlineCode());
						r = s.executeQuery();
						// If the result set is empty, the flight was not found in the booking relation, meaning the customer has not booked this flight yet.
						// We may continue the booking process.
						if (!r.next()) {
							// Create insert statement and book the flight.
							s = c.prepareStatement("INSERT INTO Booking VALUES (?,?,?,?,?,?)");
							s.setString(1, user.getEmail());
							s.setInt(2, flightList.get(index).getFlightNum());
							s.setDate(3, flightList.get(index).getFlightDate());
							s.setString(4, flightList.get(index).getAirlineCode());
							s.setString(5, creditCard);
							s.setString(6, seatClass);
							// Ask the customer for confirmation.
							System.out.println("Are you sure you would like to confirm booking (YES or NO)?:");
							String cmd = in.next();
							if (cmd.equals("YES")) {	// The customer has confirmed the booking.
								s.executeUpdate();	// Execute the insert statement.
								// If the customer chose an economy class seat, update the flight relation by reducing the number of available economy class seats.
								if (seatClass.equals("Economy")) {
									s = c.prepareStatement("UPDATE flight SET max_economy_class_seats = max_economy_class_seats - 1 "
											+ "WHERE airline_code = ? AND flight_number = ? AND flight_date = ?;");
									s.setString(1, flightList.get(index).getAirlineCode());
									s.setInt(2, flightList.get(index).getFlightNum());
									s.setDate(3, flightList.get(index).getFlightDate());
									s.executeUpdate();
								} 
								// If the customer chose a first class seat, update the flight relation by reducing the number of available first class seat.
								else if (seatClass.equals("First")) {
									s = c.prepareStatement("UPDATE flight SET max_first_class_seats = max_first_class_seats - 1 "
											+ "WHERE airline_code = ? AND flight_number = ? AND flight_date = ?;");
									s.setString(1, flightList.get(index).getAirlineCode());
									s.setInt(2, flightList.get(index).getFlightNum());
									s.setDate(3, flightList.get(index).getFlightDate());
									s.executeUpdate();
								}
							} else {	// The customer did not confirm the booking. Cancel the booking and return to inspection menu.
								System.out.println("Canceling the booking.");
							}
						} else {	// The flight was found the customer's booking relation. Cancel the booking and return to inspection menu. 
							System.out.println("You are already booked for that flight. Canceling the booking.");
						}
					} else {	// The paymnet method was not found or does not belong to the customer. Cancel the booking and return to inspection menu.
						System.out.println("Invalid payment method. You do not have a credit card with that number registered. Canceling the booking.");
					}
				}
				c.close();
				s.close();
				r.close();
			}
			catch (Exception e) {
				System.err.println("An error occurred: " + e.toString());
			} 
		} while (index != -1);
	}
	
	/*
	 * Method for handling booking management.
	 * Takes in a Customer object to check user email in queries.
	 * Uses the Booking class to create an array list of Booking objects from queries.
	 * Returns a string. If the returned string is 'q' then quit the application.
	 * Accesses the database to query booking and update booking by canceling bookings.
	 */
	public static String bookingManage (Customer user ) throws Exception {
		ArrayList<Booking> bookList = new ArrayList<Booking>();	// Create array list of Booking objects for use after the query.
		Scanner in = new Scanner (System.in);
		String cmd = "";
		int i = 1;	// Display index printed out next to query results.
		int index = 0;	// Index to be used to select the Booking object in the array list.
		// Loop until the user quits the application with 'q' or returns to main menu with 'b'
		do {
			bookManagePrompt();	// Print out the booking management menu user commands.
			cmd = in.next();
			if (cmd.equals("c")) {	// User wishes to display and possibly cancel bookings.
				try {
					System.out.println("Printing your bookings:");
					Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
					// create a statement object to execute commands
					// Create a query that returns all the bookings that belong to the customer.
					PreparedStatement s = c.prepareStatement("SELECT * FROM Booking WHERE email_address = ?;");
					s.setString(1, user.getEmail());
					ResultSet r = s.executeQuery();
					// If the result set is not empty, then the customer has bookings. Continue the booking management process.
					if (r.next()) {
						// Print column hears for the result set.
						System.out.format("...|%s||%-20s||%-20s||%-20s||%-20s||%-20s|\n", 
								"Email Address", "Flight Number", "Flight Date", "Airline Code", "Payment Method", 
								"Class of Seat");
						// Loop until there are no more tuples in the result set.
						do {
							Booking b = new Booking(r.getInt(2), r.getDate(3), r.getString(4), r.getString(6));	// Create Booking objects from each tuple in the result set.
							bookList.add(b);	// Add every Booking object to the array list.
							// Print the tuples of the result set.
							System.out.format("%d. |%s||%-20d||%-20s||%-20s||%-20s||%-20s|\n", i, 
									r.getString(1), r.getInt(2), r.getDate(3), r.getString(4), r.getString(5), 
									r.getString(6));
							i++;	// Increment the display index.
						} while (r.next());
						i = 1;	// reset the display index.
						// Ask the user for the index of the booking they wish to cancel
						System.out.println("Enter the number of the booking you wish to cancel, or enter -1 to go back.");
						index = in.nextInt();
						if (index < 0) {	// The user wishes to quit. Break the loop and leave the booking browsing menu.
							System.out.println("Leaving the booking browsing menu.");
							break;
						} else {	// The user wishes to cancel a booking. Continue with the booking management process.
							index = index - 1;	// Decrement the index because the display index begins from 1 not 0.
							// Create a delete statement to delete the specified booking.
							s = c.prepareStatement("DELETE FROM Booking WHERE email_address = ? AND flight_number = ? AND flight_date = ? AND airline_code = ?;");
							s.setString(1, user.getEmail());
							s.setInt(2, bookList.get(index).getFlightNumber());
							s.setDate(3, bookList.get(index).getFlightDate());
							s.setString(4, bookList.get(index).getAirlineCode());
							// Ask the user for confirmation before canceling the booking. 
							System.out.println("Are you sure you would like to confirm deleting the booking (YES or NO)?:");
							cmd = in.next();
							if (cmd.equals("YES")) {	// The user confirmed deletion.
								// Execute the delete statement.
								s.executeUpdate();
								// If the booking was for an economy class seat, update the flight relation by incrementing the number of available economy class seats.
								if (bookList.get(index).getClassOfSeat().equals("Economy")) {
									s = c.prepareStatement("UPDATE flight SET max_economy_class_seats = max_economy_class_seats + 1 "
											+ "WHERE airline_code = ? AND flight_number = ? AND flight_date = ?;");
									s.setString(1, bookList.get(index).getAirlineCode());
									s.setInt(2, bookList.get(index).getFlightNumber());
									s.setDate(3, bookList.get(index).getFlightDate());
									s.executeUpdate();
								}
								// If the booking was for a first class seat, updated the flight relation by incrementing the number of available first class seats.
								else if (bookList.get(index).getClassOfSeat().equals("First")) {
									s = c.prepareStatement("UPDATE flight SET max_first_class_seats = max_first_class_seats + 1 "
											+ "WHERE airline_code = ? AND flight_number = ? AND flight_date = ?;");
									s.setString(1, bookList.get(index).getAirlineCode());
									s.setInt(2, bookList.get(index).getFlightNumber());
									s.setDate(3, bookList.get(index).getFlightDate());
									s.executeUpdate();
									s.close();
								}
							} else {	// The customer did not confirm the booking. Do not cancel the booking.
								System.out.println("Canceling the deletion of the booking.");
							}
						}
					} else {	// The result set was empty. No bookings were found for the customer.
						System.out.println("You have no bookings. Please make some using flight search menu.");
					}
					c.close();
					s.close();
					r.close();
				}
				catch (Exception e) {
					System.err.println("An error occurred: " + e.toString());
				}
			} else if (cmd.equals("b")) {	// User wishes to return to main menu. Do so by returning a string other than 'q' to return to main method.
				System.out.println("Command 'b' entered. Returning to menu.");
				signedinPrompt();
				return ("");
			} else if (cmd.equals("q")) {	// User wishes to quit application. Do so by returning 'q' to main method.
				System.out.println("Command 'q' entered. Quitting the Application.");
				return ("q");
			} else {	// Handle incorrect commands. Complain about invalid command and loop.
				System.out.println("'"+ cmd + "'" + " is not a command.");
			}
		} while (!cmd.equals("q") || (!cmd.equals("b")));
		in.close();
		return ("");	// Return a string other than 'q' by default.
	}
	
	// Prints out the list of IATAs, airports, countries, and states (for Canada and US only). Used in flightSearch to help users gets the IATA of airports.
	public static void printAirports() {
		try {
			// create a connection
			Connection c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
			// create a statement object to execute commands
			Statement s = c.createStatement();
			// Create query to get all the airports as well as the airport states for US and Canada. If airport not in US or Canada say so instead of printing NULL in the state column.
			ResultSet r = s.executeQuery("SELECT airport.iata AS airport_code, name, country, CASE WHEN (airport.country = 'USA' OR airport.country = 'Canada') THEN state ELSE ('Not in the US or Canada') END\r\n" + 
					"FROM airport LEFT JOIN airport_state ON airport.iata = airport_state.iata;");
			System.out.format("|%s||%-50s||%-20s||%-25s|\n", "Airport Code", "Airport Name", "Country", "State");
			while (r.next()) {
				System.out.format("|%-12s||%-50s||%-20s||%-25s|\n", r.getString(1), r.getString(2), r.getString(3), r.getString(4));
			}
			c.close();
			s.close();
			r.close();
		}
		catch (Exception e) {
			System.err.println("An error occurred: " + e.toString());
		}
	}
	
	// Prints out the user commands for registration and sign-in when application starts.
	public static void initialPrompt() {
		System.out.println("Select an action by entering a displayed number, or enter 'q' to quit:\n1. Register new account.\t2. Sign in to existing account.");
	}
	
	// Prints out the user commands for the main menu.
	public static void signedinPrompt() {
		System.out.println("Select an action by entering a displayed number, or enter 'q' to quit:\n1. Modify Personal Information.\n2. Look Up Flights.\n3. View Booked Flights.");
	}
	
	// Prints out the user commands for the personal information modification menu.
	public static void modPIPrompt() {
		System.out.println("Select an action by entering a displayed number.\nEnter 'b' to return to menu.\nEnter 'q' to quit:\n1. Add Address.    \t2. Add Payment Method.\n3. Modify Address.\t4. Modify Payment Method.\n5. Delete Address.\t6. Delete Payment Method.\n7. Add Home Airport.\t8. Delete Home Airport.");
	}
	
	// Prints out the user commands for the flight search menu.
	public static void flightSearchPrompt() {
		System.out.println("Enter 'a' to view a list of airports.\nEnter 'b' to return to menu.\nEnter 'q' to quit.\nPlease follow the instructions otherwise:\nPlease enter your departure airport code:");
	}
	
	// Prints out the user commands for the flight inspection menu.
	public static void flightInspectPrompt() {
		System.out.println("Enter '-1' to return to quit inspection.\nEnter a number to inspect that flight:");
	}
	
	// Prints out the user commands for the booking management menu.
	public static void bookManagePrompt() {
		System.out.println("Enter 'c' to cancel or browse bookings.\nEnter 'b' to return to menu.\nEnter 'q' to quit.");
	}
}
