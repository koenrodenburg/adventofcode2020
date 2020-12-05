package day4.part2;

import java.util.HashMap;
import java.util.Map;

public class Passport {
    Map<String, String> info = new HashMap<>();

    public void addInfo(String key, String value) {
        info.put(key, value);
    }

    public boolean isValid() {
        return validateBirthYear()
                && validateIssueYear()
                && validateExpirationYear()
                && validateEyeColor()
                && validateHairColor()
                && validateHeight()
                && validatePassportID();
    }

    private boolean validateBirthYear() {
        if(!info.containsKey("byr")) {
            return false;
        }

        int byr = Integer.parseInt(info.get("byr"));

        return byr >= 1920 && byr <= 2002;
    }

    private boolean validateIssueYear() {
        if(!info.containsKey("iyr")) {
            return false;
        }

        int iyr = Integer.parseInt(info.get("iyr"));

        return iyr >= 2010 && iyr <= 2020;
    }

    private boolean validateExpirationYear() {
        if(!info.containsKey("eyr")) {
            return false;
        }

        int eyr = Integer.parseInt(info.get("eyr"));

        return eyr >= 2020 && eyr <= 2030;
    }

    private boolean validateHeight() {
        if(!info.containsKey("hgt")) {
            return false;
        }

        String hgt = info.get("hgt");

        if(!hgt.endsWith("cm") && ! hgt.endsWith("in")) {
            return false;
        }

        if(hgt.endsWith("cm")) {
            int cm = Integer.parseInt(hgt.substring(0, hgt.indexOf("cm")));
            return cm >= 150 && cm <= 193;
        } else {
            int in = Integer.parseInt(hgt.substring(0, hgt.indexOf("in")));
            return in >= 59 && in <= 76;
        }
    }

    private boolean validateHairColor() {
        if(!info.containsKey("hcl")) {
            return false;
        }

        String hcl = info.get("hcl");

        return hcl.matches("#[0-9a-f]{6}");
    }

    private boolean validateEyeColor() {
        if(!info.containsKey("ecl")) {
            return false;
        }

        String ecl = info.get("ecl");

        return ecl.equals("amb")
                || ecl.equals("blu")
                || ecl.equals("brn")
                || ecl.equals("gry")
                || ecl.equals("grn")
                || ecl.equals("hzl")
                || ecl.equals("oth");
    }

    private boolean validatePassportID() {
        if(!info.containsKey("pid")) {
            return false;
        }

        String hcl = info.get("pid");

        return hcl.matches("[0-9]{9}");
    }

}
