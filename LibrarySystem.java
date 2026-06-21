package com.beginsecure;
/*
Nadim Ramrattan
CEN-3024C
6/18/2026
Library Management System
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Main class that runs the Library System
public class LibrarySystem {
    // store patrons in a LinkedHashMap (keeps order they were added)
    private Map<String, Patron> patrons = new LinkedHashMap<>();
    private Scanner input = new Scanner(System.in);

    // main loop for the program
    public void run() {
        boolean running = true;
        while (running) {
            showMenu(); // show options to user
            String choice = input.nextLine().trim();

            // check what the user picked
            switch (choice) {
                case "1":
                    loadFromFile(); // import from file
                    break;
                case "2":
                    addPatron(); // add manually
                    break;
                case "3":
                    removePatron(); // remove patron by ID
                    break;
                case "4":
                    listPatrons(); // show all patrons
                    break;
                case "5":
                    running = false; // exit program
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // shows the menu every time
    private void showMenu() {
        System.out.println("\n--- Library Menu ---");
        System.out.println("1) Import patrons from file");
        System.out.println("2) Add patron manually");
        System.out.println("3) Remove patron");
        System.out.println("4) Show all patrons");
        System.out.println("5) Exit");
        System.out.print("Pick an option: ");
    }

    // loads patrons from a text file
    private void loadFromFile() {
        System.out.print("Enter file name: ");
        String fileName = input.nextLine().trim();
        File file = new File(fileName);

        try (Scanner fileScanner = new Scanner(file)) {
            int added = 0; // count how many got added
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(","); // split by comma
                if (parts.length == 4) { // make sure it has 4 pieces
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String addr = parts[2].trim();
                    double fine;

                    try {
                        fine = Double.parseDouble(parts[3].trim()); // parse fine
                    } catch (NumberFormatException e) {
                        System.out.println("Bad fine value in: " + line);
                        continue; // skip this line
                    }

                    // validate id and fine before adding
                    if (validPatron(id, fine)) {
                        patrons.put(id, new Patron(id, name, addr, fine));
                        added++;
                    } else {
                        System.out.println("Invalid patron skipped: " + line);
                    }
                } else {
                    System.out.println("Wrong format: " + line);
                }
            }
            System.out.println("Imported " + added + " patrons.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    // lets the librarian add one patron by typing info in
    private void addPatron() {
        System.out.print("Enter 7-digit ID: ");
        String id = input.nextLine().trim();
        System.out.print("Enter name: ");
        String name = input.nextLine().trim();
        System.out.print("Enter address: ");
        String addr = input.nextLine().trim();
        System.out.print("Enter fine: ");
        double fine;

        try {
            fine = Double.parseDouble(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Fine must be a number.");
            return; // stop if it’s not valid
        }

        // make sure everything is valid before adding
        if (validPatron(id, fine)) {
            patrons.put(id, new Patron(id, name, addr, fine));
            System.out.println("Patron added.");
        } else {
            System.out.println("Invalid patron data, nothing added.");
        }
    }

    // removes a patron if the ID exists
    private void removePatron() {
        System.out.print("Enter ID to remove: ");
        String id = input.nextLine().trim();
        if (patrons.containsKey(id)) {
            patrons.remove(id);
            System.out.println("Patron removed.");
        } else {
            System.out.println("No patron found with that ID.");
        }
    }

    // lists all patrons in the system
    private void listPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("No patrons in the system.");
        } else {
            System.out.println("\nCurrent patrons:");
            for (Patron p : patrons.values()) {
                System.out.println(p);
            }
        }
    }

    // checks if patron data is valid
    private boolean validPatron(String id, double fine) {
        // ID has to be 7 digits and fine between 0–250
        return id.matches("\\d{7}") && fine >= 0 && fine <= 250;
    }
}