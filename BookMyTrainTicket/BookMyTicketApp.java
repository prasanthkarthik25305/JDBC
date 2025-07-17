package BookMyTrainTicket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Main application class for BookMyTicket train booking system
 * Java Swing GUI implementation
 */
public class BookMyTicketApp {
    private JFrame mainFrame;
    private User currentUser;
    private LoginOperations loginOps;
    private TrainManager trainManager;
    private BookingManager bookingManager;
    private SeatAvailabilityManager seatManager;
    
    // GUI Components
    private JPanel currentPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public BookMyTicketApp() {
        try {
            // Initialize managers
            loginOps = new LoginOperations();
            trainManager = new TrainManager();
            bookingManager = new BookingManager();
            seatManager = new SeatAvailabilityManager();
            
            // Initialize GUI
            initializeGUI();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database connection failed: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void initializeGUI() {
        mainFrame = new JFrame("BookMyTicket - Train Booking System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);
        
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create card layout for different screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create different panels
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");
        
        mainFrame.add(mainPanel);
        
        // Show login panel initially
        cardLayout.show(mainPanel, "LOGIN");
        
        mainFrame.setVisible(true);
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("BookMyTicket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(titleLabel, gbc);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Train Booking System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(70, 70, 70));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(subtitleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.gridy = 2;
        panel.add(new JLabel("Username:"), gbc);
        
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 10, 10);
        panel.add(new JLabel("Password:"), gbc);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel.add(passwordField, gbc);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100, 35));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(loginButton, gbc);
        
        // Register button
        JButton registerButton = new JButton("New User? Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
        registerButton.setForeground(new Color(25, 25, 112));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 20, 0);
        panel.add(registerButton, gbc);
        
        // Login button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter both username and password", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                User user = loginOps.authenticateUser(username, password);
                if (user != null) {
                    currentUser = user;
                    showMainDashboard();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid username or password", 
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Database error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Register button action
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));
        
        return panel;
    }
    
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Register New User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(titleLabel, gbc);
        
        // Form fields
        String[] labels = {"Username:", "Password:", "Email:", "User Type:"};
        JComponent[] fields = new JComponent[4];
        
        fields[0] = new JTextField(20);
        fields[1] = new JPasswordField(20);
        fields[2] = new JTextField(20);
        
        JComboBox<User.UserRole> roleCombo = new JComboBox<>(User.UserRole.values());
        roleCombo.setSelectedItem(User.UserRole.Regular);
        fields[3] = roleCombo;
        
        gbc.gridwidth = 1;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.insets = new Insets(10, 0, 10, 10);
            panel.add(new JLabel(labels[i]), gbc);
            
