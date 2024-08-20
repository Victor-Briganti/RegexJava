package org.parser;

/**
 * Enum for the errors that can occur during the parser.
 * 
 * Each error type is associated with a unique negative integer value.
 */
public enum ErrorType {
    SUCCESSFUL(0),
    INVALID_TOKEN(-1),
    PARENTHESE_WITHOUT_CLOSE(-2),
    DOUBLE_STAR(-3);

    // Stored value of the error.
    Integer value;

    /**
     * Constructor for the enum.
     *
     * @param value The negative integer representing the error type.
     */
    ErrorType(Integer value) {
        this.value = value;
    }

    /**
     * Returns the value associated with this error type.
     *
     * @return A negative integer representing the error type.
     */
    public Integer getValue() {
        return this.value;
    }
}
