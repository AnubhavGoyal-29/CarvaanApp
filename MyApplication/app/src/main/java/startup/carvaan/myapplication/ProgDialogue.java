package startup.carvaan.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProgDialogue {
    private String dialogueMessage;
    private String dialogueTitle;
    private boolean setCancellable;
    public Context context;
    public ProgressDialog progressDialog;


    public ProgDialogue(String dialogueMessage, String dialogueTitle, boolean setCancellable, Context context,ProgressDialog progressDialog) {
        this.dialogueMessage = dialogueMessage;
        this.dialogueTitle = dialogueTitle;
        this.setCancellable = setCancellable;
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public void showDialogue(ProgressDialog progressDialog){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(dialogueMessage); // Setting Message
        progressDialog.setTitle(dialogueTitle); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(setCancellable);

    }

    public static void dissmissDialogue(ProgressDialog progressDialog){

        progressDialog.dismiss();

    }
}
