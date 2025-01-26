package errorists.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Warehouse {

    private String name;
    private String address;
    private Region region;
    private int capacity;
    private int currentStockLevel;
    private ObservableList<Shipment> shipments = FXCollections.observableArrayList();

    //Constructor
    public Warehouse(String name, String address, int capacity, Region region) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.currentStockLevel = 0;
        this.region = region;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentStockLevel() {
        return currentStockLevel;
    }

    public void setCurrentStockLevel(int currentStockLevel) {
        this.currentStockLevel = currentStockLevel;
    }

    public ObservableList<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(ObservableList<Shipment> shipments) {
        this.shipments = shipments;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    //Method to add a new shipment 
    public void addShipment(Shipment shipment) {
        if (currentStockLevel >= capacity) {
            throw new IllegalStateException("Warehouse is at full capacity");
        } else if (ChronoUnit.DAYS.between(shipment.getEntryDate(), shipment.getExitDate()) < 0) {
            throw new IllegalArgumentException("Exit date cannot be before entry date");
        } else {

            shipments.add(shipment);
            currentStockLevel++;
        }
    }

    //Method to remove shipment
    public void removeShipment(Shipment shipment) {
        shipments.remove(shipment);
        currentStockLevel--;
    }

    public Inspection getMostRecentInspection() {
        if (shipments == null || shipments.isEmpty()) {
            return null; // Return null if there are no shipments
        }
    
        Inspection latestInspection = null;
    
        for (Shipment shipment : shipments) {
            // Get the most recent inspection for the current shipment
            Inspection currentInspection = shipment.getMostRecentInspection();
    
            // Skip if the shipment has no inspections
            if (currentInspection == null) {
                continue;
            }
    
            // Update latestInspection if currentInspection is more recent
            if (latestInspection == null || 
                currentInspection.getInspectionDate().isAfter(latestInspection.getInspectionDate())) {
                latestInspection = currentInspection;
            }
        }
    
        return latestInspection;
    }

    //Calculate remaining capacity in percent
    public String getRemainingCapacity() {
        float remainingCapacity = (capacity - shipments.size()) / (float) capacity;
        return String.format("%.2f%%", remainingCapacity * 100); // Format to 2 decimal places
    }

    //Calculate average throughput time
    public String calculateThroughput() {
        if (shipments.isEmpty()) {
            return 0 + " days";
        }
        int totalDays = 0;
        for (Shipment shipment : shipments) {
            totalDays += calculateDaysInWarehouseForShipment(shipment);
        }
        if (totalDays == 1) {
            return totalDays / shipments.size() + " day";
        } else {
            return totalDays / shipments.size() + " days";
        }
    }

    private int calculateDaysInWarehouseForShipment(Shipment shipment) {
        LocalDate entryDate = findEntryDateForShipment(shipment);
        LocalDate exitDate = findExitDateForShipment(shipment);

        if (entryDate != null && exitDate != null) {
            long totalDays = ChronoUnit.DAYS.between(entryDate, exitDate);
            return (int) totalDays;
        }
        return 0;
    }

    private LocalDate findExitDateForShipment(Shipment shipment) {
        for (ShipmentLog log : shipment.getShipmentLog()) {
            if (log.getWarehouse().equals(this) && log.getExitDate() != null) {
                return log.getExitDate();
            }
        }
        return null;
    }

    private LocalDate findEntryDateForShipment(Shipment shipment) {
        for (ShipmentLog log : shipment.getShipmentLog()) {
            if (log.getWarehouse().equals(this) && log.getEntryDate() != null) {
                return log.getEntryDate();
            }
        }
        return null;
    }

}
