package errorists.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InspectionRegister {
    private final ObservableList<Inspection> inspections;

    public InspectionRegister() {
        this.inspections = FXCollections.observableArrayList();
    }

    /**
     * Returns an unmodifiable list of Inspections. Used to show a view of all
     * Inspections.
     */
    public ObservableList<Inspection> getInspections() {
        return FXCollections.unmodifiableObservableList(this.inspections);
    }

    /**
     * Adds a Inspection to the list.
     *
     * @param inspection the Inspection to be added
     */
    public void addInspection(Inspection inspection) {
        this.inspections.add(inspection);
    }

    /**
     * Removes a Inspection from the list.
     *
     * @param inspection the Inspection to be removed
     */
    public void removeInspection(Inspection inspection) {
        this.inspections.remove(inspection);
    }

           /**
     * Removes all inspections associated with a specific shipment.
     *
     * @param shipment the shipment whose inspections should be removed
     */
    public void removeInpsectionsByShipment(Shipment shipment) {
        this.inspections.removeIf(inspection -> inspection.getShipment().equals(shipment));
    }
}


