package Model;
/**
 *
 * @author Matt DiPerna
 */

public class OutSourced extends Part{

    /**
     * string for storing company name
     */
    private String companyName;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     * constructor for the OutSourced class
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max,String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName is set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
