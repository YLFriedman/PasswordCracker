
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PasswordCracker {

    public void createDatabase(ArrayList<String> commonPasswords, DatabaseInterface database) {
        ArrayList<String> augmentedPasswords = new ArrayList<>(commonPasswords);
        for(int i = 0; i < commonPasswords.size(); i ++ ) {
            String current = commonPasswords.get(i);
            ArrayList<String> permutations = PermutationFinder.findPermutations(current);
            augmentedPasswords.addAll(permutations);
        }
        for(int i = 0; i < augmentedPasswords.size(); i ++ ) {
            String plaintext = augmentedPasswords.get(i);
            try {
                String encrypted = Sha1.hash(plaintext);
                database.save(plaintext, encrypted);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public String crackPassword(String encryptedPassword, DatabaseInterface database) {
        return database.decrypt(encryptedPassword);
    }
}
