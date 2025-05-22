package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidationUtil {

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(StringUtils.DRIVER_NAME);
        return DriverManager.getConnection(StringUtils.LOCALHOST_URL,
                StringUtils.LOCALHOST_USERNAME, StringUtils.LOCALHOST_PASSWORD);
    }

    /**
     * Validates if the provided text contains only letters and whitespace.
     * 
     * @param text The text to be validated.
     * @return True if the text contains only letters and whitespace, false otherwise.
     */
    public static boolean isTextOnly(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return text.matches("[a-zA-Z\\s]+");
    }

    /**
     * Validates if the provided text contains only digits (0-9).
     * 
     * @param text The text to be validated.
     * @return True if the text contains only digits, false otherwise.
     */
    public static boolean isNumbersOnly(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return text.matches("\\d+");
    }

    /**
     * Validates if the provided text is alphanumeric, containing only letters, digits, and whitespace.
     * 
     * @param text The text to be validated.
     * @return True if the text is alphanumeric, false otherwise.
     */
    public static boolean isAlphanumeric(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return text.matches("[a-zA-Z0-9\\s]+");
    }

    /**
     * Validates if the provided text is a valid email address format.
     * 
     * @param email The email address to be validated.
     * @return True if the email address has a valid format, false otherwise.
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");
    }

    /**
     * Validates if the provided text contains no special characters other than letters, digits, and whitespace.
     * 
     * @param text The text to be validated.
     * @return True if the text contains no special characters, false otherwise.
     */
    public static boolean hasNoSpecialCharacters(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return text.matches("[a-zA-Z0-9\\s]+");
    }

    /**
     * Validates if two passwords match.
     * 
     * @param password The first password.
     * @param retypePassword The second password.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        if (password == null || retypePassword == null) {
            return false;
        }
        return password.equals(retypePassword);
    }

    /**
     * Validates if the provided password meets complexity requirements:
     * - Contains at least one uppercase letter (A-Z)
     * - Contains at least one lowercase letter (a-z)
     * - Contains at least one digit (0-9)
     * - Contains at least one symbol (@$!%*?&).
     * 
     * @param password The password to be validated.
     * @return True if the password meets complexity requirements, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$");
    }

    /**
     * Validates if the provided text has the specified length.
     * 
     * @param text The text to be validated.
     * @param length The expected length of the text.
     * @return True if the text has the specified length, false otherwise.
     */
    public static boolean hasLength(String text, int length) {
        if (text == null) {
            return false;
        }
        return text.length() == length;
    }

    /**
     * Validates if the provided text has at least the specified minimum length.
     * 
     * @param text The text to be validated.
     * @param minLength The minimum expected length of the text.
     * @return True if the text is at least the minimum length, false otherwise.
     */
    public static boolean hasMinLength(String text, int minLength) {
        if (text == null) {
            return false;
        }
        return text.length() >= minLength;
    }

    /**
     * Validates if the provided text has at most the specified maximum length.
     * 
     * @param text The text to be validated.
     * @param maxLength The maximum expected length of the text.
     * @return True if the text is at most the maximum length, false otherwise.
     */
    public static boolean hasMaxLength(String text, int maxLength) {
        if (text == null) {
            return false;
        }
        return text.length() <= maxLength;
    }

    /**
     * Validates if the provided value is within a specified range (inclusive).
     * 
     * @param value The value to check.
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return True if the value is within range, false otherwise.
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Validates if the provided text value can be converted to a valid integer.
     * 
     * @param text The text to validate.
     * @return True if the text is a valid integer, false otherwise.
     */
    public static boolean isInteger(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the provided text value can be converted to a valid float.
     * 
     * @param text The text to validate.
     * @return True if the text is a valid float, false otherwise.
     */
    public static boolean isFloat(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the provided phone number is in a valid format.
     * Expecting: digits only, 10-15 digits.
     * 
     * @param phoneNumber The phone number to validate.
     * @return True if the phone number is valid, false otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return phoneNumber.matches("\\d{10}");
    }

    /**
     * Validates if the provided text is not null and not empty after trimming.
     * 
     * @param text The text to validate.
     * @return True if the text is not null and not empty, false otherwise.
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Validates if the provided text is a valid age (numeric and reasonable range).
     * 
     * @param age The age to validate.
     * @return True if the age is valid, false otherwise.
     */
    public static boolean isValidAge(String age) {
        if (!isInteger(age)) {
            return false;
        }
        int ageInt = Integer.parseInt(age);
        return isInRange(ageInt, 18, 100); // Assuming reasonable age range
    }

    /**
     * Validates if the provided text is a valid price (positive float).
     * 
     * @param price The price to validate.
     * @return True if the price is valid, false otherwise.
     */
    public static boolean isValidPrice(String price) {
        if (!isFloat(price)) {
            return false;
        }
        float priceFloat = Float.parseFloat(price);
        return priceFloat > 0;
    }

    /**
     * Validates if the provided duration is valid (positive integer).
     * 
     * @param duration The duration to validate.
     * @return True if the duration is valid, false otherwise.
     */
    public static boolean isValidDuration(String duration) {
        if (!isInteger(duration)) {
            return false;
        }
        int durationInt = Integer.parseInt(duration);
        return durationInt > 0;
    }

    /**
     * Validates an image file based on the file name, checks if it's a common image format.
     * 
     * @param fileName The name of the image file.
     * @return True if it's a valid image file, false otherwise.
     */
    public static boolean isValidImageFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return true; // No file is valid (using default image)
        }
        return fileName.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif|bmp)$");
    }

    /**
     * Validates if the provided subject contains a reasonable amount of text.
     * 
     * @param subject The subject to validate.
     * @return True if the subject is valid, false otherwise.
     */
    public static boolean isValidSubject(String subject) {
        return isNotEmpty(subject) && hasMaxLength(subject, 100);
    }

    /**
     * Validates if the provided message contains a reasonable amount of text.
     * 
     * @param message The message to validate.
     * @return True if the message is valid, false otherwise.
     */
    public static boolean isValidMessage(String message) {
        return isNotEmpty(message) && hasMaxLength(message, 2000);
    }

    // Database check methods

    public static boolean isUsernameTaken(String username) {
        try (Connection conn = new ValidationUtil().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM member WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Username exists if count is greater than 0
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isEmailTaken(String email) {
        try (Connection conn = new ValidationUtil().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM member WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Email exists if count is greater than 0
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPhoneNumberTaken(String phoneNumber) {
        try (Connection conn = new ValidationUtil().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM member WHERE phone_number = ?")) {
            stmt.setString(1, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Phone number exists if count is greater than 0
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUsernameTakenCoach(String username) {
        try (Connection conn = new ValidationUtil().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM admin WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Username exists if count is greater than 0
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean isEmailTakenCoach(String email) {
        try (Connection conn = new ValidationUtil().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM admin WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Email exists if count is greater than 0
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPhoneNumberTakenCoach(String phoneNumber) {
        try (Connection conn = new ValidationUtil().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM admin WHERE phone_number = ?")) {
            stmt.setString(1, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Phone number exists if count is greater than 0
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

  

    // 3. Check if plan name contains no numbers
    public static boolean isValidPlanName(String planName) {
        if (planName == null || planName.trim().isEmpty()) {
            return false;
        }
        return !planName.matches(".*\\d.*"); // returns true if it does NOT contain any digit
    }
    
    
}