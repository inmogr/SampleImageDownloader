package org.inmogr.sample.images.downloader.sample.download.library.handler;

import android.content.Context;
import android.widget.Toast;

import org.inmogr.sample.images.downloader.sample.download.library.InputStreamRequest;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class InputStreamResponseHandler {

    private String fileName;
    private long fileSize;
    private long fileLength;

    public InputStreamResponseHandler(Context context) {
        this.context = context;
    }

    public boolean saveFile(InputStreamRequest request, byte[] response, String savePath) {
        if (response == null) return false;
        //Read file name from headers
        fileName = "downloads";
        String type = request.getResponseHeaders().get("content-type");
        if (type != null)
            fileName += "."+type.substring(type.indexOf("/") + 1);
        fileLength = response.length;
        fileName = getIndexedFileName(fileName);
        InputStream input = new ByteArrayInputStream(response);
        File file = new File(savePath, fileName);
        try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                fileSize += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            return true;
        } catch (Exception error) {
            showError(error);
        }
        return false;
    }

    private String getIndexedFileName(String path) {
        String tempPath = path;
        int counter = 0;
        while (fileExists(tempPath)) {
            int index = path.lastIndexOf(".");
            tempPath = path.replace(path.substring(index), (counter++)+path.substring(index));
        }
        return tempPath;
    }

    private boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    private Context context;

    private void showError(Exception error) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getFileLength() {
        return fileLength;
    }
}
