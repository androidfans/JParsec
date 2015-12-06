package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class TextState implements State {

    private char[] state = null;

    private int index = 0;

    private int begin = 0;

    public TextState(String state) {
        this.state = state.toCharArray();
    }

    @Override
    public int begin() {
        if (begin == 0)
            begin = pos();
        return pos();
    }

    @Override
    public void commit(int tran) {
        if(tran == begin)
            begin = -1;
    }

    @Override
    public void rollBack(int tran) {
        seekTo(tran);
        if (begin == tran)
            begin = -1;
    }

    public int pos() {
        return index;
    }

    private void seekTo(int pos) {
        if (pos < 0 && pos >= state.length)
            throw new RuntimeException("pos out of bounds");
        index = pos;
    }

    @Override
    public Character next() {
        if (index == state.length)
            throw new RuntimeException("index out of bounds");
        return state[index++];
    }
}