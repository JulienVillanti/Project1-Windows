import java.util.Scanner;

    public class Store3 {
        private static final String PASSWORD = "password";
        private static final int MAX_ATTEMPTS = 3;

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int maxComputers = getInventorySize(scanner);
            Computer[] inventory = new Computer[maxComputers];
            int compuNum = 1;

            while (true) {
                displayMenu();
                int choice = getUserChoice(scanner);

                switch (choice) {
                    case 1:
                        addComputers(scanner, inventory, compuNum);
                        break;
                    case 2:
                        updateComputer(scanner, inventory);
                        break;
                    case 3:
                        searchByBrand(scanner, inventory);
                        break;
                    case 4:
                        searchByPrice(scanner, inventory);
                        break;
                    case 5:
                        System.out.println("Thank you for using the program. Goodbye!");
                        return;
                    default:
                        System.out.println("Please select a valid option from the menu.");
                }
            }
        }

        private static int getInventorySize(Scanner scanner) {
            int maxComputers;
            do {
                System.out.print("Please enter the maximum number of computers your store can contain: ");
                maxComputers = scanner.nextInt();
                if (maxComputers < 1) {
                    System.out.println("Invalid input. The inventory size should be a positive number.");
                }
            } while (maxComputers < 1);
            return maxComputers;
        }

        private static void displayMenu() {
            System.out.println("""
                What would you like to do?
                1. Enter new computers (password required)
                2. Change information of a computer (password required)
                3. Display all computers by a specific brand
                4. Display all computers under a certain price
                5. Quit
                """);
        }

        private static int getUserChoice(Scanner scanner) {
            int choice;
            do {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } while (choice < 1 || choice > 5);
            return choice;
        }

        private static boolean checkPassword(Scanner scanner) {
            int passwordAttempts = 0;
            while (passwordAttempts < MAX_ATTEMPTS) {
                System.out.print("Please enter your password: ");
                String userPassword = scanner.next();

                if (userPassword.equals(PASSWORD)) {
                    return true;
                } else {
                    System.out.println("Wrong password, please try again! You have " + (MAX_ATTEMPTS - passwordAttempts) + " attempts remaining.");
                    passwordAttempts++;
                }
            }
            System.out.println("You have no more attempts. Returning to the main menu.");
            return false;
        }

        private static void addComputers(Scanner scanner, Computer[] inventory, int compuNum) {
            if (checkPassword(scanner)) {
                System.out.print("How many computers would you like to add? ");
                int numOfComputers = scanner.nextInt();

                while (numOfComputers < 0) {
                    System.out.print("The number of computers cannot be negative. Please enter a valid number: ");
                    numOfComputers = scanner.nextInt();
                }

                int spaceAvailable = inventory.length - compuNum + 1;
                if (numOfComputers <= spaceAvailable) {
                    for (int i = 0; i < numOfComputers; i++) {
                        System.out.println("Computer #" + compuNum);
                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Brand: ");
                        String brand = scanner.nextLine();
                        System.out.print("Model: ");
                        String model = scanner.nextLine();
                        double price;

                        do {
                            System.out.print("Price: ");
                            price = scanner.nextDouble();

                            if (price < 0) {
                                System.out.println("Price cannot be negative. Please enter a non-negative price.");
                            }
                        } while (price < 0);

                        inventory[compuNum - 1] = new Computer(brand, model, price);
                        compuNum++;
                    }
                    System.out.println("Computers added to the inventory.");
                } else {
                    System.out.println("There is not enough space to add that many computers. You can only add " + spaceAvailable + " more computers to the list.");
                }
            }
        }

        private static void updateComputer(Scanner scanner, Computer[] inventory) {
            if (checkPassword(scanner)) {
                System.out.print("Which computer would you like to update? ");
                int updateComputer = scanner.nextInt();

                if (updateComputer < 1 || updateComputer > inventory.length || inventory[updateComputer - 1] == null) {
                    System.out.print("The selected computer cannot be found. Would you like to try another computer? (Y/N) ");
                    String answer = scanner.next();
                    if (!answer.equalsIgnoreCase("Y")) {
                        return;
                    }
                } else {
                    Computer computer = inventory[updateComputer - 1];
                    displayComputerInfo(computer);

                    int choiceMenu2;
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
                        choiceMenu2 = scanner.nextInt();

                        switch (choiceMenu2) {
                            case 1:
                                System.out.print("Enter the updated brand: ");
                                scanner.nextLine();
                                String brand = scanner.nextLine();
                                computer.setBrand(brand);
                                break;
                            case 2:
                                System.out.print("Enter the updated model: ");
                                scanner.nextLine();
                                String model = scanner.nextLine();
                                computer.setModel(model);
                                break;
                            case 3:
                                System.out.print("Enter the updated serial number: ");
                                long serialNum = scanner.nextLong();
                                computer.setSerialNumber(serialNum);
                                break;
                            case 4:
                                System.out.print("Enter the updated price: ");
                                double price = scanner.nextDouble();
                                computer.setPrice(price);
                                break;
                            case 5:
                                System.out.println("Thank you!");
                                return;
                        }
                    } while (true);
                }
            }
        }

        private static void searchByBrand(Scanner scanner, Computer[] inventory) {
            System.out.print("Please enter the brand of the computer you would like to search for: ");
            String brandSearch = scanner.next();
            boolean foundBrand = false;

            for (Computer computer : inventory) {
                if (computer != null && computer.getBrand().equalsIgnoreCase(brandSearch)) {
                    displayComputerInfo(computer);
                    foundBrand = true;
                }
            }

            if (!foundBrand) {
                System.out.println("No computers with the brand '" + brandSearch + "' found in the inventory.");
            }
        }

        private static void searchByPrice(Scanner scanner, Computer[] inventory) {
            System.out.print("Please enter the maximum price: ");
            double checkPrice = scanner.nextDouble();

            for (Computer computer : inventory) {
                if (computer != null && computer.getPrice() < checkPrice) {
                    displayComputerInfo(computer);
                }
            }
        }

        private static void displayComputerInfo(Computer computer) {
            System.out.println("Brand: " + computer.getBrand());
            System.out.println("Model: " + computer.getModel());
            System.out.println("Price: $" + computer.getPrice());
            System.out.println("Serial Number: " + computer.getSerialNumber());
            System.out.println();
        }
    }

