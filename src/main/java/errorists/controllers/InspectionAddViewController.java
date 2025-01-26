package errorists.controllers;

import java.io.IOException;
import java.time.LocalDate;

import errorists.models.AppModel;
import errorists.models.Inspection;
import errorists.models.InspectionResult;
import errorists.models.Shipment;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class InspectionAddViewController {
    private AppModel appModel;
    private AppController appController;

    @FXML
    private ComboBox<InspectionResult> comboBoxInspectionResult;

    @FXML
    private DatePicker datePickerInspectionDate;

    @FXML
    private TextField textFieldInspectorName;

    @FXML
    private ComboBox<Shipment> comboBoxShipment;

    @FXML
    private Label labelInvalidMessage;

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        populateComboBoxes();
    }

    @FXML
    public void initialize() {

        comboBoxInspectionResult.setOnAction(event -> {
            InspectionResult selectedResult = comboBoxInspectionResult.getValue();
            if (selectedResult != null) {
                System.out.println("Selected result: " + selectedResult);
            }

        });   

        

        comboBoxShipment.setCellFactory(param -> new ListCell<Shipment>() {
            @Override
            protected void updateItem(Shipment shipment, boolean empty) {
                super.updateItem(shipment, empty);

                if (empty || shipment == null || shipment.getId() == null) {
                    setText(null);
                } else {
                    setText(shipment.getId());
                }
            }
        });

        comboBoxShipment.setButtonCell(new ListCell<Shipment>() {
            @Override
            protected void updateItem(Shipment shipment, boolean empty) {
                super.updateItem(shipment, empty);

                if (empty || shipment == null || shipment.getId() == null) {
                    setText(null);
                } else {
                    setText(shipment.getId());
                }
            }
        });
}

private void populateComboBoxes() {
        comboBoxShipment.setItems(appModel.getShipmentRegister().getShipments());
        
        comboBoxInspectionResult.getItems().addAll(InspectionResult.values());
    }

    @FXML
    public void handleButtonInspectionAddAction() throws IOException {
        InspectionResult inspectionResult = comboBoxInspectionResult.getValue();
        LocalDate inspectionDate = datePickerInspectionDate.getValue();
        String inspectorName = textFieldInspectorName.getText();
        Shipment selectedShipment = comboBoxShipment.getValue();
        
        if (inspectionResult != null && inspectionDate != null && inspectorName != null && !inspectorName.trim().isEmpty() && selectedShipment != null) {
            Inspection newInspection = new Inspection(inspectorName, inspectionDate, inspectionResult);
            selectedShipment.addInspection(newInspection);
            appModel.getInspectionRegister().addInspection(newInspection);
            // Go back to the previous view
            appController.loadView("InspectionsView");
        } else {
            // Handle case where input is invalid
            String respone = "Please fill all fields.";
            labelInvalidMessage.setText(respone);
            labelInvalidMessage.setVisible(true);
        }
    }

    @FXML
    public void handleButtonInspectionBackAction() throws IOException {
        System.out.println("Back button clicked");
        appController.loadView("InspectionsView");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
