package errorists.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import errorists.models.AppModel;
import errorists.models.Shipment;
import errorists.models.ShipmentStatus;
import errorists.models.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

public class ShipmentMoveDialogController {

    private AppModel appModel;
    private Shipment shipment;

    @FXML
    private ComboBox<Warehouse> comboBoxNewWarehouse;

    @FXML
    private DatePicker datePickerShippingDate;

    @FXML
    private DatePicker datePickerExitDate;

    @FXML
    private Button buttonSendShipment;

    @FXML
    private Button buttonBack;

    @FXML
    private Label labelShipmentName;

    @FXML
    private Label labelMoveShipment;

    @FXML
    private ComboBox<ShipmentStatus> comboBoxStatus;

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;

        populateComboBox();
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setLabelShipmentName(Shipment shipment) {
        labelShipmentName.setText(shipment.getId());
    }

    @FXML
    public void handleButtonSendShipmentAction() {
        // Handle action when the send button is clicked
        Warehouse selectedWarehouse = comboBoxNewWarehouse.getValue();
        LocalDate shippingDate = datePickerShippingDate.getValue();
        LocalDate exitDate = datePickerExitDate.getValue();
        ShipmentStatus status = comboBoxStatus.getValue();

        if (selectedWarehouse != null && shippingDate != null && exitDate != null && status != null) {
            Warehouse newWarehouse = appModel.getWarehouseRegister().findWarehouseByName(selectedWarehouse.getName());
            if (newWarehouse != null) {
                if (exitDate.isBefore(shippingDate)) {
                    labelMoveShipment.setText("Exit date cannot be before shipping date.");
                    labelMoveShipment.setVisible(true);
                } else if (shipment.getCurrentWarehouse() == newWarehouse) {
                    labelMoveShipment.setText("Shipment is already in this warehouse.");
                    labelMoveShipment.setVisible(true);
                }else if (ChronoUnit.DAYS.between(shippingDate, exitDate) > 14) {
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Shipment will be stored for over 14 days. Proceed?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        shipment.moveToWarehouse(newWarehouse, shippingDate, exitDate, status);
                        // Close the dialog
                        Stage stage = (Stage) buttonSendShipment.getScene().getWindow();
                        stage.close();
                    } else {
                        alert.close();
                    }

                } else {
                    shipment.moveToWarehouse(newWarehouse, shippingDate, exitDate, status);
                    // Close the dialog
                    Stage stage = (Stage) buttonSendShipment.getScene().getWindow();
                    stage.close();
                }

            } else {
                // Handle case where the warehouse is not found
                labelMoveShipment.setText("Warehouse not found: " + selectedWarehouse.getName());
                labelMoveShipment.setVisible(true);
            }
        } else {
            // Handle case where input is invalid
            labelMoveShipment.setText("Invalid input. Please fill all fields.");
            labelMoveShipment.setVisible(true);
        }
    }

    @FXML
    public void handleButtonBackAction() {
        // Handle action when the back button is clicked
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

    public void initialize(){ 
        // Display warehouse names in the combobox
        comboBoxNewWarehouse.setCellFactory(param -> new ListCell<Warehouse>() {
            @Override
            protected void updateItem(Warehouse warehouse, boolean empty) {
                super.updateItem(warehouse, empty);

                if (empty || warehouse == null || warehouse.getName() == null) {
                    setText(null);
                } else {
                    setText(warehouse.getName());
                }
            }
        });

        
        comboBoxNewWarehouse.setButtonCell(new ListCell<Warehouse>() {
            @Override
            protected void updateItem(Warehouse warehouse, boolean empty) {
                super.updateItem(warehouse, empty);

                if (empty || warehouse == null || warehouse.getName() == null) {
                    setText(null);
                } else {
                    setText(warehouse.getName());
                }
            }
        });

        comboBoxStatus.getItems().addAll(ShipmentStatus.values());
    }

    private void populateComboBox() {
        comboBoxNewWarehouse.setItems(appModel.getWarehouseRegister().getWarehouses());
    }
}
