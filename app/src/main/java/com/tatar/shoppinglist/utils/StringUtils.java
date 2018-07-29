package com.tatar.shoppinglist.utils;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.WordUtils;

import java.util.UUID;

public class StringUtils {

    /**
     * Gets an item name and makes the each word of the name title case for data consistency
     */
    public static String standardizeItemName(String name) {

        String normalizedString = name.trim().replaceAll("\\s+", " ");

        return WordUtils.capitalizeFully(normalizedString);
    }

    /*
     * Creates a fake id that is used to determine which lists belong to which users in Firebase.
     * Just a work around for authentication since I did not have time to implement a real authentication via Firebase.
     */
    public static String generateFakeUserId() {
        String ts = String.valueOf(System.currentTimeMillis());
        String rand = UUID.randomUUID().toString();

        return new String(Hex.encodeHex(DigestUtils.sha(ts + rand)));
    }

}
