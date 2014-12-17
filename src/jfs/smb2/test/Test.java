package jfs.smb2.test;

import java.io.IOException;

import jfs.smb2.client.ProtocolClient;

import java.security.MessageDigest;

import com.sun.javafx.collections.MappingChange.Map;

import java.util.Arrays;
import java.util.HashMap;

import jfs.common.algorithm.MD4;

public class Test {

    public static void main(String[] args) {
        /*
         * ProtocolClient client = new ProtocolClient(); try {
         * client.connect("zhyang");
         * 
         * } catch (IOException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */
        TestMD4();
    }

    public static void TestMD4() {
        for (String str : MD4TestData.keySet())
        {
            System.out.println(String.format("Test str: \"%1$s\"", str));
            try {
                MD4 md4Digest = new MD4();
                md4Digest.update(str.getBytes());
                byte[] digest = md4Digest.digest();
                String digestStr = "";
                for (byte b : digest) {
                    digestStr += String.format("%1$02x", b);
                }
                String expected = MD4TestData.get(str);
                System.out.println("expected: " + expected);
                System.out.println("actual: " + digestStr);
                // System.out.println(usernameMD5);
                System.out.println(expected.equals(digestStr) ? "Passed" : "Failed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static HashMap<String, String> MD4TestData = new HashMap<String, String>(){{
        put("", "31d6cfe0d16ae931b73c59d7e0c089c0");
        put("a", "bde52cb31de33e46245e05fbdbd6fb24");
        put("abc", "a448017aaf21d8525fc10ae87aa6729d");
        put("message digest", "d9130a8164549fe818874806e1c7014b");
        put("abcdefghijklmnopqrstuvwxyz", "d79e1c308aa5bbcdeea8ed63df412da9");
        put("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "043f8582f241db351ce627e153e7f0e4");
        put("12345678901234567890123456789012345678901234567890123456789012345678901234567890", "e33b4ddc9c38f2199c3e7b164fcc0536");
    }};
}
