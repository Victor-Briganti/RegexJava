package org.lexer;

public enum Token {
    PARENTHESE_OPEN, // (
    PARENTHESE_CLOSE, // )
    STAR, // *
    UNION, // |
    SLASH, // \
    CHARACTER, // Any character (including metachars)
    EOF, // Defines the end of the expression
    NOT_FOUND, // Defined in case of a error
}
