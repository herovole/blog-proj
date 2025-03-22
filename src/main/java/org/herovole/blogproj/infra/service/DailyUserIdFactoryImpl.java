package org.herovole.blogproj.infra.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.publicuser.DailyUserId;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.time.Date;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyUserIdFactoryImpl implements DailyUserIdFactory {

    private static final int HASH_LENGTH = 8;
    private static final String TRAIL = "0";

    // Generate hash with specified length based on multiple keys
    private static String generateHash(String[] keys, int length) throws NoSuchAlgorithmException {
        // Step 1: Combine keys into a single string
        StringBuilder combinedKeys = new StringBuilder();
        for (String key : keys) {
            combinedKeys.append(key);
        }

        // Step 2: Use SHA-256 to hash the combined string
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(combinedKeys.toString().getBytes(StandardCharsets.UTF_8));

        // Step 3: Convert the hash bytes to a hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        // Step 4: Truncate or adjust the hash to the specified length
        String hash = hexString.toString();
        return (length > 0 && length < hash.length()) ? hash.substring(0, length) : hash;
    }

    public static DailyUserIdFactoryImpl of(String key0) {
        return new DailyUserIdFactoryImpl(key0);
    }

    private final String key0;

    @Override
    public DailyUserId generate(IPv4Address address, Date date) throws NoSuchAlgorithmException {
        String[] keys = new String[]{key0, String.valueOf(address.aton()), date.letterSignature()};
        return DailyUserId.valueOf(generateHash(keys, HASH_LENGTH) + TRAIL);
    }


}
