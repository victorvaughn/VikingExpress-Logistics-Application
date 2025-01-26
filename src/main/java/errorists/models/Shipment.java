package errorists.models;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Shipment {

    private String id;
    private Warehouse currentWarehouse;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private ShipmentStatus status;
    private final ObservableList<Inspection> inspectionLog;
    private final ObservableList<ShipmentLog> shipmentLog;

    //Constructor
    public Shipment(Warehouse currentWarehouse, LocalDate entryDate, LocalDate exitDate, ShipmentStatus status) {
        this.id = ShipmentIDManager.generateUniqueID();
        this.currentWarehouse = currentWarehouse;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.status = status;
        this.inspectionLog = FXCollections.observableArrayList();
        this.shipmentLog = FXCollections.observableArrayList();

        addShipmentLog(currentWarehouse, entryDate, exitDate, status);
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Warehouse getCurrentWarehouse() {
        return currentWarehouse;
    }

    public void setCurrentWarehouse(Warehouse warehouse) {
        this.currentWarehouse = warehouse;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public ObservableList<Inspection> getInspectionLog() {
        return inspectionLog;
    }

    public ObservableList<ShipmentLog> getShipmentLog() {
        return shipmentLog;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    //Method to add shipment log
    public void addShipmentLog(Warehouse warehouse, LocalDate entryDate, LocalDate exitDate, ShipmentStatus status) {
        ShipmentLog newShipmentLog = new ShipmentLog(warehouse, entryDate, exitDate, status);
        if (ChronoUnit.DAYS.between(entryDate, exitDate) < 0) {
            throw new IllegalArgumentException("Exit date cannot be before entry date");
        } else {
            this.shipmentLog.add(newShipmentLog);
            newShipmentLog.setShipment(this);
        }
    }

    //Method to remove shipment log
    public void removeShipmentLog(ShipmentLog shipmentLog) {
        this.shipmentLog.remove(shipmentLog);
    }

    //Method to add new inspection to shipment
    public void addInspection(Inspection inspection) {
        inspectionLog.add(inspection);
        inspection.setShipment(this);
    }

    //Method to remove inspection from list
    public void removeInspection(Inspection inspection) {
        inspectionLog.remove(inspection);
    }

    // Method to move shipment to another warehouse
    public void moveToWarehouse(Warehouse newWarehouse, LocalDate entryDate, LocalDate exitDate, ShipmentStatus status) {
        if (this.currentWarehouse != null) {
            // Remove the shipment from the current warehouse
            this.currentWarehouse.removeShipment(this);
        }

        // Update current warehouse and dates
        this.currentWarehouse = newWarehouse;
        this.entryDate = entryDate;
        this.exitDate = exitDate;

        // Add the shipment to the new warehouse
        newWarehouse.addShipment(this);

        // Create a log for the new warehouse
        addShipmentLog(newWarehouse, entryDate, exitDate, status);
    }

    // Method to get the most recent inspection
    public Inspection getMostRecentInspection() {
        List<Inspection> inspectionLog = getInspectionLog(); // Retrieve the list of inspections
        if (inspectionLog == null || inspectionLog.isEmpty()) {
            return null; // Return null if there are no inspections
        }

        Inspection latestInspection = null;
        for (Inspection inspection : inspectionLog) {
            if (latestInspection == null || inspection.getInspectionDate().isAfter(latestInspection.getInspectionDate())) {
                latestInspection = inspection;
            }
        }

        return latestInspection;
    }

    //method to see total number of warehouses a shipment has been to
    public int getNumberOfWarehousesVisited() {
        ArrayList<Warehouse> uniqueWarehouse = new ArrayList<>();
        for (ShipmentLog log : shipmentLog) {
            if (!uniqueWarehouse.contains(log.getWarehouse())) {
                uniqueWarehouse.add(log.getWarehouse());
            }
        }
        return uniqueWarehouse.size();
    }

    //method to get the total duration of the shipment
    public String getStoredDuration() {
        Period storageDuration = Period.between(entryDate, exitDate);

        int years = storageDuration.getYears();
        int months = storageDuration.getMonths();
        int days = storageDuration.getDays();

        String duration = "";

        if (years > 1) {
            duration += years + " years ";
        } else if (years == 1) {
            duration += years + " year ";
        }

        if (months > 1) {
            duration += months + " months ";
        } else if (months == 1) {
            duration += months + " month ";
        }

        if (days > 1) {
            duration += days + " days";
        } else if (days == 1) {
            duration += days + " day";
        }

        if (duration.isEmpty()) {
            duration = "0 days";
        }

        return duration;
    }

    // Method to get the total number of journeys a shipment has been on
    public int getTotalJourneys() {
        return shipmentLog.size();
    }

    // Method to see if a shipment has been to the same warehouse more than once
    public boolean hasTransportationLoops() {
        Set<Warehouse> visitedWarehouses = new HashSet<>();
        for (ShipmentLog log : shipmentLog) {
            if (!visitedWarehouses.add(log.getWarehouse())) {
                return true; // Warehouse already visited
            }
        }
        return false;
    }

}
