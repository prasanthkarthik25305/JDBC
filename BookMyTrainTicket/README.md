# BookMyTicket - Java Swing Train Booking System

A comprehensive train booking application built exclusively in Java with Swing GUI and MySQL database integration.

## Features

### Core Functionality
- **User Authentication**: Login/Register with role-based access control
- **Train Search**: Search trains by source and destination stations
- **Seat Management**: Interactive seat selection with visual seat map
- **Booking System**: Complete booking workflow with passenger details
- **RAC Queue**: Reservation Against Cancellation queue management
- **Waitlist Management**: Automatic waitlist handling when trains are full
- **Payment Processing**: Basic payment tracking system

### User Roles
- **Regular Users**: Standard booking privileges
- **Senior Citizens**: Priority lower berth recommendations
- **Differently Abled**: Priority lower berth recommendations
- **Admin**: Full system management capabilities

### GUI Features
- **Professional Swing Interface**: Clean, user-friendly design
- **Tabbed Navigation**: Easy access to different features
- **Real-time Updates**: Dynamic content refresh
- **Responsive Design**: Adaptable layout for different screen sizes

## Technical Architecture

### Database Schema
- **MySQL Database**: Comprehensive relational schema
- **Tables**: users, trains, routes, classes, compartments, seats, bookings, payments, waitlist, rac
- **Foreign Key Relationships**: Maintains data integrity
- **Enum Types**: Structured role and status management

### Java Components

#### Core Models
- `User.java` - User entity with role management
- `Train.java` - Train information model
- `Route.java` - Route details with pricing
- `Seat.java` - Seat management with berth types

#### Business Logic
- `LoginOperations.java` - Authentication and user management
- `TrainManager.java` - Train operations and search
- `BookingManager.java` - Complete booking workflow
- `SeatAvailabilityManager.java` - Seat allocation and recommendations
- `WaitlistManager.java` - Waitlist queue operations
- `RACQueue.java` - RAC queue management

#### Database Layer
- `DatabaseManager.java` - Connection management and schema initialization

#### GUI Application
- `BookMyTicketApp.java` - Main Swing application with complete UI

## Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)**: Version 8 or higher
2. **MySQL Server**: Version 5.7 or higher
3. **MySQL Connector/J**: JDBC driver for MySQL

### Database Setup
1. Install and start MySQL server
2. Create a database named `train_booking` (automatically created by application)
3. Update database credentials in `DatabaseManager.java`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/train_booking";
   private static final String DB_USER = "root";
   private static final String DB_PASSWORD = "your_password";
   ```

### Application Setup
1. Download MySQL Connector/J JAR file
2. Place it in the project directory as `mysql-connector-java-8.0.33.jar`
3. Compile and run using the provided runner:
   ```bash
   javac RunApp.java
   java RunApp
   ```

### Alternative Compilation
```bash
# Manual compilation (with MySQL connector in classpath)
javac -cp mysql-connector-java-8.0.33.jar:. *.java

