package app.file_util;

import app.ChordState;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 463426265374700139L;

    private final String path;
    private final String content;
    private final boolean isDirectory;
    private final List<String> subFiles;

    public FileInfo(String path, boolean isDirectory, String content, List<String> subFiles) {
        this.path = path;
        this.isDirectory = isDirectory;
        this.content = content;
        this.subFiles = new ArrayList<>();
        if (subFiles != null) {
            this.subFiles.addAll(subFiles);
        }
    }

    public FileInfo(String path, String content) {
        this(path, false, content,  null);
    }

    public FileInfo(String path, List<String> subFiles) {
        this (path, true, "", subFiles);
    }

    public FileInfo(FileInfo fileInfo) {
        this(fileInfo.getPath(), fileInfo.isDirectory(), fileInfo.getContent(),  fileInfo.getSubFiles());
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isFile() {
        return !isDirectory;
    }

    public String getContent() {
        return content;
    }


    public List<String> getSubFiles() {
        return subFiles;
    }

    @Override
    public int hashCode() {
        return ChordState.chordHash(getPath());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FileInfo)return o.hashCode() == this.hashCode();
        return false;

    }

    @Override
    public String toString() {
        String toReturn;
        if (isDirectory) toReturn = "[" + getPath() + " {" + getSubFiles() + "}]";
        else toReturn = "[" + getPath() + "]";
        return toReturn;
    }

}
