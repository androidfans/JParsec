package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class SweetOperator {

    Parser characterOf(String string) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                return null;
            }
        };
    }

    Parser characterNotIn(String string) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                return null;
            }
        };
    }

    Parser digit(){
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                return null;
            }
        };
    }
}
