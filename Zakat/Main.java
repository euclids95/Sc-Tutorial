import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Individu individu = new Individu();

        while (true) {
            System.out.println("Welcome to the Zakat Calculator!");
            System.out.println("Please select an option:");
            System.out.println("1. Calculate Zakat");
            System.out.println("2. Show Record");
            System.out.println("4.Calculate Gold Zakat");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()

    

            switch (choice) {
                case 1:
                    clearConsole();
                    System.out.print("Enter your name: ");
                    individu.name = scanner.nextLine();
                    System.out.print("Enter your age: ");
                    individu.age = scanner.nextInt();
                    System.out.print("Enter your ID: ");
                    individu.id = scanner.nextInt();
                    System.out.print("Enter your total wealth: ");
                    individu.TotalWealth = scanner.nextDouble();

                    System.out.println("Total Zakat due: " + individu.calcZakat());

                    individu.displayInfo(); 

                    try (FileWriter cZakat = new FileWriter("Zakat.txt")) {
                        cZakat.write("Name: " + individu.name + ", Age: " + individu.age + ", ID: " + individu.id + ", Total Wealth: " + individu.TotalWealth + ", Zakat Due: " + individu.calcZakat() + "\n");
                        System.out.println("Zakat details have been written to Zakat.txt");
                    } catch (IOException e) {
                        System.out.println("An error occurred while writing to the file.");
                        e.printStackTrace();
                    }
                    System.out.println("Press 0 to back to main menu...");
                    int back = scanner.nextInt();
                    if (back == 0) {
                        clearConsole();
                    }
                     
                    break;

                case 2:
                    try (Scanner output = new Scanner(new File("Zakat.txt"))) {
                        while (output.hasNextLine()) {
                            String line = output.nextLine();
                            String[] parts = line.split(", ");
                            int i = 0;
                            if (parts.length > 2) {
                                // parts[2] should be "ID: <value>"
                                String str1 = parts[2].substring(4);
                                System.out.println(str1);                                                                                                                                                        
                            }
                            System.out.println((i + 1) + line);
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Zakat.txt file not found.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return; // Exit the main method

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public static double calculateZakat(double wealth) {
        final double ZAKAT_RATE = 0.025; // 2.5%
        return wealth * ZAKAT_RATE;
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Error clearing console: " + ex.getMessage());
        }
    }
}