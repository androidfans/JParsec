package com.ll.JParsec.lib;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by liuli on 15-12-3.
 */
public class CombinatorOperator extends Operator {
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

    public static Parser many(Parser parser) {
        class ManyParser extends Parser {

            @Override
            public Object[] parse(State state) {
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
                return re.toArray();
            }
        };
        return new ManyParser();
    }

    public static Parser many1(Parser parser) {
        class Many1Parser extends Parser{

            @Override
            public Object parse(State state) {
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
                return re.toArray();
            }
        }
        return new Many1Parser();
    }

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
                        if (state.pos() == index) {
                            throw e;
                        }
                    }
                    return re;
                }
                throw new RuntimeException();
            }
        }
        return new ChoiceParser();
    }
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
        return null;
    }

    public static Parser sepBy(Parser parser, Parser separator) {
        Parser par = choice(sepBy1(parser, separator), AtomOperator.pack(new ArrayList<Object>()));
        return par;
    }

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

    public static Parser skip(Parser parser) {
        class SkipParser extends Parser {

            @Override
            public Object parse(State state) {
                Parser par = choice(skip1(Try(parser)), AtomOperator.pack(new ArrayList<>()));
                Object re = par.parse(state);
                return re;
            }
        }
        return new SkipParser();
    }

    public static Parser manyTail(Parser parser, Parser tailParser) {
        return many(parser).over(tailParser);
    }

    public static Parser many1Tail(Parser parser, Parser tailParser) {
        return many1(parser).over(tailParser);
    }

    public static Parser otherWise(Parser parser, String description) {
        Parser par = choice(parser, AtomOperator.Fail(description));
        return par;
    }
}