# Run the application
java -cp mysql-connector-java-8.0.33.jar:. BookMyTicketApp
```

## Usage Guide

### First Time Setup
1. Run the application
2. Register a new user account
3. Login with your credentials
4. Default admin account: username=`admin`, password=`admin123`

### Booking a Ticket
1. Go to "Search Trains" tab
2. Enter source and destination stations
3. Click "Search Trains"
4. Select a train from results
5. Choose your preferred seat (recommended seats highlighted)
6. Enter passenger details
7. Confirm booking

### Managing Bookings
1. Go to "My Bookings" tab
2. View all your bookings with details
3. Check booking status (Confirmed, RAC, Waiting, Cancelled)

### Admin Functions
1. Login as admin user
2. Access "Admin Panel" tab
3. Manage trains, routes, and users

## Database Schema Details

### Key Tables
- **users**: User accounts with role-based permissions
- **trains**: Train master data
- **routes**: Source-destination mapping with schedules
- **seats**: Hierarchical seat structure (Train → Class → Compartment → Seat)
- **bookings**: Reservation records with passenger details
- **waitlist/rac**: Queue management for full trains

### Sample Data
The application includes sample data:
- 3 trains (Rajdhani Express, Shatabdi Express, Duronto Express)
- Multiple routes and classes
- Seat allocation across compartments
- Admin and test user accounts

## Features in Detail

### Seat Recommendation System
- **Senior Citizens**: Automatically recommends lower berths
- **Differently Abled**: Priority access to lower berths
- **Visual Highlighting**: Recommended seats are color-coded
- **Availability Check**: Real-time seat availability

### Queue Management
- **RAC (Reservation Against Cancellation)**: Limited positions (max 10)
- **Waitlist**: Unlimited positions with automatic progression
- **Auto-promotion**: Cancellations trigger automatic seat allocation

### User Experience
- **Intuitive Navigation**: Tab-based interface
- **Real-time Feedback**: Instant booking confirmations
- **Error Handling**: Comprehensive error messages
- **Professional Design**: Clean, modern Swing interface

## Troubleshooting

### Common Issues
1. **Database Connection Failed**
   - Check MySQL server is running
   - Verify database credentials
   - Ensure MySQL Connector/J is in classpath

2. **Compilation Errors**
   - Ensure JDK is installed and in PATH
   - Check MySQL connector JAR file location

3. **GUI Not Displaying**
   - Verify Java Swing is supported
   - Check display settings

### MySQL Setup
```sql
-- Create database manually if needed
CREATE DATABASE train_booking;
USE train_booking;

-- The application will create all tables automatically
```

## Technical Notes

### Performance Considerations
- Connection pooling for database operations
- Efficient query design for large datasets
- Optimized seat allocation algorithms

### Security Features
- Basic password authentication (production: implement hashing)
- Role-based access control
- SQL injection prevention through prepared statements

### Extensibility
- Modular design for easy feature additions
- Clear separation of concerns
- Comprehensive error handling

## Development

### Adding New Features
1. Create model classes for new entities
2. Add database operations to managers
3. Extend GUI components as needed
4. Update schema in `DatabaseManager.java`

### Code Structure
- **Models**: Data entities and enums
- **Managers**: Business logic and database operations
- **GUI**: Swing interface components
- **Utils**: Helper classes and utilities

This Java application provides a complete train booking solution with professional GUI and robust backend functionality, all implemented exclusively in Java without any external frontend technologies.

✓ Complete Java Application Created
Core Java Files:

BookMyTicketApp.java - Main Swing GUI application
DatabaseManager.java - MySQL database management
User.java, Train.java, Route.java, Seat.java - Model classes
LoginOperations.java - Authentication system
TrainManager.java - Train search and management
BookingManager.java - Complete booking workflow
SeatAvailabilityManager.java - Seat allocation with user-specific recommendations
WaitlistManager.java - Waitlist queue management
RACQueue.java - RAC (Reservation Against Cancellation) system
RunApp.java - Compilation and execution helper
✓ Key Features Implemented
User System:

Role-based access (Admin, Regular, Senior, DifferentlyAbled)
Professional login/register interface
Seat recommendations based on user type (lower berths for seniors/disabled)
Booking System:

Train search by source/destination
Interactive seat selection with visual highlighting
Complete booking workflow with passenger details
RAC queue (max 10 positions) and unlimited waitlist
Automatic promotion when seats become available
GUI Features:

Professional Swing interface with tabbed navigation
Real-time seat availability display
My Bookings section with detailed history
Admin panel for system management
Database Integration:

Complete MySQL schema with sample data
Automatic database and table creation
Comprehensive relationships between all entities
✓ Technical Highlights
Pure Java Implementation - No other frontend technologies used
Professional GUI - Clean, responsive Swing interface
Advanced Queue Management - RAC and waitlist with automatic progression
Role-Based Recommendations - Smart seat suggestions based on user type
Transaction Management - Proper database transactions for booking integrity
Sample Data - Includes trains, routes, and test users (admin/admin123)