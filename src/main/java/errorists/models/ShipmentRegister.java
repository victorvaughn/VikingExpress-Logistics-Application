package errorists.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShipmentRegister {

    private final ObservableList<Shipment> shipments;

    public ShipmentRegister() {
        this.shipments = FXCollections.observableArrayList();
    }

    /**
     * Returns an unmodifiable list of Shipments. Used to show a view of all
     * Shipments.
     */
    public ObservableList<Shipment> getShipments() {
        return FXCollections.unmodifiableObservableList(this.shipments);
    }

    /**
     * Adds a shipment to the list.
     *
     * @param shipment the shipment to be added
     */
    public void addShipment(Shipment shipment) {
        this.shipments.add(shipment);
    }

    /**
     * Removes a shipment from the list.
     *
     * @param shipment the shipment to be removed
     */
    public void removeShipment(Shipment shipment) {
        this.shipments.remove(shipment);
    }

    /**
     * Removes all shipments associated with a specific warehouse.
     *
     * @param warehouse the warehouse whose shipments should be removed
     */
    public void removeShipmentsByWarehouse(Warehouse warehouse) {
        this.shipments.removeIf(shipment -> shipment.getCurrentWarehouse().equals(warehouse));

    }
}
