
package com.elvis.magicdevice.FileChooser;

import android.content.Context;
import android.os.FileObserver;
import android.support.v4.content.AsyncTaskLoader;

import java.io.FileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

public class FileLoader extends AsyncTaskLoader<List<File>> {
    public static final String HIDDEN_PREFIX = ".";
    private static final int FILE_OBSERVER_MASK = FileObserver.CREATE
            | FileObserver.DELETE | FileObserver.DELETE_SELF
            | FileObserver.MOVED_FROM | FileObserver.MOVED_TO
            | FileObserver.MODIFY | FileObserver.MOVE_SELF;

    private FileObserver mFileObserver;

    private List<File> mData;
    private String mPath;

    public FileLoader(Context context, String path) {
        super(context);
        this.mPath = path;
    }

    @Override
    public List<File> loadInBackground() {

        ArrayList<File> list = new ArrayList<File>();

        // Current directory File instance
        final File pathDir = new File(mPath);

        // List file in this directory with the directory filter
        final File[] dirs = pathDir.listFiles(sDirFilter);
        if (dirs != null) {
            // Sort the folders alphabetically
            Arrays.sort(dirs, sComparator);
            // Add each folder to the File list for the list adapter
            for (File dir : dirs)
                list.add(dir);
        }

        // List file in this directory with the file filter
        final File[] files = pathDir.listFiles(sFileFilter);
        if (files != null) {
            // Sort the files alphabetically
            Arrays.sort(files, sComparator);
            // Add each file to the File list for the list adapter
            for (File file : files)
                list.add(file);
        }

        return list;
    }

    @Override
    public void deliverResult(List<File> data) {
        if (isReset()) {
            onReleaseResources(data);
            return;
        }

        List<File> oldData = mData;
        mData = data;

        if (isStarted())
            super.deliverResult(data);

        if (oldData != null && oldData != data)
            onReleaseResources(oldData);
    }

    @Override
    protected void onStartLoading() {
        if (mData != null)
            deliverResult(mData);

        if (mFileObserver == null) {
            mFileObserver = new FileObserver(mPath, FILE_OBSERVER_MASK) {
                @Override
                public void onEvent(int event, String path) {
                    onContentChanged();
                }
            };
        }
        mFileObserver.startWatching();

        if (takeContentChanged() || mData == null)
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (mData != null) {
            onReleaseResources(mData);
            mData = null;
        }
    }

    @Override
    public void onCanceled(List<File> data) {
        super.onCanceled(data);

        onReleaseResources(data);
    }

    protected void onReleaseResources(List<File> data) {

        if (mFileObserver != null) {
            mFileObserver.stopWatching();
            mFileObserver = null;
        }
    }

    public static FileFilter sDirFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return directories only and skip hidden directories
            return file.isDirectory() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };
    public static Comparator<File> sComparator = new Comparator<File>() {
        @Override
        public int compare(File f1, File f2) {
            // Sort alphabetically by lower case, which is much cleaner
            return f1.getName().toLowerCase().compareTo(
                    f2.getName().toLowerCase());
        }
    };
    public static FileFilter sFileFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return files only (not directories) and skip hidden files
            return file.isFile() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };

}