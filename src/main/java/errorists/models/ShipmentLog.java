package errorists.models;

import java.time.LocalDate;

public class ShipmentLog {

    private Shipment shipment;
    private Warehouse warehouse;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private ShipmentStatus shipmentStatus;

    //Constructor
    public ShipmentLog(Warehouse warehouse, LocalDate entryDate, LocalDate exitDate, ShipmentStatus status) {
        this.warehouse = warehouse;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.shipmentStatus = status;
    }

    //getters and setters
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
    

}
