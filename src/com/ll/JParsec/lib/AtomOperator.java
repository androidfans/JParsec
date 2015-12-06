package com.ll.JParsec.lib;


/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {

    /**
     * equal operator expect value equals the parameter.
     * @param chr the given value
     * @return the Parser
     */
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

    /**
     * notEqual operator expect value differentiates from the parameter
     * @param chr the given value
     * @return the Parser
     */
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

    /**
     * oneOf operator expect value is a member of parameters
     * @param chars several value may include the state's next value
     * @return the Parser
     */
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

    /**
     * noneOf operator expect value isn't a member of parameters
     * @param chars several value may not include the state's next value
     * @return the Parser
     */
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

    /**
     * the Return operator always success and return the given value
     * @param val a value to return
     * @return the Parser
     */
    public static Parser Return(Object val) {
        class ReturnParser extends Parser<Object> {

            @Override
            public Object parse(State state) {
                return val;
            }
        }
        return new ReturnParser();
    }

    /**
     * TODO : should add a exception hierarchy make the state can inform the operator what the exception is
     * EOF will success when the state get the stream's endpoint.under other circumstance , it always failed
     * @return the Parser
     */
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

    /**
     * the fail operator always failed and you can pass the message to indicate the reason of the fail
     * @param message the error message
     * @return the Parser
     */
    public static Parser fail(String message) {
        class FailParser extends Parser<Object>{

            @Override
            public Object parse(State state) {
                throw new RuntimeException(message);
            }
        }
        return new FailParser();
    }
}
