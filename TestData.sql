/* This is the test data for the application */

/* Airport Data */
INSERT INTO Airport VALUES ('ORD','OHare International Airport','USA');
INSERT INTO Airport VALUES ('ATL','Atlanta International Airport','USA');
INSERT INTO Airport VALUES ('PUD','Pudong Airport','China');
INSERT INTO Airport VALUES ('HND','Tokyo Haneda International Airport','Japan');
INSERT INTO Airport VALUES ('DXB','Dubai International Airport','UAE');
INSERT INTO Airport VALUES ('DEL','Indira Gandhi International Airport','India');
INSERT INTO Airport VALUES ('LAX','Los Angeles International Airport','USA');
INSERT INTO Airport VALUES ('LHR','Heathrow Airport','UK');
INSERT INTO Airport VALUES ('CDG','Charles de Gaulle Airport','France');
INSERT INTO Airport VALUES ('DFW','Dallas/Fort Worth International Airport','USA');
INSERT INTO Airport VALUES ('AMS','Amsterdam Airport Schipol','Netherlands');
INSERT INTO Airport VALUES ('FRA','Frankfurt Airport','Germany');
INSERT INTO Airport VALUES ('IST','Istanbul Ataturk Airport','Turkey');
INSERT INTO Airport VALUES ('JFK','John F. Kennedy International Airport','USA');
INSERT INTO Airport VALUES ('DEN','Denver International Airport','USA');
INSERT INTO Airport VALUES ('MAD','Madrid Barajas Airport','Spain');
INSERT INTO Airport VALUES ('KUL','Kuala Lampur International Airport','Malaysia');
INSERT INTO Airport VALUES ('LAS','McCarran International Airport','USA');
INSERT INTO Airport VALUES ('SEA','Seattle-Tacoma International Airport','USA');
INSERT INTO Airport VALUES ('MIA','Miami International Airport','USA');
INSERT INTO Airport VALUES ('YBR','Brandon Municipal Airport','Canada');
INSERT INTO Airport VALUES ('KUM','Yakushima Airport','Japan');
INSERT INTO Airport VALUES ('KIN','Norman Manley International Airport','Jamaica');
INSERT INTO Airport VALUES ('CLH','Coolah Airport','Australia');
INSERT INTO Airport VALUES ('DOH','Doha Qatar Airport','Qatar');

/* States of airports in Canada and US*/
INSERT INTO airport_state VALUES ('ORD', 'Illionis');
INSERT INTO airport_state VALUES ('ATL', 'Georgia');
INSERT INTO airport_state VALUES ('LAX', 'California');
INSERT INTO airport_state VALUES ('DFW', 'Texas');
INSERT INTO airport_state VALUES ('JFK', 'New York');
INSERT INTO airport_state VALUES ('DEN', 'Colorado');
INSERT INTO airport_state VALUES ('LAS', 'Nevada');
INSERT INTO airport_state VALUES ('SEA', 'Washington');
INSERT INTO airport_state VALUES ('MIA', 'Florida');
INSERT INTO airport_state VALUES ('YBR', 'Mantinoba');

/* Customer Data */
INSERT INTO Customer VALUES ('jake_long@yahoo.com','Jake Long', 'ORD');
INSERT INTO Customer VALUES ('mike14ten@gmail.com','Mike Tennyson', 'YBR');
INSERT INTO Customer VALUES ('laysal@gmail.com','Layan Salameh', 'DOH');
INSERT INTO Customer VALUES ('LarrySRiley@armyspy.com','Larry S. Riley', 'DEN');
INSERT INTO Customer VALUES ('MatheoPettersen@teleworm.us','Matheo Pettersen', 'AMS');
INSERT INTO Customer VALUES ('EmmaAndersen@jourrapide.com','Emma Andersen', 'AMS');
INSERT INTO Customer VALUES ('FleurLoiseau@gmail.us','Fleur Loiseau', 'CDG');
INSERT INTO Customer VALUES ('JamilKhayriSarkis@rhyta.com','JJamil Khayri Sarkis', 'FRA');
INSERT INTO Customer VALUES ('VictoriaMiller@dayrep.com','Victoria Miller', 'LHR');
INSERT INTO Customer VALUES ('DavidHaining@yahoo.com','David Haining', 'CLH');

/* Airline Data*/
INSERT INTO airline VALUES('AA', 'American Airline', 'USA');
INSERT INTO airline VALUES('UA', 'United Airline', 'USA');
INSERT INTO airline VALUES('DL', 'Delta Airline', 'USA');
INSERT INTO airline VALUES('AC', 'Air Canada', 'Canada');
INSERT INTO airline VALUES('CL', 'China Airline', 'China');
INSERT INTO airline VALUES('AF', 'Air France', ' France');
INSERT INTO airline VALUES('LH', 'Lufthansa', 'Germany');
INSERT INTO airline VALUES('JM', 'Air Jamaica', 'Jamaica');
INSERT INTO airline VALUES('NH', 'All Nippon Airway', 'Japan');
INSERT INTO airline VALUES('JL', 'Japan Airline', 'Japan');
INSERT INTO airline VALUES('IB', 'Iberia', 'Spain');
INSERT INTO airline VALUES('MH', 'Malaysia Airline', 'Malaysia');
INSERT INTO airline VALUES('QR', 'Qatar Airways', 'Qatar');
INSERT INTO airline VALUES('EK', 'Emirates Airline', 'UAE');
INSERT INTO airline VALUES('HD', 'Air Holland', 'Netherlands');
INSERT INTO airline VALUES('YK','Cyprus Turkish Airlines','Turkey');
INSERT INTO airline VALUES('RV','Air Canada Rouge','Canada');
INSERT INTO airline VALUES('ZX','Air BC','Canada');

