package test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;

public class BloomFilter {

    BitSet bitSet;
    MessageDigest md1;
    MessageDigest md2;
    BigInteger bigInteger;

    List<MessageDigest> mdList;

    int size;

    public BloomFilter(int size, String alg1, String alg2) {
        this.size = size;
        bitSet = new BitSet();

        try {
            md1 = MessageDigest.getInstance(alg1);
            md2 = MessageDigest.getInstance(alg2);
        } catch (Exception e) {
            System.out.println("problem with algs");
        }
        mdList = new ArrayList<>();
        mdList.add(md1);
        mdList.add(md2);

    }

    public void add(String word) {

        for (MessageDigest md : mdList) {
            byte[] toHash = md.digest(word.getBytes());
            bigInteger = new BigInteger(1, toHash);
            int hashValue = Math.abs(bigInteger.abs().intValue()) % size;
            bitSet.set(hashValue, true);

        }

    }

    public boolean contains(String word) {

        for (MessageDigest md : mdList) {
            byte[] toHash = md.digest(word.getBytes());
            bigInteger = new BigInteger(1, toHash);
            int hashValue = Math.abs(bigInteger.abs().intValue()) % size;
            if (!bitSet.get(hashValue))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder bitsString = new StringBuilder(bitSet.length());
        for (int i = 0; i < bitSet.length(); i++) {
            bitsString.append(bitSet.get(i) ? 1 : 0);
        }
        return bitsString.toString();
    }
}