package org.compiler;

import org.engine.Engine;
import org.parser.Parser;
import org.matcher.*;
import org.nodes.*;
import org.state.*;

/**
 * The Compiler class acts as an adapter between the Engine and the Parser.
 * It uses the Parser to generate an Abstract Syntax Tree (AST) and then
 * traverses
 * this tree to generate states, which are then added to the Execution Engine.
 * Finally, the Engine is returned, fully prepared for execution.
 */
public class Compiler {
    private Engine engine;
    private final Parser parser;
    private int numStates;

    /**
     * Generate the character state.
     * Associate the previous state with this one using a CharacterMatcher, and
     * then return the newly created state.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The created state
     */
    private State generateCharState(CharNode node, State previousState) {
        numStates++;
        State state = new State("q" + numStates);
        previousState.addTransition(state, new CharacterMatcher(node.getValue()));
        return state;
    }

    /**
     * Generate any state.
     * Associate the previous state with this one using a AnyMatcher, and
     * then return the newly created state.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The created state
     */
    private State generateAnyState(AnyNode node, State previousState) {
        numStates++;
        State state = new State("q" + numStates);
        previousState.addTransition(state, new AnyMatcher());
        return state;
    }

    /**
     * Generate the group state.
     * Associate the previous state with this one using a EpsilonMatcher, and then
     * recursively generate all the states that are on its right side. In the end
     * return the last state generated.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The last state of the group
     */
    private State generateGroupState(GroupNode node, State previousState) {
        if (node.getRight() == null) {
            return null;
        }

        numStates++;
        State state = new State("q" + numStates);
        previousState.addHighestTransition(state, new EpsilonMatcher());

        return astToStatesRec(node.getRight(), state);
    }

    /**
     * Generate the star state.
     * Creates a star state, associate this new state with its right side.
     * The right side (now a new state), is associated with the star state, this is
     * equivalent to a loop.
     * A last node is created, the right side and the star node are associate this
     * last node with a EpsilonMatcher.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The last state of the group
     */
    private State generateStarState(StarNode node, State previousState) {
        if (node.getRight() == null) {
            return null;
        }

        numStates++;
        State state = new State("q" + numStates);
        previousState.addHighestTransition(state, new EpsilonMatcher());

        State nextState = astToStatesRec(node.getRight(), state);

        if (nextState == null) {
            return null;
        }

        nextState.addTransition(state, new EpsilonMatcher());

        numStates++;
        State finalState = new State("q" + numStates);

        state.addTransition(finalState, new EpsilonMatcher());
        nextState.addTransition(finalState, new EpsilonMatcher());

        return finalState;
    }

    /**
     * Generate the plus state.
     * Creates a plus state, associate this new state with its right side.
     * This operator follows the same creation of the star one, with one extra
     * step, it needs to match at least once.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The last state of the group
     */
    private State generatePlusState(PlusNode node, State previousState) {
        if (node.getRight() == null) {
            return null;
        }

        numStates++;
        State state = new State("q" + numStates);
        previousState.addHighestTransition(state, new EpsilonMatcher());

        State nextState = astToStatesRec(node.getRight(), state);

        if (nextState == null) {
            return null;
        }

        nextState.addTransition(state, new EpsilonMatcher());

        numStates++;
        State finalState = new State("q" + numStates);

        nextState.addTransition(finalState, new EpsilonMatcher());

        return finalState;
    }

    /**
     * Generate the question state.
     * Creates a question state, associate this new state with its right side.
     * This operator acts similar to the union operator, its objective is to match
     * the node or nothing.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The last state of the group
     */
    private State generateQuestionState(QuestionNode node, State previousState) {
        if (node.getRight() == null) {
            return null;
        }

        numStates++;
        State state = new State("q" + numStates);
        previousState.addHighestTransition(state, new EpsilonMatcher());

        State nextState = astToStatesRec(node.getRight(), state);

        if (nextState == null) {
            return null;
        }

        numStates++;
        State finalState = new State("q" + numStates);

        nextState.addTransition(finalState, new EpsilonMatcher());
        state.addTransition(finalState, new EpsilonMatcher());

        return finalState;
    }

