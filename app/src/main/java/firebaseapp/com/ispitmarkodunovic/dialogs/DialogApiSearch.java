package firebaseapp.com.ispitmarkodunovic.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import firebaseapp.com.ispitmarkodunovic.R;

public class DialogApiSearch extends AppCompatDialogFragment {
    // use the dial fragment class
    private EditText editTextPosition;
    private dialogPositionListener4 listener; // this is my interface

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // context is get activity
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // create view
        View view = inflater.inflate(R.layout.dialog_api_search, null);

        builder.setView(view); // this dialog will have my layout
        builder.setView(view)
                .setTitle("Enter Search Term")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String position = editTextPosition.getText().toString();
                        listener.searchApi(position); // this sends the data to the main activity

                    }
                });

        // initialization

        editTextPosition = view.findViewById(R.id.edit_position_toolbar);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (dialogPositionListener4) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+ "Must implement DialogPositionListener");
        }
    }

    // it needs an interface in order to get data
    public interface dialogPositionListener4{
        void searchApi(String position);
    }
}