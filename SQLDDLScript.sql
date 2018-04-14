/* This is the SQL DDL Script for the database creation */

/* Airport relation */
CREATE TABLE Airport
(
  IATA CHAR(3),
  Name VARCHAR(50),
  Country VARCHAR(50),
  PRIMARY KEY (IATA)
);

/* Customer relation*/
CREATE TABLE Customer
(
  Email_Address VARCHAR(100),
  Name VARCHAR(50),
  Home_Airport CHAR(3),
  PRIMARY KEY (Email_Address),
  FOREIGN KEY (Home_Airport) REFERENCES Airport
);

/* Records multiple credit cards and addresses for each customer and assigns an address to each credit card */
CREATE TABLE Customer_ADCR
(
  Email_Address VARCHAR(100),
  Address VARCHAR(500),
  Credit_Card CHAR(16) UNIQUE,
  FOREIGN KEY (Email_Address) REFERENCES Customer
);

/* Records the state of airport for airports in US or Canada */
CREATE TABLE Airport_State
(
  IATA CHAR(3),
  State VARCHAR(30),
  PRIMARY KEY (IATA),
  FOREIGN KEY(IATA) REFERENCES Airport
);

/* Airline relation */
CREATE TABLE Airline
(
  Airline_code CHAR(2),
  Name VARCHAR(50),
  Country_of_Origin VARCHAR(50),
  PRIMARY KEY (Airline_code)
);

/* Flight relation */
CREATE TABLE Flight
(
  Airline_code CHAR(2),
  Flight_Number INT,
  Flight_Date DATE,
  Max_First_Class_Seats INT,
  Max_Economy_Class_Seats INT,
  Destination_Airport CHAR(3),
  Arrival_Airport CHAR(3),
  Departure_Time TIME,
  Arrival_Time TIME,
  First_Class_Price INT,
  Economy_Class_Price INT,
  PRIMARY KEY (Airline_code, Flight_Number, Flight_Date),
  FOREIGN KEY (Airline_code) REFERENCES Airline,
  FOREIGN KEY (Destination_Airport) REFERENCES Airport,
  FOREIGN KEY (Arrival_Airport) REFERENCES Airport,
  CHECK (Economy_Class_Price < First_Class_Price)
);

/* Booking relation */
CREATE TABLE Booking
(
  Email_Address VARCHAR(100),
  Flight_Number INT,
  Flight_Date DATE,
  Airline_code CHAR(2),
  Credit_Card_Used CHAR(16),
  Class_of_Seat VARCHAR(10),
  PRIMARY KEY (Email_Address, Flight_Number, Flight_Date, Airline_code),
  FOREIGN KEY (Email_Address) REFERENCES Customer,
  FOREIGN KEY (Airline_code, Flight_Number, Flight_Date) REFERENCES Flight
);
