package csi3471.group5.bank;

/**
 * This class contains receipt information about the Guest's Hotel Reservation.
 */
public class Receipt {
    Double total, tax;
    String accHolder, item;

    /**
     * Receipt object constructor
     * @param total The total cost of the guest's stay, or the cancellation fee.
     * @param tax The amount in tax the guest must pay (8% of the total)
     * @param accHolder The guest's name associated with the reservation.
     * @param item Descriptor of what the guest is paying for. This can be details about the
     *             reserved room, or a cancellation fee
     */
    public Receipt(Double total, Double tax, String accHolder, String item) {
        this.total = total;
        this.tax = tax;
        this.accHolder = accHolder;
        this.item = item;
    }

    /**
     * ToString function for the receipt class
     * @return String containing the tax, total, and guest's name
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(item);
        sb.append(": ").append(String.format("%.2f", total));
        sb.append("\nTax: ").append(String.format("%.2f", tax));
        sb.append("\nTotal: ").append(String.format("%.2f", total + tax));
        sb.append("\nAccount Holder: ").append(accHolder);
        return sb.toString();
    }
}
