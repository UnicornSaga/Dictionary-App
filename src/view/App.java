package view;

import model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import controller.DAOManager;
import controller.Dict;
import controller.HttpURLConn;
import controller.GGTranslateAPI;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App implements Initializable {
    Word word_to_find;
    DAOManager daoMngr = new DAOManager();

    @FXML
    private ObservableList<String> rcmlist = FXCollections.observableArrayList();

    @FXML
    private ListView listView = new ListView(rcmlist);

    @FXML
    private Button add_word_btn;

    @FXML
    private TextField word_bar;

    @FXML
    private TextField word_meaning_bar;

    @FXML
    private TextField word_pronounce_bar;

    public void addWordAlert() {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(130);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setFont(new Font("Arial", 20));
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        if (word_to_add.checkExists()) {
            label.setText("Từ này đã có trong từ điển!");
        } else {
            label.setText("Đã thêm từ thành công!");
        }
        stage.show();
    }

    public void delWordAlert() {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(130);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setFont(new Font("Arial", 20));
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        if (word_to_del.checkExists()) {
            label.setText("Xóa từ thành công!");
        } else {
            label.setText("Từ này hiện không có trong từ điển!");
        }
        stage.show();
    }

    public Word word_to_add;
    public Word word_to_del;
    public Word word_to_edit;
    public Word word_to_check;

    @FXML
    private Button check_word_btn;

    @FXML
    private TextField check_word_bar;

    public void checkWord() {
        Dict dict;
        if(search_mode.getValue() == "av") {
            dict = new Dict("av", daoMngr.conn);
        }else{
            dict = new Dict("va", daoMngr.conn);
        }
        word_to_check = dict.searchWord(check_word_bar.getText());
        check_word_alert();
    }

    public void check_word_alert() {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(130);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setFont(new Font("Arial", 20));
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        if (word_to_check.checkExists()) {
            label.setText("Từ này hiện có trong từ điển!");
        } else {
            label.setText("Từ này hiện không có trong từ điển!");
        }
        stage.show();
    }

    public void addWord() {
        Dict dict;
        if(search_mode.getValue() == "av") {
            dict = new Dict("av", daoMngr.conn);
        }else{
            dict = new Dict("va", daoMngr.conn);
        }
        word_to_add = dict.searchWord(word_bar.getText());
        String html = "";
        if(word_to_add.checkExists()) {
            addWordAlert();
            return;
        } else {
            Word newWord = new Word();
            html = "<h1>" + word_bar.getText() + "</h1>";
            html += "<h3><i>" + word_pronounce_bar.getText() + "</i></h3>";
            html += "<p>" + word_meaning_bar.getText() + "</p>";
            newWord.setHtmlTag(html);
            newWord.setId(dict.getNewId());
            newWord.setWord_explain(word_meaning_bar.getText());
            newWord.setWord_target(word_bar.getText());
            newWord.setPronounce(word_pronounce_bar.getText());
            dict.insertWord(newWord);
            addWordAlert();
        }
    }

    @FXML
    private Button del_word_btn;

    @FXML
    private TextField del_word_bar;

    @FXML
    private Label del_word_mes;

    public void delWord() {
        Dict dict;
        if(search_mode.getValue() == "av") {
            dict = new Dict("av", daoMngr.conn);
        }else{
            dict = new Dict("va", daoMngr.conn);
        }
        word_to_del = dict.searchWord(del_word_bar.getText());
        if (word_to_del.checkExists()) {
            dict.deleteWord(del_word_bar.getText());
            delWordAlert();
        } else {
            delWordAlert();
        }
    }

    @FXML
    private Button mod_word_btn;

    @FXML
    private TextField word_mod_bar;

    @FXML
    private TextField word_mod_pronounce;

    @FXML
    private TextField word_mod_meaning;

    public void editWord() {
        Dict dict;
        if(search_mode.getValue() == "av") {
            dict = new Dict("av", daoMngr.conn);
        }else{
            dict = new Dict("va", daoMngr.conn);
        }
        word_to_edit = dict.searchWord(word_mod_bar.getText());
        String html = "";
        if (word_to_edit.checkExists()) {
            Word newWord = new Word(word_to_edit);
            html = "<h1>" + word_mod_bar.getText() + "</h1>";
            html += "<h3><i>" + word_mod_pronounce.getText() + "</i></h3>";
            html += "<p>" + word_mod_meaning.getText() + "</p>";
            newWord.setHtmlTag(html);
            dict.updateWord(word_to_edit, newWord);
            modWordAlert();
        } else {
            modWordAlert();
        }
    }

    public void modWordAlert() {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(130);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setFont(new Font("Arial", 20));
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        if (word_to_edit.checkExists()) {
            label.setText("Sửa từ thành công!");
        } else {
            label.setText("Từ này hiện không có trong từ điển!");
        }
        stage.show();
    }

    @FXML
    private TextField search_bar;

    @FXML
    private ChoiceBox <String> search_mode;

    @FXML
    private WebView webView = new WebView();

    @FXML
    private WebEngine engine;

    @FXML
    private TextField thesaurus_bar;

    @FXML
    private Label thes_ant;

    @FXML
    private  Label thes_syn;

    /** Tìm từ đồng/trái nghĩa. */
    public void thesaurusSearch() {
        thes_syn.setVisible(false);
        thes_ant.setVisible(false);
        String syn = "";
        try {
            syn = HttpURLConn.searchSynonym(thesaurus_bar.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ant = "";
        try {
            ant = HttpURLConn.searchAntonym(thesaurus_bar.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        thes_ant.setText(ant);
        thes_syn.setText(syn);
        thes_syn.setVisible(true);
        thes_ant.setVisible(true);
    }

    @FXML
    private TextField gg_bar;

    @FXML
    private Button gg_api_speaker;

    @FXML
    private Label gg_api_result;

    /** Search của gg API. */
    public void gg_api_search() throws IOException {
        gg_api_result.setText(GGTranslateAPI.getMeaning(gg_bar.getText()));
        gg_api_speaker.setVisible(true);
    }

    /** Voice của gg API. */
    public void gg_speaker_on_voice() throws IOException, JavaLayerException {
        Player player = new Player(GGTranslateAPI.getAudio(gg_bar.getText()));
        player.play();
    }

    public App() throws Exception {
    }

    @FXML
    void search(KeyEvent event) throws Exception {
        Dict dict;
        if(search_mode.getValue() == "av") {
            dict = new Dict("av", daoMngr.conn);
        }else{
            dict = new Dict("va", daoMngr.conn);
        }
        if(event.getCode() == KeyCode.ENTER) {
            word_to_find = dict.searchWord(search_bar.getText().toLowerCase().trim());
            engine.loadContent(word_to_find.getHtmlTag());
        } else {
            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                rcmlist = FXCollections.observableArrayList(dict.recommendWord(search_bar.getText().toLowerCase().trim()));
                listView.setItems(rcmlist);
            });
        }
    }

    @FXML
    private Button dictionary_voice;

    public void on_voice_dictionary() throws IOException, JavaLayerException {
        Player player = new Player(GGTranslateAPI.getAudio(search_bar.getText()));
        player.play();
    }

    @FXML
    void fast_search() {
        Dict dict;
        if(search_mode.getValue() == "av") {
            dict = new Dict("av", daoMngr.conn);
        }else{
            dict = new Dict("va", daoMngr.conn);
        }
        String word = (String) listView.getSelectionModel().selectedItemProperty().getValue();
        search_bar.setText(word);
        Word word_to_search = dict.searchWord(word);
        String htmlTag = word_to_search.getHtmlTag();
        engine.loadContent(htmlTag);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gg_api_speaker.setVisible(false);
        try {
            daoMngr.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        search_mode.getItems().addAll("av", "va");
        search_mode.setValue("av");
        engine = webView.getEngine();
    }
}

