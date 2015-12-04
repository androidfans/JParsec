package com.ll.JParsec.test;

import com.ll.JParsec.lib.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liuli on 15-12-4.
 */
public class CombinatorOperatorTest {

    @Test
    public void testTry() throws Exception {
        State state = new TextState("0");
        Parser ne = AtomOperator.notEqual('0');
        Parser Try = CombinatorOperator.Try(ne);
        TestUtil.AssertThrowException(Try, state);
    }

    @Test
    public void testBetween() throws Exception {
        State state = new TextState("abd");
        Parser eq1 = AtomOperator.equal('a');
        Parser eq2 = AtomOperator.equal('b');
        Parser eq3 = AtomOperator.equal('d');
        Parser bet = CombinatorOperator.between(eq1, eq2, eq3);
        Assert.assertEquals(bet.parse(state), 'b');
        Assert.assertEquals(3, state.pos().intValue());
    }

    @Test
    public void testMany() throws Exception {
        State state = new TextState("aaaaaaab");
        Parser eq = AtomOperator.equal('a');
        Parser many = CombinatorOperator.many(eq);
        ArrayList<Object> arr = (ArrayList<Object>) many.parse(state);
        for (Object c : arr) {
            Assert.assertEquals('a', ((Character) c).charValue());
        }
        Assert.assertEquals(7, state.pos().intValue());
    }

    @Test
    public void testMany1() throws Exception {
        State state = new TextState("aaaaaaab");
        Parser eq = AtomOperator.equal('a');
        Parser many1 = CombinatorOperator.many1(eq);
        ArrayList<Object> arr = (ArrayList<Object>) many1.parse(state);
        for (Object c : arr) {
            Assert.assertEquals('a', ((Character) c).charValue());
        }
        Assert.assertEquals(7, state.pos().intValue());

        state = new TextState("baaaaab");
        TestUtil.AssertThrowException(many1,state);
    }

    @Test
    public void testChoice() throws Exception {

        State state = new TextState("ab");
        Parser eq1 = AtomOperator.equal('b');
        Parser eq2 = AtomOperator.equal('a');
        Parser Try = CombinatorOperator.Try(eq1);
        Parser choice = CombinatorOperator.choice(Try, Try, Try, eq2);
        choice.parse(state);

        Assert.assertEquals(1, state.pos().intValue());
    }

    @Test
    public void testSepBy1() throws Exception {
        State state = new TextState("a|a|a|a");
        Parser s = AtomOperator.equal('|');
        Parser eq = AtomOperator.equal('a');
        Parser sepBy1 = CombinatorOperator.sepBy1(eq, s);
        List<Object> list = (List<Object>) sepBy1.parse(state);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(7, state.pos().intValue());


        state = new TextState("b|a|a|a");
        TestUtil.AssertThrowException(sepBy1, state);
    }

    @Test
    public void testSepBy() throws Exception {
        State state = new TextState("a|a|a|a");
        Parser s = AtomOperator.equal('|');
        Parser eq = AtomOperator.equal('a');
        Parser sepBy = CombinatorOperator.sepBy(eq, s);
        List<Object> list = (List<Object>) sepBy.parse(state);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(7, state.pos().intValue());
    }

    @Test
    public void testSkip1() throws Exception {

        State state = new TextState("aaab");
        Parser eq = AtomOperator.equal('a');
        Parser sk1 = CombinatorOperator.skip1(eq);
        ArrayList<Object> arr = (ArrayList<Object>) sk1.parse(state);
        Assert.assertEquals(null, arr);

        Assert.assertEquals(4, state.pos().intValue());

        state = new TextState("baaab");
        TestUtil.AssertThrowException(sk1,state);
    }

    @Test
    public void testSkip() throws Exception {
        State state = new TextState("aaab");
        Parser eq = AtomOperator.equal('a');
        Parser sk = CombinatorOperator.skip(eq);
        ArrayList<Object> arr = (ArrayList<Object>) sk.parse(state);
        Assert.assertEquals(null, arr);

        Assert.assertEquals(4, state.pos().intValue());
    }

    @Test
    public void testManyTail() throws Exception {
        State state = new TextState("aab");
        Parser eq = AtomOperator.equal('a');
        Parser ne = AtomOperator.notEqual('a');
        Parser mat = CombinatorOperator.manyTail(eq, ne);
        ArrayList<Object> arr = (ArrayList<Object>) mat.parse(state);
        for (Object c : arr) {
            Assert.assertEquals('a',((Character)c).charValue());
        }
    }

    @Test
    public void testMany1Tail() throws Exception {
        State state = new TextState("aab");
        Parser eq = AtomOperator.equal('a');
        Parser ne = AtomOperator.notEqual('a');
        Parser ma1t = CombinatorOperator.many1Tail(eq, ne);
        ArrayList<Object> arr = (ArrayList<Object>) ma1t.parse(state);
        for (Object c : arr) {
            Assert.assertEquals('a',((Character)c).charValue());
        }

        state = new TextState("baab");
        TestUtil.AssertThrowException(ma1t, state);
    }

    @Test
    public void testOtherWise() throws Exception {
        State state = new TextState("bd");
        Parser eq = AtomOperator.equal('a');
        Parser ow = CombinatorOperator.otherWise(eq, "the first operator is fail , so bad");
        try {
            ow.parse(state);
        } catch (RuntimeException e) {
            Assert.assertEquals("the first operator is fail , so bad", e.getMessage());
        }

        state = new TextState("ab");
        Character c = (Character) ow.parse(state);
        Assert.assertEquals('a', c.charValue());
    }
}