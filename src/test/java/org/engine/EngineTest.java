package org.engine;

import java.util.ArrayList;

import org.junit.Test;
import org.matcher.*;

import java.util.Set;

import org.state.*;

import static org.junit.Assert.*;

public class EngineTest {
    Engine engine;

    @Test
    public void startState() {
        engine = new Engine();
        State state = new State("q0");
        engine.setStartState(state);
        assertEquals(state, engine.getStartState());
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

        engine.setStartState(startState);
        engine.addFinalState(finalState);

        assertTrue(engine.compute("ab"));
        assertFalse(engine.compute("ac"));
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

        engine.setStartState(startState);
        engine.addFinalState(leftMiddleState);
        engine.addFinalState(rightMiddleState);

        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("b"));
        assertFalse(engine.compute("c"));
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

        engine.setStartState(startState);
        engine.addFinalState(emptyState);

        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("b"));
        assertTrue(engine.compute("aaaaaaaaaa"));
    }

    @Test
    public void anyOperator() {
        engine = new Engine();

        // Here we are trying to create a automaton that matches the ".".
        State startState = new State("q0");
        State emptyState = new State("q1");

        startState.addTransition(emptyState, new AnyMatcher());

        engine.addState(startState);
        engine.addState(emptyState);

        engine.setStartState(startState);
        engine.addFinalState(emptyState);

        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("b"));
        assertTrue(engine.compute("aaaaaaaaaa"));
    }

    @Test
    public void PlusOperator() {
        engine = new Engine();

        // Here we are trying to create a automaton that matches the "a+".
        State startState = new State("q0");
        State middleState = new State("q1");
        State endState = new State("q2");

        startState.addTransition(middleState, new CharacterMatcher('a'));

        middleState.addTransition(middleState, new CharacterMatcher('a'));
        middleState.addHighestTransition(endState, new EpsilonMatcher());

        engine.addState(startState);
        engine.addState(middleState);
        engine.addState(endState);

        engine.setStartState(startState);
        engine.addFinalState(endState);

        assertTrue(engine.compute("a"));
        assertFalse(engine.compute("b"));
        assertTrue(engine.compute("aaaaaaaaaa"));
    }

}
