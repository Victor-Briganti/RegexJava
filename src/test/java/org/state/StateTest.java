package org.state;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.matcher.CharacterMatcher;
import org.matcher.EpsilonMatcher;
import org.matcher.PatternMatcher;
import org.state.State.Transition;

public class StateTest {
    State state;

    @Test
    public void stateConstructor() {
        state = new State("q0");
        int result = state.getName().compareTo("q0");
        assertEquals(0, result);
    }

    @Test
    public void stateAddTransition() {
        state = new State("q0");

        ArrayList<State> states = new ArrayList<State>();
        ArrayList<PatternMatcher> matchers = new ArrayList<PatternMatcher>();

        for (int i = 0; i < 30; i++) {
            String identifier = "q" + Integer.toString(i);

            states.add(new State(identifier));
            if (i == 29) {
                matchers.add(new EpsilonMatcher());
            } else {
                matchers.add(new CharacterMatcher(identifier.charAt(1)));
            }

            state.addTransition(states.get(i), matchers.get(i));
        }

        ArrayList<Transition> transitions = state.getTransitions();
        for (int i = 0; i < 30; i++) {
            assertEquals(states.get(i), transitions.get(i).state());
            assertEquals(matchers.get(i), transitions.get(i).patternMatcher());
        }

    }

    @Test
    public void stateAddHighestTransition() {
        state = new State("q0");

        ArrayList<State> states = new ArrayList<State>();
        ArrayList<PatternMatcher> matchers = new ArrayList<PatternMatcher>();

        for (int i = 0; i < 30; i++) {
            String identifier = "q" + Integer.toString(i);

            if (i == 29) {
                states.add(0, new State(identifier));
                matchers.add(0, new EpsilonMatcher());
                state.addHighestTransition(states.get(0), matchers.get(0));
            } else {
                states.add(new State(identifier));
                matchers.add(new CharacterMatcher(identifier.charAt(1)));
                state.addTransition(states.get(i), matchers.get(i));
            }
        }

        ArrayList<Transition> transitions = state.getTransitions();

        for (int i = 0; i < 30; i++) {
            assertEquals(states.get(i), transitions.get(i).state());
            assertEquals(matchers.get(i), transitions.get(i).patternMatcher());
        }

    }

}
