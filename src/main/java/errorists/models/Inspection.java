package errorists.models;

import java.time.LocalDate;

public class Inspection {
    private String inspectorName;
    private LocalDate inspectionDate;
    private Shipment shipment;
    private InspectionResult inspectionResult;
    private Warehouse warehouse;

    //Constructors
    public Inspection(String inspectorName, LocalDate inspectionDate, InspectionResult inspectionResult) {
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
        this.inspectionResult = inspectionResult;
    }

    //getters and setters
    public String getInspectorName() {
        return inspectorName;
    }

    public void setInpsectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public InspectionResult getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(InspectionResult inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public Warehouse getInspectionLocation() {

        // Get the location of the inspection
        return shipment.getCurrentWarehouse();
    }


    public void setInspectionLocation(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    
}
