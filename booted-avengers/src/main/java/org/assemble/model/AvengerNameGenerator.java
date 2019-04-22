package org.assemble.model;

import java.util.HashSet;
import java.util.Set;

public class AvengerNameGenerator {

    private static final Set<String> used = new HashSet<>();
    
    private static final String[] ADJECTIVES = {
        "Iron", "Captain", "Hulk", "Thor", "Black", "Hawkeye", "Scarlet", "Vision", "Mantis", "Star", "War", "Spider", "Doctor", "Rocket", "Wasp", "Shuri", "Winter"
    };

    private static final String[] NOUNS = {
        "Man", "America", "Marvel", "Widow", "Panther", "Witch", "Machine", "Strange", "Gamora", "Lord", "Falcon", "Groot", "Raccoon", "Okoye", "Soldier", "Drax"
    };
    
    public static String generateName() {
        int i = 0;
        String username = null;
        while (i < 100) {
            int random1 = (int) Math.floor(Math.random() * ADJECTIVES.length);
            int random2 = (int) Math.floor(Math.random() * NOUNS.length);
            username = ADJECTIVES[random1] + " " + NOUNS[random2];

            if (!used.add(username)) {
                i++;
            } else {
                return username;
            }
        }

        return username;
    }
}
