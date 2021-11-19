package model;

public class Word {
    private int id;
    private String word_target;
    private String htmlTag;
    private String word_explain;
    private String pronounce;
    private String synonym;
    private String antonym;

    /** Constructor. */
    public Word() {

    }

    public Word(int id, String word_target,String htmlTag, String word_explain, String pronounce, String synonym, String antonym) {
        this.id =id;
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.pronounce = pronounce;
        this.htmlTag = htmlTag;
        this.antonym = antonym;
        this.synonym =synonym;
    }

    public Word (String word_target) {
        this.word_target = word_target;
    }

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(Word other) {
        this.id = other.id;
        this.word_target = other.word_target;
        this.word_explain = other.word_explain;
        this.pronounce = other.pronounce;
        this.htmlTag = other.htmlTag;
        this.antonym = other.antonym;
        this.synonym = other.synonym;
    }


    /** Setter and getter. */
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setWord_target(String word_target){
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setPronounce(String pronounce){
        this.pronounce = pronounce;
    }

    public void setHtmlTag(String htmlTag){
        this.htmlTag = htmlTag;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getWord_target(){
        return this.word_target;
    }

    public String getWord_explain(){
        String[] explains = this.word_explain.split(";");
        String ans="";
        for(String x:explains) {
            ans += x + "\n";
        }
        return ans;
    }

    public String getPronounce(){
        return this.pronounce;
    }

    public String getHtmlTag(){
        return this.htmlTag;
    }

    public String getSynonym() {
        return this.synonym;
    }

    public String getAntonym() {
        return this.antonym;
    }

    public void display(){
        System.out.println("Word: " + this.word_target + "\t" + this.pronounce);
        System.out.println("Meaning: " + this.word_explain);
        System.out.println("Synonym: " + this.synonym);
        System.out.println("Antonym: " + this.antonym + "\n");
    }

    public boolean checkExists(){
        if(this.word_target==null)
            return false;
        return true;
    }
}