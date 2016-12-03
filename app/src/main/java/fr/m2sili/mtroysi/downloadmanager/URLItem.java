package fr.m2sili.mtroysi.downloadmanager;

import android.os.Environment;
import android.widget.ProgressBar;

/**
 * Created by android on 11/27/16.
 */

public class URLItem {
    private final String downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    private String url;
    private double size;
    private String name;
    private int progression;
    private boolean inProgress;
    private int position;

    public URLItem(String url) {
        this.url = url;
        String[] parts = url.split("/");
        this.name = downloadFolder + "/" + parts[parts.length-1];
        this.inProgress = true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgression() {
        return progression;
    }

    public void setProgression(int progression) {
        this.progression = progression;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
