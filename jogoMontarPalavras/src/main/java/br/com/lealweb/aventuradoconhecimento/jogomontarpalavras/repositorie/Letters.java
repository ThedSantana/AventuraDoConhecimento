package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Figure;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Letter;

/**
 * Created by leonardoleal on 28/08/16.
 */
public class Letters {

    private static final String TAG = "repositorie.Letters";

    private Map<Character, Letter> letters;

    public Letters() {
        letters = new HashMap<Character, Letter>();

        letters.put('a', new Letter('a', GameUtil.decodeImage("letter/a.png")));
        letters.put('b', new Letter('b', GameUtil.decodeImage("letter/b.png")));
        letters.put('c', new Letter('c', GameUtil.decodeImage("letter/c.png")));
        letters.put('d', new Letter('d', GameUtil.decodeImage("letter/d.png")));
        letters.put('e', new Letter('e', GameUtil.decodeImage("letter/e.png")));
        letters.put('f', new Letter('f', GameUtil.decodeImage("letter/f.png")));
        letters.put('g', new Letter('g', GameUtil.decodeImage("letter/g.png")));
        letters.put('h', new Letter('h', GameUtil.decodeImage("letter/h.png")));
        letters.put('i', new Letter('i', GameUtil.decodeImage("letter/i.png")));
        letters.put('j', new Letter('j', GameUtil.decodeImage("letter/j.png")));
        letters.put('k', new Letter('k', GameUtil.decodeImage("letter/k.png")));
        letters.put('l', new Letter('l', GameUtil.decodeImage("letter/l.png")));
        letters.put('m', new Letter('m', GameUtil.decodeImage("letter/m.png")));
        letters.put('n', new Letter('n', GameUtil.decodeImage("letter/n.png")));
        letters.put('o', new Letter('o', GameUtil.decodeImage("letter/o.png")));
        letters.put('p', new Letter('p', GameUtil.decodeImage("letter/p.png")));
        letters.put('q', new Letter('q', GameUtil.decodeImage("letter/q.png")));
        letters.put('r', new Letter('r', GameUtil.decodeImage("letter/r.png")));
        letters.put('s', new Letter('s', GameUtil.decodeImage("letter/s.png")));
        letters.put('t', new Letter('t', GameUtil.decodeImage("letter/t.png")));
        letters.put('u', new Letter('u', GameUtil.decodeImage("letter/u.png")));
        letters.put('v', new Letter('v', GameUtil.decodeImage("letter/v.png")));
        letters.put('w', new Letter('w', GameUtil.decodeImage("letter/w.png")));
        letters.put('x', new Letter('x', GameUtil.decodeImage("letter/x.png")));
        letters.put('y', new Letter('y', GameUtil.decodeImage("letter/y.png")));

        letters.put('z', new Letter('z', GameUtil.decodeImage("letter/z.png")));
    }

    public Letter getLetterByValue(Character c) {
        return letters.get(c);
    }


}
