package CartSystem;

public class Item {
    private String userName;
    private String id;
    private String productType;
    private String name;
    private String model;
    private String manufacturer;
    private String description;
    private int sellingPrice;
    private int expiringYear;
    private int purchasingPrice;
    private int quantity;

    public Item(String userName, String id, String productType, String name, String model,
                String manufacturer, String description, int sellingPrice,
                int expiringYear, int purchasingPrice, int quantity) {
        this.userName = userName;
        this.id = id;
        this.productType = productType;
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.expiringYear = expiringYear;
        this.purchasingPrice = purchasingPrice;
        this.quantity = quantity;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }

    public String getProductType() {
        return productType;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getExpiringYear() {
        return expiringYear;
    }

    public int getPurchasingPrice() {
        return purchasingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    // Format for printing in UI
    public String getProductInfo() {
        return "User: " + userName +
                "\nID: " + id +
                "\nType: " + productType +
                "\nName: " + name +
                "\nModel: " + model +
                "\nManufacturer: " + manufacturer +
                "\nDescription: " + description +
                "\nSelling Price: $" + sellingPrice +
                "\nExpiring Year: " + expiringYear +
                "\nPurchasing Price: $" + purchasingPrice +
                "\nQuantity: " + quantity;
    }

    // Format for saving to file
    public String getTxtFormatCart() {
        return userName + "," + id + "," + productType + "," + name + "," + model + "," +
                manufacturer + "," + description + "," + sellingPrice + "," + expiringYear + "," +
                purchasingPrice + "," + quantity;
    }
}
