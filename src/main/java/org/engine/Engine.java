package org.engine;

import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

import org.state.State;
import org.matcher.PatternMatcher;

/**
 * The Engine class is responsible for evaluating regular expressions.
 * It processes the states that are generated after the compilation of the
 * regular expressions.
 */
public class Engine {

    /**
     * Represents the current state of evaluation and the index within
     * the input string that is being processed.
     */
    static class Context {
        State state;
        int index;

        /**
         * Constructs a Context with the specified index and state.
         * 
         * @param index The current position in the input string.
         * @param state The current state of the evaluation process.
         *              Should not be null
         */
        Context(int index, State state) {
            if (state == null) {
                throw new IllegalArgumentException("Context saved state should not be null");
            }

            this.state = state;
            this.index = index;
        }

        /**
         * Getter for the Context saved index.
         * 
         * @return The context saved index
         */
        public int getIndex() {
            return index;
        }

        /**
         * Getter for the Context saved state
         * 
         * @return The context saved state
         */
        public State getState() {
            return state;
        }

    }

    // Set containing all the states of the regex
    private final Set<State> states;

    // Start state of the regex
    // Represent the first state from which the evaluation begins.
    private State startState;

    // All the final states of the expression.
    // Represents the ones that turn the automaton in to the accept state.
    private Set<State> finalStates;

    /**
     * Computes whether the input string is accepted by the regular expression.
     * This method uses a stack-based approach to perform a depth-first search
     * of the automaton's states.
     * 
     * @param input The input string to be evaluated.
     * @return true if the input string is accepted by the regular expression,
     *         false otherwise.
     */
    private boolean executeRegex(String input) {
        Stack<Context> stack = new Stack<>();
        stack.push(new Context(0, startState));

        while (!stack.isEmpty()) {
            Context current = stack.pop();
            int index = current.getIndex();
            State state = current.getState();

            if (finalStates.contains(state)) {
                return true;
            }

            char symbol = index >= input.length() ? '\0' : input.charAt(index);

            // Transitions are pushed in reverse order because we want the first to be in
            // the last position of the stack
            for (int i = state.getTransitions().size() - 1; i >= 0; i--) {
                State.Transition transition = state.getTransitions().get(i);
                State toState = transition.state();
                PatternMatcher matcher = transition.patternMatcher();

                if (matcher.tokenMatch(symbol)) {
                    int nextIndex = matcher.isEpsilon() ? index : index + 1;
                    stack.push(new Context(nextIndex, toState));
                }

            }
        }

        return false;
    }

    /**
     * Constructor for the execution engine
     */
    public Engine() {
        states = new HashSet<>();
        startState = null;
        finalStates = new HashSet<>();
    }

    /**
     * Returns the set of states in the regular expression.
     * 
     * @return The set of states.
     */
    public Set<State> getStates() {
        return states;
    }

    /**
     * Inserts a new state on the set of states
     * 
     * @param state The new state to be inserted
     */
    public void addState(State state) {
        states.add(state);
    }

    /**
     * Returns the start state of the regular expression.
     * 
     * @return The start state.
     */
    public State getStartState() {
        return startState;
    }

    /**
     * Sets the start state of the regular expression.
     * 
     * @param startState The new start state to be set.
     */
    public void setStartState(State startState) {
        this.startState = startState;
    }

    /**
     * Returns the set of final states of the regular expression.
     * 
     * @return The set of final states.
     */
    public Set<State> getFinalStates() {
        return finalStates;
    }

    /**
     * Adds a state to the set of final states.
     * 
     * @param state The new state to be inserted
     */
    public void addFinalState(State state) {
        this.finalStates.add(state);
    }

    /**
     * Iterate through the string execution the regular expression until the match
     * is found, or the string reach the end.
     * 
     * @param input The input string to be evaluated.
     * @return true if the input string is accepted by the regular expression,
     *         false otherwise.
     */
    public boolean compute(String input) {
        int i = 0;
        while (i < input.length()) {
            if (executeRegex(input.substring(i))) {
                return true;
            }

            i++;
        }

        return false;
    }

}
