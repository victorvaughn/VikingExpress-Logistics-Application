package errorists.controllers;

import java.util.List;

import errorists.models.AppModel;
import errorists.models.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardController {

    private AppModel appModel;
    private AppController appController;

    @FXML
    private PieChart pieChartStock;

    @FXML
    private Label labelWarehouseName;

    @FXML
    private Label labelStockLevel;

    @FXML
    private Label labelRegion;

    @FXML
    private Label labelCapacity;

    @FXML
    private Label labelRemainingCapacity;

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
        // Initialize the PieChart when the AppModel is set
        initializePieChart();
        initializeWarehouseInfo();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void initializePieChart() {
        if (appModel == null) {
            throw new IllegalStateException("AppModel is not set. Make sure to call setAppModel before initialization.");
        }

        int northCapacity = 0;
        int middleCapacity = 0;
        int southCapacity = 0;

        for (Warehouse warehouse : appModel.getWarehouseRegister().getWarehouses()) {
            int remainingCapacity = warehouse.getCapacity() - warehouse.getCurrentStockLevel();

            switch (warehouse.getRegion()) {
                case NORTH ->
                    northCapacity += remainingCapacity;
                case MIDDLE ->
                    middleCapacity += remainingCapacity;
                case SOUTH ->
                    southCapacity += remainingCapacity;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("NORTH " + northCapacity, northCapacity),
                new PieChart.Data("MIDDLE " + middleCapacity, middleCapacity),
                new PieChart.Data("SOUTH " + southCapacity, southCapacity)
        );

        pieChartStock.setData(pieChartData);
    }

    private void initializeWarehouseInfo() {
        if (appModel == null) {
            throw new IllegalStateException("AppModel is not set. Make sure to call setAppModel before initialization.");
        }

        // Retrieve the list of warehouses
        List<Warehouse> warehouses = appModel.getWarehouseRegister().getWarehouses();
        if (warehouses == null || warehouses.isEmpty()) {
            labelWarehouseName.setText("No warehouses available in the register.");
            labelStockLevel.setText("-");
            labelRegion.setText("-");
            labelCapacity.setText("-");
            labelRemainingCapacity.setText("-");
            return;
        }

        // Initialize variables
        Warehouse busiestWarehouse = null;
        int maxStockLevel = Integer.MIN_VALUE;

        // Iterate through the list of warehouses
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getCurrentStockLevel() > maxStockLevel) {
                maxStockLevel = warehouse.getCurrentStockLevel();
                busiestWarehouse = warehouse;
            }
        }

        // Update the UI
        updateWarehouseInfoUI(busiestWarehouse);
    }

    // Update the UI with the warehouse information
    private void updateWarehouseInfoUI(Warehouse warehouse) {
        labelWarehouseName.setText(warehouse.getName());
        labelStockLevel.setText(String.valueOf(warehouse.getCurrentStockLevel()));
        labelRegion.setText(warehouse.getRegion().toString());
        labelCapacity.setText(String.valueOf(warehouse.getCapacity()));
        labelRemainingCapacity.setText(String.valueOf(warehouse.getCapacity() - warehouse.getCurrentStockLevel()));
    }
}
