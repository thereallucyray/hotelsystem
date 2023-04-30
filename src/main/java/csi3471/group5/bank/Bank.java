package csi3471.group5.bank;

/**
 * This class performs Bank operations to facilitate Guest's payment.
 */
public class Bank {
    final Double tax = 0.08;

    /**
     * This method hashes the card information input by the Guest to be stored as
     * a card "token".
     * @param cardNum The guest's credit card number.
     * @param cvv The guest's credit card cvv.
     * @param expMonth The guest's credit card expiration month.
     * @param expYear The guest's credit card expiration year.
     * @return The hashed card information as a hex string.
     */
    public String getToken(String cardNum, int cvv, int expMonth, int expYear){
        return bytesToHex((cardNum+cvv+expMonth+expYear).getBytes());
    }

    /**
     * This method generates a receipt object for a given Guest reservation.
     * @param bankToken The token representing the Guest's credit card info.
     * @param total The total price of the Guest's stay at the hotel. This may be the cancellation fee.
     * @param accHolder The guest's name, per the reservation.
     * @param item A descriptor of what the guest is being charged for. This may be details on the room
     *             type, or cancellation fee.
     * @return a new Receipt object specific to the Guest's reservation.
     */
    public Receipt getReceipt(String bankToken, Double total, String accHolder, String item){
        return new Receipt(total, total*tax, accHolder, item);
    }

    private final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    /**
     * This method converts a byte array to a hex string
     * @param bytes A byte array representing the guest's credit card info
     * @return A hex string hash of the guest's card information.
     */
    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


}
