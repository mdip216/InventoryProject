package Controller;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 *
 * @author Matt DiPerna
 */

public class MainMenuController implements Initializable {
    /**
     * for setting the Stage
     */
    private Stage stage;
    /**
     * for setting the Scene
     */
    private Parent scene;

    /**
     * an element of the gui
     */
    @FXML
    private TableView<Part> mainPartsTableView;
    /**
     * an element of the gui
     */
    @FXML
    private TableView<Product> mainProductsTableView;
    /**
     * an element of the gui
     */
    @FXML
    private Button MainProductsDeleteBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button mainExitBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button mainPartsAddBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button mainPartsDeleteBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button mainPartsModifyBtn;
    /**
     * an element of the gui
     */
    @FXML
    private TextField mainPartsTxt;
    /**
     * an element of the gui
     */
    @FXML
    private Button mainProductsAddBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button mainProductsModifyBtn;
    /**
     * an element of the gui
     */
    @FXML
    private TextField mainProductsTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> partsInvCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, String> partsNameCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> partsIDTableCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Product, Integer> prodIDCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Product, Integer> prodInvCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Product, String> prodNameCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Product, Double> prodPriceCol;


    /**
     * @param event the event sets the scene to Add_product.fxml
     */
    @FXML
    void OnActionAddProd(ActionEvent event) throws IOException {
        setStage("/View/Add_Product.fxml",event);
    }

    /**
     * @param event the event sets the scene to Add_part.fxml
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Add_Part.fxml"));
        loader.load();

        AddPartController controller = loader.getController();
        controller.sendLabel();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        scene.setStyle("-fx-font-family: 'Times New Roman';");
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * @param event the event removes the part from Inventory.allParts
     * this could be improved if it only deleted parts that were not part of a product
     */
    @FXML
    void onActionDeletePart(ActionEvent event) throws IOException {

        try{

            int id = mainPartsTableView.getSelectionModel().getSelectedItem().getId();
            for(Part i: Inventory.getAllParts()){
                if(i.getId()==id){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Deletion");
                    alert.setContentText("Are you sure you wish to delete the selected part?");
                    alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");

                    //we need to check if they press cancel
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get()==ButtonType.CANCEL){
                        break;
                    }

                    Inventory.getAllParts().remove(i);
                    break;
                }
            }


            setStage("/View/Main.fxml",event);
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Part Selected");
            alert.setContentText("Please select the part you wish to delete");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();
        }

    }

    /**
     * @param event the event removes the product from Inventory.allProducts
     */
    @FXML
    void onActionDeleteProd(ActionEvent event) throws IOException {
        try{
            int id = mainProductsTableView.getSelectionModel().getSelectedItem().getId();
            for(Product i: Inventory.getAllProducts()){
                if(i.getId()==id){
                    if(i.getAllAssociatedParts().size()>0){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Cannot Delete");
                        alert.setContentText("This product cannot be deleted while it still has associated parts!");
                        alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
                        alert.show();
                        return;
                    }

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Deletion");
                    alert.setContentText("Are you sure you wish to delete the selected part?");
                    alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");

                    //we need to check if they press cancel
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get()==ButtonType.CANCEL){
                        break;
                    }
                    Inventory.getAllProducts().remove(i);
                    break;
                }
            }

            setStage("/View/Main.fxml",event);
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Product Selected");
            alert.setContentText("Please select the product you wish to delete");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();
        }


    }

    /**
     * @param event the event exits the application
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * @param event the event changes the scene to Modify_part and sends the selected part
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Modify_Part.fxml"));
            loader.load();
            ModifyPartController controller = loader.getController();
            controller.sendLabel();

            //read selected tableview item
            controller.sendPart(mainPartsTableView.getSelectionModel().getSelectedItem());


            //setting stage
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            scene.setStyle("-fx-font-family: 'Times New Roman';");
            stage.setScene(new Scene(scene));
            stage.show();

        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Part Selected");
            alert.setContentText("Please select the part you wish to modify");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();
        }


    }

    /**
     * @param event the event changes the scene to Modify_Product and sends the selected product
     */
    @FXML
    void onActionModifyProd(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Modify_Product.fxml"));
            loader.load();
            ModifyProductController controller = loader.getController();


            //read selected tableview item
            controller.sendProduct(mainProductsTableView.getSelectionModel().getSelectedItem());



            //setting stage
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            scene.setStyle("-fx-font-family: 'Times New Roman';");
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Product Selected");
            alert.setContentText("Please select the product you wish to modify");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();
        }


    }

    /**
     * @param event the event searches for the string or id in our product's names or id
     */
    @FXML
    void onActionSearchProd(ActionEvent event) {
        String s = mainProductsTxt.getText();

        if(s.isEmpty()){
            mainProductsTableView.setItems(Inventory.getAllProducts());
        }
        ObservableList<Product> searched = filterProd(s.toLowerCase());
        if(searched.isEmpty()){
            try{
                int id = Integer.parseInt(s);
                searched = filterProdID(id);
            }catch(NumberFormatException e){

            }
        }
        if(searched.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Parts Found");
            alert.setContentText("No parts found for your search");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();

        }
        mainProductsTableView.setItems(searched);
    }

    /**
     * @param name searches for the string in our product's names
     */
    public ObservableList<Product> filterProd(String name){
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for(Product i: Inventory.getAllProducts()){
            if(i.getName().toLowerCase().contains(name)){
                filteredProducts.add(i);
            }
        }
        if(filteredProducts.isEmpty()){

        }
        return filteredProducts;
    }

    /**
     * @param id searches for the id in our product's id
     */
    public ObservableList<Product> filterProdID(int id){
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for(Product i: Inventory.getAllProducts()){
            if(i.getId()==id){
                filteredProducts.add(i);
            }
        }
        if(filteredProducts.isEmpty()){

        }
        return filteredProducts;
    }


    /**
     * @param event searches the parts for a part that contains the string or id passed
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {

        String s = mainPartsTxt.getText();

        if(s.isEmpty()){
            mainPartsTableView.setItems(Inventory.getAllParts());
        }
        ObservableList<Part> searched = filter(s.toLowerCase());
        if(searched.isEmpty()){
            try{
                int id = Integer.parseInt(s);
                searched = filterID(id);
            }catch(NumberFormatException e){

            }
        }
        if(searched.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Parts Found");
            alert.setContentText("No parts found for your search");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();

        }
        mainPartsTableView.setItems(searched);
    }

    /**
     * @param name searches for the name in our part's names
     */
    public ObservableList<Part> filter(String name){
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for(Part i: Inventory.getAllParts()){
            if(i.getName().toLowerCase().contains(name)){
                filteredParts.add(i);
            }
        }
        if(filteredParts.isEmpty()){

        }
        return filteredParts;
    }
    /**
     * @param id searches for the id in our part's ids
     */
    public ObservableList<Part> filterID(int id){
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for(Part i: Inventory.getAllParts()){
            if(i.getId()==id){
                filteredParts.add(i);
            }
        }
        if(filteredParts.isEmpty()){

        }
        return filteredParts;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartsTableView.setItems(Inventory.getAllParts());
        //automatically calls get methods

        //parts
        partsIDTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //products
        mainProductsTableView.setItems(Inventory.getAllProducts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    }


    /**
     * @param s takes a string s and on an event sets the scene to the path passed a s
     */
    void setStage(String s,ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(s)));
        scene.setStyle("-fx-font-family: 'Times New Roman';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
