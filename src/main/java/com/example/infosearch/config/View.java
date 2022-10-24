package com.example.infosearch.config;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creater: Cuerz
 * Date: 2022/9/29
 */
@Getter
public abstract class View implements Initializable {

    protected String HunterKeyConf;
    protected String HunterNumConf;
    protected String HunterApiUrlConf;

    protected String FOFAEmailConf;
    protected String FOFAKeyConf;
    protected String FOFANumConf;
    protected String FOFAApiUrlConf;

    @FXML
    public TextArea Log;

    @FXML
    public TextField HunterSentence, FOFASentence;

    @FXML
    public TextField HunterNum, FOFANum;

    @FXML
    public ToggleGroup data;

    public abstract void initialize(URL location, ResourceBundle resources);

}
