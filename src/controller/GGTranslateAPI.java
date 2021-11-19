package controller;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class GGTranslateAPI {

    public static InputStream getAudio(String word) throws IOException {
        word = word.trim().replace(" ","+");
        String link = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en&q=" + word;
        URL url = new URL(link);
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStream audioSrc = urlConn.getInputStream();
        return new BufferedInputStream(audioSrc);
    }

    public static String getMeaning(String word) throws IOException {
        word = word.trim().replace(" ","+");
        String link = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=vi&dt=t&q=" + word;
        URL url = new URL(link);
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String res = in.readLine().split("\"")[1];
        return res;

    }

    public static void main(String[] args) throws IOException, JavaLayerException {
        String word = "Live   a life   you   will    remember !";
        Player player = new Player(getAudio(word));
        player.play();

        System.out.println(getMeaning(word));
    }
}