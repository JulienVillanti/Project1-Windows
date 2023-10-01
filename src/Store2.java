import java.util.Scanner;

public class Store2 {
    private static final String PASSWORD = "password";
    private static final int MAX_ATTEMPTS = 3;

    public static boolean checkPassword(String pass) {
        Scanner kb = new Scanner(System.in);
        int passwordAttempts = 0;

        while (passwordAttempts < MAX_ATTEMPTS) {
            System.out.println("Please enter your password: ");
            String userPassword = kb.next();

            if (userPassword.equals(pass)) {
                return true;
            } else {
                System.out.println("Wrong password, please try again! You have " + (MAX_ATTEMPTS - passwordAttempts) + " attempts remaining.");
                passwordAttempts++;
            }
        }
        System.out.println("You have no more attempts. Returning to the main menu.");
        return false;
    }

    public static void findComputersByBrand(Computer[] inventory, String brandSearch) {
        boolean foundBrand = false;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getBrand().equalsIgnoreCase(brandSearch)) {
                displayComputerInfo(inventory[i]);
                foundBrand = true;
            }
        }
        if (!foundBrand) {
            System.out.println("No computers with the brand '" + brandSearch + "' found in the inventory.");
        }
    }

    public static void findCheaperThan(Computer[] inventory, double checkPrice) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getPrice() < checkPrice) {
                displayComputerInfo(inventory[i]);
            }
        }
    }

    public static void displayComputerInfo(Computer computer) {
        System.out.println("Brand: " + computer.getBrand());
        System.out.println("Model: " + computer.getModel());
        System.out.println("Price: $" + computer.getPrice());
        System.out.println("Serial Number: " + computer.getSerialNumber());
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Welcome to Pargol Computer Store!");
        int maxComputers = 0;
        int compuNum = 1;

        while (maxComputers < 1) {
            System.out.println("Please enter the maximum number of computers your store can contain: ");
            maxComputers = kb.nextInt();
            if (maxComputers < 1) {
                System.out.println("Invalid input. The inventory size should be a positive number.");
            }
        }

        Computer[] inventory = new Computer[maxComputers];

        do {
            System.out.println("""
                    What would you like to do?
                    1. Enter new computers (password required)
                    2. Change information of a computer (password required)
                    3. Display all computers by a specific brand
                    4. Display all computers under a certain price
                    5. Quit
                    """);

            int choiceMenu = kb.nextInt();

            switch (choiceMenu) {
                case 1:
                    if (checkPassword(PASSWORD)) {
                        System.out.println("How many computers would you like to add?");
                        int numOfComputers = kb.nextInt();

                        while (numOfComputers < 0) {
                            System.out.println("The number of computers cannot be negative. Please enter a valid number: ");
                            numOfComputers = kb.nextInt();
                        }

                        int freeInventorySpots = 0; // Initialize outside the loop

                        for (int i = 0; i < inventory.length; i++) {
                            if (inventory[i] == null) {
                                freeInventorySpots++;
                            }
                        }

                        if (compuNum + numOfComputers - 1 <= maxComputers) {
                            if (numOfComputers <= freeInventorySpots) {
                                while (numOfComputers > 0) {
                                    System.out.println("Computer # " + compuNum);
                                    kb.nextLine(); // Consume the newline character

                                    System.out.println("Brand: ");
                                    String brand = kb.nextLine();
                                    System.out.println("Model: ");
                                    String model = kb.nextLine();
                                    double price;

                                    do {
                                        System.out.println("Price: ");
                                        price = kb.nextDouble();

                                        if (price < 0) {
                                            System.out.println("Price cannot be negative. Please enter a non-negative price.");
                                        }
                                    } while (price < 0);

                                    inventory[compuNum - 1] = new Computer(brand, model, price);
                                    compuNum++;
                                    numOfComputers--;
                                }

                                System.out.println("Computers added to the inventory.");
                            } else {
                                while (true)
                                System.out.println("There is not enough space to add that many computers. You can only add " + freeInventorySpots + " more computers to the list.");
                            }break;
                        } else {
                            System.out.println("There is not enough space to add that many computers. You can only add " + (maxComputers - compuNum + 1) + " more computers to the list.");
                        }
                    }
                    break;
                case 2:
                    if (checkPassword(PASSWORD)) {
                        System.out.println("Which computer would you like to update?");
                        int updateComputer = kb.nextInt();

                        if (updateComputer < 1 || updateComputer > compuNum || inventory[updateComputer - 1] == null) {
                            System.out.println("The selected computer cannot be found. Would you like to try another computer? (Y/N)");
                            String answer = kb.next();
                            if (!answer.equalsIgnoreCase("Y")) {
                                return;
                            }
                        } else {
                            Computer computer = inventory[updateComputer - 1];
                            displayComputerInfo(computer);

                            do {
                                System.out.println("""
                                        What information would you like to change?
                                        1. Brand
                                        2. Model
                                        3. Serial Number
                                        4. Price
                                        5. Quit
                                        Enter your choice:
                                        """);
                                int choiceMenu2 = kb.nextInt();

                                switch (choiceMenu2) {
                                    case 1:
                                        System.out.println("Enter the updated brand: ");
                                        kb.nextLine();
                                        String brand = kb.nextLine();
                                        computer.setBrand(brand);
                                        break;
                                    case 2:
                                        System.out.println("Enter the updated model: ");
                                        kb.nextLine();
                                        String model = kb.nextLine();
                                        computer.setModel(model);
                                        break;
                                    case 3:
                                        System.out.println("Enter the updated serial number: ");
                                        long serialNum = kb.nextLong();
                                        computer.setSerialNumber(serialNum);
                                        break;
                                    case 4:
                                        System.out.println("Enter the updated price: ");
                                        double price = kb.nextDouble();
                                        computer.setPrice(price);
                                        break;
                                    case 5:
                                        System.out.println("Thank you!");
                                        return;
                                }
                            } while (true);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Please enter the brand of the computer you would like to search for: ");
                    String brandSearch = kb.next();
                    findComputersByBrand(inventory, brandSearch);
                    break;

                case 4:
                    System.out.println("Please enter the maximum price: ");
                    double checkPrice = kb.nextDouble();
                    findCheaperThan(inventory, checkPrice);
                    break;

                case 5:
                    System.out.println("Thank you for using the program. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please select a valid option from the menu.");
                    break;
            }

        } while (true);
    }
}