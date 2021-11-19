package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Search {
    private static final String USER_AGENT = "Mozilla/5.0";
    private String str;
    public Search(String str) {
        this.str = str;
    }

    public String searchSynonymAndAntonym(String wordToSearch) throws Exception {
        System.out.println("Sending request...");

        String url = "https://api.datamuse.com/words?rel_"+ str +"=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String ans="";

        try {
            // converting JSON array to ArrayList of words
            ArrayList<Search.Word> words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Search.Word.class)
            );
            if(str.equals("syn"))
                ans = "Synonym word of '" + wordToSearch + "': ";
            else
                ans = "Antonym word of '" + wordToSearch + "': ";
            if(words.size() > 0) {
                for(Search.Word word : words) {
                    ans += word.getWord() + ", ";
                }
                ans += "...";
                return ans;
            }
            else {
                return (ans+"Not found!");
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return (ans+ "Not found!");
    }

    private static class Word {
        private String word;
        private int score;

        public String getWord() {return this.word;}
        public int getScore() {return this.score;}
    }

    public static void main(String[] args) throws Exception {
        Search search = new Search("ant");
        search.searchSynonymAndAntonym("father");
    }
}
