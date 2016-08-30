package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

/**
 * Created by leonardoleal on 30/08/16.
 */
public class LongClickListener implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View view) {

//        Log.d("getY", " -> -> "+ event.getY());
//        Log.d("getX", " -> -> "+ event.getX());
        ClipData data = ClipData.newPlainText("", "");
        DragShadow dragShadow = new DragShadow(view);

        view.startDrag(data, dragShadow, view, 0);

        return false;
    }


    private class DragShadow extends View.DragShadowBuilder {
        ColorDrawable grayBox;

        public DragShadow(View view) {
            super(view);

            grayBox = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            grayBox.draw(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            View v = getView();

            int height = (int) v.getHeight() / 2;
            int width = (int) v.getWidth() / 2;

            grayBox.setBounds(0, 0, width, height);

            shadowSize.set(width, height);
            shadowTouchPoint.set((int) width / 2, (int) height / 2);
        }
    }
}
