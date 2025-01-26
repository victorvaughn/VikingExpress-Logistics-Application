package errorists.controllers;

import java.io.IOException;

import errorists.models.AppModel;
import errorists.models.Region;
import errorists.models.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;  
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WarehouseAddViewController {

    private AppController appController;
    private AppModel appModel;

    @FXML
    private ComboBox<Region> comboboxWarehouseRegion;

    @FXML
    private TextField textfieldWarehouseAddress;

    @FXML
    private TextField textfieldWarehouseCapacity;

    @FXML
    private TextField textfieldWarehouseName;

    @FXML
    private Label labelInvalid;

    public void populateComboBox() {
        comboboxWarehouseRegion.getItems().addAll(Region.values());
    }

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        populateComboBox();
    }

    @FXML
    public void handleButtonAddWarehouseAction(ActionEvent event) throws IOException {
        Region selectedRegion = comboboxWarehouseRegion.getValue();
        String address = textfieldWarehouseAddress.getText();
        String capacityText = textfieldWarehouseCapacity.getText();
        String name = textfieldWarehouseName.getText();

    
        // Initialize variables
        String errorMessage = null;
        int capacity = 0;
    
        // Initialize status variable
        int status = 0;
    
        // Check for invalid input
        if (selectedRegion == null || address.trim().isEmpty() || name.trim().isEmpty() || capacityText.trim().isEmpty()) {
            status = 1;  // Empty inputs

        } else {
            // Check capacity
            try {
                capacity = Integer.parseInt(capacityText);
                if (capacity <= 0) {
                    status = 2;  // Invalid capacity
                } else {
                    status = 3;  // Input is valid
                }
            } catch (NumberFormatException e) {
                status = 2;  // Invalid capacity
            }
        }
    
        // Switch cases for different status
        switch (status) {
            case 1:  // Empty inputs
                errorMessage = "All fields must be filled in.";
                break;
    
            case 2:  // Invalid capacity
                if (capacity <= 0) {
                    errorMessage = "Capacity must be greater than 0.";
                } else {
                    errorMessage = "Capacity must be a valid number.";
                }
                break;
    
            case 3:  // Input is valid
                Warehouse newWarehouse = new Warehouse(name, address, capacity, selectedRegion);
                appModel.getWarehouseRegister().addWarehouse(newWarehouse);
                appController.loadView("WarehousesView");
                return; 
        }
    
        // Display error message
        if (errorMessage != null) {
            labelInvalid.setText(errorMessage);
            labelInvalid.setVisible(true);  
            System.out.println(errorMessage);
        }
    }


    public void handleButtonBackAction() throws IOException {
        // Go back to the previous view
        appController.loadView("WarehousesView");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

}
