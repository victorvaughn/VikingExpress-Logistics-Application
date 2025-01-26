package errorists.models;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ShipmentIDManager {
    private static final Random random = new Random();
    private static final int ID_LENGTH = 8;
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static Set<String> existingIDs = new HashSet<>();

    //Constructor
    public ShipmentIDManager(Set<String> existingIDs) {
        this.existingIDs = existingIDs;
    }

    //generate a unique ID an check if the ID is already in use
    public static String generateUniqueID() {
        String newID;
        do {
            newID = generateID();
        } while (existingIDs.contains(newID));
        existingIDs.add(newID);
        return newID;
    }

    //generate a random ID of length 8 and return it as a string
    public static String generateID() {
        StringBuilder id = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            id.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return id.toString();
    }

    //Method to add an existing ID from the set
    public void addExistingID(String id) {
        existingIDs.add(id);
    }

    //Method to remove an existing ID from the set
    public void removeExistingID(String id) {
        existingIDs.remove(id);
    }
}
