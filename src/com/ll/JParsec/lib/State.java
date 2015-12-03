package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public interface State {
    Integer begin();

    void commit(Integer tran);

    void rollBack(Integer tran) throws Exception;

    Character next() throws Exception;
}