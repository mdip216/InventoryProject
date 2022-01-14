package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Matt DiPerna
 */
public class Inventory {
    /**
     * ObservableList for storing parts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * ObservableList for storing products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param part the part is added to the allParts ObservableList
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * @return the parts in allParts are returned
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @param product the product is added to the allProducts ObservableList
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * @return the products in allProducts are returned
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
