package org.state;

import java.util.ArrayList;

import org.matcher.PatternMatcher;

/**
 * Represents a state that in the automaton. The state is used to match the
 * current symbol and transition to the next valid state.
 */
public class State {

    /**
     * Represents a transition in the automaton. A transition defines how the
     * automaton moves from one state to the other based on the specific pattern
     * matching.
     */
    public record Transition(State state, PatternMatcher patternMatcher) {
        public Transition {
            if (state == null) {
                throw new IllegalArgumentException("State must not be null");
            }

            if (patternMatcher == null) {
                throw new IllegalArgumentException("PatternMatch must not be null");
            }
        }
    }

    // Represents the name of the states 'q0', 'q1' ... 'qn'
    // Used only for debugging purposes.
    private final String name;

    // List with all the accepted transitions that this state have.
    private final ArrayList<Transition> transitions;

    /**
     * Constructor of the state
     * 
     * @param name the name of the state
     */
    public State(String name) {
        this.name = name;
        transitions = new ArrayList<Transition>();
    }

    /**
     * Adds a transition from this state to a specified target state.
     * The new transition is appended to the end of the transition list.
     * 
     * @param state          the target state for the transition
     * @param patternMatcher the pattern matcher defining the transition condition.
     */
    public void addTransition(State state, PatternMatcher patternMatcher) {
        transitions.add(new Transition(state, patternMatcher));
    }

    /**
     * Adds a transition from this state to a specified target state.
     * The new transition is prepended to the end of the transition list.
     * 
     * @param state          the target state for the transition
     * @param patternMatcher the pattern matcher defining the transition condition.
     */
    public void addHighestTransition(State state, PatternMatcher patternMatcher) {
        transitions.addFirst(new Transition(state, patternMatcher));
    }

    /**
     * Getter for the list of transitions of this state
     * 
     * @return a list of transitions associated with this state
     */
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    /**
     * Getter for the name of the state
     * 
     * @return the name of the state
     */
    public String getName() {
        return name;
    }
}
