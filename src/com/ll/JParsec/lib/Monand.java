package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public interface Monand {
    Parser bind(Parser parser);

    Parser then(Parser parser);

    Parser over(Parser parser);
}
