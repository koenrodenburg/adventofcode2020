package day25;

public class Day25 {
    private static final long doorPublicKey = 17773298;
    private static final long cardPublicKey = 15530095;

    private static final long SUBJECT_NUMBER = 7;
    private static final long DIVISOR = 20201227;

    public static void main(String[] args) {
        long doorLoopSize = determineLoopSize(doorPublicKey, SUBJECT_NUMBER);
        long encryptionKey = transform(cardPublicKey, doorLoopSize);
        System.out.println(encryptionKey);
    }

    private static long determineLoopSize(long publicKey, long subjectNumber) {
        long value = 1;
        int loopSize = 0;
        while(value != publicKey) {
            value = value * subjectNumber % DIVISOR;
            loopSize++;
        }
        return loopSize;
    }

    private static long transform(long subjectNumber, long loopSize) {
        long value = 1;
        for (int i = 0; i < loopSize; i++) {
            value = value * subjectNumber % DIVISOR;
        }
        return value;
    }
}
