package com.ll.JParsec.lib;

/**
 * Created by liuli on 15-12-3.
 */
public class AtomOperator extends Operator {

    /*
    匿名内部类竟然不能传泛型,惊呆了!!
     */
    public static Parser equal(Character character) {
        class EqualParser extends Parser<Character> {
            @Override
            Character parse(State state) throws Exception {
                Character data = state.next();
                if (data.equals(character)) {
                    return data;
                }
                throw new RuntimeException("expect a value equal " + character + " but get" + data);
            }
        }
        return new EqualParser();
    }
    public static Parser notEqual(Character character) {
        class NotEqualParser extends Parser<Character>{
            @Override
            Character parse(State state) throws Exception {
                Character data = state.next();
                if (!data.equals(character)) {
                    return data;
                }
                throw new RuntimeException("expect a value not equal" + character + " but get" + data);
            }
        }
        return new NotEqualParser();
    }

    public static Parser oneOf(Character...characters){
        class OneOfParser extends Parser<Character>{
            @Override
            Character parse(State state) throws Exception {
                Character data = state.next();
                for (Character c : characters) {
                    if(data.equals(c))
                        return data;
                }
                throw new RuntimeException("expect one of" + characters);
            }
        }
        return new OneOfParser();
    }

    public static Parser noneOf(Character... characters) {
        class NoneOfParser extends Parser<Character>{

            @Override
            Character parse(State state) throws Exception {
                Character data = state.next();
                for (Character c : characters) {
                    if (data.equals(c)) {
                        throw new RuntimeException("expect none of" + characters);
                    }
                }
                return data;
            }
        }
        return new NoneOfParser();
    }
}
