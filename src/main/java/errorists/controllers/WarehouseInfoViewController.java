package errorists.controllers;

import java.io.IOException;

import errorists.models.Inspection;
import errorists.models.InspectionResult;
import errorists.models.Shipment;
import errorists.models.ShipmentLog;
import errorists.models.ShipmentStatus;
import errorists.models.Warehouse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class WarehouseInfoViewController {

    private AppController appController;

    @FXML
    private Label labelWarehouseName;

    @FXML
    private Label labelWarehouseAddress;

    @FXML
    private Label labelWarehouseCapacity;

    @FXML
    private Label labelWarehouseStockLevel;

    @FXML
    private Label labelWarehouseLastInpseciton;

    @FXML
    private Label labelShipmentID;

    @FXML
    private Label labelShipmentTimeStored;

    @FXML
    private Label labelShipmentLastInspection;

    @FXML
    private Label labelShipmentTotalJourneys;

    @FXML
    private Label labelShipmentStatus;

    @FXML
    private Label labelShipmentTransportationLoops;

    @FXML
    private TableView<Shipment> tableViewShipments;

    @FXML
    private TableColumn<Shipment, String> tableColumnShipmentID;

    @FXML
    private TableView<Inspection> tableViewInspectionLog;

    @FXML
    private TableColumn<Inspection, String> tableColumnInspectionDate;

    @FXML
    private TableColumn<Inspection, InspectionResult> tableColumnInspectionResult;

    @FXML
    private TableColumn<Inspection, String> tableColumnInspectorName;

    @FXML
    private TableColumn<Inspection, String> tableColumnInspectionLocation;

    @FXML
    private TableView<ShipmentLog> tableViewShipmentLog;

    @FXML
    private TableColumn<ShipmentLog, String> tableColumnWarehouse;

    @FXML
    private TableColumn<ShipmentLog, String> tableColumnEntryDate;

    @FXML
    private TableColumn<ShipmentLog, String> tableColumnExitDate;

    @FXML
    private TableColumn<ShipmentLog, ShipmentStatus> tableColumnStatus;

    @FXML
    private Label labelResponseLog;

    @FXML
    private Label labelResponseInspection;

    @FXML
    private Label labelActionResponse;

    @FXML
    private ListView<String> listViewInspectors;

    @FXML
    public void initialize() {
        tableColumnShipmentID.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableViewShipments.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setShipmentInfo(newValue);
                tableViewInspectionLog.setItems(newValue.getInspectionLog());

                tableColumnInspectionDate.setCellValueFactory(new PropertyValueFactory<>("inspectionDate"));

                tableColumnInspectionResult.setCellValueFactory(new PropertyValueFactory<>("inspectionResult"));
                tableColumnInspectionResult.setCellFactory(ComboBoxTableCell.forTableColumn(InspectionResult.values()));
                tableColumnInspectionResult.setOnEditCommit(event -> {
                    Inspection inspection = event.getRowValue();
                    inspection.setInspectionResult(event.getNewValue());
                });

                tableColumnInspectorName.setCellValueFactory(new PropertyValueFactory<>("inspectorName"));

                tableColumnInspectionLocation.setCellValueFactory(cellData -> {
                    Inspection inspection = cellData.getValue();
                    return new SimpleStringProperty(inspection.getInspectionLocation().getName());
                });

                tableViewShipmentLog.setItems(newValue.getShipmentLog());

                tableColumnWarehouse.setCellValueFactory(cellData -> {
                    ShipmentLog log = cellData.getValue();
                    return new SimpleStringProperty(log.getWarehouse().getName());
                });

                tableColumnEntryDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
                tableColumnExitDate.setCellValueFactory(new PropertyValueFactory<>("exitDate"));
                
                tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("shipmentStatus"));
                tableColumnStatus.setCellFactory(ComboBoxTableCell.forTableColumn(ShipmentStatus.values()));
                tableColumnStatus.setOnEditCommit(event -> {
                    ShipmentLog shipmentLog = event.getRowValue();
                    shipmentLog.setShipmentStatus(event.getNewValue());
                });
            }
    });

    }

    public void setWarehouseInfo(Warehouse warehouse) {
         // Set basic warehouse details
    labelWarehouseName.setText(warehouse.getName());
    labelWarehouseAddress.setText(warehouse.getAddress());
    labelWarehouseCapacity.setText(String.valueOf(warehouse.getCapacity()));
    labelWarehouseStockLevel.setText(String.valueOf(warehouse.getCurrentStockLevel()));

    // Determine the most recent inspection using a for loop
    Inspection mostRecentInspection = null;
    for (Shipment shipment : warehouse.getShipments()) {
        for (Inspection inspection : shipment.getInspectionLog()) {
            if (mostRecentInspection == null || 
                inspection.getInspectionDate().isAfter(mostRecentInspection.getInspectionDate())) {
                mostRecentInspection = inspection;
            }
        }
    }

    // Set label for the most recent inspection
    if (mostRecentInspection != null) {
        labelWarehouseLastInpseciton.setText(mostRecentInspection.getInspectionDate().toString());
    } else {
        labelWarehouseLastInpseciton.setText("No inspections");
    }

    // Populate the list view with inspector names
    listViewInspectors.getItems().clear();
    ObservableList<Shipment> shipments = FXCollections.observableArrayList(warehouse.getShipments());
    if (shipments != null) {
        for (Shipment shipment : shipments) {
            for (Inspection inspection : shipment.getInspectionLog()) {
                listViewInspectors.getItems().add(inspection.getInspectorName());
            }
        }
    }

    // Populate the table view with shipments of the selected warehouse
    tableViewShipments.setItems(warehouse.getShipments());
}


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setShipmentInfo(Shipment shipment) {
        labelShipmentID.setText(shipment.getId());
        labelShipmentTimeStored.setText(shipment.getStoredDuration());
        labelShipmentLastInspection.setText(shipment.getMostRecentInspection() != null ? shipment.getMostRecentInspection().getInspectionDate().toString() : "No inspections");
        labelShipmentTotalJourneys.setText(String.valueOf(shipment.getTotalJourneys()));
        labelShipmentStatus.setText(shipment.getStatus().toString());
        labelShipmentTransportationLoops.setText(String.valueOf(shipment.hasTransportationLoops()));
    }

    public void handleMenuItemAddShipmentAction() {
        System.out.println("Add Shipments Menu Item Clicked");
    }

    public void handleMenuItemDeleteShipmentAction() {
        labelActionResponse.setText("");
        labelActionResponse.setVisible(false);
        System.out.println("Delete Shipments Menu Item Clicked");

        Shipment selectedShipment = tableViewShipments.getSelectionModel().getSelectedItem();
        if (selectedShipment != null) {
            try {
                // Remove shipment from Warehouse
                Warehouse currentWarehouse = selectedShipment.getCurrentWarehouse();
                if (currentWarehouse != null) {
                    currentWarehouse.removeShipment(selectedShipment);
                }

                // Remove shipment from ShipmentRegister
                if (appController != null && appController.getAppModel() != null) {
                    appController.getAppModel().getShipmentRegister().removeShipment(selectedShipment);
                }

                // Update the TableView
                tableViewShipments.getItems().remove(selectedShipment);

                System.out.println("Shipment deleted successfully.");
                labelActionResponse.setText("Shipment deleted successfully.");
            } catch (Exception e) {
                System.out.println("Error deleting shipment.");
                labelActionResponse.setText("Error deleting shipment.");
            }
        } else {
            String response = "No shipment selected.";
            labelActionResponse.setText(response);
            System.out.println(response);
        }

        labelActionResponse.setVisible(true);
    }

    public void handleButtonAddInspectionAction(ActionEvent event) {
        System.out.println("Add Inspection Button Clicked");
        String response = "No shipment selected.";
        labelResponseInspection.setText(response);
        labelResponseInspection.setVisible(true);

    }

    public void handleButtonDeleteInspectionAction(ActionEvent event) {
        System.out.println("Delete Inspection Button Clicked");
        String response = "No inspection selected";
        labelResponseInspection.setText(response);
        labelResponseInspection.setVisible(true);
    }

    public void handleButtonAddLogEntryAction(ActionEvent event) throws IOException {
        labelResponseLog.setText("");
        labelResponseLog.setVisible(false);
        System.out.println("Add Log Entry Button Clicked");
        Shipment selectedShipment = tableViewShipments.getSelectionModel().getSelectedItem();
        if (selectedShipment != null) {
            appController.showShipmentLogAddDialog(selectedShipment);
        } else {
            String response = "No shipment selected.";
            labelResponseLog.setText(response);
            labelResponseLog.setVisible(true);
        }
    }

    public void handleButtonDeleteLogEntryAction(ActionEvent event) {
        System.out.println("Delete Log Entry Button Clicked");
        try {
            ShipmentLog selectedLog = tableViewShipmentLog.getSelectionModel().getSelectedItem();
            if (selectedLog != null) {
                selectedLog.getShipment().removeShipmentLog(selectedLog);
            } else {
                String response = "No log selected.";
                labelResponseLog.setText(response);
                labelResponseLog.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println("Error deleting log entry");
        }

    }

    public void handleMenuItemMoveShipmentAction(ActionEvent event) {
        labelActionResponse.setText("");
        labelActionResponse.setVisible(false);
        System.out.println("Move Shipments Menu Item Clicked");
        try {
            Shipment selectedShipment = tableViewShipments.getSelectionModel().getSelectedItem();
            if (selectedShipment != null) {
                appController.showMoveShipmentDialog(selectedShipment);
            } else {
                System.out.println("No shipment selected.");
                String response = "No shipment selected";
                labelActionResponse.setText(response);
                labelActionResponse.setVisible(true);
            }
        } catch (IOException e) {
            System.out.println("Error loading ShipmentMoveView");
        }
    }

}
