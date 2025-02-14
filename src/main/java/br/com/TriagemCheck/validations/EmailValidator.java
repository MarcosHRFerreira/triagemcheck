package br.com.TriagemCheck.validations;


public class EmailValidator {

    // Singleton instance
    private static final EmailValidator instance = new EmailValidator();

    // Private constructor to prevent instantiation
    private EmailValidator() {}

    // Method to get the singleton instance
    public static EmailValidator getInstance() {
        return instance;
    }

    // Method to validate email addresses
    public boolean isValid(String email) {
        // Implement your email validation logic here
        // Example using a simple regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

}
