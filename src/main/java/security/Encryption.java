package security;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Encryption {
    public String encrypt(String pw) {
        return Hashing.sha256().hashString(pw, StandardCharsets.UTF_8).toString();
    }
}
