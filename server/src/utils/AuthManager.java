package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, String> users;

    public AuthManager() {
        users = new HashMap<>();
        users.put("ivan", "9a0a82f0c0cf31470d7affede3406cc9aa8410671520b727044eda15b4c25532a9b5cd8aaf9cec4919d76255b6bfb00f");
    }

    public boolean checkUser(String login, String pass){
        String encryptedPass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(pass.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            encryptedPass = no.toString(16);
            while (encryptedPass.length() < 32) {
                encryptedPass = "0" + encryptedPass;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users.containsKey(login) && users.get(login).equals(encryptedPass);
    }

    public void addUser(String login, String pass){
        users.put(login, pass);
    }
}
