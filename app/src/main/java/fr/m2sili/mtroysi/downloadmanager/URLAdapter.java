package fr.m2sili.mtroysi.downloadmanager;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

/**
 * Created by android on 11/27/16.
 */

public class URLAdapter extends ArrayAdapter<URLItem> {

    private final Context context;
//    private final String downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    public URLAdapter(Context context) {
        super(context, R.layout.ligne);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.ligne, parent, false);

        TextView label = (TextView) v.findViewById(R.id.label);
        TextView state = (TextView) v.findViewById(R.id.state);
        ProgressBar bar = (ProgressBar)v.findViewById(R.id.progress);

        URLItem urlItem = getItem(position);
        urlItem.setPosition(position);
        String[] parts = urlItem.getName().split("/");
        label.setText(parts[parts.length-1]);

        if (!urlItem.isInProgress() ) {
            state.setText("Téléchargement terminé");
            bar.setVisibility(View.GONE);
        }
        else {
            state.setText("En cours...");
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(urlItem.getProgression());
        }

        return v;
    }
}
