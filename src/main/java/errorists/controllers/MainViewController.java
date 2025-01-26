package errorists.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;



public class MainViewController {
    private AppController appController;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    public void handleButtonDashboardAction(ActionEvent event){
        System.out.println("Dashboard Button Clicked");
        try {
            appController.loadView("Dashboard");
        } catch (IOException e) {
            
        }
    }

    @FXML
    public void handleButtonWarehousesViewAction(ActionEvent event){
        System.out.println("Warehouse Button Clicked");
        try {
            appController.loadView("WarehousesView");
        } catch (IOException e) {
            
        }
    }

    @FXML
    public void handleButtonShipmentAction(ActionEvent event){
            System.out.println("Shipment Button Clicked");
            try {
                appController.loadView("ShipmentsView");
            } catch (IOException e) {
                
            }
        }

    @FXML
    public void handleButtonInspectionAction(ActionEvent event){
            System.out.println("Inspection Button Clicked");
            try {
                appController.loadView("InspectionsView");
            } catch (IOException e) {
                
            }
        }

    @FXML
    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public ScrollPane getMainScrollPane() {
        return mainScrollPane;
    }

    
}
