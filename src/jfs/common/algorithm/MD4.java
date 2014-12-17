package jfs.common.algorithm;

import java.util.Arrays;

public final class MD4 {
    private byte[] message;

    // Temp variables used in each round
    private int A;
    private int B;
    private int C;
    private int D;
    private int[] X;

    private static final int word = 32 / 8; // 4
    private static final int crem = 448 / 8; // 56
    private static final int cmod = 512 / 8; // 64
    private static final int blockSize = cmod / word; // 16
    private static final int IA = 0x67452301;
    private static final int IB = 0xefcdab89;
    private static final int IC = 0x98badcfe;
    private static final int ID = 0x10325476;
    private static final int op2const = 0x5A827999;
    private static final int op3const = 0x6ED9EBA1;

    public void update(byte[] input) {
        message = input;
        X = new int[blockSize]; // X = new byte[16];
    }

    public byte[] digest() {
        byte[] bytes = createInputArray();
        append(bytes);
        int[] M = byteArrayToIntArrayLE(bytes);

        A = IA;
        B = IB;
        C = IC;
        D = ID;
        int AA, BB, CC, DD;

        for (int i = 0; i < M.length / blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                X[j] = M[i * blockSize + j];
            }

            AA = A;
            BB = B;
            CC = C;
            DD = D;

            round1();
            round2();
            round3();

            A = A + AA;
            B = B + BB;
            C = C + CC;
            D = D + DD;
        }

