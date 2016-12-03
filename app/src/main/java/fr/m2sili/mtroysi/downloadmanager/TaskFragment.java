package fr.m2sili.mtroysi.downloadmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by android on 11/28/16.
 */

public class TaskFragment extends Fragment {
    static interface TaskCallBacks {
        public void onItemUpdate(URLItem urlItem);
        public void onItemDone(URLItem urlItem);
    }
    private TaskCallBacks mMainActivityListener = null;
    private URLAdapter adapter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mMainActivityListener = (TaskCallBacks) activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.mMainActivityListener = null;
    }
    protected void onProgressUpdate(URLItem urlItem) {
        if (mMainActivityListener != null) mMainActivityListener.onItemUpdate(urlItem);
    }
    protected void onPostExecute(URLItem urlItem) {
        if (mMainActivityListener != null) mMainActivityListener.onItemDone(urlItem);
    }

    public void launchDownload(URLItem urlItem) {
        new DownloadTask(this).execute(urlItem);
    }

    public URLAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(URLAdapter adapter) {
        this.adapter = adapter;
    }
}

