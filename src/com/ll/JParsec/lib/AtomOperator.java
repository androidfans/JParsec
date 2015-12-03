package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {

    public Parser equal(Character character) {
        return new Parser() {
            @Override
            Character parse(State state) {
                Character data = state.next();
                if (data.equals(character)) {
                    return data;
                }
                throw new RuntimeException("expect a value equal " + character);
            }
        };
    }

    public Parser notEqual(Character character) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                Character data = state.next();
                if (!data.equals(character)) {
                    return data;
                }
                throw new RuntimeException("expect a value not equal" + character);
            }
        };
    }

    public Parser oneOf(Character...characters){
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                Character data = state.next();
                for (Character c : characters) {
                    if(data.equals(c))
                        return data;
                }
                throw new RuntimeException("expect one of" + characters);
            }
        };
    }

    public Parser noneOf(Character... characters) {
        return new Parser() {
            @Override
            Object parse(State state) throws Exception {
                Character data = state.next();
                for (Character c : characters) {
                    if (data.equals(c)) {
                        throw new RuntimeException("expect none of" + characters);
                    }
                }
                return data;
            }
        };
    }
}
