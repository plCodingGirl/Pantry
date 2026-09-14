public class ProductStorage {
    Product product;
    StorageLocation storageLocation;
    boolean available;

    public ProductStorage (Product product, StorageLocation storageLocation, boolean available) {
        this.product = product;
        this.storageLocation = storageLocation;
        this.available = available;
    }
}
