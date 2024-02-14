package src.com.yuan;

public class Main {
    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.start("./conf");
    }
}
