package errorists.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WarehouseRegister {
     private final ObservableList<Warehouse> warehouses;

    public WarehouseRegister() {
        this.warehouses = FXCollections.observableArrayList();
    }

    /**
     * Returns an unmodifiable list of warehouses.
     * This prevents the caller from directly modifying the list
     * and ensures encapsulation is preserved.
     */
    public ObservableList<Warehouse> getWarehouses() {
        return FXCollections.unmodifiableObservableList(this.warehouses);
    }

    /**
     * Adds a warehouse to the list.
     *
     * @param warehouse the warehouse to be added
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Removes a warehouse from the list.
     *
     * @param warehouse the warehouse to be removed
     */
    public void removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
    }

    /**
     * Finds a warehouse by its ID.
     *
     * @param id the ID of the warehouse
     * @return the warehouse if found, null otherwise
     */
    public Warehouse findWarehouseByName(String name) {
        for (Warehouse warehouse : this.warehouses) {
            if (warehouse.getName().equals(name)) {
                return warehouse;
            }
        }
        return null;
    }
}
