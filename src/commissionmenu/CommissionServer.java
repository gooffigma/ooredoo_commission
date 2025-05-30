
package commissionmenu;

import java.io.*;
import java.net.*;
import java.sql.*;

public class CommissionServer implements Runnable {
    private ServerSocket serverSocket;
    private Connection dbConnection;

    public CommissionServer() {
        try {
            // Initialize database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ooredoo_commission", 
                "root", "");
            
            // Create server socket on port 5000
            serverSocket = new ServerSocket(5000);
        } catch (Exception e) {
            System.err.println("Server initialization error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Server started. Waiting for clients...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, dbConnection)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private Connection dbConnection;

        public ClientHandler(Socket socket, Connection dbConnection) {
            this.clientSocket = socket;
            this.dbConnection = dbConnection;
        }

        @Override
        public void run() {
            try (
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            ) {
                // Receive data from client
                double individualSales = in.readDouble();
                System.out.println(individualSales);
                double crs = in.readDouble();
                double teamSales = in.readDouble();
                
                // Calculate commission
                double commission = calculateCommission(individualSales, crs, teamSales);
                
                // Send commission back to client
                out.writeDouble(commission);
                out.flush();
            } catch (IOException e) {
                System.err.println("Client handling error: " + e.getMessage());
            }
        }
        
        private double calculateCommission(double individualSales, double crs, double teamSales) {
            double commission = 0;
            double rate = 0;
            double bonus = 0;
            
            try {
                // Get individual commission rate and bonus
                String sql = "SELECT rate, bonus FROM commission_rates WHERE " +
                    "(sales_threshold = '>25000' AND ? > 25000) OR " +
                    "(sales_threshold = '15000-25000' AND ? BETWEEN 15000 AND 25000) OR " +
                    "(sales_threshold = '<15000' AND ? < 15000)";
                
                PreparedStatement pstmt = dbConnection.prepareStatement(sql);
                pstmt.setDouble(1, individualSales);
                pstmt.setDouble(2, individualSales);
                pstmt.setDouble(3, individualSales);
                
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    rate = rs.getDouble("rate");
                    bonus = rs.getDouble("bonus");
                }
                commission = (individualSales * rate / 100) + bonus;
                
                // Get retention bonus
                sql = "SELECT bonus FROM commission_rates WHERE " +
                    "(sales_threshold = 'CRS>80' AND ? > 80) OR " +
                    "(sales_threshold = 'CRS60-80' AND ? BETWEEN 60 AND 80)";
                
                pstmt = dbConnection.prepareStatement(sql);
                pstmt.setDouble(1, crs);
                pstmt.setDouble(2, crs);
                
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    commission += rs.getDouble("bonus");
                }
                
                // Apply team performance scaling
                if (teamSales > 200000) {
                    commission *= 1.1;
                } else if (teamSales > 100000) {
                    commission *= 1.05;
                }
                
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
            
            return commission;
        }
    }

    public static void main(String[] args) {
        CommissionServer server = new CommissionServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}