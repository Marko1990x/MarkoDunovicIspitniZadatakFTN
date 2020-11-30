package firebaseapp.com.ispitmarkodunovic.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import firebaseapp.com.ispitmarkodunovic.R;

public class DialogAboutMeToolbar extends AppCompatDialogFragment {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // context is get activity
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // create view
        View view = inflater.inflate(R.layout.about_me_dialog, null);

        builder.setView(view); // this dialog will have my layout
        builder.setView(view)
                .setTitle("Made By Marko Dunović")
                .setMessage("ispitni zadatak")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return builder.create();
    }

}
