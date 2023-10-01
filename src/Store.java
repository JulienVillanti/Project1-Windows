import java.util.Scanner;

public class Store {
    public static boolean checkPassword1(String pass) {
        Scanner kb = new Scanner(System.in);
        int passwordAttempts = 0;

        while (passwordAttempts < 3) {
            System.out.print("Please enter you password: ");
            String userPassword = kb.next();

            if (userPassword.equals(pass)) {
                return true;
            } else {
                System.out.println("Wrong password, please try again! You have " + (2 - passwordAttempts) + " attempts remaining.");
                passwordAttempts++;
            }
        }
        System.out.println("You have no more attempts, back to main menu");
        return false;
    }

    //------Writing the method for finding computers by brand
    public static void findComputersBy(Computer[] inventory, String brandSearch) {
        boolean foundBrand = false;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getBrand().equals(brandSearch)) {
                System.out.println("Computer # " + (i + 1) + " :");
                System.out.println("Brand: " + inventory[i].getBrand());
                System.out.println("Model: " + inventory[i].getModel());
                System.out.println("Price: " + inventory[i].getPrice());
                System.out.println("Serial Number: " + inventory[i].getSerialNumber());
                System.out.println();
                foundBrand = true;

            }
        }
        if (!foundBrand) {
            System.out.println("There is no " + brandSearch + " computers with that brand in our inventory");
        }
    }

    //------Writing the method for finding computers under a certain price
    public static void findCheaperThan(Computer[] inventory, double checkPrice) {
        boolean priceCompare = false;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getPrice() < checkPrice) {
                System.out.println("Computer # " + (i + 1) + " :");
                System.out.println("Brand: " + inventory[i].getBrand());
                System.out.println("Model: " + inventory[i].getModel());
                System.out.println("Price: " + inventory[i].getPrice());
                System.out.println("Serial Number: " + inventory[i].getSerialNumber());
                System.out.println();
            }
        }
    }

    public static void negativeNum(double price) {
        boolean negNum = false;
        if (price < 0) {
            System.out.println("This value cannot be negative!");
        }
    }

    public static void main(String[] args) {
        //Computer c1 = new Computer("Apple", "MacPro", 3000);
        //c1.displayComputer(c1);
        Scanner kb = new Scanner(System.in);
        System.out.println("Welcome to Pargol Computer Store!");
        int maxComputers = 0;
        final String password = "password";
        int compuNum = 1;


        while (maxComputers < 1) {
            System.out.println("Please enter the maximum amount of computers your store can contain: ");
            maxComputers = kb.nextInt();
            if (maxComputers < 1) {
                System.out.println("Invalid, your inventory should be positive");
            }
        }
        Computer[] inventory = new Computer[maxComputers];

        do {
            //menu
            System.out.println("""
                    What do you want to do ?
                    1.\tEnter new computers (password required)
                    2.\tChange information of a computer (password required)
                    3.\tDisplay all computers by a specific brand
                    4.\tDisplay all computers under a certain a price.
                    5.\tQuit
                    """);

            int choiceMenu = kb.nextInt();
            if (choiceMenu != 1 && choiceMenu != 2 && choiceMenu != 3 && choiceMenu != 4 && choiceMenu != 5) {
                System.out.println("Your choice is not included in the menu!");
                //
                // ----NEED TO WRITE BLOCK OF CODE WHEN USER ENTER A LETTER OR A STRING-------
            }//lse-if(choiceMenu == ){

            //}

            switch (choiceMenu) {

                case 1 -> {

                    if (checkPassword1(password)) {
                        System.out.println("How many computers would you like to add?");
                        int numOfComputers = kb.nextInt();
                        while (numOfComputers < 0) {
                            System.out.println("This cannot be a negative number. Please enter the number again: ");
                            numOfComputers = kb.nextInt();
                            for (int i= 0; i < inventory.length; i++){
                                int freeInventorySpot = 0;
                                if (inventory[i] == null){
                                    freeInventorySpot++;
                                }else{
                                    System.out.println("You cannot add those computers. The remaining number of spot is : " + freeInventorySpot);
                                }
                            }
                        }
                        double price = 0;
                        if (numOfComputers <= maxComputers) {
                            System.out.println("Please enter your computer's specifications: ");
                            kb.nextLine();

                            for (int i = 0; i < numOfComputers; i++) {
                                System.out.println("Computer # " + compuNum);
                                System.out.println("Brand: ");
                                String brand = kb.nextLine();
                                System.out.println("Model: ");
                                String model = kb.nextLine();
                                //System.out.println("Price: ");
                                do {
                                    System.out.println("Price: ");
                                    price = kb.nextDouble();
                                    if (price < 0) {
                                        System.out.println("Price cannot be negative. Please enter a non-negative price.");
                                    }
                                } while (price < 0);
                                kb.nextLine();
                                inventory[i] = new Computer(brand, model, price);
                                compuNum++;
                            }
                            System.out.println("Computers added to inventory.");
                            System.out.println("Here is your store: \n");
                            for (Computer computer : inventory) {
                                if (computer != null) {
                                    System.out.println("Brand: " + computer.getBrand());
                                    System.out.println("Model: " + computer.getModel());
                                    System.out.println("Price: " + computer.getPrice() + " $");
                                    System.out.println("Serial Number: " + computer.getSerialNumber());
                                    System.out.println();
                                }
                            }
                        } else {
                            System.out.println("There is not enough space to add that computer. You can only add " + (inventory.length - compuNum) + " computers to the list.");

                        }

                    }

                }
                case 2 -> {
                    if (checkPassword1(password)) {
                        System.out.println("Which computer would you like to update?");
                        int updateComputer = kb.nextInt();

                        if (updateComputer < 0 || updateComputer >= inventory.length || inventory[updateComputer] == null) {
                            System.out.println("This computer cannot be found. Would you like to enter another computer ? (Y/N)");
                            String answer = kb.next();
                            if (answer.equalsIgnoreCase("N")) {
                                return;
                            }
                        } else {
                            Computer computer = inventory[updateComputer];
                            System.out.println("Current information of the computer: \n" +
                                    "\tComputer # " + updateComputer + "\n" +
                                    "\tBrand: " + computer.getBrand() + "\n" +
                                    "\tModel: " + computer.getModel() + "\n" +
                                    "\tSN: " + computer.getSerialNumber() + "\n" +
                                    "\tPrice: " + computer.getPrice() + "\n");

                            do {
                                System.out.println("""
                                        What information would you like to change?
                                        1.\tBrand
                                        2.\tModel
                                        3.\tSerial Number
                                        4.\tPrice
                                        5.\tQuit
                                        Enter your choice:
                                        """);
                                int choiceMenu2 = kb.nextInt();
                                if (choiceMenu2 != 1 && choiceMenu2 != 2 && choiceMenu2 != 3 && choiceMenu2 != 4 && choiceMenu2 != 5) {
                                    System.out.println("Your choice is not included in the menu!");
                                }
                                switch (choiceMenu2) {
                                    case 1:
                                        System.out.println("Enter the updated information of for your computer: ");
                                        kb.nextLine();
                                        System.out.println("Brand: ");
                                        String brand = kb.nextLine();
                                        computer.setBrand(brand);
                                        break;

                                    case 2:
                                        System.out.println("Enter the updated information of for your computer: ");
                                        kb.nextLine();
                                        System.out.println("Model: ");
                                        String model = kb.nextLine();
                                        computer.setModel(model);
                                        break;

                                    case 3:
                                        System.out.println("Enter the updated information of for your computer: ");
                                        kb.nextLine();
                                        System.out.println("SN: ");
                                        long serialNum = kb.nextLong();
                                        computer.setSerialNumber(serialNum);
                                        break;

                                    case 4:
                                        System.out.println("Enter the updated information of for your computer: ");
                                        kb.nextLine();
                                        System.out.println("Price: ");
                                        double price = kb.nextDouble();
                                        computer.setPrice(price);
                                        break;

                                    case 5:
                                        System.out.println("Thank you !");
                                        return;
                                }
                            } while (true);
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Please enter the brand of the computer you would like to search for: ");
                    String brandSearch = kb.next();
                    findComputersBy(inventory, brandSearch);

                }

                case 4 -> {
                    System.out.println("Please enter the brand of the computer you would like to search for: ");
                    double checkPrice = kb.nextDouble();
                    findCheaperThan(inventory, checkPrice);
                }

                case 5 -> {
                    System.out.println("Thank you for using my program! Goodbye!");
                    System.exit(5);
                }
                default -> {
                    System.out.println("Please select a choice from the menu!");
                }
            }

        }
        while (true);
    }
}
