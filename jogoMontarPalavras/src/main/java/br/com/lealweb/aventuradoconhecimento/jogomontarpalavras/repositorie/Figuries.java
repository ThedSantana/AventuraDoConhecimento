package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.repositorie;

import java.util.ArrayList;
import java.util.List;

import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.GameUtil;
import br.com.lealweb.aventuradoconhecimento.jogomontarpalavras.model.Figure;

/**
 * Created by leonardoleal on 28/08/16.
 */
public class Figuries {
    
    private static final String TAG = "repositorie.Figuries";

    private List<Figure> figures;

    public Figuries() {
        figures = new ArrayList<Figure>();

        figures.add(new Figure("abóbora", GameUtil.decodeImage("abobora.gif")));
        figures.add(new Figure("beholder", GameUtil.decodeImage("beholder.gif")));
        figures.add(new Figure("bruxa ", GameUtil.decodeImage("bruxa.gif")));
        figures.add(new Figure("bruxa", GameUtil.decodeImage("bruxa1.gif")));
        figures.add(new Figure("casa", GameUtil.decodeImage("casa_assombrada.gif")));
        figures.add(new Figure("ciclope", GameUtil.decodeImage("ciclope.gif")));
        figures.add(new Figure("dragão", GameUtil.decodeImage("dragao.gif")));
        figures.add(new Figure("fantasma", GameUtil.decodeImage("fantasma.gif")));
        figures.add(new Figure("frankestein", GameUtil.decodeImage("frankestein.gif")));
        figures.add(new Figure("gato", GameUtil.decodeImage("gato_preto.gif")));
        figures.add(new Figure("godzzila", GameUtil.decodeImage("godzzila.gif")));
        figures.add(new Figure("gremlin", GameUtil.decodeImage("gremlin.gif")));
        figures.add(new Figure("medusa", GameUtil.decodeImage("medusa.gif")));
        figures.add(new Figure("mumia", GameUtil.decodeImage("mumia.gif")));
        figures.add(new Figure("raio", GameUtil.decodeImage("raio.gif")));
        figures.add(new Figure("robô    ", GameUtil.decodeImage("robo.gif")));
        figures.add(new Figure("vampiro", GameUtil.decodeImage("vampiro.gif")));
        figures.add(new Figure("zumbi", GameUtil.decodeImage("zoombie.gif")));
    }

    public Figure getFigure() {
        return figures.remove(figures.size()-1);
    }
}
