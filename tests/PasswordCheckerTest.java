public class PasswordCheckerTest {
    public static void main(String[] args) {
        expect(PasswordChecker.checkPassword("").score == 0, "Empty password should score 0");
        expect(PasswordChecker.checkPassword("abc").score == 1, "Lowercase-only short password should score 1");
        expect(PasswordChecker.checkPassword("Abcdef12!").score == 5, "Password meeting all rules should score 5");
        expect(PasswordChecker.checkPassword("alllowercase").score == 2, "Long lowercase password should score 2");
        System.out.println("All PasswordChecker tests passed.");
    }

    private static void expect(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
