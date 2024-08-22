package org.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.matcher.CharacterMatcher;
import org.matcher.EpsilonMatcher;

import java.util.Set;

import org.state.*;

public class EngineTest {
    Engine engine;

    @Test
    public void startState() {
        engine = new Engine();
        State state = new State("q0");
        engine.setStarState(state);
        assertEquals(state, engine.getStarState());
    }

    @Test
    public void finalStates() {
        engine = new Engine();

        ArrayList<State> states = new ArrayList<State>();

        for (int i = 0; i < 30; i++) {
            states.add(new State("q" + Integer.toString(i)));
            engine.addFinalState(states.get(i));
        }

        Set<State> finalStates = engine.getFinalStates();

        for (int i = 0; i < 30; i++) {
            assertTrue(finalStates.contains(states.get(i)));
        }
    }

    @Test
    public void allStates() {
        engine = new Engine();

        ArrayList<State> states = new ArrayList<State>();

        for (int i = 0; i < 30; i++) {
            states.add(new State("q" + Integer.toString(i)));
            engine.addState(states.get(i));
        }

        Set<State> allStates = engine.getStates();

        for (int i = 0; i < 30; i++) {
            assertTrue(allStates.contains(states.get(i)));
        }
    }

    @Test
    public void basicComputation() {
        engine = new Engine();

        // Here we are trying to create a automaton that matches the "ab".
        State startState = new State("q0");
        State middleState = new State("q1");
        State finalState = new State("q2");

        startState.addTransition(middleState, new CharacterMatcher('a'));
        middleState.addTransition(finalState, new CharacterMatcher('b'));

        engine.addState(startState);
        engine.addState(middleState);
        engine.addState(finalState);

        engine.setStarState(startState);
        engine.addFinalState(finalState);

        assertTrue(engine.compute("ab"));
        assertTrue(!engine.compute("ac"));
    }

    @Test
    public void orOperator() {
        engine = new Engine();

        // Here we are trying to create a automaton that matches the "(a|b)".
        State startState = new State("q0");
        State leftMiddleState = new State("q1");
        State rightMiddleState = new State("q1");

        startState.addTransition(leftMiddleState, new CharacterMatcher('a'));
        startState.addTransition(rightMiddleState, new CharacterMatcher('b'));

        engine.addState(startState);
        engine.addState(leftMiddleState);
        engine.addState(rightMiddleState);

        engine.setStarState(startState);
        engine.addFinalState(leftMiddleState);
        engine.addFinalState(rightMiddleState);

        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("b"));
        assertTrue(!engine.compute("c"));
    }

    @Test
    public void starOperator() {
        engine = new Engine();

        // Here we are trying to create a automaton that matches the "a*".
        State startState = new State("q0");
        State emptyState = new State("q1");

        startState.addTransition(startState, new CharacterMatcher('a'));
        startState.addTransition(emptyState, new EpsilonMatcher());

        engine.addState(startState);
        engine.addState(emptyState);

        engine.setStarState(startState);
        engine.addFinalState(emptyState);

        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("b"));
        assertTrue(engine.compute("aaaaaaaaaa"));
    }

}
