package com.ll.JParsec.lib;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by liuli on 15-12-3.
 */
public class CombinatorOperator {
    /**
     * the Try operator accept a Parser and execute it . if the Parser failed , Try operator will rollback it to the correct position
     * but the Try operator doesn't catch some exception , it will throw the exception from the given Parser
     * @param parser Parser to try
     * @return the after-try parser
     */
    public static Parser Try(Parser parser) {
        class TryParser extends Parser<Object> {

            @Override
            public Object parse(State state) {
                Integer tran = state.begin();
                Object data = null;
                try {
                    data = parser.parse(state);
                } catch (RuntimeException e) {
                    state.rollBack(tran);
                    throw e;
                }
                state.commit(tran);
                return data;
            }
        }
        return new TryParser();
    }

    /**
     * the between operator will execute the open operator then the close operator and then the psc operator,and return the result from close operator
     * @param open the open Parser
     * @param close the close Parser
     * @param psc the psc Parser
     * @return the between Parser
     */
    public static Parser between(Parser open, Parser close, Parser psc) {
        class BetweenParser extends Parser<Object> {

            @Override
            public Object parse(State state) {
                open.parse(state);
                Object data = close.parse(state);
                psc.parse(state);
                return data;
            }
        }
        return new BetweenParser();
    }

    /**
     * the many operator match zero to many times using the given parser
     * the many operator encapsulate the try operator
     * and it has catch the exception internal , so you have no necessary to handle some exception
     * @param parser the parser to match many times
     * @return the many Parser
     */
    public static Parser many(Parser parser) {
        class ManyParser extends Parser {

            @Override
            public ArrayList<Object> parse(State state) {
                ArrayList<Object> re = new ArrayList<>();
                Parser psc = Try(parser);
                Object obj = null;
                for (;;) {
                    try {
                        obj = psc.parse(state);
                    } catch (RuntimeException e) {
                        break;
                    }
                    re.add(obj);
                }
                return re;
            }
        };
        return new ManyParser();
    }

    /**
     * many1 operator likes many, but many1 will match at least one times.
     * if the first time not matched , it will throw the exception , and the first operator will not rollback
     * @param parser the Parser to match many1 times
     * @return the many1 Parser
     */
    public static Parser many1(Parser parser) {
        class Many1Parser extends Parser{

            @Override
            public ArrayList<Object>  parse(State state) {
                Object r = parser.parse(state);
                ArrayList<Object> re = new ArrayList<>();
                re.add(r);
                Parser psc = Try(parser);
                Object obj = null;
                for (;;) {
                    try {
                        obj = psc.parse(state);
                    } catch (RuntimeException e) {
                        break;
                    }
                    re.add(obj);
                }
                return re;
            }
        }
        return new Many1Parser();
    }

    /**
     * the choice operator accept several parsers , and it will try them singly
     * if someone feasible,it will return the result from it.
     * and it will throw exception when all the parsers failed or a parser doesn't rollback after execution
     * it means you should try every operator unless the last one by yourself
     * @param parsers parsers to choice
     * @return the choice Parser
     */
    public static Parser choice(Parser... parsers) {
        class ChoiceParser extends Parser{

            @Override
            public Object parse(State state) {
                Object re = null;
                for (Parser parser : parsers) {
                    int index = state.pos();
                    try {
                        re =  parser.parse(state);
                    } catch (RuntimeException e) {
                        if (state.pos() != index) {
                            throw e;
                        }
                        continue;
                    }
                    return re;
                }
                throw new RuntimeException();
            }
        }
        return new ChoiceParser();
    }

    /**
     * sepBy operator match one to many times parser with separator
     * @param parser the parser
     * @param separator the separator
     * @return the parser
     */
    public static Parser sepBy1(Parser parser, Parser separator) {
        class SepBy1Parser extends Parser {

            @Override
            public Object parse(State state) {
                Parser par = parser.bind(new HandlerAdapter() {
                    @Override
                    public Object bindHandle(Object value, State state) {
                        ArrayList<Object> arrayList = new ArrayList<Object>();
                        arrayList.add(value);
                        arrayList.addAll((Collection<?>) many(separator.then(parser)).parse(state));
                        return arrayList;
                    }
                });
                return par.parse(state);
            }
        }
        return new SepBy1Parser();
    }

    /**
     * sepBy operator match zero to many times parser with separator
     * and the parser will return all the parser's value and drop the separator's value
     * @param parser
     * @param separator
     * @return the parser
     */
    public static Parser sepBy(Parser parser, Parser separator) {
        Parser par = choice(sepBy1(parser, separator), AtomOperator.Return(new ArrayList<Object>()));
        return par;
    }

    /**
     * skip1 separator match one to many times but it will not store the value
     * the first match must success otherwise it will throw the exception
     * and it also will not store the value
     * @param parser the parser to skip one
     * @return the parser
     */
    public static Parser skip1(Parser parser) {
        return parser.bind(new HandlerAdapter(){
            @Override
            public Object bindHandle(Object value, State state) {

                for (; ; ) {
                    try {
                        parser.parse(state);
                    } catch (RuntimeException e) {
                        return null;
                    }
                }
            }
        });
    }

    /**
     * skip separator match zero to many times but it will not store the value
     * the skip operator has handle the try , you don't need to wrap the try operator to the parser
     * @param parser the parser to skip
     * @return the Parser
     */
    public static Parser skip(Parser parser) {
        class SkipParser extends Parser {

            @Override
            public Object parse(State state) {
                Parser par = choice(skip1(parser), AtomOperator.Return(new ArrayList<>()));
                Object re = par.parse(state);
                return re;
            }
        }
        return new SkipParser();
    }

    /**
     * match many times .if failure occured ,then use the tailParser to Parse.
     * And manyTail use Over Monand ,so the tailParser will be abandoned.
     * @param parser used to match many times
     * @param tailParser used to match the tail
     * @return
     */
    public static Parser manyTail(Parser parser, Parser tailParser) {
        return many(parser).over(tailParser);
    }

    /**
     * match many times at least one.if failure occured ,then use the tailParser to Parse.
     * And manyTail use Over Monand ,so the tailParser will be abandoned.
     * @param parser used to match many times at least one
     * @param tailParser used to match the tail
     * @return
     */
    public static Parser many1Tail(Parser parser, Parser tailParser) {
        return many1(parser).over(tailParser);
    }

    /**
     * otherwise operator match the given parser if failed ,it will throw an exception with given description
     * @param parser the parser
     * @param description the description
     * @return the parser
     */
    public static Parser otherWise(Parser parser, String description) {
        Parser par = choice(parser, AtomOperator.fail(description));
        class OtherWiseParser extends Parser {

            @Override
            public Object parse(State state) {
                Object re = null;
                try {
                    re = parser.parse(state);
                } catch (RuntimeException e) {
                    throw new RuntimeException(description);
                }
                return re;
            }
        }
        return new OtherWiseParser();
    }
}