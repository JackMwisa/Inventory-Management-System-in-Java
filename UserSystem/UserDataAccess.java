package UserSystem;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDataAccess {
    public static final Path userFile = Paths.get("src", "FILEDB", "User.txt").toAbsolutePath();

    // Load all users from the file
    public static ArrayList<UserTemp> loadUsers() {
        ArrayList<UserTemp> users = new ArrayList<>();
        try (Scanner input = new Scanner(new FileReader(userFile.toString()))) {
            while (input.hasNextLine()) {
                String[] data = input.nextLine().split(",");
                users.add(new UserTemp(data[0], data[1], data[2], data[3], Double.parseDouble(data[4])));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Save all users to the file
    public static void saveUsers(ArrayList<UserTemp> users) {
        try (FileWriter writer = new FileWriter(userFile.toString())) {
            for (UserTemp user : users) {
                writer.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}
