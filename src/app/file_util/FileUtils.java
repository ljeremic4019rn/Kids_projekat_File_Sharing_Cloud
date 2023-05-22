package app.file_util;

import app.AppConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileUtils {


    public static boolean isPathFile(String rootDirectory, String path) {
        File f = new File(rootDirectory + "\\" + path);
        System.out.println("# file exists");
        return f.isFile();
    }

    public static FileInfo getFileInfoFromPath(String rootDirectory, String path) {
        path = rootDirectory + "\\" + path;
        File f = new File(path);

        if (!f.exists()) {
            AppConfig.timestampedErrorPrint("File " + path + " doesn't exist.");
            return null;
        }
        if (f.isDirectory()) {
            AppConfig.timestampedErrorPrint(path + " is a directory and not a file.");
            return null;
        }

        try {
            String filePath = path.replace(rootDirectory + "\\", "");

            BufferedReader reader = new BufferedReader(new FileReader(f));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line + "\n");
            }
            reader.close();

            if (!fileContent.isEmpty())
                fileContent.deleteCharAt(fileContent.length() - 1);

            return new FileInfo(filePath, fileContent.toString(), AppConfig.myServentInfo.getChordId());
        } catch (IOException e) {
            AppConfig.timestampedErrorPrint("Couldn't read " + path + ".");
        }

        return null;

    }

    //todo proveri dal je dobro
    public static List<FileInfo> getDirectoryInfoFromPath(String rootDirectory, String path) {

        List<FileInfo> fileInfoList = new ArrayList<>();

        path = rootDirectory + "\\" + path;
        File f = new File(path);
        if (!f.exists()) {
            AppConfig.timestampedErrorPrint("Directory " + path + " doesn't exist.");
            return fileInfoList;
        }

        if (f.isFile()) {
            AppConfig.timestampedErrorPrint(path + " is a file and not a directory.");
            return fileInfoList;
        }

        Queue<String> dirs = new LinkedList<>();
        dirs.add(path);

        while (!dirs.isEmpty()) {
            String dirPath = dirs.poll();
            List<String> subFiles = new ArrayList<>();

            File dir = new File(dirPath);
            for (File file : dir.listFiles()) {
                String filePath = file.getPath().replace(rootDirectory + "\\", "");
                subFiles.add(filePath);

                if (file.isFile()) {
                    FileInfo fileInfo = getFileInfoFromPath(rootDirectory, filePath);
                    if (fileInfo != null) {
                        fileInfoList.add(fileInfo);
                    }
                } else {
                    dirs.add(file.getPath());
                }
            }

            dirPath = dirPath.replace(rootDirectory + "\\", "");
            fileInfoList.add(new FileInfo(dirPath, subFiles, AppConfig.myServentInfo.getChordId()));
        }
        return fileInfoList;
    }

    public static boolean storeFile(String rootDirectory, FileInfo fileInfo) {
        //Prvo napravimo sve direktorijume iznad ovog fajla ako ih ima

        //todo sve ovo vrati ako treba

//        if (fileInfo.getPath().contains("\\")) {
//            String dirPath = rootDirectory + "\\" + fileInfo.getPath().substring(0, fileInfo.getPath().lastIndexOf('\\'));
//            File dir = new File(dirPath);
//            dir.mkdirs();
//        }

//        String filePath = rootDirectory + "\\" + fileInfo.getPath();
//        File f = new File(filePath);
//
//        System.out.println("Found file " + f + " and adding it to the WorkingMap");

//        try {
//            f.createNewFile();
//
//            PrintWriter fileWriter = new PrintWriter(new FileWriter(f));
//            fileWriter.write(fileInfo.getContent());
//            fileWriter.close();
//
//            //Dodajemo fajl u working direktorijum jer je pull-ovan
//
//            AppConfig.chordState.addToWorkingMap(fileInfo, f.lastModified());
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }

//        AppConfig.chordState.addToStorageMap(fileInfo, f.lastModified());

        return true;
    }


    //todo proveri dal je dobro (VRV ULSESS)
    public static void removeFile(String rootDirectory, String path) {

        //Brisemo fajl
        String filePath = rootDirectory + "\\" + path;
        File f = new File(filePath);
        f.delete();

        //Prodjemo kroz sve njegove nad-direktorijume i obrisemo ih ako su prazni
        if (path.contains("\\")){
            path = path.substring(0, path.lastIndexOf('\\'));
            String[] split = path.split("\\\\");
            for (int i = split.length - 1; i >= 0; i--) {
                String dirPath = split[i];
                for (int j = i - 1; j >=0; j--) {
                    dirPath = split[j] + "\\" + dirPath;
                }
                dirPath = rootDirectory + "\\" + dirPath;
                File dir = new File(dirPath);
                if (dir != null && dir.listFiles() != null && dir.listFiles().length == 0) {
                    dir.delete();
                }
            }
        }

    }

}
