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

        letters.put('i', new Letter('i', GameUtil.decodeImage("letter/i.png")));

        letters.put('m', new Letter('m', GameUtil.decodeImage("letter/m.png")));

        letters.put('u', new Letter('u', GameUtil.decodeImage("letter/u.png")));

        letters.put('z', new Letter('z', GameUtil.decodeImage("letter/z.png")));
    }

    public Letter getLetterByValue(Character c) {
        return letters.get(c);
    }


}
