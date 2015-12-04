package com.ll.JParsec.lib;


/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {

    /*
    匿名内部类竟然不能传泛型,惊呆了!!
     */
    public static Parser one() {
        class OneParser extends Parser<Character> {

            @Override
            public Character parse(State state) {
                Character data = state.next();
                return data;
            }
        }
        return new OneParser();
    }

    public static Parser pack(Object retVal) {
        class packParser extends Parser {

            @Override
            public Object parse(State state) {
                return retVal;
            }
        }
        return new packParser();
    }

    public static Parser equal(Character character) {
        class EqualParser extends Parser<Character> {
            @Override
            public Character parse(State state){
                Character data = state.next();
                if (data.equals(character)) {
                    return data;
                }
                throw new RuntimeException("expect a value equal " + character + " but get" + data);
            }
        }
        return new EqualParser();
    }
    public static Parser notEqual(Character character) {
        class NotEqualParser extends Parser<Character>{
            @Override
            public Character parse(State state) {
                Character data = state.next();
                if (!data.equals(character)) {
                    return data;
                }
                throw new RuntimeException("expect a value not equal" + character + " but get" + data);
            }
        }
        return new NotEqualParser();
    }

    public static Parser oneOf(Character...characters){
        class OneOfParser extends Parser<Character>{
            @Override
            public Character parse(State state){
                Character data = state.next();
                for (Character c : characters) {
                    if(data.equals(c))
                        return data;
                }
                throw new RuntimeException("expect one of" + characters);
            }
        }
        return new OneOfParser();
    }

    public static Parser noneOf(Character... characters) {
        class NoneOfParser extends Parser<Character>{

            @Override
            public Character parse(State state){
                Character data = state.next();
                for (Character c : characters) {
                    if (data.equals(c)) {
                        throw new RuntimeException("expect none of" + characters );
                    }
                }
                return data;
            }
        }
        return new NoneOfParser();
    }

    public static Parser Return(Object val) {
        class ReturnParser extends Parser<Object> {

            @Override
            public Object parse(State state) {
                return val;
            }
        }
        return new ReturnParser();
    }

    public static Parser EOF() {
        class EOFParser extends Parser{

            @Override
            public Object parse(State state) {
                Character data = null;
                try {
                    data = state.next();
                } catch (RuntimeException e) {

                }
                throw new RuntimeException("expect eof but" + data);
            }
        }
        return new EOFParser();
    }

    public static Parser Fail(String message) {
        class FailParser extends Parser<Object>{

            @Override
            public Object parse(State state) {
                throw new RuntimeException(message);
            }
        }
        return new FailParser();
    }
}
