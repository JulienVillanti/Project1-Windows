import java.util.Objects;

public class Computer {
    private String brand;
    private String model;
    private double price;
    private long serialNumber;
    private static int serialNumberCounter = 10000;
    private static int computerCounter = 0;


    public Computer() {
        serialNumber = serialNumberCounter;
        serialNumberCounter++;
        computerCounter++;
    }

    public Computer(String br, String m, double pr) {
        brand = br;
        model = m;
        price = pr;
        serialNumber = serialNumberCounter;
        serialNumberCounter++;
        computerCounter++;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String br) {
        brand = br;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String m) {
        model = m;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double pr) {
        price = pr;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long sn) {
        serialNumber = sn;
    }

    public static void displayComputer(Computer c) {
        System.out.println("==The info is : ");
        System.out.println("==The computer's brand is : " + c.getBrand());
        System.out.println("==The computer's price is : " + c.getPrice());
        System.out.println("==The computer's model is : " + c.getModel());
        System.out.println("==The computer's serial number is : " + c.getSerialNumber());
        System.out.println("===================================");

    }

    @Override
    public String toString() {
        return "Computer{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", serialNumber=" + serialNumber +
                '}';
    }

    public static int findNumberOfCreatedComputers() {
        return computerCounter;
    }

    public boolean equal(Computer c) {
        if (brand == c.getBrand() && model == c.getModel() && price == c.getPrice()) {
            return true;
        } else {
            return false;
        }
    }

}

