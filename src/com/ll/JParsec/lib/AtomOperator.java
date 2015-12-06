package com.ll.JParsec.lib;


/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {

    public static Parser equal(char chr) {
        class EqualParser extends Parser<Character>  {
            @Override
            public Character parse(State state){
                char data = state.next();
                if (data == chr) {
                    return data;
                }
                throw new RuntimeException("expect a value equal " + chr + " but get" + data);
            }
        }
        return new EqualParser();
    }
    public static Parser notEqual(char chr) {
        class NotEqualParser extends Parser{
            @Override
            public Character parse(State state) {
                char data = state.next();
                if (data != chr) {
                    return data;
                }
                throw new RuntimeException("expect a value not equal" + chr + " but get" + data);
            }
        }
        return new NotEqualParser();
    }

    public static Parser oneOf(char...chars){
        class OneOfParser extends Parser{
            @Override
            public Character parse(State state){
                char data = state.next();
                for (char c : chars) {
                    if(data == c)
                        return data;
                }
                throw new RuntimeException("expect one of" + chars);
            }
        }
        return new OneOfParser();
    }

    public static Parser noneOf(char... chars) {
        class NoneOfParser extends Parser{

            @Override
            public Character parse(State state){
                char data = state.next();
                for (char c : chars) {
                    if (data == c) {
                        throw new RuntimeException("expect none of" + chars );
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
                char data = 0;
                try {
                    data = state.next();
                } catch (RuntimeException e) {
                    return null;
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
