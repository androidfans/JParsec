package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public abstract class Parser <T> implements Monand {
    public abstract T parse(State state);

    @Override
    public Parser bind(Handler handler) {

        class BindParser extends Parser {

            @Override
            public Object parse(State state) {
                Object val = Parser.this.parse(state);
                Object re = handler.bindHandle(val, state);
                return re;
            }
        }

        return new BindParser();
    }

    @Override
    public Parser then(Parser parser) {
        class ThenParser extends Parser {

            @Override
            public Object parse(State state) {
                Parser.this.parse(state);
                return parser.parse(state);
            }
        }
        return new ThenParser();
    }

    @Override
    public Parser over(Parser parser) {
        class OverParser extends Parser{

            @Override
            public Object parse(State state) {
                Object val = Parser.this.parse(state);
                parser.parse(state);
                return val;
            }
        }
        return new OverParser();
    }
}
