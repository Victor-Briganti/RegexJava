package org.lexer;

/**
 * Represents the various types of tokens that can be identified by the
 * lexer.
 */
public enum Token {
    PARENTHESE_OPEN, // '('
    PARENTHESE_CLOSE, // ')'
    STAR, // '*'
    UNION, // '|'
    SLASH, // '\'
    ANY, // '.'
    PLUS, // '+'
    QUESTION, // '?'
    CHARACTER, // A literal character
    EOF, // Defines the end of the expression
    NOT_FOUND, // Defined in case of an error
}
