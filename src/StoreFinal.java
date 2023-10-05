//------------------------------------------------------
//Project 1
// Julien Villanti
//Written by: Julien Villanti --- 2390054;
//------------------------------------------------------
// My program is a fairly simple inventory system, of a computer store in this case. The user(store owner) is first asked to determine the size of the inventory and then can enter and modify the information fo the computer in the inventory.
// A password is required for the first 2 options, and then the user can also research computers in the inventory, by brand or under a certain price

import java.util.InputMismatchException;
import java.util.Scanner;

public class StoreFinal {
    private static final String PASSWORD = "password";
    private static final int MAX_ATTEMPTS = 3;

    //-----------------PASSWORD CHECK Method
// We will be using this check twice in the program, in the menu option 1 and 2
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

    //This method will help us verify is the inventory is empty for the last 2 choices of the menu
    public static boolean isInventoryEmpty(Computer[] inventory) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                return false;
            }
        }
        return true;
    }

    // This method will be used to display the information of the computers
    public static void displayComputerInfo(Computer computer) {
        System.out.printf("Brand: %s Model: %s Price: $%.2f Serial Number: %d%n",
                computer.getBrand(), computer.getModel(), computer.getPrice(), computer.getSerialNumber());
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
        } else if (isInventoryEmpty(inventory)) {
            System.out.println("Your inventory is empty!");
        }
    }

    public static void findCheaperThan(Computer[] inventory, double checkPrice) {
        boolean foundPrice = false;
        boolean hasComputers = false;

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                hasComputers = true;
                if (inventory[i].getPrice() < checkPrice) {
                    displayComputerInfo(inventory[i]);
                    foundPrice = true;
                }
            }
        }
        if (!hasComputers) {
            System.out.println("Your inventory is empty!");
        } else if (!foundPrice) {
            System.out.println("There are no computers available under that price.");
            if (checkPrice < 0) {
                System.out.println("The price cannot be negative, please enter the price again: ");
            }
        }
    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Welcome to Pargol Computer Store!");
        int maxComputers = 0;
        int compuNum = 1;

        while (maxComputers < 1) {
            System.out.println("Enter the maximum number of computers in your store: ");
            maxComputers = kb.nextInt();
            if (maxComputers < 1) {
                System.out.println("Invalid input. The inventory size should be a positive number.");
            }
        }

        Computer[] inventory = new Computer[maxComputers];

        //Creating the main menu
        do {
            System.out.println("""
                    What would you like to do?
                    1. Enter new computers (password required)
                    2. Change information of a computer (password required)
                    3. Display all computers by a specific brand
                    4. Display all computers under a certain price
                    5. Quit
                    """);
            int choiceMenu = 0;
            try {
                choiceMenu = kb.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid, your choice was not a part of the menu! Please select one of the choices offered.");
                kb.nextLine();
            }
            switch (choiceMenu) {
                case 1:
                    if (checkPassword(PASSWORD)) {
                        while (true) {
                            System.out.println("How many computers would you like to add? (0 to exit)");
                            int numOfComputers = kb.nextInt();

                            if (numOfComputers == 0) {

                                break; // Exit the "add computers" section
                            }

                            while (numOfComputers < 0) {
                                System.out.println("The number of computers cannot be negative. Please enter a valid number: ");
                                numOfComputers = kb.nextInt();
                            }

                            int freeInventorySpots = 0;

                            for (int i = 0; i < inventory.length; i++) {
                                if (inventory[i] == null) {
                                    freeInventorySpots++;
                                }
                            }


                            if (compuNum + numOfComputers - 1 <= maxComputers) {
                                if (numOfComputers <= freeInventorySpots) {
                                    while (numOfComputers > 0) {
                                        System.out.println("Computer # " + compuNum);
                                        kb.nextLine();
                                        System.out.print("Brand: ");
                                        System.out.println();
                                        String brand = kb.nextLine();
                                        System.out.print("Model: ");
                                        System.out.println();
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
                                    System.out.println("There is not enough space to add that many computers. You can only add " + freeInventorySpots + " more computers to the list.");
                                }
                            } else {
                                System.out.println("There is not enough space to add that many computers. You can only add " + (maxComputers - compuNum + 1) + " more computers to the list.");
                            }
                        }
                    }
                    break;
                case 2:
                    if (checkPassword(PASSWORD)) {
                        boolean backToMenu = false;
                        while (!backToMenu) {
                            System.out.println("Which computer would you like to update?");
                            int updateComputer = kb.nextInt();

                            if (updateComputer == 0) {
                                break;
                            }

                            if (updateComputer < 1 || updateComputer > compuNum || inventory[updateComputer - 1] == null) {
                                System.out.println("The selected computer cannot be found. Would you like to try another computer? (Y/N)");
                                String answer = kb.next();
                                if (answer.equalsIgnoreCase("N")) {
                                    break;
                                }
                            } else {
                                Computer computer = inventory[updateComputer - 1];
                                displayComputerInfo(computer);
                                //Creating the sub-menu to modify computers specs
                                do {
                                    System.out.println("""
                                            What information would you like to change?
                                            1. Brand
                                            2. Model
                                            3. Serial Number
                                            4. Price
                                            5. (Back to Main Menu)
                                            Enter your choice:
                                            """);

                                    int choiceMenu2 = 0;
                                    try {
                                        choiceMenu2 = kb.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid, you cannot enter a letter! Please select one of the choices from the menu.");
                                        kb.nextLine();
                                    }
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
                                            while (true) {
                                                System.out.println("Enter the updated price: ");
                                                double price = kb.nextDouble();

                                                if (price < 0) {
                                                    System.out.println("Your price cannot be negative, please enter another price!");
                                                } else {
                                                    computer.setPrice(price);
                                                    break; // Exit the loop if the price is non-negative
                                                }
                                            }
                                            break;
                                        case 5:
                                            System.out.println("Returning to main menu!");
                                            backToMenu = true;
                                            break;
                                    }
                                } while (!backToMenu);
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Please enter the brand of the computer you would like to search for: ");
                    String brandSearch = kb.next();
                    findComputersByBrand(inventory, brandSearch);
                    break;

                case 4:
                    boolean validPrice = false;
                    while (!validPrice) {
                        System.out.println("Please enter the maximum price: ");
                        double checkPrice = kb.nextDouble();

                        if (checkPrice >= 0) {
                            findCheaperThan(inventory, checkPrice);
                            validPrice = true;
                        } else {
                            System.out.println("The price cannot be negative!");
                        }
                    }

                    if (!validPrice) {
                        System.out.println("No valid price entered. Returning to the main menu.");
                    }

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

