package fr.m2sili.mtroysi.downloadmanager;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class DownloadManager extends ListActivity implements TaskFragment.TaskCallBacks {

    private URLAdapter adapter;
    private static final String TAG_TASKS_FRAGMENT = "task_fragment";
    private TaskFragment mTaskFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        adapter = new URLAdapter(this);

        FragmentManager fm = getFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASKS_FRAGMENT);
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASKS_FRAGMENT).commit();
        }

        setListAdapter(adapter);

        if(mTaskFragment.getAdapter() != null) {
            populate();
        } else {
            mTaskFragment.setAdapter(new URLAdapter(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final View alertDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(alertDialogView);

        TextView txtAlert = (TextView) alertDialogView
                .findViewById(R.id.viewAlert);

        switch (item.getItemId()) {
            case R.id.new_url: {
                txtAlert.setText("Saisir l'URL : ");
                adb.setTitle("Nouvelle URL");
            }
            break;
        }

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                Téléchargement
                EditText editText = (EditText) alertDialogView.findViewById(R.id.champSaisie);
                URLItem urlItem = new URLItem(editText.getText().toString());
                adapter.add(urlItem);
                mTaskFragment.getAdapter().add(urlItem);
//                new DownloadTask(mTaskFragment).execute(urlItem);
                mTaskFragment.launchDownload(urlItem);
            }
        });

        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + adapter.getItem(position).getName()), "image/*");
        startActivity(intent);
    }

    @Override
    public void onItemUpdate(URLItem urlItem) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemDone(URLItem urlItem) {
        adapter.getItem(urlItem.getPosition()).setInProgress(false);
        adapter.notifyDataSetChanged();
        Toast.makeText(DownloadManager.this, "Téléchargement de " + adapter.getItem(urlItem.getPosition()).getUrl() + " terminé !",Toast.LENGTH_LONG).show();
    }

    private void populate() {
        int i;
        for(i = 0; i < mTaskFragment.getAdapter().getCount(); ++i) {
            adapter.add(mTaskFragment.getAdapter().getItem(i));
        }
    }
}
