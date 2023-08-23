package br.com.makrosystems.haven.Testall;

import android.os.AsyncTask;
import android.os.Environment;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssyncTask extends AsyncTask< String, String, String> {
    @Override
    protected void onPreExecute(){}

    @Override
    protected String doInBackground(String... strings) {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        List<String> fileNames = Arrays.asList("HavenLicenseDecode.jar", "Haven.conf", "Magisk-v21.4.zip", "TesteFile.txt");

        List<String> foundFiles = null;
        try {
            foundFiles = searchFiles(rootPath, fileNames);
        } catch (ErrnoException e) {
            e.printStackTrace();
        }
        if (foundFiles.isEmpty()) {
            System.out.println("\n\n\n\n no file found \n\n\n\n");
        } else {
            System.out.println("\n\n\n\n file found \n\n\n\n");
            for (String filePath : foundFiles) {
                System.out.println(filePath);
            }
            System.out.println("\n\n\n\n Files above \n\n\n\n");
        }
        return rootPath;
    }
    public static List<String> searchFiles(String rootPath, List<String> fileNames) throws ErrnoException {
        List<String> foundFiles = new ArrayList<>();
        searchFilesRecursively(new File(rootPath), fileNames, foundFiles);
        return foundFiles;
    }
    private static void searchFilesRecursively(File directory, List<String> fileNames, List<String> foundFiles) throws ErrnoException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFilesRecursively(file, fileNames, foundFiles);
                } else {
                    String fileName = file.getName();
                    for (String targetFileName : fileNames) {
                        if (fileName.equalsIgnoreCase(targetFileName)) {
                            foundFiles.add(file.getAbsolutePath());
                            StructStat info2 = Os.stat(file.getAbsolutePath().toString());
                            StructStat info1 = Os.lstat(file.getAbsolutePath().toString());
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
    protected void onPostExecute(String string){


    }
}
