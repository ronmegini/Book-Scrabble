package test;

import java.util.BitSet;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class BloomFilter {

    BitSet bitSet;
    MessageDigest hash1;
    MessageDigest hash2;
    BigInteger bigInteger;

    List<MessageDigest> hashList;

    int size;

    public BloomFilter(int size, String firstHashAlgo, String secondHashAlgo) {
        this.size = size;
        bitSet = new BitSet();
        hashList = new ArrayList<>();

        try {
            hash1 = MessageDigest.getInstance(firstHashAlgo);
            hash2 = MessageDigest.getInstance(secondHashAlgo);
        } catch (Exception e) {
            System.out.println("Given hash algo doesnt exist");
        }

        hashList.add(hash1);
        hashList.add(hash2);

    }

    public void add(String word) {

        for (MessageDigest hash : hashList) {
            byte[] toHash = hash.digest(word.getBytes());
            bigInteger = new BigInteger(1, toHash);
            int hashValue = Math.abs(bigInteger.abs().intValue()) % size;
            bitSet.set(hashValue, true);

        }

    }

    public boolean contains(String word) {

        for (MessageDigest hash : hashList) {
            byte[] toHash = hash.digest(word.getBytes());
            bigInteger = new BigInteger(1, toHash);
            int hashValue = Math.abs(bigInteger.abs().intValue()) % size;
            // Hash value doesnt exist in
            if (bitSet.get(hashValue) == false)
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