package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

public class AddProductController implements Initializable {
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
    private TableView<Part> addProdasscPartsTbl;
    /**
     * an element of the gui
     */
    @FXML
    private Button addProdAddBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button addProdCancelBtn;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdIDTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdInvTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdMaxTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdMinTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdNameTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> addProdPartIDCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> addProdPartInvCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, String> addProdPartNameCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Double> addProdPartPriceCol;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdPriceTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> addProdProdIDCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Integer> addProdProdInvCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, String> addProdProdNameCol;
    /**
     * an element of the gui
     */
    @FXML
    private TableColumn<Part, Double> addProdProdPriceCol;
    /**
     * an element of the gui
     */
    @FXML
    private Button addProdRemoveAsscPartBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Button addProdSaveBtn;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addProdSearchTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TableView<Part> addProdPartsTable;

    /**
     * @param event searches the searched ObservableList for the string passed on the event
     */

    @FXML
    void onActionSearchPart(ActionEvent event) {
        String s = addProdSearchTxt.getText();
        addProdPartsTable.setItems(Inventory.getAllParts());
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
        addProdPartsTable.setItems(searched);

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
     * @param event adds the Product to the main table and saves it in Inventory.allProducts
     */
    @FXML
    void onActionAddProd(ActionEvent event) {


        if(addProdPartsTable.getSelectionModel().getSelectedItem()!=null) {
            sendPart(addProdPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * @param event sets stage back to main
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        setStage("/View/Main.fxml",event);
    }

    /**
     * @param event removes the selected part from the addedParts ObservableList
     */
    @FXML
    void onActionRemoveAsscPart(ActionEvent event) {
        //we need to know which is selected
        if (addProdasscPartsTbl.getSelectionModel().getSelectedItem() != null) {
            int id = addProdasscPartsTbl.getSelectionModel().getSelectedItem().getId();

            for (Part i : addedParts) {
                if (i.getId() == id) {
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
     * @param event the event saves part in Inventory.allProducts and sets the stage to main
     *
     */

    @FXML
    void onActionSave(ActionEvent event) {
        int id =0;
        int maximum = 0;
        if(Inventory.getAllProducts().isEmpty()){
            id =1;
        }else{
            for(Product i: Inventory.getAllProducts()){
                if(i.getId()>maximum){
                    maximum = i.getId();
                }
            }
            id = ++maximum;
        }
        try{
            String name = addProdNameTxt.getText();
            int stock = Integer.parseInt(addProdInvTxt.getText());
            double price = Double.parseDouble(addProdPriceTxt.getText());
            int min = Integer.parseInt(addProdMinTxt.getText());
            int max = Integer.parseInt(addProdMaxTxt.getText());
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

            Product prod = new Product(id,name,price,stock,min,max);

            //we need to also add the associated parts list
            for(Part i: addedParts){
                prod.addAssociatedPart(i);
            }
            Inventory.addProduct(prod);
            setStage("/View/Main.fxml",event);
        }catch(NumberFormatException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Incorrect Format");
            alert.setContentText("Please check your format");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProdIDTxt.setText("Auto Gen- Disabled");
        addProdIDTxt.setDisable(true);


        addProdPartsTable.setItems(Inventory.getAllParts());
        //automatically calls get methods

        //parts
        addProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * @param part saves the selected part to the addProdasscPartsTbl TableView
     */
    public void sendPart(Part part){

        addedParts.add(part);
        addProdasscPartsTbl.setItems(addedParts);
        addProdProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

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
