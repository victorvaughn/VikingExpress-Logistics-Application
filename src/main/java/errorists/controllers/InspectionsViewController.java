package errorists.controllers;

import java.io.IOException;
import java.time.LocalDate;

import errorists.models.AppModel;
import errorists.models.Inspection;
import errorists.models.InspectionResult;
import errorists.models.Shipment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class InspectionsViewController {
    private AppModel appModel;
    private AppController appController;

    @FXML
    private TableView<Inspection> inspectionsTableView;

    @FXML
    private TableColumn<Inspection, String> tableColumnInspectorName;

    @FXML
    private TableColumn<Inspection, String> tableColumnShipmentID;

    @FXML
    private TableColumn<Inspection, LocalDate> tableColumnInspectionDate;

    @FXML
    private TableColumn<Inspection, InspectionResult> tableColumnInspectionResult;

    @FXML
    private TextField textFieldFilter;

    @FXML
    private Label labelDeleteAction;

    private ObservableList<Inspection> inspectionsList;

    public void initialize() {
        // Enable editable inspector name
        tableColumnInspectorName.setCellValueFactory(new PropertyValueFactory<>("inspectorName"));
        tableColumnInspectorName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnInspectorName.setOnEditCommit(event -> {
            Inspection inspection = event.getRowValue();
            inspection.setInpsectorName(event.getNewValue());
        });
        

        tableColumnShipmentID.setCellValueFactory(cellData -> {
            Inspection inspection = cellData.getValue();
            String shipmentID = inspection.getShipment().getId();
            return new ReadOnlyObjectWrapper<>(shipmentID);
        });


        textFieldFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filterShipmentsById(newValue); 
        });
    

        tableColumnInspectionDate.setCellValueFactory(new PropertyValueFactory<>("inspectionDate"));
        
        // Enable editable inspection result
        tableColumnInspectionResult.setCellValueFactory(new PropertyValueFactory<>("inspectionResult"));
        tableColumnInspectionResult.setCellFactory(ComboBoxTableCell.forTableColumn(InspectionResult.values()));
        tableColumnInspectionResult.setOnEditCommit(event -> {
            Inspection inspection = event.getRowValue();
            inspection.setInspectionResult(event.getNewValue());
        });
    }

    // Filter inspection by shipment ID
    private void filterShipmentsById(String searchText) {
        if (inspectionsList == null) {
            return; 
        }
    
        FilteredList<Inspection> filteredList = new FilteredList<>(inspectionsList, inspection -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // If searchtext is empty, show all
            }
            Shipment shipment = inspection.getShipment();
            return shipment != null && shipment.getId().toLowerCase().contains(searchText.toLowerCase());
        });
    
        inspectionsTableView.setItems(filteredList); //Updates the tableview based on input
    }
    
    
    @FXML
    public void handleButtonAddInspectionAction (ActionEvent event) throws IOException {
        System.out.println("Add Inspection Menu Item Clicked");
        appController.loadView("InspectionAddView");
    }

    @FXML
    public void handleButtonDeleteInspectionAction (ActionEvent event) {
        System.out.println("Delete Inspection Menu Item Clicked");
        Inspection selectedInspection = inspectionsTableView.getSelectionModel().getSelectedItem();
        labelDeleteAction.setText("");
        labelDeleteAction.setVisible(false);

        if (selectedInspection != null) {
            Shipment currentShipment = selectedInspection.getShipment();
            currentShipment.removeInspection(selectedInspection);
            appModel.getInspectionRegister().removeInspection(selectedInspection);

            populateTableView();
        } else {
            labelDeleteAction.setText("No inspection selected");
            labelDeleteAction.setVisible(true);
        }

    }

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        inspectionsList = FXCollections.observableArrayList(appModel.getInspectionRegister().getInspections());
        populateTableView();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    private void populateTableView() {
        inspectionsTableView.setItems(appModel.getInspectionRegister().getInspections());
    }
}