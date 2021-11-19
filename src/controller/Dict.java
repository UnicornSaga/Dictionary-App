package controller;

import model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dict implements DictInterface{
    private String language;
    private Connection conn;

    public Dict(String language, Connection conn) {
        this.language = language;
        this.conn = conn;
    }

    @Override
    public Word searchWord(String other){
        try {
            String sql = "select *from "+ language + " where word = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, other);
            ResultSet rs = ps.executeQuery();
            if(rs.isClosed()) {
                return new Word();
            }
            int id = rs.getInt(1);
            String htmlTag = rs.getString(3);
            String word_explain = "";
            String pronounce = rs.getString(5);
            String synonym = rs.getString(6);
            String antonym = rs.getString(7);
            while (rs.next()) {
                word_explain += rs.getString(4)+"\n";
            }
            Word ans = new Word(id, other, htmlTag, word_explain, pronounce, synonym,antonym);
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Word();
        }
    }

    @Override
    public ObservableList<String> recommendWord(String other) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            String s = "'" + other + "%'";
            String sql = "select word from " + this.language + " where word like " + s + " limit 50";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int i=0;
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    @Override
    public void insertWord(Word word){
        try {
            String sql = "insert into " + language + "(id, word, html, description, pronounce, synonym, antonym) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, word.getId());
            ps.setString(2, word.getWord_target());
            ps.setString(3, word.getHtmlTag());
            ps.setString(4, word.getWord_explain());
            ps.setString(5, word.getPronounce());
            ps.setString(6, word.getSynonym());
            ps.setString(7, word.getAntonym());
            ps.executeUpdate();
            System.out.println("The word has been inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWord(String other){
        try {
            String sql="delete from " + language + " WHERE  word = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, other);
            ps.execute();
            System.out.println("The word "+ other +" has been removed!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void updateWord(Word word, Word newWord) {
        try {
            String sql = "update " + language + " set word = ?, description = ?, html = ? where word = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newWord.getWord_target());
            ps.setString(2, newWord.getWord_explain());
            ps.setString(3, newWord.getHtmlTag());
            ps.setString(4, word.getWord_target());
            ps.execute();
            System.out.println("The word has been updated!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public int getNewId() {
        int ans = 0;
        try {
            String sql  = "select id from " + language + " ORDER by id desc limit 1;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ans = rs.getInt(1);
            return ans+1;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }
}