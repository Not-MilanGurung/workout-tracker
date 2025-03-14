package beds.security;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class HashAndCheck {
	public static String getHash(String plainTextPassword) {
		return BCrypt.withDefaults().hashToString(12, plainTextPassword.toCharArray());
	}

	public static Boolean checkPass(String plainTextPassword, String hashedPassword) {
		return BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword).verified;
	}
}
