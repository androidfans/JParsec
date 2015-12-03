package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {

    /*
    这异常处理起来简直恶心
     */
    public Parser equal(Character character) {
        return new Parser() {
            @Override
            Character parse(State state) {
                Character next = state.next();
                if (next.equals(character)) {
                    return next;
                }
                throw new RuntimeException("except a value equal " + character);
            }
        };
    }

    public Parser notEqual(Character character) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                Character next = state.next();
                if (!next.equals(character)) {
                    return next;
                }
                throw new RuntimeException("except a value not equal" + character);
            }
        };
    }

    public Parser oneOf(Character...characters){
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                return null;
            }
        };
    }

    public Parser noneOf(Character... characters) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                return null;
            }
        };
    }


}
