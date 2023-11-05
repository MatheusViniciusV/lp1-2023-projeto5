package br.cefetmg.snacksmart.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SenhaManager {
    public static String fazHash(final String senha) {
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("SHA-256");

            byte[] hash = new byte[0];
            hash = algorithm.digest(senha.getBytes("UTF-8"));

            StringBuilder senhaHash = new StringBuilder();
            for (byte b : hash) {
                senhaHash.append(String.format("%02X", 0xFF & b));
            }

            return senhaHash.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
