package be.algielen.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void ensureFolderExists(File rootDirectory, File folder) throws IOException {
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new IOException("Folder cannot be created at " + rootDirectory.getPath());
            }
        } else if (folder.exists() && !folder.isDirectory()) {
            throw new IOException("Folder already exists but is a file");
        }
    }
}
