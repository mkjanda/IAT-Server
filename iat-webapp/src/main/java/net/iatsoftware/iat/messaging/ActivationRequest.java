/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "AcitvationRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class ActivationRequest extends net.iatsoftware.iat.generated.GActivationRequest {
    private static final Random random = new Random();
    private static final int[] ProductKeyModuli = {22, 6, 17, 30, 5, 15, 24, 2, 19, 35};
    private String eMailVerification;

    private static final byte XOR_MASK = (byte) 0x92;
    private static final int[] XOR_MASK_BITS = {1, 0, 0, 1, 0, 0, 1, 0};

    public static String toBase36(int n) {
        String str = "";
        while (n != 0) {
            str = Character.forDigit(n % 36, 36) + str;
            n -= n % 36;
            n /= 36;
        }
        return str.toUpperCase();
    }

    public static List<Integer> factor(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int ctr = 2;
        while (ctr <= n) {
            if (n % ctr == 0) {
                result.add(ctr);
                n /= ctr;
            } else {
                ctr++;
            }
        }
        return result;
    }

    public static boolean isRelativelyPrime(int n, int m) {
        List<Integer> nFactors = factor(n);
        List<Integer> mFactors = factor(m);
        for (int ctr1 = 0; ctr1 < nFactors.size(); ctr1++) {
            for (int ctr2 = 0; ctr2 < mFactors.size(); ctr2++) {
                if (nFactors.get(ctr1).intValue() == mFactors.get(ctr2).intValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String generateProductKey() {
        StringBuilder result = new StringBuilder();
        for (int ctr = 0; ctr < 10; ctr++) {
            int n = random.nextInt(36 * 36);
            while ((n < ProductKeyModuli[ctr]) || (!isRelativelyPrime(n, ProductKeyModuli[ctr]))) {
                n = random.nextInt(36 * 36);
            }
            String b36Str = toBase36(n);
            if (b36Str.length() != 2) {
                b36Str = "0" + b36Str;
            }
            result.append(b36Str);
        }
        return result.toString();
    }

    private static int fromBase36(String str) {
        int n = 0;
        int ctr = 0;
        while (str.length() > 0) {
            n += Character.digit(str.charAt(str.length() - 1), 36) * Math.pow(36, ctr++);
            str = str.substring(0, str.length() - 1);
        }
        return n;
    }

    private static boolean getBit(byte data, int pos) {
        return ((data & (1 << pos)) != 0);
    }

    private static byte setBit(byte data, int pos, boolean val) {
        if (val) {
            data |= (1 << pos);
        } else {
            byte mask = 0;
            for (int ctr = 0; ctr < 8; ctr++) {
                if (ctr != pos) {
                    mask |= (1 << ctr);
                }
            }
            data &= mask;
        }
        return data;
    }

    public static byte[] deXOR(byte[] xorData) {
        byte[] data = new byte[xorData.length];
        for (int ctr1 = 0; ctr1 < data.length; ctr1++) {
            for (int ctr2 = 0; ctr2 < 8; ctr2++) {
                if (XOR_MASK_BITS[7 - ctr2] == 1) {
                    if (getBit(xorData[ctr1], ctr2)) {
                        data[ctr1] = setBit(data[ctr1], ctr2, false);
                    } else {
                        data[ctr1] = setBit(data[ctr1], ctr2, true);
                    }
                } else {
                    if (getBit(xorData[ctr1], ctr2)) {
                        data[ctr1] = setBit(data[ctr1], ctr2, true);
                    } else {
                        data[ctr1] = setBit(data[ctr1], ctr2, false);
                    }
                }
            }
        }
        return data;
    }

    private void generateEMailVerificationCode(long clientID) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] data = new byte[9];
        random.nextBytes(data);
        eMailVerification = encoder.encodeToString(data);
        byte[] clientData = Long.toString(clientID).getBytes(StandardCharsets.UTF_8);
        for (int ctr = 0; ctr < clientData.length; ctr++) {
            clientData[ctr] ^= XOR_MASK;
        }
        String clientIDB64 = encoder.encodeToString(clientData);
        eMailVerification += encoder.encodeToString(Integer.toString(clientIDB64.length()).getBytes(StandardCharsets.UTF_8)) + "|" + clientIDB64;
        data = new byte[6];
        random.nextBytes(data);
        eMailVerification += encoder.encodeToString(data);
        eMailVerification = User.encodeKey(eMailVerification);
    }

    public String getEMailVerification(long clientID) {
        if (eMailVerification.isEmpty()) {
            generateEMailVerificationCode(clientID);
        }
        return eMailVerification;
    }

    public static String generateActivationCode(String productKey) {
        String actCode = "";
        for (int ctr = 0; ctr < productKey.length() >> 1; ctr++) {
            int n = fromBase36(productKey.substring(ctr * 2, ctr * 2 + 2));
            int m = random.nextInt(35 * 36);
            while ((!isRelativelyPrime(m, ProductKeyModuli[ctr])) || (!isRelativelyPrime(m, n))) {
                m = random.nextInt(35 * 36);
            }
            n *= m;
            n += ProductKeyModuli[ctr];
            String b36Str = toBase36(n);
            while (b36Str.length() < 4) {
                b36Str = "0" + b36Str;
            }
            actCode += b36Str;
        }
        return actCode.toUpperCase();
    }

}
