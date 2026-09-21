import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pantry {
    List <Product> products;
    List <StorageLocation> storageLocations;
    List <ProductStorage> productStorages;

    public Pantry() {
        products = new ArrayList<>();
        storageLocations = new ArrayList<>();
        storageLocations.add(new StorageLocation("Regał - Półka 1"));
        storageLocations.add(new StorageLocation("Regał - Półka 2"));
        storageLocations.add(new StorageLocation("Kuchnia - Szuflada A4"));
        productStorages = new ArrayList<>();
    }

    boolean isProductExists(String productName) {
        for (Product product : products) {
            if (product.name.equals(productName)){
                return true;
            }
        }
        return false;
    }

    boolean isProductInLocation(String productName, String location) {
        for (ProductStorage productStorage : productStorages) {
            if (productStorage.product.name.equals(productName) && productStorage.storageLocation.name.equals(location)) {
                return true;
            }
        }
        return false;
    }

    void addProduct (Product product) {
        if (!isProductExists(product.name)) {
            products.add(product);
        }
    }

    void addProductStorage (Product product, StorageLocation storageLocation) {
        if (!isProductInLocation(product.name, storageLocation.name)){
            productStorages.add(new ProductStorage(product, storageLocation, true));
        }
    }

    StorageLocation getStorageLocation (int storageLocation) {
        return storageLocations.get(storageLocation-1);
    }

    Product getProduct (int product) {
        return products.get(product-1);
    }

    int getProductCount() {
        return products.size();
    }

    void showProductsAndCategories() {
        System.out.println("Produkty w Twojej spiżarni");
        for (Product product : products) {
            System.out.println("Produkt " + product.name + " Kategoria " + product.category);
        }
    }

    void showProducts() {
        System.out.println("Produkty w Twojej spiżarni");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println((i + 1) + " - Produkt " + product.name);
        }
    }

    void showProductsWithLocations() {
        System.out.println("Produkty w Twojej spiżarni i ich lokalizacja:");
        for (ProductStorage productStorage : productStorages) {
            if (productStorage.available){
                System.out.println("Produkt " + productStorage.product.name + " Kategoria " +productStorage.product.category
                        + " Lokalizacja " + productStorage.storageLocation);
            }
        }
    }

    void showStorageLocations() {
        System.out.println("Dostępne miejsca:");
        for (StorageLocation storageLocation : storageLocations) {
            System.out.println(storageLocation);
        }
    }

    void markAsUnavailable (Product product, StorageLocation storageLocation) {
        productStorages.stream()
                .filter( ps -> ps.product.name.equals(product.name) && ps.storageLocation.name.equals(storageLocation.name))
                .findFirst()
                .ifPresent(ps -> ps.available = false);
    }

    public static void main (String[] args){
        Pantry pantry = new Pantry();
        Scanner scanner = new Scanner(System.in);
        String choose;

        do {
            System.out.println(""" 
                Wybierz 1 lub 2:
                1 - Dodaj kolejny produkt
                2 - Zużyj produkt 
                3 - Zakończ
                """);
            choose = scanner.nextLine();

            switch (choose) {
                case "1":
                    System.out.println("Dodaj nowy produkt");
                    String productName = scanner.nextLine();
                    System.out.println("Podaj kategorie produktu");
                    String categoryName = scanner.nextLine();
                    Product product = new Product(productName, new Category(categoryName));
                    pantry.addProduct(product);

                    pantry.showStorageLocations();
                    System.out.println("Wybierz numer lokalizacji:");
                    int storageLocation = Integer.parseInt(scanner.nextLine());
                    pantry.addProductStorage(product, pantry.getStorageLocation(storageLocation));
                    break;
                case "2":
                    System.out.println("Zużywam produkt - podaj produkt");
                    pantry.showProducts();
                    int productToConsume = Integer.parseInt(scanner.nextLine());

                    System.out.println("Zużywam produkt - podaj lokalizacje produktu");
                    pantry.showStorageLocations();
                    int storageLocationToConsume = Integer.parseInt(scanner.nextLine());

                    pantry.markAsUnavailable(pantry.getProduct(productToConsume), pantry.getStorageLocation(storageLocationToConsume));
                    System.out.println("Produkt został zużyty");
                    break;
                case "3":
                    System.out.println("Kończymy działanie programu");
                    break;

                default:
                    System.out.println("Nieznana opcja, spróbuj ponownie");

            }
        } while (!choose.equals("3"));

        System.out.println("Ilość produktów w Twojej spiżarni to " + pantry.getProductCount());
        pantry.showProductsAndCategories();
        pantry.showProductsWithLocations();
        }
}
