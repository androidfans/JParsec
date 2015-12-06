package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public interface State {
    int begin();

    void commit(int tran);

    void rollBack(int tran);

    Character next() throws RuntimeException;

    int pos();
}