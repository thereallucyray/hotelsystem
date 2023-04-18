package csi3471.group5.bank;

public class Receipt {
    Double total, tax;
    String accHolder, item;

    public Receipt(Double total, Double tax, String accHolder, String item) {
        this.total = total;
        this.tax = tax;
        this.accHolder = accHolder;
        this.item = item;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(item);
        sb.append(": ").append(total);
        sb.append("\nTax: ").append(tax);
        sb.append("\nTotal: ").append(total + tax);
        sb.append("\nAccount Holder: ").append(accHolder);
        return sb.toString();
    }
}
