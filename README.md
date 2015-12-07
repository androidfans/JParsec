# JParsec
JParsec is a lightweight stream parse library from Haskell language.
the stream include the text , the stream from java.io. and other streamlike data.
you just need to implement the State interface and call the parse method then pass your data,
then the Operator will serve for you. I have support a default implementation of the State , the most frequently-used Text State.

I hava write very exhaustive notation to indicate the usage of the operators.And the test package includes almost every operator's test case . if you don't know how to use it , refer to the test cases.

As an entire example for the JParsec , the [JPJson project](https://github.com/androidfans/JPJson) will give supporting for you.

This library is a member of the series of parsecs in [Dwarftisan](https://github.com/Dwarfartisan).