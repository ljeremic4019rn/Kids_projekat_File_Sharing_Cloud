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
    private final int OgNode;

    public FileInfo(String path, boolean isDirectory, String content, List<String> subFiles, int ogNode) {
        this.path = path;
        this.isDirectory = isDirectory;
        this.content = content;
        this.subFiles = new ArrayList<>();
        if (subFiles != null) {
            this.subFiles.addAll(subFiles);
        }
        this.OgNode = ogNode;
    }

    public FileInfo(String path, String content, int ogNode) {
        this(path, false, content,  null, ogNode);
    }

    public FileInfo(String path, List<String> subFiles, int ogNode) {
        this (path, true, "", subFiles, ogNode);
    }

    public FileInfo(FileInfo fileInfo) {
        this(fileInfo.getPath(), fileInfo.isDirectory(), fileInfo.getContent(),  fileInfo.getSubFiles(), fileInfo.getOgNode());
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public int getOgNode() {
        return OgNode;
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
        if (isDirectory) toReturn = "[" + getPath() + " {" + getSubFiles() + "}] - inside node: " + getOgNode();
        else toReturn = "[" + getPath() + "] - inside node: " + getOgNode();
        return toReturn;
    }

}
