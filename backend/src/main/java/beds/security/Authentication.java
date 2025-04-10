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

	public static int updateUser(String newUsername, String password) {
		if (userExists(newUsername)) return -1;

		try (Connection conn = DatabaseConnection.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT Hash FROM Users WHERE UserID = ?");
			stmt.setInt(1, DatabaseConnection.getUserID());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String storedHash = rs.getString("Hash");
				if (!HashAndCheck.checkPass(password, storedHash)) {
					return -2; // Password is incorrect
				}
			} else {
				return -3; // User not found
			}
			stmt = conn.prepareStatement("UPDATE Users SET Username = ? WHERE UserID = ?");
			stmt.setString(1, newUsername);
			stmt.setInt(2, DatabaseConnection.getUserID());
			stmt.executeUpdate();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -3;
	}

	public static int updatePassword(String oldPassword, String newPassword) {
		Connection con = DatabaseConnection.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT Hash FROM Users WHERE UserID = ?");
			stmt.setInt(1, DatabaseConnection.getUserID());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String storedHash = rs.getString("Hash");
				if (!HashAndCheck.checkPass(oldPassword, storedHash)) {
					return -1; // Password is incorrect
				}
			} else {
				return -2; // User not found
			}
			stmt = con.prepareStatement("UPDATE Users SET Hash = ? WHERE UserID = ?");
			stmt.setString(1, HashAndCheck.getHash(newPassword));
			stmt.setInt(2, DatabaseConnection.getUserID());
			stmt.executeUpdate();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -2;
	}

	public static String getUsername() {
		Connection conn = DatabaseConnection.getConnection();
		try (PreparedStatement stmt = conn.prepareStatement("SELECT Username FROM Users WHERE UserID = ?")) {
			stmt.setInt(1, DatabaseConnection.getUserID());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("Username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