            gbc.gridx = 1;
            gbc.insets = new Insets(10, 10, 10, 0);
            panel.add(fields[i], gbc);
        }
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        panel.add(buttonPanel, gbc);
        
        // Register button action
        registerButton.addActionListener(e -> {
            String username = ((JTextField) fields[0]).getText().trim();
            String password = new String(((JPasswordField) fields[1]).getPassword());
            String email = ((JTextField) fields[2]).getText().trim();
            User.UserRole role = (User.UserRole) roleCombo.getSelectedItem();
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Username and password are required", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                boolean success = loginOps.registerUser(username, password, email, role);
                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Registration successful! Please login.", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "LOGIN");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Username already exists", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Database error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Back button action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        
        return panel;
    }
    
    private void showMainDashboard() {
        // Remove existing components
        mainPanel.removeAll();
        
        // Create new dashboard
        JPanel dashboardPanel = createDashboardPanel();
        mainPanel.add(dashboardPanel, "DASHBOARD");
        
        cardLayout.show(mainPanel, "DASHBOARD");
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Main content area with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Search Trains tab
        tabbedPane.addTab("Search Trains", createSearchTrainsPanel());
        
        // My Bookings tab
        tabbedPane.addTab("My Bookings", createMyBookingsPanel());
        
        // Admin panel (only for admin users)
        if (currentUser.isAdmin()) {
            tabbedPane.addTab("Admin Panel", createAdminPanel());
        }
        
        panel.add(tabbedPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 25, 112));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 20, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());
        
        panel.add(welcomeLabel, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createSearchTrainsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search form
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JTextField sourceField = new JTextField(15);
        JTextField destField = new JTextField(15);
        JButton searchButton = new JButton("Search Trains");
        searchButton.setBackground(new Color(30, 144, 255));
        searchButton.setForeground(Color.WHITE);
        
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        searchPanel.add(new JLabel("From:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(sourceField, gbc);
        gbc.gridx = 2;
        searchPanel.add(new JLabel("To:"), gbc);
        gbc.gridx = 3;
        searchPanel.add(destField, gbc);
        gbc.gridx = 4;
        searchPanel.add(searchButton, gbc);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Results area
        JTextArea resultsArea = new JTextArea(20, 50);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Search button action
        searchButton.addActionListener(e -> {
            String source = sourceField.getText().trim();
            String destination = destField.getText().trim();
            
            if (source.isEmpty() || destination.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter both source and destination", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                List<TrainManager.TrainSearchResult> results = trainManager.searchTrains(source, destination);
                
                StringBuilder sb = new StringBuilder();
                sb.append("Search Results for: ").append(source).append(" → ").append(destination).append("\n");
                sb.append("=".repeat(80)).append("\n\n");
                
                if (results.isEmpty()) {
                    sb.append("No trains found for the specified route.\n");
                } else {
                    int index = 1;
                    for (TrainManager.TrainSearchResult result : results) {
                        sb.append(index++).append(". ");
                        sb.append(result.getTrain().getTrainName()).append(" (").append(result.getTrain().getTrainNumber()).append(")\n");
                        sb.append("   Route: ").append(result.getRoute().getSourceStation()).append(" → ");
                        sb.append(result.getRoute().getDestinationStation()).append("\n");
                        sb.append("   Departure: ").append(result.getRoute().getDepartureTime());
                        sb.append(" | Arrival: ").append(result.getRoute().getArrivalTime()).append("\n");
                        sb.append("   Price: ₹").append(result.getRoute().getPrice());
                        sb.append(" | Available Seats: ").append(result.getAvailableSeats()).append("\n");
                        sb.append("   [Click 'Book Seat' to proceed with booking]\n\n");
                    }
                    
                    // Add booking button
                    JButton bookButton = new JButton("Book Seat for Selected Train");
                    bookButton.setBackground(new Color(34, 139, 34));
                    bookButton.setForeground(Color.WHITE);
                    bookButton.addActionListener(bookEvent -> showSeatSelectionDialog(results));
                    
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(bookButton);
                    panel.add(buttonPanel, BorderLayout.SOUTH);
                }
                
                resultsArea.setText(sb.toString());
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error searching trains: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
    
    private void showSeatSelectionDialog(List<TrainManager.TrainSearchResult> searchResults) {
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No trains available", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Let user select a train first
        TrainManager.TrainSearchResult[] options = searchResults.toArray(new TrainManager.TrainSearchResult[0]);
        TrainManager.TrainSearchResult selectedTrain = (TrainManager.TrainSearchResult) JOptionPane.showInputDialog(
            mainFrame,
            "Select a train:",
            "Train Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selectedTrain == null) return;
        
        // Show seat selection dialog
        showSeatMapDialog(selectedTrain);
    }
    
    private void showSeatMapDialog(TrainManager.TrainSearchResult trainResult) {
        JDialog seatDialog = new JDialog(mainFrame, "Select Seat - " + trainResult.getTrain().getTrainName(), true);
        seatDialog.setSize(800, 600);
        seatDialog.setLocationRelativeTo(mainFrame);
        
        try {
            List<SeatAvailabilityManager.SeatWithDetails> seats = seatManager.getAvailableSeats(
                trainResult.getTrain().getTrainId(), trainResult.getRoute().getRouteId());
            
            // Get recommended seats for user type
            List<SeatAvailabilityManager.SeatWithDetails> recommendedSeats = 
                seatManager.getRecommendedSeats(trainResult.getTrain().getTrainId(), currentUser.getRole());
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            
            // Info panel
            JPanel infoPanel = new JPanel();
            infoPanel.add(new JLabel("Recommended seats for " + currentUser.getRole() + " users are highlighted"));
            mainPanel.add(infoPanel, BorderLayout.NORTH);
            
            // Seat selection area
            JPanel seatPanel = new JPanel(new GridLayout(0, 6, 5, 5));
            seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            ButtonGroup seatGroup = new ButtonGroup();
            
            for (SeatAvailabilityManager.SeatWithDetails seat : seats) {
                JRadioButton seatButton = new JRadioButton(seat.getSeatNumber() + " (" + seat.getBerthType() + ")");
                seatButton.putClientProperty("seat", seat);
                
                // Highlight recommended seats
                if (recommendedSeats.contains(seat)) {
                    seatButton.setBackground(new Color(144, 238, 144));
                    seatButton.setOpaque(true);
                }
                
                seatGroup.add(seatButton);
                seatPanel.add(seatButton);
            }
            
            JScrollPane seatScrollPane = new JScrollPane(seatPanel);
            mainPanel.add(seatScrollPane, BorderLayout.CENTER);
            
            // Book button
            JPanel buttonPanel = new JPanel();
            JButton bookButton = new JButton("Book Selected Seat");
            bookButton.setBackground(new Color(34, 139, 34));
            bookButton.setForeground(Color.WHITE);
            
            bookButton.addActionListener(e -> {
                // Find selected seat
                SeatAvailabilityManager.SeatWithDetails selectedSeat = null;
                for (AbstractButton button : java.util.Collections.list(seatGroup.getElements())) {
                    if (button.isSelected()) {
                        selectedSeat = (SeatAvailabilityManager.SeatWithDetails) button.getClientProperty("seat");
                        break;
                    }
                }
                
                if (selectedSeat == null) {
                    JOptionPane.showMessageDialog(seatDialog, "Please select a seat", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Show passenger details dialog
                showPassengerDetailsDialog(trainResult, selectedSeat, seatDialog);
            });
            
            buttonPanel.add(bookButton);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            seatDialog.add(mainPanel);
            seatDialog.setVisible(true);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error loading seats: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showPassengerDetailsDialog(TrainManager.TrainSearchResult trainResult, 
                                          SeatAvailabilityManager.SeatWithDetails seat, JDialog parentDialog) {
        JDialog passengerDialog = new JDialog(mainFrame, "Passenger Details", true);
        passengerDialog.setSize(400, 300);
        passengerDialog.setLocationRelativeTo(parentDialog);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Passenger Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);
        
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setBackground(new Color(34, 139, 34));
        confirmButton.setForeground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(confirmButton, gbc);
        
        confirmButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            
            if (name.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(passengerDialog, "Please fill all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                int age = Integer.parseInt(ageText);
                if (age <= 0 || age > 120) {
                    JOptionPane.showMessageDialog(passengerDialog, "Please enter a valid age", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create booking
                BookingManager.BookingResult result = bookingManager.createBooking(
                    currentUser.getUserId(),
                    seat.getSeatId(),
                    trainResult.getTrain().getTrainId(),
                    trainResult.getRoute().getRouteId(),
                    name,
                    age
                );

                if (result.isSuccess()) {
                    // Show payment dialog
                    BigDecimal bookingAmount = trainResult.getRoute().getPrice();
                    PaymentDialog paymentDialog = new PaymentDialog(mainFrame, result.getId(), bookingAmount);
                    paymentDialog.setVisible(true);
                    
                    passengerDialog.dispose();
                    parentDialog.dispose();
                    
                    // Refresh my bookings tab
                    refreshMyBookings();
                     if (paymentDialog.isPaymentSuccessful()) {
                        JOptionPane.showMessageDialog(passengerDialog, 
                            "Booking and payment successful!\nBooking ID: " + result.getId(), 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        passengerDialog.dispose();
                        // parentDialog.dispose();
                        
                        // // Refresh my bookings tab
                        // refreshMyBookings();
                    } else {
                        JOptionPane.showMessageDialog(passengerDialog, 
                            "Payment was not completed. Booking has been cancelled.", 
                            "Payment Cancelled", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(passengerDialog, 
                        "Booking failed: " + result.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(passengerDialog, "Please enter a valid age", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(passengerDialog, "Database error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        passengerDialog.add(panel);
        passengerDialog.setVisible(true);
    }
    
    private JPanel createMyBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshMyBookings());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Bookings display area
        JTextArea bookingsArea = new JTextArea(25, 70);
        bookingsArea.setEditable(false);
        bookingsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(bookingsArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Store reference for refreshing
        panel.putClientProperty("bookingsArea", bookingsArea);
        
        // Load initial bookings
        refreshMyBookings();
        
        return panel;
    }
    
    private void refreshMyBookings() {
        try {
            List<BookingManager.BookingDetails> bookings = bookingManager.getBookingsForUser(currentUser.getUserId());
            
            StringBuilder sb = new StringBuilder();
            sb.append("My Bookings\n");
            sb.append("=".repeat(100)).append("\n\n");
            
            if (bookings.isEmpty()) {
                sb.append("No bookings found.\n");
            } else {
                for (BookingManager.BookingDetails booking : bookings) {
                    sb.append("Booking ID: ").append(booking.getBookingId()).append("\n");
                    sb.append("Train: ").append(booking.getTrainName()).append(" (").append(booking.getTrainNumber()).append(")\n");
                    sb.append("Route: ").append(booking.getSourceStation()).append(" → ").append(booking.getDestinationStation()).append("\n");
                    sb.append("Passenger: ").append(booking.getPassengerName()).append(" (Age: ").append(booking.getPassengerAge()).append(")\n");
                    sb.append("Seat: ").append(booking.getSeatNumber() != null ? booking.getSeatNumber() : "N/A");
                    if (booking.getBerthType() != null) {
                        sb.append(" (").append(booking.getBerthType()).append(")");
                    }
                    sb.append("\n");
                    sb.append("Status: ").append(booking.getStatus()).append("\n");
                    sb.append("Booking Time: ").append(booking.getBookingTime()).append("\n");
                    sb.append("Price: ₹").append(booking.getPrice()).append("\n");
                    sb.append("-".repeat(80)).append("\n\n");
                }
            }
            
            // Find and update the bookings area
            Component[] components = mainPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JTextArea bookingsArea = (JTextArea) ((JPanel) comp).getClientProperty("bookingsArea");
                    if (bookingsArea != null) {
                        bookingsArea.setText(sb.toString());
                        break;
                    }
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error loading bookings: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Admin Panel - Train and User Management"));
        // Add admin functionality here
        return panel;
    }
    
    private void logout() {
        currentUser = null;
        mainPanel.removeAll();
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");
        cardLayout.show(mainPanel, "LOGIN");
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookMyTicketApp();
        });
    }
}
