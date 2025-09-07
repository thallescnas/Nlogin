package me.natu.nlogin.main.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public final class Encryptor {

    private static final int ITERATIONS = 3;
    private static final int MEMORY_KIB = 65536;
    private static final int PARALLELISM = 2;

    private static final Argon2 ARGON2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);


    public static String hashPassword(String plainPassword) {
        return ARGON2.hash(ITERATIONS, MEMORY_KIB, PARALLELISM, plainPassword.toCharArray());
    }

    public static boolean verifyPassword(String plainPassword, String hash) {
        return ARGON2.verify(hash, plainPassword.toCharArray());
    }

}
