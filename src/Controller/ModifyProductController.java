package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 *
 * @author Matt DiPerna
 */

public class ModifyProductController implements Initializable {
    /**
     * for setting the Stage
     */
    private Stage stage;
    /**
     * for setting the Scene
     */
    private Parent scene;
    /**
     * for storing Parts to be displayed
     */
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();
    /**
     * an element of the gui
     */
    @FXML
    private Button modProdAddBtn;

    /**
     * an element of the gui
     */
    @FXML
    private Button modProdCancelBtn;

    /**
     * an element of the gui
     */
    @FXML
    private TableView<Part> modProdAsscPartsTable;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdIDTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdInvTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdMaxTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdMinTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdNameTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> modProdPartIdCol;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdPartInvCol;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdPartNameCol;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdPartPriceCol;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdPriceTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdProdIDCol;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdProdInvCol;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdProdName;

    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<?, ?> modProdProdPriceCol;

    /**
     * an element of the gui
     */
    @FXML
    private Button modProdRemoveAssctdPartBtn;

    /**
     * an element of the gui
     */
    @FXML
    private Button modProdSaveBtn;

    /**
     * an element of the gui
     */
    @FXML
    private TextField modProdSearchTxt;

    /**
     * an element of the gui
     */
    @FXML
    private TableView<Part> modProdPartsTable;

    /**
     * @param event the sends the selected part to the sendPart() method
     *
     */
    @FXML
    void OnActionAdd(ActionEvent event) {
        if(modProdPartsTable.getSelectionModel().getSelectedItem()!=null) {
            sendPart(modProdPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * @param part adds the part to the addedParts ObservableList and then sets the table to include it
     */
    public void sendPart(Part part){

        addedParts.add(part);
        //we have to make sure these parts get added to the product's observable list on save

        modProdAsscPartsTable.setItems(addedParts);
        modProdProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /**
     * @param event removes the part selected
     *
     */
    @FXML
    void OnActionRemoveAsscPart(ActionEvent event) {
        //we need to know which is selected
        if (modProdAsscPartsTable.getSelectionModel().getSelectedItem() != null) {
            //this is the part id
            int partId = modProdAsscPartsTable.getSelectionModel().getSelectedItem().getId();
            for (Part i : addedParts) {
                if (i.getId() == partId) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Deletion");
                    alert.setContentText("Are you sure you wish to delete the selected part?");
                    alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");

                    //we need to check if they press cancel
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get()==ButtonType.CANCEL){
                        break;
                    }
                    addedParts.remove(i);
                    return;
                }
            }
        }
    }

    /**
     * @param event sets the stage back to main
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        setStage("/View/Main.fxml",event);
    }


    /**
     * @param event saves the product to Inventory.allProducts()
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try{
            int id = Integer.parseInt(modProdIDTxt.getText());
            String name = modProdNameTxt.getText();
            int stock = Integer.parseInt(modProdInvTxt.getText());
            double price = Double.parseDouble(modProdPriceTxt.getText());
            int min = Integer.parseInt(modProdMinTxt.getText());
            int max = Integer.parseInt(modProdMaxTxt.getText());
            if(min>max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Min Max");
                alert.setContentText("Minimum cannot be more than than maximum!");
                alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
                alert.show();
                return;
            }if(stock>max||stock<min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Min Max");
                alert.setContentText("Inventory amount not possible!");
                alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
                alert.show();
                return;
            }


                for(Product i: Inventory.getAllProducts()){

                    if(i.getId()==id){
                        Inventory.getAllProducts().remove(i);

                        Product prod = new Product(id,name,price,stock,min,max);
                        for(Part y: addedParts){
                            prod.addAssociatedPart(y);
                        }
                        Inventory.addProduct(prod);
                        break;
                    }
                }


            setStage("/View/Main.fxml",event);
        }catch(NumberFormatException | IOException e){

        }

    }
    /**
     * @param event saves the product to Inventory.allProducts()
     */
    @FXML
    void OnActionSearchPart(ActionEvent event) {
        String s = modProdSearchTxt.getText();
        if(s.isEmpty()){
            modProdPartsTable.setItems(Inventory.getAllParts());
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
        modProdPartsTable.setItems(searched);

    }
    /**
     * @param name searches for parts that contain name and returns them to the table
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
     * @param id searches for parts that contain id and returns them to the table
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

    /**
     * @param product saves product and displays it
     */
    public void sendProduct(Product product){

        modProdIDTxt.setText(String.valueOf(product.getId()));
        modProdNameTxt.setText(product.getName());
        modProdInvTxt.setText(String.valueOf(product.getStock()));
        modProdMinTxt.setText(String.valueOf(product.getMin()));
        modProdMaxTxt.setText(String.valueOf(product.getMax()));
        modProdPriceTxt.setText(String.valueOf(product.getPrice()));

        for(Part i: product.getAllAssociatedParts()) {
            addedParts.add(i);

        }
        modProdAsscPartsTable.setItems(addedParts);
        modProdProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdIDTxt.setDisable(true);

        modProdPartsTable.setItems(Inventory.getAllParts());
        //automatically calls get methods

        //parts
        modProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

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
