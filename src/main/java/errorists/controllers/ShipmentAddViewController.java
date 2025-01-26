package errorists.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import errorists.models.AppModel;
import errorists.models.Shipment;
import errorists.models.ShipmentStatus;
import errorists.models.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class ShipmentAddViewController {

    private AppModel appModel;
    private AppController appController;

    @FXML
    private ComboBox<Warehouse> comboBoxWarehouse;

    @FXML
    private DatePicker datePickerEntryDate;

    @FXML
    private DatePicker datePickerExitDate;

    @FXML
    private ComboBox<ShipmentStatus> comboBoxStatus;

    @FXML
    private Label labelResponseViewDetails;

    public void initialize() {
        comboBoxWarehouse.setCellFactory(param -> new ListCell<Warehouse>() {
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

        comboBoxWarehouse.setButtonCell(new ListCell<Warehouse>() {
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
    }

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        populateComboBoxes();
    }

    public void populateComboBoxes() {
        comboBoxWarehouse.setItems(appModel.getWarehouseRegister().getWarehouses());

        comboBoxStatus.getItems().addAll(ShipmentStatus.values());
    }

    @FXML
    public void handleButtonShipmentAddAction() throws IOException {
        Warehouse selectedWarehouse = comboBoxWarehouse.getValue();
        LocalDate entryDate = datePickerEntryDate.getValue();
        LocalDate exitDate = datePickerExitDate.getValue();
        ShipmentStatus status = comboBoxStatus.getValue();

        if (selectedWarehouse != null && entryDate != null && exitDate != null && status != null) {
            try {
                Shipment newShipment = new Shipment(selectedWarehouse, entryDate, exitDate, status);
                if (ChronoUnit.DAYS.between(newShipment.getEntryDate(), newShipment.getExitDate()) > 14) {
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to store this shipment for 14+ days?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        selectedWarehouse.addShipment(newShipment);
                        appModel.getShipmentRegister().addShipment(newShipment);
                        // Go back to the previous view
                        appController.loadView("ShipmentsView");
                    } else {
                        alert.close();

                    }
                }
                else {
                    selectedWarehouse.addShipment(newShipment);
                    appModel.getShipmentRegister().addShipment(newShipment);
                    // Go back to the previous view
                    appController.loadView("ShipmentsView");
                }

            } catch (Exception e) {
                String response = e.getMessage();
                labelResponseViewDetails.setText(response);
                labelResponseViewDetails.setVisible(true);
            }

        } else {
            // Handle case where input is invalid

            String response = "Invalid input. Please fill all fields.";

            labelResponseViewDetails.setText(response);
            labelResponseViewDetails.setVisible(true);
        }
    }

    @FXML
    public void handleButtonShipmentBackAction() throws IOException {
        // Handle action when the back button is clicked
        appController.loadView("ShipmentsView");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
