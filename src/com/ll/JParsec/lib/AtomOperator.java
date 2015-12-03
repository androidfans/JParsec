package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {
    public Parser equal(Character character) {
        return new Parser() {
            @Override
            Character parse(State state) throws Exception {
                try {
                    if (state.next().equals(character)) {
                        return character;
                    }
                    throw new Exception("except a value equal " + character);
                } catch (Exception e) {
                    throw new Exception("parse failed", e);
                }
            }
        };
    }

    public Parser notEqual(Character character) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                return null;
            }
        };
    }

    public Parser oneOf(Character...characters){
        return new Parser() {
            @Override
            <T> T parse(State state) throws Exception {
                return null;
            }
        };
    }

    public Parser noneOf(Character... characters) {
        return new Parser() {
            @Override
            <T> T parse(State state) throws Exception {
                return null;
            }
        };
    }


}
