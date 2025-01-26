package errorists.controllers;

import java.time.LocalDate;

import errorists.models.AppModel;
import errorists.models.Shipment;
import errorists.models.ShipmentLog;
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

public class ShipmentLogAddDialogController {

    private AppModel appModel;
    private Shipment shipment;

    @FXML
    private ComboBox<Warehouse> comboBoxWarehouse;

    @FXML
    private DatePicker datePickerEntryDate;

    @FXML
    private DatePicker datePickerExitDate;

    @FXML
    private Button buttonAddShipmentLog;

    @FXML
    private Button buttonBack;

    @FXML
    private Label labelShipmentLog;

    @FXML
    private ComboBox<ShipmentStatus> comboBoxStatus;

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        populateComboBox();
    }

    @FXML
    public void handleButtonAddShipmentLogAction() {
        Warehouse selectedWarehouse = comboBoxWarehouse.getValue();
        LocalDate entryDate = datePickerEntryDate.getValue();
        LocalDate exitDate = datePickerExitDate.getValue();
        ShipmentStatus status = comboBoxStatus.getValue();

        if (selectedWarehouse != null && entryDate != null && exitDate != null && status != null) {
            try {
                if (shipment.getShipmentLog().isEmpty()) {
                    shipment.addShipmentLog(selectedWarehouse, entryDate, exitDate, status);
                    // Close the dialog
                    Stage stage = (Stage) buttonAddShipmentLog.getScene().getWindow();
                    stage.close();
                } else {
                    for (ShipmentLog log : shipment.getShipmentLog()) {
                        if (log.getWarehouse() == selectedWarehouse) {
                            Alert alert = new Alert(AlertType.CONFIRMATION, "This warehouse has visited this location. Continue?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.NO) {
                                alert.close();
                            } else {
                                shipment.addShipmentLog(selectedWarehouse, entryDate, exitDate, status);
                                // Close the dialog
                                Stage stage = (Stage) buttonAddShipmentLog.getScene().getWindow();
                                stage.close();
                            }

                        } else {
                            shipment.addShipmentLog(selectedWarehouse, entryDate, exitDate, status);
                            // Close the dialog
                            Stage stage = (Stage) buttonAddShipmentLog.getScene().getWindow();
                            stage.close();
                        }
                    }

                }

            } catch (Exception e) {
                labelShipmentLog.setText(e.getMessage());
                labelShipmentLog.setVisible(true);

            }

        } else {
            // Handle case where input is invalid
            labelShipmentLog.setText("Invalid input. Please fill all fields.");
            labelShipmentLog.setVisible(true);

        }
    }

    @FXML
    public void handleButtonBackAction() {
        // Handle action when the back button is clicked
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

    private void populateComboBox() {
        comboBoxWarehouse.setItems(appModel.getWarehouseRegister().getWarehouses());
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

        comboBoxStatus.getItems().addAll(ShipmentStatus.values());
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
