package com.ll.JParsec.lib;

import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.util.ArrayList;

/**
 * Created by liuli on 15-12-5.
 */
public class TypeUtil {
    public static Character[] CharArrayToCharacterArray(char[] charArr) {
        Character[] characters = new Character[charArr.length];
        for (int i = 0; i < charArr.length; i++) {
            characters[i] = charArr[i];
        }
        return characters;
    }
}
