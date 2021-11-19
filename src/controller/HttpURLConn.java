package controller;
public class HttpURLConn{

    public static String searchSynonym(String wordToSearch) throws Exception {
        Search search = new Search("syn");
        return search.searchSynonymAndAntonym(wordToSearch);
    }

    public static String searchAntonym(String wordToSearch) throws Exception {
        Search search = new Search("ant");
        return search.searchSynonymAndAntonym(wordToSearch);
    }
}