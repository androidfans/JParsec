package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public abstract class Parser <T> implements Monand {
    public abstract T parse(State state);

    @Override
    public Parser bind(Handler handler) {
        return new Parser() {
            @Override
            public Object parse(State state){
                Object val = parse(state);
                Object re = handler.bindHandle(val, state);
                return re;
            }
        };
    }

    @Override
    public Parser then(Parser parser) {
        return new Parser() {
            @Override
            public Object parse(State state){
                parse(state);
                return parser.parse(state);
            }
        };
    }

    @Override
    public Parser over(Parser parser) {
        return new Parser() {
            @Override
            public Object parse(State state){
                Object val = parse(state);
                parser.parse(state);
                return val;
            }
        };
    }
}
