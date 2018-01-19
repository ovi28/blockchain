package blockchain;


import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    private int index;
    private Long timestamp;
    private String hash;
    private String previousHash;
    private String creator;

    public Block() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Block block = (Block) o;
        return index == block.index
                && timestamp.equals(block.timestamp)
                && hash.equals(block.hash)
                && previousHash.equals(block.previousHash)
                && creator.equals(block.creator);
    }

    @Override
    public int hashCode() {
       return 0;
    }

    public Block(int index, String preHash, String creator) {
        this.index = index;
        this.previousHash = preHash;
        this.creator = creator;
        timestamp = System.currentTimeMillis();
        hash = calculateHash(String.valueOf(index) + previousHash + String.valueOf(timestamp));
    }

    public String getCreator() {
        return creator;
    }

    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    private String calculateHash(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "HASH_ERROR";
        }

        /*
        //make the mining process more complex by calculating factorial of 5000 just for fun
        BigInteger result = BigInteger.valueOf(1);

        for (int factor = 2; factor <= 10000; factor++) {
            result = result.multiply(BigInteger.valueOf(factor));
        }
        */
        final byte bytes[] = digest.digest(text.getBytes());
        final StringBuilder hexString = new StringBuilder();
        for (final byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}