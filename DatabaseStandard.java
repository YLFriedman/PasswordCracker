
import java.util.HashMap;

public class DatabaseStandard implements DatabaseInterface{


    public HashMap<String, String> passwordMap;
    private final int INITIAL_SIZE = 200000;

    public DatabaseStandard() {
        this.passwordMap = new HashMap<>(INITIAL_SIZE);
    }



    @Override
    public String save(String plainPassword, String encryptedPassword) {
        passwordMap.put(encryptedPassword, plainPassword);
        return null;
    }

    @Override
    public String decrypt(String encryptedPassword) {
        String password = passwordMap.get(encryptedPassword);
        return password;
    }

    @Override
    public int size() {
        return passwordMap.size();
    }

    @Override
    public void printStatistics() {

        System.out.println("*****Statistics for DatabaseStandard*****");
        System.out.println(String.format("Size is %s passwords", String.valueOf(size())));
        System.out.println(String.format("Initial number of indexes when created was %s", String.valueOf(INITIAL_SIZE)));

    }
}
