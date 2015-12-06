package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-4.
 */
public abstract class Handler {
    public abstract Object bindHandle(Object value, State state);

    public abstract Object thenHandle(State state);

    public abstract Object overHandle(State state);
}

class HandlerAdapter extends Handler {
    @Override
    public Object bindHandle(Object value, State state) {
        return null;
    }

    @Override
    public Object thenHandle(State state) {
        return null;
    }

    @Override
    public Object overHandle(State state) {
        return null;
    }
}
