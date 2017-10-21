package com.green.kinsomy.interview.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by kinsomy on 2017/10/21.
 */

public class MyFileFilter implements FileFilter {

    private String fileName;

    public MyFileFilter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean accept(File file) {
        return file.getName().contains(fileName);
    }
}
