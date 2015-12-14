# JParsec
JParsec is a lightweight stream parse library from Haskell language.
the stream include the text , the stream from java.io. and other streamlike data.
you just need to implement the State interface and call the parse method then pass your data,
then the Operator will serve for you. I have support a default implementation of the State , the most frequently-used Text State.

I hava write very exhaustive notation to indicate the usage of the operators.And the test package includes almost every operator's test case . if you don't know how to use it , refer to the test cases.

As an entire example for the JParsec , the [JPJson project](https://github.com/androidfans/JPJson) will give supporting for you.

This library is a member of the series of parsecs in [Dwarftisan](https://github.com/Dwarfartisan).

# JParsec
JParsec 是一个来自于Haskell语言的轻量级的**流**解析库.
这里的**流**包括文本,java.io中的流以及其他的流式的数据.你只需要实现State接口并且调用parse方法传入你构造的State数据.然后你所构造的各种算子就会开始为你服务了.JParsec库中已经有了一个默认的State实现,用于描述最广泛需要使用到解析功能的TextState.

我为每个算子都编写了详尽的注释以指导你算子的使用方法.并且代码中的Test包中包含几乎每个算子的测试用例.如果你看了注释还是不知道怎么使用这些算子,你可以参考这些测试用例.

并且[JPJson 项目](https://github.com/androidfans/JPJson)作为一个使用JParsec实现的json解析库,也将为您演示JParsec的一次在稍大的项目中的完整使用.

本库也是[Dwarftisan 组织](https://github.com/Dwarfartisan)中Parsec系列的Java版本实现.
