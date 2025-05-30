/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commissionmenu;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CommissionClient implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    @Override
    public void run() {
        try {
            // Connect to server on localhost port 5000
            socket = new Socket("localhost", 5000);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            
            Scanner scanner = new Scanner(System.in);
            
            // Get input from user with validation
            System.out.print("Enter individual sales revenue (OMR): ");
            double individualSales = validateInput(scanner, 0, Double.MAX_VALUE);
            
            System.out.print("Enter customer retention score (0-100): ");
            double crs = validateInput(scanner, 0, 100);
            
            System.out.print("Enter team sales revenue (OMR): ");
            double teamSales = validateInput(scanner, 0, Double.MAX_VALUE);
            
            // Send data to server
            out.writeDouble(individualSales);
            out.writeDouble(crs);
            out.writeDouble(teamSales);
            out.flush();
            
            // Receive and display commission
            double commission = in.readDouble();
            System.out.printf("Your total commission is: %.2f OMR%n", commission);
            
            // Close connections
            scanner.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
    
    private double validateInput(Scanner scanner, double min, double max) {
        while (true) {
            try {
                double input = Double.parseDouble(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a value between %.2f and %.2f: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    public static void main(String[] args) {
        CommissionClient client = new CommissionClient();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}