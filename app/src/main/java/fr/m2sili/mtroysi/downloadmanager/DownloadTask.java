package fr.m2sili.mtroysi.downloadmanager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by android on 11/27/16.
 */

public class DownloadTask extends AsyncTask<URLItem, URLItem, URLItem> {

    protected TaskFragment taskFragment = null;

    public DownloadTask(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;
    }

    @Override
    protected URLItem doInBackground(URLItem... urlItems) {
        try {
            URL url = new URL(urlItems[0].getUrl());
            URLConnection connection = url.openConnection();
            connection.connect();
            long totalSize = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(urlItems[0].getName());
            byte data[] = new byte[1024];
            int count;
            long current = 0;
            while ((count = input.read(data)) != -1) {
                current += count;
                output.write(data, 0, count);
                int tmp = (int) ((current * 100) / totalSize);
                urlItems[0].setProgression(tmp);
                publishProgress(urlItems[0]);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.v("DownloadManager","Exception lors du téléchargement.");
        }
        return urlItems[0];
    }

    @Override
    protected void onProgressUpdate(URLItem... urlItems) {
//        adapter.getItem(adapter.getCount() - 1).setProgression(values[0]);
//        adapter.notifyDataSetChanged();
        taskFragment.onProgressUpdate(urlItems[0]);
    }

    @Override
    protected void onPostExecute(URLItem urlItem) {
//        adapter.getItem(adapter.getCount() - 1).setInProgress(false);
//        adapter.notifyDataSetChanged();
//        Toast.makeText(adapter.getContext(), "Téléchargement de " + adapter.getItem(adapter.getCount() - 1).getUrl() + " terminé !",Toast.LENGTH_LONG).show();
        taskFragment.onPostExecute(urlItem);
    }
}
