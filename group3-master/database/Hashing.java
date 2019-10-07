package database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Hashing {


    public static String sha256(String input){

        String output;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedhash = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8));

            output = bytesToHex(encodedhash);
            System.out.println(output);
            return output;


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }



    private static String bytesToHex(byte[] hash){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++){
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();

    }

    public static void main(String[] args){

        while (true){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

        }

    }


}


