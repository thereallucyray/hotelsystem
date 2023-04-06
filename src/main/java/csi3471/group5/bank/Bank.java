package csi3471.group5.bank;

import java.nio.charset.StandardCharsets;

public class Bank {
    final Double tax = 0.08;
    public String getToken(String cardNum, int cvv, int expMonth, int expYear){
        return bytesToHex((cardNum+cvv+expMonth+expYear).getBytes());
    }

    public Receipt getReceipt(String bankToken, Double total, String accHolder, String item){
        return new Receipt(total, total*tax, accHolder, item);
    }
    private final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
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
