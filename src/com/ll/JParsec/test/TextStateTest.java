package com.ll.JParsec.test;

import com.ll.JParsec.lib.State;
import com.ll.JParsec.lib.TextState;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by liuli on 15-12-4.
 */
public class TextStateTest {

    @Test
    public void testCommit() throws Exception {
        State state = new TextState("0123456789");
        Integer chan =  state.begin();
        state.next();
        state.next();
        state.commit(chan);
        assertEquals(state.next().charValue(), '2');
    }

    @Test
    public void testRollback() throws Exception {
        State state = new TextState("0123456789");
        Integer chan =  state.begin();
        state.next();
        state.next();
        state.rollBack(chan);
        assertEquals(state.next().charValue(), '0');
    }

    @Test
    public void testNext() throws Exception {
        State state = new TextState("0123456789");
        state.next();
        state.next();
        Character data = state.next();
        assertEquals(data.charValue(), '2');
    }
}