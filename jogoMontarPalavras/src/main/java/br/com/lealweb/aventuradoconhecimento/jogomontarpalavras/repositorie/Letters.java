package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Letter;

public class Letters {

    private static final String TAG = "repositorie.Letters";

    private Map<Character, Bitmap> letters;

    public Letters() {
        letters = new HashMap<>();

        letters.put('a', GameUtil.decodeImage("letter/a.png"));
        letters.put('b', GameUtil.decodeImage("letter/b.png"));
        letters.put('c', GameUtil.decodeImage("letter/c.png"));
        letters.put('d', GameUtil.decodeImage("letter/d.png"));
        letters.put('e', GameUtil.decodeImage("letter/e.png"));
        letters.put('f', GameUtil.decodeImage("letter/f.png"));
        letters.put('g', GameUtil.decodeImage("letter/g.png"));
        letters.put('h', GameUtil.decodeImage("letter/h.png"));
        letters.put('i', GameUtil.decodeImage("letter/i.png"));
        letters.put('j', GameUtil.decodeImage("letter/j.png"));
        letters.put('k', GameUtil.decodeImage("letter/k.png"));
        letters.put('l', GameUtil.decodeImage("letter/l.png"));
        letters.put('m', GameUtil.decodeImage("letter/m.png"));
        letters.put('n', GameUtil.decodeImage("letter/n.png"));
        letters.put('o', GameUtil.decodeImage("letter/o.png"));
        letters.put('p', GameUtil.decodeImage("letter/p.png"));
        letters.put('q', GameUtil.decodeImage("letter/q.png"));
        letters.put('r', GameUtil.decodeImage("letter/r.png"));
        letters.put('s', GameUtil.decodeImage("letter/s.png"));
        letters.put('t', GameUtil.decodeImage("letter/t.png"));
        letters.put('u', GameUtil.decodeImage("letter/u.png"));
        letters.put('v', GameUtil.decodeImage("letter/v.png"));
        letters.put('w', GameUtil.decodeImage("letter/w.png"));
        letters.put('x', GameUtil.decodeImage("letter/x.png"));
        letters.put('y', GameUtil.decodeImage("letter/y.png"));
        letters.put('z', GameUtil.decodeImage("letter/z.png"));
    }

    public Letter getLetterByValue(Character c) {
        return new Letter(c, letters.get(c));
    }

    public Letter getRandomLetter() {
        Set<Character> keysSet = letters.keySet();
        Character[] keys = new Character[letters.size()];
        keysSet.toArray(keys);

        Random random = new Random();

        return getLetterByValue(keys[random.nextInt(letters.size())]);
    }
}
