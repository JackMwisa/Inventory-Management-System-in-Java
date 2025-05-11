package model;

public class Product {
    private int id;
    private String name;
    private String type;
    private String model;
    private String manufacturer;
    private String description;
    private double sellingPrice;
    private double purchasingPrice;
    private int expiringYear;
    private int quantity;

    public Product(int id, String name, String type, String model,
                   String manufacturer, String description,
                   double sellingPrice, double purchasingPrice,
                   int expiringYear, int quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.model = model;
        this.manufacturer = manufacturer;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.purchasingPrice = purchasingPrice;
        this.expiringYear = expiringYear;
        this.quantity = quantity;
    }

    public Product(String name, String type, String model,
                   String manufacturer, String description,
                   double sellingPrice, double purchasingPrice,
                   int expiringYear, int quantity) {
        this(0, name, type, model, manufacturer, description, sellingPrice, purchasingPrice, expiringYear, quantity);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public double getPurchasingPrice() { return purchasingPrice; }
    public void setPurchasingPrice(double purchasingPrice) { this.purchasingPrice = purchasingPrice; }

    public int getExpiringYear() { return expiringYear; }
    public void setExpiringYear(int expiringYear) { this.expiringYear = expiringYear; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Type: %s | Model: %s | Manufacturer: %s | Price: $%.2f | Qty: %d",
                id, name, type, model, manufacturer, sellingPrice, quantity);
    }
}
