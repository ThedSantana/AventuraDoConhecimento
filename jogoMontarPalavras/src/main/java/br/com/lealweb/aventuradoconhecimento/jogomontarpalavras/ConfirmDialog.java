package br.com.lealweb.aventuradoconhecimento.jogomontarpalavras;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by leonardoleal on 17/10/16.
 */
public class ConfirmDialog {

    private final Context context;
    private DialogInterface.OnClickListener dialogClickListener;

    public ConfirmDialog(Context context, DialogInterface.OnClickListener dialogClickListener) {
        this.context = context;
        this.dialogClickListener = dialogClickListener;
    }

    public void show(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(R.string.yes, dialogClickListener)
                .setNegativeButton(R.string.no, dialogClickListener).show();

    }
}
