package com.ll.JParsec.test;

import com.ll.JParsec.lib.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liuli on 15-12-6.
 */
public class TextOperatorTest {

    @Test
    public void testChr() throws Exception {
        
        State state = new TextState("01");
        Parser<Character> chr = TextOperator.Chr('0');
        assertEquals(chr.parse(state).charValue(), '0');

        TestUtil.AssertThrowException(chr, state);
    }

    @Test
    public void testStr() throws Exception {
        State state = new TextState("0123456789");
        Parser<String> str = TextOperator.Str("01234");
        assertEquals("01234", str.parse(state));

        state = new TextState("012d34");
        TestUtil.AssertThrowException(str, state);
    }

    @Test
    public void testCharOf() throws Exception {
        State state = new TextState("0");
        Parser chrOf = TextOperator.charOf("01234");
        assertEquals('0', chrOf.parse(state));

        state = new TextState("5");
        TestUtil.AssertThrowException(chrOf, state);
    }

    @Test
    public void testSpace() throws Exception {
        State state = new TextState(" ");
        Parser<Character> spaceP = TextOperator.space();
        assertEquals(new Character(' '), spaceP.parse(state));

        state = new TextState("1");
        TestUtil.AssertThrowException(spaceP, state);

    }

    @Test
    public void testNewLine() throws Exception {
        State state = new TextState(Global.LINESEPARATOR);
        Parser sep = TextOperator.whiteSpace();
        assertEquals(Global.LINESEPARATOR, sep.parse(state).toString());
    }

    @Test
    public void testWhiteSpace() throws Exception {
        State state = new TextState(" " + Global.LINESEPARATOR + "\t");
        Parser sep = TextOperator.whiteSpace();
        assertEquals(" ", sep.parse(state).toString());
        assertEquals(Global.LINESEPARATOR, sep.parse(state).toString());
        assertEquals("\t", sep.parse(state).toString());
    }

    @Test
    public void testDigit() throws Exception {
        State state = new TextState("1a");
        Parser digit = TextOperator.Digit();
        assertEquals('1', digit.parse(state));

        TestUtil.AssertThrowException(digit, state);
    }

    @Test
    public void testUInt() throws Exception {
        State state = new TextState("45212.ag");
        Parser Uint = TextOperator.uInt();
        assertEquals("45212", Uint.parse(state).toString());

        TestUtil.AssertThrowException(Uint,state);
    }

    @Test
    public void testInt() throws Exception {
        State state = new TextState("45212.ag");
        Parser Int = TextOperator.Int();
        assertEquals("45212", Int.parse(state).toString());

        state = new TextState("-4562h.");
        assertEquals("-4562",Int.parse(state));
    }

    @Test
    public void testUFloat() throws Exception {
        State state = new TextState("0.23");
        Parser uFloat = TextOperator.uFloat();
        assertEquals("0.23", uFloat.parse(state));

        state = new TextState(".23");
        assertEquals("0.23", uFloat.parse(state));
    }

    @Test
    public void testFloat() throws Exception {
        State state = new TextState("0.23");
        Parser Float = TextOperator.Float();
        assertEquals("0.23", Float.parse(state));

        state = new TextState("-1.23");
        assertEquals("-1.23", Float.parse(state));

        state = new TextState(".23");
        assertEquals("0.23", Float.parse(state));

        state = new TextState("-.23");
        assertEquals("-0.23", Float.parse(state));
    }
}