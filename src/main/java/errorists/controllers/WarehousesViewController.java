package errorists.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import errorists.models.AppModel;
import errorists.models.Inspection;
import errorists.models.Region;
import errorists.models.Shipment;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class WarehousesViewController {

    private AppModel appModel;
    private AppController appController;

    @FXML
    private TableView<Warehouse> tableViewWarehouse;

    @FXML
    private TableColumn<Warehouse, String> tableColumnWarehouseName;

    @FXML
    private TableColumn<Warehouse, String> tableColumnWarehouseAddress;

    @FXML
    private TableColumn<Warehouse, Integer> tableColumnWarehouseCapacity;

    @FXML
    private TableColumn<Warehouse, Integer> tableColumnWarehouseCurrentStockLevel;

    @FXML
    private TableColumn<Warehouse, Region> tableColumnWarehouseRegion;

    @FXML
    private TableColumn<Warehouse, String> tableColumnWarehouseRemainingCapacity;

    @FXML
    private TableColumn<Warehouse, LocalDate> tableColumnWarehouseLastInspectionDate;

    @FXML
    private TableColumn<Warehouse, Boolean> tableColumnWarehouseNeedsAttention;

    @FXML
    private TableColumn<Warehouse, String> tableColumnWarehouseThroughput;

    @FXML
    private TextField textFieldSearchWarehouse;

    @FXML
    private Label labelResponseViewDetails;


    @FXML
    public void initialize() {
        // Set up the table columns
        tableColumnWarehouseName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Enable editing of the warehouse name
        tableColumnWarehouseName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnWarehouseName.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            warehouse.setName(event.getNewValue());
        });

        // Enable editing of the warehouse address
        tableColumnWarehouseAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnWarehouseAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnWarehouseAddress.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            warehouse.setAddress(event.getNewValue());
        });

        // Enable editing of the warehouse capacity
        tableColumnWarehouseCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tableColumnWarehouseCapacity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnWarehouseCapacity.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            warehouse.setCapacity(event.getNewValue());
        });

        tableColumnWarehouseCurrentStockLevel.setCellValueFactory(new PropertyValueFactory<>("currentStockLevel"));

        // Enable editing of the warehouse region
        tableColumnWarehouseRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        tableColumnWarehouseRegion.setCellFactory(ComboBoxTableCell.forTableColumn(Region.values()));
        tableColumnWarehouseRegion.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            warehouse.setRegion(event.getNewValue());
        });

        tableColumnWarehouseRemainingCapacity.setCellValueFactory(cellData -> {
            Warehouse warehouse = cellData.getValue();
            String remainingCapacity = warehouse.getRemainingCapacity();
            return new ReadOnlyObjectWrapper<>(remainingCapacity);
        });

        tableColumnWarehouseLastInspectionDate.setCellValueFactory(cellData -> {
            Warehouse warehouse = cellData.getValue();

            Inspection lastInspection = warehouse.getMostRecentInspection();
            if (lastInspection != null) {
                LocalDate lastInspectionDate = lastInspection.getInspectionDate();
                return new ReadOnlyObjectWrapper<>(lastInspectionDate);
            } else {
                return new ReadOnlyObjectWrapper<>(null);
            }
        });

        tableColumnWarehouseNeedsAttention.setCellValueFactory(cellData -> {
            Warehouse warehouse = cellData.getValue();

            for (Shipment shipment : warehouse.getShipments()) {
                long totalDays = ChronoUnit.DAYS.between(shipment.getEntryDate(), shipment.getExitDate());
                if (totalDays > 14) {
                    return new ReadOnlyObjectWrapper<>(true);
                }
            }
            return new ReadOnlyObjectWrapper<>(false);
        });

        textFieldSearchWarehouse.textProperty().addListener((observable, oldValue, newValue) -> {
            filterWarehouseByName(newValue);
        });

        tableColumnWarehouseThroughput.setCellValueFactory(cellData -> {
            Warehouse warehouse = cellData.getValue();
            String throughput = warehouse.calculateThroughput();
            return new ReadOnlyObjectWrapper<>(throughput);
        });

        tableViewWarehouse.setRowFactory(tv -> new TableRow<Warehouse>() {
            @Override
            protected void updateItem(Warehouse warehouse, boolean empty) {
                super.updateItem(warehouse, empty);
                if (warehouse == null || empty) {
                    setStyle(""); // Reset style for empty rows
                } else {
                    boolean needsAttention = false;
                    for (Shipment shipment : warehouse.getShipments()) {
                        long totalDays = ChronoUnit.DAYS.between(shipment.getEntryDate(), shipment.getExitDate());
                        if (totalDays > 14) {
                            needsAttention = true;
                        }
                    }
                    if (needsAttention) {
                        setStyle("-fx-background-color:rgba(187, 34, 34, 0.51);");
                    } else {
                        setStyle(""); // Reset style for non-matching rows
                    }
                }
            }
        });
    }

    // Filter the table view based on the search text
    private void filterWarehouseByName(String searchText) {
        ObservableList<Warehouse> filteredList = FXCollections.observableArrayList();
    
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(appModel.getWarehouseRegister().getWarehouses()); 
        } else {
            for (Warehouse warehouse : appModel.getWarehouseRegister().getWarehouses()) {
                
                if (warehouse.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(warehouse);
                }
            }
        }
    
        tableViewWarehouse.setItems(filteredList);  // Updates the table view based on input
    }

    @FXML
    public void handleButtonAddWarehouseAction(ActionEvent event) {
        System.out.println("Add Warehouse Button Clicked");
        try {
            appController.loadView("WarehouseAddView");
        } catch (Exception e) {
            labelResponseViewDetails.setText(e.getMessage());
        }
    }

    @FXML
    public void handleButtonDeleteWarehouseAction(ActionEvent event) {
        System.out.println("Delete Warehouse Button Clicked");
        Warehouse selectedWarehouse = tableViewWarehouse.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
    
            labelResponseViewDetails.setText("");
    
            // Remove all shipments associated with this warehouse
            ObservableList<Shipment> shipments = appModel.getShipmentRegister().getShipments();
            for (int i = shipments.size() - 1; i >= 0; i--) {
                Shipment shipment = shipments.get(i);
                if (shipment.getCurrentWarehouse().equals(selectedWarehouse)) {
                    // Remove inspections associated with the shipment
                    appModel.getInspectionRegister().removeInpsectionsByShipment(shipment);
    
                    // Remove the shipment itself
                    appModel.getShipmentRegister().removeShipment(shipment);
                }
            }
    
            // Remove the selected warehouse
            appModel.getWarehouseRegister().removeWarehouse(selectedWarehouse);
    
            // Update the table view
            populateTableView();
        } else {
            labelResponseViewDetails.setText("No Warehouse selected");
            System.out.println("No Warehouse selected");
        }
    }

    @FXML
    public void handleButtonViewDetailsAction(ActionEvent event) throws IOException {
        System.out.println("View Details Button Clicked");
        Warehouse selectedWarehouse = tableViewWarehouse.getSelectionModel().getSelectedItem();

        if (selectedWarehouse != null) {
            appController.loadWarehouseInfoView(selectedWarehouse);

        } else {
            String response = "No warehouse selected!";
            labelResponseViewDetails.setText(response);
            labelResponseViewDetails.setVisible(true);

        }
    }

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        populateTableView();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    private void populateTableView() {
        tableViewWarehouse.setItems(appModel.getWarehouseRegister().getWarehouses());
    }
}