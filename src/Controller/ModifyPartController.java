package Controller;

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
/**
 *
 * @author Matt DiPerna
 */

public class ModifyPartController implements Initializable {
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
    private Button modPartCancelBtn;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartIDTxt;
    /**
     * an element of the gui
     */
    @FXML
    private RadioButton modPartInHouseRadio;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartInvTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartMachineIDTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartMaxTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartMinTxt;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartNameTxt;
    /**
     * an element of the gui
     */
    @FXML
    private RadioButton modPartOutsrcRadio;
    /**
     * an element of the gui
     */
    @FXML
    private TextField modPartPriceTxt;
    /**
     * an element of the gui
     */
    @FXML
    private Button modPartSaveBtn;
    /**
     * an element of the gui
     */
    @FXML
    private Label machineorCompLbl;
    @FXML

    /**
     * @param event the event sets the scene back to main
     */
    void OnActionCancel(ActionEvent event) throws IOException {
        setStage("/View/Main.fxml",event);
    }

    /**
     * @param event the event saves the part to Inventory.allParts
     */
    @FXML
    void OnActionSavePart(ActionEvent event) throws IOException {
        try{
            int id = Integer.parseInt(modPartIDTxt.getText());
            String name = modPartNameTxt.getText();
            int stock = Integer.parseInt(modPartInvTxt.getText());
            double price = Double.parseDouble(modPartPriceTxt.getText());
            int min = Integer.parseInt(modPartMinTxt.getText());
            int max = Integer.parseInt(modPartMaxTxt.getText());
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
            if(modPartInHouseRadio.isSelected()){
                int machineId = Integer.parseInt(modPartMachineIDTxt.getText());
                // we need to match to the id and save the changes with the set methods
                for(Part i: Inventory.getAllParts()){

                    if(i.getId()==id){
                        Inventory.getAllParts().remove(i);
                        Inventory.addPart(new InHouse(id,name,price,stock,min,max,machineId));
                        break;
                    }
                }

            }else{
                String compName = modPartMachineIDTxt.getText();
                // we need to match to the id and save the changes with the set methods
                for(Part i: Inventory.getAllParts()){

                    if(i.getId()==id){
                        Inventory.getAllParts().remove(i);
                        Inventory.addPart(new OutSourced(id,name,price,stock,min,max,compName));
                        break;
                    }
                }

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
     * @param event the event switches the label to In-House
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        sendLabel();
    }

    /**
     * @param event the event switches the label to Outsourced
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        sendLabel();
    }

    /**
     * sets the modPartIDTxt to disabled
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modPartIDTxt.setDisable(true);

    }

    /**
     * switches the label between Machine ID and Company Name
     */
    public void sendLabel(){
        if(modPartInHouseRadio.isSelected()){
            machineorCompLbl.setText("Machine ID");
        }else{
            machineorCompLbl.setText("Company Name");
        }
    }
    /**
     * @param part switches the part between In-House and OutSourced
     */
    public void sendPart(Part part){
        modPartIDTxt.setText(String.valueOf(part.getId()));
        modPartNameTxt.setText(part.getName());
        modPartInvTxt.setText(String.valueOf(part.getStock()));
        modPartMinTxt.setText(String.valueOf(part.getMin()));
        modPartMaxTxt.setText(String.valueOf(part.getMax()));
        modPartPriceTxt.setText(String.valueOf(part.getPrice()));

        //need to get subclass methods
        if(part instanceof InHouse){
            modPartMachineIDTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }else{
            modPartMachineIDTxt.setText(((OutSourced) part).getCompanyName());
            modPartOutsrcRadio.setSelected(true);
            machineorCompLbl.setText("Company Name");
        }

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
