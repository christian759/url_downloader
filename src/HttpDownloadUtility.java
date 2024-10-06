import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadUtility {

    private static final int BUFFER_SIZE = 4096;

    public static void downloadFile(String fileURL, String saveDir)
    throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // checking for HTTP RESPONSE first
        if (responseCode == HttpURLConnection.HTTP_OK){
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Dispostion");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null){
                // extract filename from the URL
                int index = disposition.indexOf("filename=");
                if(index > 0){
                    fileName = disposition.substring(index +10, disposition.length() -1);
                }
            }else {
                // extract filename from the URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/")+1, fileURL.length());
            }
            System.out.println("Content-Type = "+ contentType);
            System.out.println("Content-description = " + disposition);
            System.out.println("Content-length = "+ contentLength);
            System.out.println("fileName = "+ fileName);

            // opens input stream from the http connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            //opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            System.out.println("File Downloaded");
        }else {
            System.out.println("NO file to download, Server replied Http code: " + responseCode);
        }
        httpConn.disconnect();
    }
}
