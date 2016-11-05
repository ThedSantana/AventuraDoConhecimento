package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Figure;

public class Figuries {
    
    private static final String TAG = "repositorie.Figuries";

    private List<Figure> figures;

    public Figuries() {
        figures = new ArrayList<>();

        figures.add(new Figure("bruxa", GameUtil.decodeImage("bruxa.png")));
        figures.add(new Figure("bruxa", GameUtil.decodeImage("bruxa1.png")));
        figures.add(new Figure("casa", GameUtil.decodeImage("casa_assombrada.png")));
        figures.add(new Figure("ciclope", GameUtil.decodeImage("ciclope.png")));
        figures.add(new Figure("fantasma", GameUtil.decodeImage("fantasma.png")));
        figures.add(new Figure("gato", GameUtil.decodeImage("gato_preto.png")));
        figures.add(new Figure("medusa", GameUtil.decodeImage("medusa.png")));
        figures.add(new Figure("mumia", GameUtil.decodeImage("mumia.png")));
        figures.add(new Figure("raio", GameUtil.decodeImage("raio.png")));
        figures.add(new Figure("vampiro", GameUtil.decodeImage("vampiro.png")));
        figures.add(new Figure("zumbi", GameUtil.decodeImage("zumbi.png")));
    }

    public Figure getFigureAleatorie() {
        Random random = new Random(System.currentTimeMillis());
        return figures.remove(random.nextInt(figures.size()));
    }

    public boolean isEmpty() {
        return figures.isEmpty();
    }
}