/* Flight Data*/
INSERT INTO flight VALUES('UA','2435','2018-1-23','20','220','ORD','ATL','7:30:00','9:40:00','4290','1050');
INSERT INTO flight VALUES('UA','2211','2018-2-23','15','300','ATL','ORD','10:20:00','12:50:00','4590','1290');
INSERT INTO flight VALUES('AA','6572','2018-01-24','10','150','LAX','AMS','15:40:00','6:30:00','3270','540'	);
INSERT INTO flight VALUES('JL','8921','2018-1-13','5','85','KUM','DXB','23:45:00','10:50:00','1635','525');
INSERT INTO flight VALUES('QR','7029','2018-1-14','22','230','DXB','LHR','17:00:00','23:45:00','6630','1290'	);
INSERT INTO flight VALUES('LH','5632','2018-4-18','25','275','LHR','DXB','6:30:00','17:00:00','7500','1665'	);
INSERT INTO flight VALUES('QR','1953','2018-4-18','18','180','DXB','KUM','23:22:00','7:30:00','5625','1020');
INSERT INTO flight VALUES('AA','4725','2018-4-16','10','110','KIN','ORD','10:50:00','23:22:00','3270','690');
INSERT INTO flight VALUES('LH','5789','2018-4-17','5','230','ORD','YBR','8:30:00','15:45:00','2940'	,'960');
INSERT INTO flight VALUES('RV','2333','2018-5-25','22','275','YBR','ORD','10:35:00','15:55:00','3750','1230');
INSERT INTO flight VALUES('DL','1157','2018-05-25','25','180','ORD','KIN','17:30:00','6:10:00','2265','795');
INSERT INTO flight VALUES('IB','8569','2018-8-28','10','110','MAD','CDG','18:25:00','20:15:00','2955','975');
INSERT INTO flight VALUES('AF','2134','2018-8-28','5','300','CDG','FRA','21:15:00','23:35:00','6450','2745'	);
INSERT INTO flight VALUES('LH','5854','2018-8-29','22','150','FRA','IST','0:25:00','5:35:00','3900'	,'735');
INSERT INTO flight VALUES('AF','2174','2018-11-13','15','220','IST','CDG','9:55:00','14:55:00','2835','705');
INSERT INTO flight VALUES('IB','6842','2018-11-13','22','300','CDG','MAD','16:00:00','18:25:00','2340','525');
INSERT INTO flight VALUES('YK','3254','2018-11-13','25','150','IST','FRA','8:45:00','16:55:00','4500','1965');
INSERT INTO flight VALUES('YK','2154','2018-11-14','18','85','IST','MAD','9:30:00','17:35:00','9000','2580');
INSERT INTO flight VALUES('AA','8874','2018-3-8','10','300','JFK','SEA','18:55:00','21:50:00','6030','1875');
INSERT INTO flight VALUES('AA','9684','2018-3-8','25','150','SEA','LAX','22:30:00','0:50:00','6045','2100');
INSERT INTO flight VALUES('AA','4628','2018-03-09','10','230','SEA','JFK','9:45:00','12:55:00','3615','1575');
INSERT INTO flight VALUES('UA','7515','2018-6-15','5','110','JFK','LAX','15:20:00','20:00:00','4425','1455');
INSERT INTO flight VALUES('UA','6482','2018-06-15','22','230','LAX','SEA','21:00:00','23:00:00','4980','2850');
INSERT INTO flight VALUES('DL','4865','2018-09-03','15','110','LAS','MIA','5:00:00','10:25:00','5430','2265');
INSERT INTO flight VALUES('DL','2456','2018-9-30','5','230','MIA','LAS','6:30:00','11:55:00','2355','660');
INSERT INTO flight VALUES('AA','5548','2018-09-03','22','275','MIA','DFW','12:55:00','16:25:00','2805','960');
INSERT INTO flight VALUES('DL','5498','2018-9-30','15','150','DFW','LAS','17:00:00','19:35:00','1995','435');
INSERT INTO flight VALUES('UA','1331','2017-12-14','22','85','JFK','AMS','22:25:00','5:40:00','2970','765');
INSERT INTO flight VALUES('HD','2128','2018-04-23','25','230','AMS','JFK','17:55:00','1:20:00','3690','1050');
INSERT INTO flight VALUES('EK','7546','2018-07-30','8','7','DOH','HND','07:00:00','19:25:00','6960','1560');
INSERT INTO flight VALUES('NH','9547','2018-07-30','5','6','HND','KUL','20:00:00','2:25:00','6960','1560');
INSERT INTO flight VALUES('MH','7846','2018-07-31','5','3','KUL','DEL','4:00:00','9:25:00','6960','1560');
INSERT INTO flight VALUES('AA','7546','2018-07-31','2','3','DEL','LAX','10:00:00','23:25:00','6960','1560');
INSERT INTO flight VALUES('HD','8759','2018-08-01','1','1','LAX','AMS','1:00:00','10:25:00','6960','1560');
INSERT INTO flight VALUES('HD','6912','2018-08-01','2','2','AMS','SEA','12:00:00','23:25:00','6960','1560');

