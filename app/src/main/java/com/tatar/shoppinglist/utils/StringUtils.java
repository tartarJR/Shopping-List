package com.tatar.shoppinglist.utils;


import org.apache.commons.text.WordUtils;

public class StringUtils {

    /**
     * Gets an item name and makes the each word of the name title case for data consistency
     */
    public static String standardizeItemName(String name) {

        String normalizedString = name.trim().replaceAll("\\s+", " ");

        return WordUtils.capitalizeFully(normalizedString);
    }

}
