/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author Michael Janda
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductKey {
    private String productKey = "";
    private static final int[] ProductKeyModuli = {22, 6, 17, 30, 5, 15, 24, 2, 19, 35};

    private String toBase36(int n) {
        String str = "";
        while (n != 0) {
            str = Character.forDigit(n % 36, 36) + str;
            n -= n % 36;
            n /= 36;
        }
        return str.toUpperCase();
    }

    private List<Integer> factor(int n) {
        List<Integer> result = new ArrayList<>();
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

    private boolean isRelativelyPrime(int n, int m) {
        List<Integer> nFactors = factor(n);
        List<Integer> mFactors = factor(m);
        for (int nFact : nFactors) {
            for (int mFact : mFactors) {
                if (mFact == nFact)
                    return false;
            }
        }
        return true;
    }

    public String generateProductKey() {
        String result = "";
        Random rand = new Random();
        for (int ctr = 0; ctr < 10; ctr++) {
            int n = rand.nextInt(36 * 36);
            while ((n < ProductKeyModuli[ctr]) || (!isRelativelyPrime(n, ProductKeyModuli[ctr]))) {
                n = rand.nextInt(36 * 36);
            }
            String b36Str = toBase36(n);
            if (b36Str.length() != 2) {
                b36Str = "0" + b36Str;
            }
            result += b36Str;
        }
        this.productKey = result;
        return result;
    }
    
    public ProductKey() {}
    
    public String getProductKey() {
        return this.productKey;
    }
}
