/**
 *
 * @author Matt DiPerna
 * The Javadoc files are included in the Javadocs folder
 */
package sample;
import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * @param primaryStage the primaryStage is set
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
       //setting main stage
        Parent root = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        root.setStyle("-fx-font-family: 'Times New Roman';");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {

        //Sample data
        InHouse inHouse = new InHouse(1,"Seat",20.99,5,1,10,1);
        InHouse inHouse2 = new InHouse(2,"Tires",40.99,3,1,8,2);
        OutSourced outSourced = new OutSourced(3,"Handlebars",50.99,7,1,15,"Bike Store");
        Product product = new Product(1,"Bike",200.99,3,1,3);
        Product product2 = new Product(2,"Tricycle",500.99,1,1,5);
        product.addAssociatedPart(inHouse);
        product.addAssociatedPart(inHouse2);
        product.addAssociatedPart(outSourced);
        product2.addAssociatedPart(inHouse);
        product2.addAssociatedPart(inHouse2);

        Inventory.addPart(inHouse);
        Inventory.addPart(inHouse2);
        Inventory.addPart(outSourced);
        Inventory.addProduct(product);
        Inventory.addProduct(product2);
        launch(args);
    }
}