/* Booking Data*/
INSERT INTO booking VALUES('jake_long@yahoo.com','6572','2018-01-24','AA','5673873498193453','Economy');
INSERT INTO booking VALUES('mike14ten@gmail.com','5789','2018-4-17','LH','2098746355432245','First');
INSERT INTO booking VALUES('laysal@gmail.com','9684','2018-3-8','AA','4532786498322012','First');
INSERT INTO booking VALUES('LarrySRiley@armyspy.com', '7546','2018-07-30','EK','5673873498193453','Economy');
INSERT INTO booking VALUES('MatheoPettersen@teleworm.us','7029','2018-1-14','QR','4929580275735158','Economy');
INSERT INTO booking VALUES('EmmaAndersen@jourrapide.com','2333','2018-5-25','RV','4556414461983000','Economy');
INSERT INTO booking VALUES('jake_long@yahoo.com','2128','2018-04-23','HD','5673873498193453','First');
INSERT INTO booking VALUES('mike14ten@gmail.com','2333','2018-5-25','RV','2098746355432245','First');
INSERT INTO booking VALUES('laysal@gmail.com','6482','2018-06-15','UA','4532786498322012','Economy');
INSERT INTO booking VALUES('laysal@gmail.com','2456','2018-9-30','DL','4532786498322012','First');
INSERT INTO booking VALUES('laysal@gmail.com','7546','2018-07-30','EK','4532786498322012','First');


/* Customer ADCR Data */			
INSERT INTO Customer_adcr VALUES('jake_long@yahoo.com','3353 S. State St. Chicago, Illinois, USA 60616','5673873498193453');			
INSERT INTO Customer_adcr VALUES('mike14ten@gmail.com','772 E. Leaf Blv. Vancouver, British Columbia, Canada 23227','2098746355432245');			
INSERT INTO Customer_adcr VALUES('laysal@gmail.com','4480 Al Rayan Ave. Dammam 32764','4532786498322012');			
INSERT INTO Customer_adcr VALUES('LarrySRiley@armyspy.com','588 Pick Street Aurora, CO, USA 80011','5420834698121889');			
INSERT INTO Customer_adcr VALUES('MatheoPettersen@teleworm.us','Cremerstraat 37 3195 VB  Pernis Netherlands','4929580275735158');			
INSERT INTO Customer_adcr VALUES('EmmaAndersen@jourrapide.com','Schoolstraat 128 6971 CJ  Brummen Netherlands','4916683722886718');			
INSERT INTO Customer_adcr VALUES('EmmaAndersen@jourrapide.com','Alkmaarderstraatweg 33 1901 DA  Castricum Netherlands','4556414461983000');			
INSERT INTO Customer_adcr VALUES('EmmaAndersen@jourrapide.com','81 Panorama Road BITHRAMERE NSW 2340 Australia','5407008524035673');			
INSERT INTO Customer_adcr VALUES('MatheoPettersen@teleworm.us','2975 Barton Street Stoney Creek, ON L8G 2V1, Canada','5187838267654041');			
INSERT INTO Customer_adcr VALUES('LarrySRiley@armyspy.com','1250 Church Street Brooklyn, NY 11212, USA','5288662291391752');			
INSERT INTO Customer_adcr VALUES('FleurLoiseau@gmail.us','76, boulevard Albin Durand 95000 CERGY, France','5436955832014060');			
INSERT INTO Customer_adcr VALUES('FleurLoiseau@gmail.us','76, boulevard Albin Durand 95000 CERGY, France','4916397170447119');			
INSERT INTO Customer_adcr VALUES('FleurLoiseau@gmail.us','76, boulevard Albin Durand 95000 CERGY, France','5400960262233521');			
INSERT INTO Customer_adcr VALUES('JamilKhayriSarkis@rhyta.com','Borstelmannsweg 54 92204 Amberg, Germany','5138468501781788');			
INSERT INTO Customer_adcr VALUES('VictoriaMiller@dayrep.com','54 Maidstone Road WERRINGTON PL15 9WP, UK','4929263262356680');			
INSERT INTO Customer_adcr VALUES('VictoriaMiller@dayrep.com','54 Maidstone Road WERRINGTON PL15 9WP, UK','5465821447823291');			
INSERT INTO Customer_adcr VALUES('DavidHaining@yahoo.com','81 South Street LESLIE VALE TAS 7054, Australia','5331685157367563');	

