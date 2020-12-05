package day4.part1;

import java.util.HashMap;
import java.util.Map;

public class Passport {
    Map<String, String> info = new HashMap<>();

    public void addInfo(String key, String value) {
        info.put(key, value);
    }

    public boolean isComplete() {
        return info.containsKey("byr")
                && info.containsKey("iyr")
                && info.containsKey("eyr")
                && info.containsKey("hgt")
                && info.containsKey("hcl")
                && info.containsKey("ecl")
                && info.containsKey("pid");
    }
}
