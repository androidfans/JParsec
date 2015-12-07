package com.ll.JParsec.lib;

import java.util.ArrayList;

/**
 * Created by liuli on 15-12-3.
 */
public class TextOperator {

    /**
     * Chr operator just like equal , but it is special for character
     * @param chr the given character to match
     * @return the Parser
     */
    public static Parser Chr(char chr) {
        return AtomOperator.equal(chr);
    }

    /**
     * Str operator match a string
     * @param string the given string
     * @return the parser
     */
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

    /**
     * charOf operator is a specialized oneOf for character to decide if a char in a string
     * @param string the given string
     * @return the Parser
     */
    public static Parser charOf(String string) {
        return AtomOperator.oneOf(string.toCharArray());
    }

    /**
     * space operator match a space
     * @return the Parser
     */
	public static Parser space() {
        return Chr(' ');
    }

    /**
     * newLine operator match a line separator character(string)
     * @return the parser
     */
    public static Parser newLine() {
        return Str(Global.LINESEPARATOR);
    }

    /**
     * whieteSpace operator match a white space character(string) which include tab space and line separator
     * @return
     */
    public static Parser whiteSpace() {
        return CombinatorOperator.choice(CombinatorOperator.Try(space()), CombinatorOperator.Try(newLine()), Chr('\t'));
    }

    /**
     * Digit operator match a Digit character
     * @return the parser
     */
    public static Parser Digit() {
        return charOf("0123456789");
    }

    /**
     * uInt match an unsigned int value
     * @return the parser
     */
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

    /**
     * Int match an int value
     * @return the Parser
     */
    public static Parser<String> Int() {
        return CombinatorOperator.choice(CombinatorOperator.Try(Chr('-')).then(uInt()).bind(new MinusHandler()), uInt());
    }

    /**
     * uFloat match uFloat value.and it also support the .xx format .it returns 0.xx value
     * @return the parser
     */
    public static Parser<String> uFloat() {
        class UFloatParser extends Parser<String> {
            @Override
            public String parse(State state) {
                Parser chrPoint = Chr('.');
                Parser Try = CombinatorOperator.Try(uInt());
                Parser choice = CombinatorOperator.choice(Try.over(chrPoint), chrPoint.then(AtomOperator.Return("0")));
                String left = (String) choice.parse(state);
                String right = uInt().parse(state);
                return left + "." + right;
            }
        }
        return new UFloatParser();
    }

    /**
     * Float match a Float value. also support the .xx format
     * @return the parser
     */
    public static Parser<String> Float() {
        return CombinatorOperator.choice(CombinatorOperator.Try(Chr('-')).then(uFloat()).bind(new MinusHandler()), uFloat());
    }
}

/**
 * MinusHandler to match the negative symbol
 */
class MinusHandler extends HandlerAdapter{
    @Override
    public Object bindHandle(Object value, State state) {
        return "-" + value;
    }
}
