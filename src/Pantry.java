import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pantry {
    List <Product> products;
    //List <Category> categories;
    List <StorageLocation> storageLocations;
    List <ProductStorage> productStorages;

    public Pantry() {
        products = new ArrayList<>();
        storageLocations = new ArrayList<>();
        storageLocations.add(new StorageLocation("Regał - Półka 1"));
        storageLocations.add(new StorageLocation("Regał - Półka 2"));
        storageLocations.add(new StorageLocation("Kuchnia - Szuflada A4"));
        //categories = new ArrayList<>();
        //productStorages = new ArrayList<>();
    }

    boolean isProductExists(String productName) {
        for (Product product : products) {
            if (product.name.equals(productName)){
                return true;
            }
        }
        System.out.println("Dodaję produkt");
        return false;
    }

    void addProduct (Product product) {
        if (!isProductExists(product.name)) {
            products.add(product);
        }
    }

    int getProductCount() {
        return products.size();
    }

    void showProducts() {
        System.out.println("Produkty w Twojej spiżarni");
        for (Product product : products) {
            System.out.println("Produkt " + product.name + " Kategoria " + product.category);
        }
    }

    void showStorageLocations() {
        System.out.println("Dostępne miejsca:");
        for (StorageLocation storageLocation : storageLocations) {
            System.out.println(storageLocation);
        }
    }

    public static void main (String[] args){
        Pantry pantry = new Pantry();
        Scanner scanner = new Scanner(System.in);
        String productName;
        String categoryName;
        Product product;

    do {
        System.out.println("Dodaj nowy produkt");
        productName = scanner.nextLine();
        System.out.println("Podaj kategorie produktu");
        categoryName = scanner.nextLine();

        product = new Product(productName, new Category(categoryName));

        pantry.addProduct(product);
        System.out.println(""" 
                Wybierz 1 lub 2:
                1 - Dodaj kolejny produkt
                2 - Zakończ 
                """);
    } while (scanner.nextLine().equals("1"));

        System.out.println("Ilość produktów w Twojej spiżarni to " + pantry.getProductCount());
        pantry.showProducts();
        pantry.showStorageLocations();
        }
}
