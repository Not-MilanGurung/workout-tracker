package beds.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beds.database.DatabaseConnection;

public class Authentication {
	public static boolean registerUser(String username, String password) {
        if (userExists(username)) return false;
        String hashedPassword = HashAndCheck.getHash(password);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (Username, Hash) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()){
             PreparedStatement stmt = conn.prepareStatement("SELECT Hash, UserID FROM Users WHERE Username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("Hash");
                if(HashAndCheck.checkPass(password, storedHash)){
					DatabaseConnection.setID(rs.getInt("UserID"));
					return true;
				}
				else{
					return false;
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean userExists(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT UserID FROM Users WHERE Username = ?")) {
            stmt.setString(1, username);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
