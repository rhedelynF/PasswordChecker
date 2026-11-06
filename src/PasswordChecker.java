import java.util.ArrayList;
import java.util.List;

public class PasswordChecker {
    public static class Result {
        public final int score;
        public final String strength;
        public final List<String> checks;
        public final List<String> suggestions;

        Result(int score, String strength, List<String> checks, List<String> suggestions) {
            this.score = score;
            this.strength = strength;
            this.checks = checks;
            this.suggestions = suggestions;
        }
    }

    public static Result checkPassword(String password) {
        if (password == null) password = "";

        int score = 0;
        List<String> checks = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        boolean longEnough = password.length() >= 8;
        boolean upper = password.matches(".*[A-Z].*");
        boolean lower = password.matches(".*[a-z].*");
        boolean digit = password.matches(".*\\d.*");
        boolean special = password.matches(".*[^A-Za-z0-9].*");

        boolean[] rules = {longEnough, upper, lower, digit, special};
        String[] labels = {
            "At least 8 characters",
            "At least one uppercase letter",
            "At least one lowercase letter",
            "At least one number",
            "At least one special character"
        };

        for (int i = 0; i < rules.length; i++) {
            if (rules[i]) {
                score++;
                checks.add("✓ " + labels[i]);
            } else {
                checks.add("✗ " + labels[i]);
                suggestions.add("Add " + labels[i].toLowerCase() + ".");
            }
        }

        String strength;
        if (score <= 1) strength = "Very Weak";
        else if (score == 2) strength = "Weak";
        else if (score == 3) strength = "Fair";
        else if (score == 4) strength = "Strong";
        else strength = "Very Strong";

        if (password.length() < 12) {
            suggestions.add("Consider using 12 or more characters for better protection.");
        }
        if (password.isEmpty()) {
            suggestions.clear();
            suggestions.add("Enter a password to see how it scores.");
        }

        return new Result(score, strength, checks, suggestions);
    }
}
