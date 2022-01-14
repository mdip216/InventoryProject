package Controller;
/**
 *
 * @author Matt DiPerna
 */

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
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
    private Button AddPartCancelBtn;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartIDTxt;
    /**
     * an element of the gui
     */
    @FXML
    private RadioButton addPartInHouseRadio;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartInvTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartMachineorCompTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartMaxTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartMinTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartNameTxt;
    /**
     * an element of the gui
     */
    @FXML
    private RadioButton addPartOutsrcRadio;
    /**
     * an element of the gui
     */
    @FXML
    private TextField addPartPriceTxt;
    /**
     * an element of the gui
     */
    @FXML
    private Button addPartSaveBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Label machineIDLbl;

    /**
     * @param event the event cancels the save and the stage is set back to main
     */

    @FXML
    void onActionCancelAdd(ActionEvent event) throws IOException {
        setStage("/View/Main.fxml",event);
    }
    /**
     * @param event the event changes the label to Inhouse
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        sendLabel();
    }

    /**
     * @param event the event changes the label to Outsource
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        sendLabel();
    }

    /**
     * @param event the event saves part in Inventory.allParts and sets the stage to main
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        //String needs to be converted with wrapper class
        int id =0;
        int maximum = 0;
        if(Inventory.getAllParts().isEmpty()){
            id =1;
        }else{
            for(Part i: Inventory.getAllParts()){
                if(i.getId()>maximum){
                    maximum = i.getId();
                }
            }
            id = ++maximum;
        }

        try{

            String name = addPartNameTxt.getText();
            int stock = Integer.parseInt(addPartInvTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
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
            if(addPartInHouseRadio.isSelected()){
                int machineId = Integer.parseInt(addPartMachineorCompTxt.getText());
                Inventory.addPart(new InHouse(id,name,price,stock,min, max,machineId));
            }else{
                String compName = addPartMachineorCompTxt.getText();
                Inventory.addPart(new OutSourced(id,name,price,stock,min, max,compName));
            }
            setStage("/View/Main.fxml",event);

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Incorrect Format");
            alert.setContentText("Please check your format");
            alert.getDialogPane().setStyle("-fx-font-family: 'Times New Roman';");
            alert.show();
        }
    }

    /**
     * changes the label based on which Label is selected
     */
    public void sendLabel(){
        if(addPartInHouseRadio.isSelected()){
            machineIDLbl.setText("Machine ID");
        }else{
            machineIDLbl.setText("Company Name");
        }
    }

    /**
     * sets the id text to disabled
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartIDTxt.setText("Auto Gen- Disabled");
        addPartIDTxt.setDisable(true);
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
