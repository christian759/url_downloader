import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileURL = "https://www.youtube.com/watch?v=7seSIKMZbPM";
        String saveDir = "C:\\Users\\chris";
        try {
            HttpDownloadUtility.downloadFile(fileURL, saveDir);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}