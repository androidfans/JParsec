package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public abstract class Parser {
    abstract Object parse(State state) throws Exception;
}
