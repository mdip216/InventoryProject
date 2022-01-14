package Model;
/**
 *
 * @author Matt DiPerna
 */

public class InHouse extends Part{

    /**
     * int for machineId
     */
    private int machineId;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     * constructor for the Inhouse class
     */
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
