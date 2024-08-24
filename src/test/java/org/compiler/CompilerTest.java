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

}
