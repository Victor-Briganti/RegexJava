package org.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.engine.*;

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
        assertEquals(true, engine.compute("addaadaavsswwsaaabwwsas"));
    }

    @Test
    public void compilerExecuteBasicNotFound() {
        compiler = new Compiler("aab");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(!engine.compute("addaadaavsswwsabwwsas"));
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
        assertTrue(!engine.compute("0cde"));
    }

    @Test
    public void compilerExecutionUnionCase4() {
        compiler = new Compiler("bata(ta|vo)");
        compiler.compile();
        Engine engine = compiler.getEngine();
        assertTrue(engine.compute("batata"));
        assertTrue(engine.compute("batavo"));
        assertTrue(!engine.compute("batato"));
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

}