        int[] output = { A, B, C, D };
        return intArrayToByteArrayLE(output);
    }

    private byte[] createInputArray() {
        int outputLen = message.length;
        int quot = outputLen / cmod;
        int rem = outputLen % cmod;

        quot++;
        if (rem >= crem) {
            quot++;
        }

        outputLen = quot * cmod;

        return Arrays.copyOf(message, outputLen);
    }

    private void append(byte[] input) {
        int oriLen = message.length;
        int lensPos = input.length - cmod + crem; // input.length - 8;
        input[oriLen] = (byte) 0x80;
        // Arrays.fill(input, oriLen + 1, lensPos, 0);
        byte[] lenBytes = longToByteArrayLE(oriLen * 8l);
        System.arraycopy(lenBytes, 0, input,
                lensPos, lenBytes.length);
        // Arrays.fill(input, lensPos + 4, input.length, 0);
    }

    // (a + F(b,c,d) + X[k]) <<< s
    private int op1(int a, int b, int c, int d, int k, int s) {
        return bitsRotateLeft(a + F(b, c, d) + X[k], s);
    }

    // (a + G(b,c,d) + X[k] + 5A827999) <<< s
    private int op2(int a, int b, int c, int d, int k, int s) {
        return bitsRotateLeft(a + G(b, c, d) + X[k] + op2const, s);
    }

    // (a + H(b,c,d) + X[k] + 6ED9EBA1) <<< s
    private int op3(int a, int b, int c, int d, int k, int s) {
        return bitsRotateLeft(a + H(b, c, d) + X[k] + op3const, s);
    }

    private void round1() {
        A = op1(A, B, C, D, 0, 3);
        D = op1(D, A, B, C, 1, 7);
        C = op1(C, D, A, B, 2, 11);
        B = op1(B, C, D, A, 3, 19);
        A = op1(A, B, C, D, 4, 3);
        D = op1(D, A, B, C, 5, 7);
        C = op1(C, D, A, B, 6, 11);
        B = op1(B, C, D, A, 7, 19);
        A = op1(A, B, C, D, 8, 3);
        D = op1(D, A, B, C, 9, 7);
        C = op1(C, D, A, B, 10, 11);
        B = op1(B, C, D, A, 11, 19);
        A = op1(A, B, C, D, 12, 3);
        D = op1(D, A, B, C, 13, 7);
        C = op1(C, D, A, B, 14, 11);
        B = op1(B, C, D, A, 15, 19);
    }

    private void round2() {
        A = op2(A, B, C, D, 0, 3);
        D = op2(D, A, B, C, 4, 5);
        C = op2(C, D, A, B, 8, 9);
        B = op2(B, C, D, A, 12, 13);
        A = op2(A, B, C, D, 1, 3);
        D = op2(D, A, B, C, 5, 5);
        C = op2(C, D, A, B, 9, 9);
        B = op2(B, C, D, A, 13, 13);
        A = op2(A, B, C, D, 2, 3);
        D = op2(D, A, B, C, 6, 5);
        C = op2(C, D, A, B, 10, 9);
        B = op2(B, C, D, A, 14, 13);
        A = op2(A, B, C, D, 3, 3);
        D = op2(D, A, B, C, 7, 5);
        C = op2(C, D, A, B, 11, 9);
        B = op2(B, C, D, A, 15, 13);
    }

    private void round3() {
        A = op3(A, B, C, D, 0, 3);
        D = op3(D, A, B, C, 8, 9);
        C = op3(C, D, A, B, 4, 11);
        B = op3(B, C, D, A, 12, 15);
        A = op3(A, B, C, D, 2, 3);
        D = op3(D, A, B, C, 10, 9);
        C = op3(C, D, A, B, 6, 11);
        B = op3(B, C, D, A, 14, 15);
        A = op3(A, B, C, D, 1, 3);
        D = op3(D, A, B, C, 9, 9);
        C = op3(C, D, A, B, 5, 11);
        B = op3(B, C, D, A, 13, 15);
        A = op3(A, B, C, D, 3, 3);
        D = op3(D, A, B, C, 11, 9);
        C = op3(C, D, A, B, 7, 11);
        B = op3(B, C, D, A, 15, 15);
    }

    private static byte[] intArrayToByteArrayLE(int[] ints) {
        int bytesLen = 4 * ints.length;
        byte[] bytes = new byte[bytesLen];
        int bi;
        int v;
        for (int i = 0; i < ints.length; i++) {
            bi = 4 * i;
            v = ints[i];
            bytes[bi] = (byte) (v & 0xFF);
            bytes[bi + 1] = (byte) ((v >> 8) & 0xFF);
            bytes[bi + 2] = (byte) ((v >> 16) & 0xFF);
            bytes[bi + 3] = (byte) ((v >> 24) & 0xFF);
        }
        return bytes;
    }

    private static int[] byteArrayToIntArrayLE(byte[] bytes) {
        int intsLen = bytes.length / 4;
        int[] ints = new int[intsLen];
        int bi;
        for (int i = 0; i < intsLen; i++) {
            bi = 4 * i;
            ints[i] = ((int) bytes[bi] & 0xff)
                    | (((int) bytes[bi + 1] & 0xff) << 8)
                    | (((int) bytes[bi + 2] & 0xff) << 16)
                    | (((int) bytes[bi + 3] & 0xff) << 24);
        }
        return ints;
    }

    private static byte[] longToByteArrayLE(long input) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (input & 0xFF);
        bytes[1] = (byte) ((input >> 8) & 0xFF);
        bytes[2] = (byte) ((input >> 16) & 0xFF);
        bytes[3] = (byte) ((input >> 24) & 0xFF);
        bytes[4] = (byte) ((input >> 32) & 0xFF);
        bytes[5] = (byte) ((input >> 40) & 0xFF);
        bytes[6] = (byte) ((input >> 48) & 0xFF);
        bytes[7] = (byte) ((input >> 56) & 0xFF);
        return bytes;
    }

    private static int bitsRotateLeft(int i, int k) {
        return (i << k) | (i >>> (Integer.SIZE - k));
    }

    private static int F(int X, int Y, int Z) {
        return (X & Y) | (~X & Z);
    }

    private static int G(int X, int Y, int Z) {
        return (X & Y) | (X & Z) | (Y & Z);
    }

    private static int H(int X, int Y, int Z) {
        return X ^ Y ^ Z;
    }
}
