import java.util.Scanner;
import java.util.Random;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Password Generator ===");
        
        // Get password length
        System.out.print("Enter password length (8-64): ");
        int length = scanner.nextInt();
        if (length < 8 || length > 64) {
            System.out.println("Invalid length. Using default length of 12.");
            length = 12;
        }
        
        // Get character set options
        System.out.println("Include the following character types:");
        System.out.print("Lowercase letters? (y/n): ");
        boolean useLower = scanner.next().equalsIgnoreCase("y");
        System.out.print("Uppercase letters? (y/n): ");
        boolean useUpper = scanner.next().equalsIgnoreCase("y");
        System.out.print("Numbers? (y/n): ");
        boolean useNumbers = scanner.next().equalsIgnoreCase("y");
        System.out.print("Special characters? (y/n): ");
        boolean useSpecial = scanner.next().equalsIgnoreCase("y");
        
        // Validate at least one character set is selected
        if (!useLower && !useUpper && !useNumbers && !useSpecial) {
            System.out.println("At least one character type must be selected. Using all types.");
            useLower = useUpper = useNumbers = useSpecial = true;
        }
        
        // Generate password
        String password = generatePassword(length, useLower, useUpper, useNumbers, useSpecial);
        
        // Display results
        System.out.println("\nGenerated Password: " + password);
        System.out.println("Password Strength: " + checkPasswordStrength(password));
        
        scanner.close();
    }

    public static String generatePassword(int length, boolean useLower, boolean useUpper, 
                                         boolean useNumbers, boolean useSpecial) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        
        // Build the character set based on user preferences
        StringBuilder charSet = new StringBuilder();
        if (useLower) charSet.append(LOWERCASE);
        if (useUpper) charSet.append(UPPERCASE);
        if (useNumbers) charSet.append(NUMBERS);
        if (useSpecial) charSet.append(SPECIALS);
        
        // Ensure at least one character from each selected set is included
        if (useLower) {
            password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        }
        if (useUpper) {
            password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        }
        if (useNumbers) {
            password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        if (useSpecial) {
            password.append(SPECIALS.charAt(random.nextInt(SPECIALS.length())));
        }
        
        // Fill the rest of the password with random characters from the combined set
        for (int i = password.length(); i < length; i++) {
            password.append(charSet.charAt(random.nextInt(charSet.length())));
        }
        
        // Shuffle the characters to mix the mandatory characters
        return shuffleString(password.toString());
    }
    
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        Random random = new Random();
        
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        
        return new String(characters);
    }
    
    public static String checkPasswordStrength(String password) {
        int strength = 0;
        
        // Check length
        if (password.length() >= 12) strength++;
        if (password.length() >= 16) strength++;
        
        // Check character diversity
        boolean hasLower = false, hasUpper = false, hasNumber = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (LOWERCASE.indexOf(c) != -1) hasLower = true;
            else if (UPPERCASE.indexOf(c) != -1) hasUpper = true;
            else if (NUMBERS.indexOf(c) != -1) hasNumber = true;
            else if (SPECIALS.indexOf(c) != -1) hasSpecial = true;
        }
        
        if (hasLower) strength++;
        if (hasUpper) strength++;
        if (hasNumber) strength++;
        if (hasSpecial) strength++;
        
        // Determine strength level
        if (strength <= 3) return "Weak";
        else if (strength <= 6) return "Moderate";
        else if (strength <= 8) return "Strong";
        else return "Very Strong";
    }
}