package errorists.controllers;

import java.io.IOException;
import java.time.LocalDate;

import errorists.models.AppModel;
import errorists.models.Inspection;
import errorists.models.Shipment;
import errorists.models.ShipmentStatus;
import errorists.models.Warehouse;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShipmentsViewController {

    private AppModel appModel;
    private AppController appController;

    @FXML
    private TableView<Shipment> shipmentsTableView;

    @FXML
    private TableColumn<Shipment, String> tableColumnShipmentID;

    @FXML
    private TableColumn<Shipment, String> tableColumnCurrentWarehouse;

    @FXML
    private TableColumn<Shipment, String> tableColumnStorageDuration;

    @FXML
    private TableColumn<Shipment, ShipmentStatus> tableColumnStatus;

    @FXML
    private TableColumn<Shipment, String> tableColumnInspectionStatus;

    @FXML
    private TableColumn<Shipment, LocalDate> tableColumnLastInspectionDate;

    @FXML
    private TableColumn<Shipment, Integer> tableColumnTotalJourneys;

    @FXML
    private TableColumn<Shipment, Boolean> tableColumnTransportationLoops;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private Label labelDeleteShipment;


    public void initialize() {
        tableColumnShipmentID.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableColumnCurrentWarehouse.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();

            String warehouseName = shipment.getCurrentWarehouse().getName();
            return new ReadOnlyObjectWrapper<>(warehouseName);

        });
        tableColumnStorageDuration.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();

            String duration = shipment.getStoredDuration();

            return new ReadOnlyObjectWrapper<>(duration);
        });

        // set the status of the shipment
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnStatus.setCellFactory(ComboBoxTableCell.forTableColumn(ShipmentStatus.values()));
        tableColumnStatus.setOnEditCommit(event -> {
            Shipment shipment = event.getRowValue();
            shipment.setStatus(event.getNewValue());
        });


        tableColumnStatus.setCellFactory(ComboBoxTableCell.forTableColumn(ShipmentStatus.values()));
        tableColumnStatus.setOnEditCommit(event -> {
            Shipment shipment = event.getRowValue();
            shipment.setStatus(event.getNewValue());
        });



        tableColumnInspectionStatus.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();

            Inspection lastInspection = shipment.getMostRecentInspection();
            if (lastInspection != null) {
                return new ReadOnlyObjectWrapper<>("Inspected");
            } else {
                return new ReadOnlyObjectWrapper<>("No inspection");
            }
        });

        tableColumnLastInspectionDate.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();

            Inspection lastInspection = shipment.getMostRecentInspection();
            if (lastInspection != null) {
                LocalDate lastInspectionDate = lastInspection.getInspectionDate();
                return new ReadOnlyObjectWrapper<>(lastInspectionDate);
            } else {
                return new ReadOnlyObjectWrapper<>(null);
            }
        });

        tableColumnTotalJourneys.setCellValueFactory(new PropertyValueFactory<>("totalJourneys"));

        tableColumnTransportationLoops.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();
            boolean hasLoops = shipment.hasTransportationLoops();
            return new ReadOnlyObjectWrapper<>(hasLoops);
        });

        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterShipmentsById(newValue);  // Filters the searchbar when typing
        });

        shipmentsTableView.setRowFactory(tv -> new TableRow<Shipment>() {
            @Override
            protected void updateItem(Shipment shipment, boolean empty) {
                super.updateItem(shipment, empty);
                if (shipment == null || empty) {
                    setStyle(""); // Reset style for empty rows
                } else if (shipment.hasTransportationLoops()) {
                    setStyle("-fx-background-color:rgba(187, 34, 34, 0.51);");
                } else {
                    setStyle(""); // Reset style for non-matching rows
                }
            }
        });
    }


    private void filterShipmentsById(String searchText) {
        ObservableList<Shipment> filteredList = FXCollections.observableArrayList();
    
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(appModel.getShipmentRegister().getShipments()); 
        } else {
            for (Shipment shipment : appModel.getShipmentRegister().getShipments()) {
                
                if (shipment.getId().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(shipment);
                }
            }
        }
    
        shipmentsTableView.setItems(filteredList);  // Updates the table view based on input
    }


    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        populateTableView();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    private void populateTableView() {
        shipmentsTableView.setItems(appModel.getShipmentRegister().getShipments());
    }

    @FXML
    public void handleButtonAddShipmentAction(ActionEvent event) throws IOException {
        System.out.println("Add Shipment button clicked");
        appController.loadView("ShipmentAddView");
    }

    @FXML
    public void handleButtonRemoveShipmentAction(ActionEvent event) {
        System.out.println("Delete Shipment button clicked");
        Shipment selectedShipment = shipmentsTableView.getSelectionModel().getSelectedItem();

        if (selectedShipment != null) {
            // Remove inspections of the shipment
            appModel.getInspectionRegister().removeInpsectionsByShipment(selectedShipment);

            // Remove the shipment from the warehouse
            Warehouse currentWarehouse = selectedShipment.getCurrentWarehouse();
            if (currentWarehouse != null) {
                currentWarehouse.removeShipment(selectedShipment);
            }

            // Remove the shipment from the shipment register
            appModel.getShipmentRegister().removeShipment(selectedShipment);
            populateTableView();
        } else {
            String response = "No shipment selected.";
            labelDeleteShipment.setText(response);
            labelDeleteShipment.setVisible(true);
            System.out.println("No shipment selected.");
        }
    }


}
