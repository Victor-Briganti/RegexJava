package org.compiler;

import org.junit.Test;

import org.engine.*;

import static org.junit.Assert.*;

public class CompilerTest {
    Compiler compiler;

    @Test
    public void compile() {
        compiler = new Compiler("aaaass");
        assertEquals(0, compiler.compile());
        compiler.setExpression("qafaf)");
        assertTrue(compiler.compile() < 0);
    }

    @Test
    public void compilerExecuteBasic() {
        compiler = new Compiler("aab");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("addaadaavsswwsaaabwwsas"));
    }

    @Test
    public void compilerExecuteBasicNotFound() {
        compiler = new Compiler("aab");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertFalse(engine.compute("addaadaavsswwsabwwsas"));
    }

    @Test
    public void compilerExecutionGroup() {
        compiler = new Compiler("0(a(b((c)(d)e)))");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionStarCase1() {
        compiler = new Compiler("a*");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionStarCase2() {
        compiler = new Compiler("h*");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionStarCase3() {
        compiler = new Compiler("0*abcde");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionStarCase4() {
        compiler = new Compiler("0*(ab(cd)e)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionStarCase5() {
        compiler = new Compiler("ab*");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("abbbbbbbb"));
    }

    @Test
    public void compilerExecutionStarCase6() {
        compiler = new Compiler("(ab)*");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("dede"));
    }

    @Test
    public void compilerExecutionUnionCase1() {
        compiler = new Compiler("(a|b)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0acde"));
    }

    @Test
    public void compilerExecutionUnionCase2() {
        compiler = new Compiler("(a|b)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0bcde"));
    }

    @Test
    public void compilerExecutionUnionCase3() {
        compiler = new Compiler("(a|b)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertFalse(engine.compute("0cde"));
    }

    @Test
    public void compilerExecutionUnionCase4() {
        compiler = new Compiler("bata(ta|vo)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("batata"));
        assertTrue(engine.compute("batavo"));
        assertFalse(engine.compute("batato"));
    }

    @Test
    public void compilerExecutionUnionCase5() {
        compiler = new Compiler("(a|b)*");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("abababababababa"));
    }

    @Test
    public void compilerExecutionUnionCase6() {
        compiler = new Compiler("a|b*");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("bbbbbb"));
        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("c"));
    }

    @Test
    public void compilerExecutionAny() {
        compiler = new Compiler(".");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("a"));
        assertTrue(engine.compute("b"));
        assertTrue(engine.compute("c"));
        assertTrue(engine.compute("d"));
        assertTrue(engine.compute("e"));
        assertTrue(engine.compute("f"));
        assertTrue(engine.compute("g"));
    }

    @Test
    public void compilerExecutionPlusCase1() {
        compiler = new Compiler("a+");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionPlusCase2() {
        compiler = new Compiler("h+");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertFalse(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionPlusCase3() {
        compiler = new Compiler("0+abcde");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("000abcde"));
    }

    @Test
    public void compilerExecutionPlusCase4() {
        compiler = new Compiler("0+(ab(cd)e)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionPlusCase5() {
        compiler = new Compiler("ab+");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("abbbbbbbb"));
    }

    @Test
    public void compilerExecutionPlusCase6() {
        compiler = new Compiler("(ab)+");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("dedeab"));
        assertFalse(engine.compute("dede"));
    }

    @Test
    public void compilerExecutionQuestionCase1() {
        compiler = new Compiler("a?");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionQuestionCase2() {
        compiler = new Compiler("h?");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
    }

    @Test
    public void compilerExecutionQuestionCase3() {
        compiler = new Compiler("0?abcde");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("000abcde"));
    }

    @Test
    public void compilerExecutionQuestionCase4() {
        compiler = new Compiler("0?(ab(cd)e)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("0abcde"));
        assertTrue(engine.compute("abcde"));
    }

    @Test
    public void compilerExecutionQuestionCase5() {
        compiler = new Compiler("ab?");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("abbbbbbbb"));
        assertTrue(engine.compute("ac"));
    }

    @Test
    public void compilerExecutionQuestionCase6() {
        compiler = new Compiler("(ab)?");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("dedeab"));
        assertTrue(engine.compute("dedea"));
        assertTrue(engine.compute("dedeb"));
        assertTrue(engine.compute("dede"));
    }

}
