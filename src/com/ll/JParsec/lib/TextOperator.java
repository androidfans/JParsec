package com.ll.JParsec.lib;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.ArrayList;

/**
 * Created by liuli on 15-12-3.
 */
public class TextOperator {

    public static Parser Chr(Character character) {
        return AtomOperator.equal(character);
    }

    public static Parser Str(String string) {
        class StrParser extends Parser<String> {

            @Override
            public String parse(State state) {
                char[] StrArr = string.toCharArray();
                for (char c : StrArr) {
                    try {
                        AtomOperator.equal(c).parse(state);
                    } catch (RuntimeException e) {
                        throw e;
                    }
                }
                return string;
            }
        }
        return new StrParser();
    }

    public static Parser charOf(String string) {
        return AtomOperator.oneOf(string.toCharArray());
    }


	public static Parser space() {
        return Chr(' ');
    }

    public static Parser NewLine() {
        return Str(Global.LINESEPARATOR);
    }

    public static Parser whiteSpace() {
        return CombinatorOperator.choice(CombinatorOperator.Try(space()), CombinatorOperator.Try(NewLine()), Chr('\t'));
    }

    public static Parser Digit() {
        return charOf("0123456789");
    }

    public static Parser<String> uInt() {
        class UIntParser extends Parser<String> {

            @Override
            public String parse(State state) {
                Parser many1 = CombinatorOperator.many1(Digit());
                ArrayList<Character> arr = (ArrayList<Character>) many1.parse(state);
                char[] chrs = new char[arr.size()];
                for (int i = 0; i < chrs.length; i++) {
                    chrs[i] = arr.get(i);
                }
                return new String(chrs);
            }
        }
        return new UIntParser();
    }

    public static Parser<String> Int() {
        class IntHandler extends HandlerAdapter{
            @Override
            public Object bindHandle(Object value, State state) {
                return "-" + value;
            }
        }
        return CombinatorOperator.choice(CombinatorOperator.Try(Chr('-')).then(uInt()).bind(new IntHandler()), uInt());
    }

    public static Parser<String> uFloat() {
        class UFloatParser extends Parser<String> {
            @Override
            public String parse(State state) {
                Parser chrPoint = Chr('.');
                Parser Try = CombinatorOperator.Try(uInt());
                Parser choice = CombinatorOperator.choice(Try);
                return "";
            }
        }
        return new UFloatParser();
    }

    public static Parser<String> Float() {
        return CombinatorOperator.choice(CombinatorOperator.Try(Chr('-')), uFloat());
    }
}
