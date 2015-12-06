package com.ll.JParsec.test;

import com.ll.JParsec.lib.AtomOperator;
import com.ll.JParsec.lib.Parser;
import com.ll.JParsec.lib.State;
import com.ll.JParsec.lib.TextState;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liuli on 15-12-4.
 */
public class AtomOperatorTest {

    @Test
    public void testEqual() throws Exception {
        State state = new TextState("01");
        Parser<Character> eq = AtomOperator.equal('0');
        assertEquals(eq.parse(state).charValue(), '0');

        try {
            eq.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        fail("exception not occur");
    }

    @Test
    public void testNotEqual() throws Exception {
        State state = new TextState("01");
        Parser<Character> neq = AtomOperator.notEqual('1');
        assertEquals(neq.parse(state).charValue(), '0');

        TestUtil.AssertThrowException(neq, state);
    }

    @Test
    public void testOneOf() throws Exception {
        State state = new TextState("0a");
        Parser<Character> oneOf = AtomOperator.oneOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        assertEquals(oneOf.parse(state).charValue(), '0');
        try {
            oneOf.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    public void testNoneOf() throws Exception {
        State state = new TextState("a0");
        Parser<Character> noneOf = AtomOperator.noneOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        assertEquals(noneOf.parse(state).charValue(), 'a');

        try {
            noneOf.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    public void testEOF() throws Exception {
        State state = new TextState("01");
        Parser eof = AtomOperator.EOF();
        state.next();
        state.next();
        eof.parse(state);

        state = new TextState("0");
        TestUtil.AssertThrowException(eof, state);
    }
}