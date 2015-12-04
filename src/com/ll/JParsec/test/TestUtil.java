package com.ll.JParsec.test;

import com.ll.JParsec.lib.Parser;
import com.ll.JParsec.lib.State;
import junit.framework.Assert;

/**
 * Created by liuli on 15-12-5.
 */
public class TestUtil {
    public static void AssertThrowException(Parser parser,State state) {
        try {
            parser.parse(state);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail();
    }
}
