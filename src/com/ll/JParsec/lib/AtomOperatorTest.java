package com.ll.JParsec.lib;

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
        Assert.assertEquals(eq.parse(state).charValue(), '0');

        try {
            eq.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("exception not occur");
    }

    @Test
    public void testNotEqual() throws Exception {
        State state = new TextState("01");
        Parser<Character> eq = AtomOperator.notEqual('1');
        Assert.assertEquals(eq.parse(state).charValue(), '0');

        try {
            eq.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("exception not occur");
    }

    @Test
    public void testOneOf() throws Exception {
        State state = new TextState("0a");
        Parser<Character> eq = AtomOperator.oneOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Assert.assertEquals(eq.parse(state).charValue(), '0');
        try {
            eq.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testNoneOf() throws Exception {

    }
}