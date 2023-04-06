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
        return "Receipt{" +
                "total=" + total +
                ", tax=" + tax +
                ", accHolder='" + accHolder + '\'' +
                ", item='" + item + '\'' +
                '}';
    }
}
