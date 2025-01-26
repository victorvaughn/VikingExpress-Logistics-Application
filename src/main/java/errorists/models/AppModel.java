package errorists.models;

import java.time.LocalDate;

public class AppModel {

    private final WarehouseRegister warehouseRegister;
    private final ShipmentRegister shipmentRegister;
    private final InspectionRegister inspectionRegister;

    public AppModel() {
        warehouseRegister = new WarehouseRegister();
        shipmentRegister = new ShipmentRegister();
        inspectionRegister = new InspectionRegister();

        addTestData();
    }

    //getters
    public ShipmentRegister getShipmentRegister() {
        return this.shipmentRegister;
    }

    public WarehouseRegister getWarehouseRegister() {
        return this.warehouseRegister;
    }

    public InspectionRegister getInspectionRegister() {
        return this.inspectionRegister;
    }

    private void addTestData() {
        Warehouse warehouse1 = new Warehouse("Odin's Vault", "123 Asgard St", 1000, Region.NORTH);
        Warehouse warehouse2 = new Warehouse("Valhalla Hub", "456 Valhalla Ave", 1500, Region.MIDDLE);
        Warehouse warehouse3 = new Warehouse("Thor's Depot", "789 Mjolnir Rd", 2000, Region.SOUTH);
        Warehouse warehouse4 = new Warehouse("Freya's Storehouse", "101 Freya Ln", 2500, Region.NORTH);
        Warehouse warehouse5 = new Warehouse("Loki's Lair", "202 Trickster Blvd", 3000, Region.MIDDLE);
        Warehouse warehouse6 = new Warehouse("Heimdall's Watch", "303 Rainbow Bridge", 3500, Region.SOUTH);
        Warehouse warehouse7 = new Warehouse("Bifrost Bunker", "404 Bifrost Ave", 4000, Region.NORTH);
        Warehouse warehouse8 = new Warehouse("Fenrir's Den", "505 Wolf St", 4500, Region.MIDDLE);
        Warehouse warehouse9 = new Warehouse("Jormungandr's Pit", "606 Serpent Rd", 5000, Region.SOUTH);
        Warehouse warehouse10 = new Warehouse("Yggdrasil Yard", "707 World Tree Blvd", 5500, Region.NORTH);
        Warehouse warehouse11 = new Warehouse("Midgard Market", "808 Earth Ave", 6000, Region.MIDDLE);
        Warehouse warehouse12 = new Warehouse("Asgardian Annex", "909 Asgard St", 6500, Region.SOUTH);

        warehouseRegister.addWarehouse(warehouse1);
        warehouseRegister.addWarehouse(warehouse2);
        warehouseRegister.addWarehouse(warehouse3);
        warehouseRegister.addWarehouse(warehouse4);
        warehouseRegister.addWarehouse(warehouse5);
        warehouseRegister.addWarehouse(warehouse6);
        warehouseRegister.addWarehouse(warehouse7);
        warehouseRegister.addWarehouse(warehouse8);
        warehouseRegister.addWarehouse(warehouse9);
        warehouseRegister.addWarehouse(warehouse10);
        warehouseRegister.addWarehouse(warehouse11);
        warehouseRegister.addWarehouse(warehouse12);

        Shipment shipment1 = new Shipment(warehouse1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 1), ShipmentStatus.DELIVERED);
        Shipment shipment2 = new Shipment(warehouse1, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 1), ShipmentStatus.DELIVERED);
        Shipment shipment3 = new Shipment(warehouse3, LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 2), ShipmentStatus.DELIVERED);
        Shipment shipment4 = new Shipment(warehouse4, LocalDate.of(2020, 4, 1), LocalDate.of(2021, 9, 5), ShipmentStatus.DELIVERED);
        Shipment shipment5 = new Shipment(warehouse5, LocalDate.of(2021, 5, 1), LocalDate.of(2021, 6, 5), ShipmentStatus.DELIVERED);
        Shipment shipment6 = new Shipment(warehouse6, LocalDate.of(2021, 6, 1), LocalDate.of(2021, 7, 5), ShipmentStatus.DELIVERED);
        Shipment shipment7 = new Shipment(warehouse7, LocalDate.of(2021, 7, 1), LocalDate.of(2021, 8, 5), ShipmentStatus.DELIVERED);
        Shipment shipment8 = new Shipment(warehouse8, LocalDate.of(2021, 8, 1), LocalDate.of(2021, 9, 5), ShipmentStatus.DELIVERED);
        Shipment shipment9 = new Shipment(warehouse9, LocalDate.of(2021, 9, 1), LocalDate.of(2021, 9, 5), ShipmentStatus.DELIVERED);
        Shipment shipment10 = new Shipment(warehouse11, LocalDate.of(2021, 10, 1), LocalDate.of(2021, 10, 5), ShipmentStatus.DELIVERED);
        Shipment shipment11 = new Shipment(warehouse12, LocalDate.of(2021, 11, 1), LocalDate.of(2021, 11, 5), ShipmentStatus.DELIVERED);
        Shipment shipment12 = new Shipment(warehouse10, LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 5), ShipmentStatus.DELIVERED);
        Shipment shipment13 = new Shipment(warehouse1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 1), ShipmentStatus.DELIVERED);
        Shipment shipment14 = new Shipment(warehouse1, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 1), ShipmentStatus.DELIVERED);
        Shipment shipment15 = new Shipment(warehouse3, LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 2), ShipmentStatus.DELIVERED);
        Shipment shipment16 = new Shipment(warehouse4, LocalDate.of(2020, 4, 1), LocalDate.of(2021, 9, 5), ShipmentStatus.DELIVERED);
        Shipment shipment17 = new Shipment(warehouse5, LocalDate.of(2021, 5, 1), LocalDate.of(2021, 6, 5), ShipmentStatus.DELIVERED);

        warehouse1.addShipment(shipment1);
        warehouse1.addShipment(shipment2);
        warehouse3.addShipment(shipment3);
        warehouse4.addShipment(shipment4);
        warehouse5.addShipment(shipment5);
        warehouse6.addShipment(shipment6);
        warehouse7.addShipment(shipment7);
        warehouse8.addShipment(shipment8);
        warehouse9.addShipment(shipment9);
        warehouse11.addShipment(shipment10);
        warehouse12.addShipment(shipment11);
        warehouse10.addShipment(shipment12);
        warehouse1.addShipment(shipment13);
        warehouse1.addShipment(shipment14);
        warehouse3.addShipment(shipment15);
        warehouse4.addShipment(shipment16);
        warehouse5.addShipment(shipment17);

        shipmentRegister.addShipment(shipment1);
        shipmentRegister.addShipment(shipment2);
        shipmentRegister.addShipment(shipment3);
        shipmentRegister.addShipment(shipment4);
        shipmentRegister.addShipment(shipment5);
        shipmentRegister.addShipment(shipment6);
        shipmentRegister.addShipment(shipment7);
        shipmentRegister.addShipment(shipment8);
        shipmentRegister.addShipment(shipment9);
        shipmentRegister.addShipment(shipment10);
        shipmentRegister.addShipment(shipment11);
        shipmentRegister.addShipment(shipment12);
        shipmentRegister.addShipment(shipment13);
        shipmentRegister.addShipment(shipment14);
        shipmentRegister.addShipment(shipment15);
        shipmentRegister.addShipment(shipment16);
        shipmentRegister.addShipment(shipment17);

        shipment1.setCurrentWarehouse(warehouse1);
        shipment2.setCurrentWarehouse(warehouse1);
        shipment3.setCurrentWarehouse(warehouse3);
        shipment4.setCurrentWarehouse(warehouse4);
        shipment5.setCurrentWarehouse(warehouse5);
        shipment6.setCurrentWarehouse(warehouse6);
        shipment7.setCurrentWarehouse(warehouse7);
        shipment8.setCurrentWarehouse(warehouse8);
        shipment9.setCurrentWarehouse(warehouse9);
        shipment10.setCurrentWarehouse(warehouse11);
        shipment11.setCurrentWarehouse(warehouse12);
        shipment12.setCurrentWarehouse(warehouse10);
        shipment13.setCurrentWarehouse(warehouse1);
        shipment14.setCurrentWarehouse(warehouse1);
        shipment15.setCurrentWarehouse(warehouse3);
        shipment16.setCurrentWarehouse(warehouse4);
        shipment17.setCurrentWarehouse(warehouse5);

        Inspection inspection1 = new Inspection("Odin", LocalDate.of(2021, 1, 1), InspectionResult.PASS);
        Inspection inspection2 = new Inspection("Thor", LocalDate.of(2021, 2, 1), InspectionResult.PASS);
        Inspection inspection3 = new Inspection("Freya", LocalDate.of(2021, 3, 1), InspectionResult.FAIL);
        Inspection inspection4 = new Inspection("Loki", LocalDate.of(2021, 4, 1), InspectionResult.PASS);
        Inspection inspection5 = new Inspection("Heimdall", LocalDate.of(2021, 5, 1), InspectionResult.PASS);
        Inspection inspection6 = new Inspection("Fenrir", LocalDate.of(2021, 6, 1), InspectionResult.PASS);
        Inspection inspection7 = new Inspection("Jormungandr", LocalDate.of(2021, 7, 1), InspectionResult.PASS);
        Inspection inspection8 = new Inspection("Yggdrasil", LocalDate.of(2021, 8, 1), InspectionResult.PASS);
        Inspection inspection9 = new Inspection("Midgard", LocalDate.of(2021, 9, 1), InspectionResult.PASS);
        Inspection inspection10 = new Inspection("Asgard", LocalDate.of(2021, 10, 1), InspectionResult.PASS);
        Inspection inspection11 = new Inspection("Valhalla", LocalDate.of(2021, 11, 1), InspectionResult.PASS);
        Inspection inspection12 = new Inspection("Bifrost", LocalDate.of(2021, 12, 1), InspectionResult.PASS);

        shipment1.addInspection(inspection1);
        shipment2.addInspection(inspection2);
        shipment3.addInspection(inspection3);
        shipment4.addInspection(inspection4);
        shipment5.addInspection(inspection5);
        shipment6.addInspection(inspection6);
        shipment7.addInspection(inspection7);
        shipment8.addInspection(inspection8);
        shipment9.addInspection(inspection9);
        shipment10.addInspection(inspection10);
        shipment11.addInspection(inspection11);
        shipment12.addInspection(inspection12);

        inspectionRegister.addInspection(inspection1);
        inspectionRegister.addInspection(inspection2);
        inspectionRegister.addInspection(inspection3);
        inspectionRegister.addInspection(inspection4);
        inspectionRegister.addInspection(inspection5);
        inspectionRegister.addInspection(inspection6);
        inspectionRegister.addInspection(inspection7);
        inspectionRegister.addInspection(inspection8);
        inspectionRegister.addInspection(inspection9);
        inspectionRegister.addInspection(inspection10);
        inspectionRegister.addInspection(inspection11);
        inspectionRegister.addInspection(inspection12);

        inspection1.setInspectionLocation(shipment1.getCurrentWarehouse());
        inspection2.setInspectionLocation(shipment2.getCurrentWarehouse());
        inspection3.setInspectionLocation(shipment3.getCurrentWarehouse());
        inspection4.setInspectionLocation(shipment4.getCurrentWarehouse());
        inspection5.setInspectionLocation(shipment5.getCurrentWarehouse());
        inspection6.setInspectionLocation(shipment6.getCurrentWarehouse());
        inspection7.setInspectionLocation(shipment7.getCurrentWarehouse());
        inspection8.setInspectionLocation(shipment8.getCurrentWarehouse());
        inspection9.setInspectionLocation(shipment9.getCurrentWarehouse());
        inspection10.setInspectionLocation(shipment10.getCurrentWarehouse());
        inspection11.setInspectionLocation(shipment11.getCurrentWarehouse());
        inspection12.setInspectionLocation(shipment12.getCurrentWarehouse());

    }

}
