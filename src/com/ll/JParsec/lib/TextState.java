package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class TextState implements State {

    private char[] state = null;

    private Integer index = 0;

    private Integer begin = null;

    public TextState(String state) {
        this.state = state.toCharArray();
    }

    @Override
    public Integer begin() {
        if (begin == null)
            begin = pos();
        return pos();
    }

    @Override
    public void commit(Integer tran) {
        if(tran == begin)
            begin = -1;
    }

    @Override
    public void rollBack(Integer tran) throws Exception {
        seekTo(tran);
        if (begin == tran)
            begin = -1;
    }

    private Integer pos() {
        return index;
    }

    private void seekTo(Integer pos) throws Exception {
        if (pos < 0 && pos >= state.length)
            throw new Exception("pos out of bounds");
        index = pos;
    }

    @Override
    public Character next() {
        if (index == state.length)
            throw new RuntimeException("index out of bounds");
        return state[index++];
    }
}