package com.tatar.shoppinglist.utils;

public class StringUtils {

    /**
     * Gets an item name and capitalizes first letter of the each word for data consistency
     */
    public static String standardizeItemName(String name) {
        String trimmedName = name.trim();
        String[] words = trimmedName.split(" ");

        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }

        return sb.toString().trim();
    }

}