    /**
     * Generate the union state.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The last state of the group
     */
    private State generateUnionState(UnionNode node, State previousState) {
        if (node.getRight() == null) {
            return null;
        }

        UnionAuxNode unionNode = (UnionAuxNode) node.getRight();
        if (unionNode.getRight() == null || unionNode.getLeft() == null) {
            return null;
        }

        numStates++;
        State initState = new State("q" + numStates);
        previousState.addHighestTransition(initState, new EpsilonMatcher());

        State leftState = astToStatesRec(unionNode.getLeft(), initState);
        State rightState = astToStatesRec(unionNode.getRight(), initState);

        if (leftState == null || rightState == null) {
            return null;
        }

        numStates++;
        State finalState = new State("q" + numStates);
        leftState.addTransition(finalState, new EpsilonMatcher());
        rightState.addTransition(finalState, new EpsilonMatcher());

        return finalState;
    }

    /**
     * Generates a new state based on the type of the given node and links it with
     * the previous state.
     * 
     * @param node          The current node that determines the type of state to
     *                      generate.
     * @param previousState The state to which the newly generated state will be
     *                      linked as a transition.
     * @return The newly generated state if the node type is recognized and handled;
     *         otherwise, returns null.
     */
    private State generateState(RegexNode node, State previousState) {
        if (node instanceof CharNode) {
            return generateCharState((CharNode) node, previousState);
        }

        if (node instanceof AnyNode) {
            return generateAnyState((AnyNode) node, previousState);
        }

        if (node instanceof GroupNode) {
            return generateGroupState((GroupNode) node, previousState);
        }

        if (node instanceof StarNode) {
            return generateStarState((StarNode) node, previousState);
        }

        if (node instanceof PlusNode) {
            return generatePlusState((PlusNode) node, previousState);
        }

        if (node instanceof QuestionNode) {
            return generateQuestionState((QuestionNode) node, previousState);
        }

        if (node instanceof UnionNode) {
            return generateUnionState((UnionNode) node, previousState);
        }

        return null;
    }

    /**
     * Generate the group state.
     * Associate the previous state with this one using a EpsilonMatcher, and then
     * recursively generate all the states that are on its right side. In the end
     * return the last state generated.
     * 
     * @param node          Current node that is going to be a state
     * @param previousState used to link the current state with the previous one
     * @return The last state of the group
     */
    private State astToStatesRec(RegexNode node, State previousState) {
        State curState = generateState(node, previousState);

        if (curState == null) {
            return null;
        }

        if (node.getLeft() == null && node.getRight() == null) {
            return curState;
        }

        if (node.getLeft() != null) {
            return astToStatesRec(node.getLeft(), curState);
        }

        return curState;
    }

    /**
     * Generate all the states based on the AST
     *
     * @return 0 if the generation worked, and -1 otherwise.
     */
    private int astToStates() {
        if (parser.getAST() == null) {
            return -1;
        }

        State firstState = new State("q0");
        engine.addState(firstState);
        engine.setStartState(firstState);

        State finalState = astToStatesRec(parser.getAST(), firstState);
        engine.addState(finalState);
        engine.addFinalState(finalState);

        return 0;
    }

    /**
     * Initializes the Compiler with the input string.
     *
     * @param expression The input string to be compiled. Must not be null.
     */
    public Compiler(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression should not be null");
        }

        engine = new Engine();
        parser = new Parser(expression);
        numStates = 1;
    }

    /**
     * Return the compiled engine.
     *
     * @return compiled engine. Could be null.
     */
    public Engine getEngine() {
        return engine;
    }

    /**
     * Sets a new expression to be compiled
     *
     * @param expression The new expression that is going to be compiled.
     */
    public void setExpression(String expression) {
        parser.setInput(expression);
        engine = null;
        numStates = 1;
    }

    /**
     * Compile the expression
     *
     * @return 0 if everything worked, and negative number otherwise.
     */
    public int compile() {
        int parserResult = parser.parse();
        if (parserResult < 0) {
            return parserResult;
        }

        if (astToStates() < 0) {
            return -1;
        }

        return 0;
    }

}
