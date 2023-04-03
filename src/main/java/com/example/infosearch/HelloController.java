package com.example.infosearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.infosearch.config.View;
import com.example.infosearch.utils.fofa.Fofa;
import com.example.infosearch.utils.hunter.Hunter;
import com.example.infosearch.utils.log.log;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HelloController extends View {

    public final ObservableList<HunterRes> HunterResultTotal = FXCollections.observableArrayList();
    @FXML
    public TableView<HunterRes> HunterResult;
    @FXML
    private TableColumn<HunterRes, Integer> HunterID;
    @FXML
    private TableColumn<HunterRes, String> HunterIP;
    @FXML
    private TableColumn<HunterRes, String> HunterPort;
    @FXML
    private TableColumn<HunterRes, String> HunterDomain;
    @FXML
    private TableColumn<HunterRes, String> HunterTitle;
    @FXML
    private TableColumn<HunterRes, String> HunterOs;
    @FXML
    private TableColumn<HunterRes, String> HunterCity;

    public final ObservableList<FOFARes> FOFAResultTotal = FXCollections.observableArrayList();
    @FXML
    public TableView<FOFARes> FOFAResult;
    @FXML
    private TableColumn<FOFARes, Integer> FOFAID;
    @FXML
    private TableColumn<FOFARes, String> FOFAIP;
    @FXML
    private TableColumn<FOFARes, String> FOFAPort;
    @FXML
    private TableColumn<FOFARes, String> FOFADomain;
    @FXML
    private TableColumn<FOFARes, String> FOFATitle;
    @FXML
    private TableColumn<FOFARes, String> FOFAServer;
    @FXML
    private TableColumn<FOFARes, String> FOFACity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("______                                      \n" +
                "| ___ \\                                     \n" +
                "| |_/ /  ___    __ _  _ __ ___    ___  _ __ \n" +
                "|    /  / _ \\  / _` || '_ ` _ \\  / _ \\| '__|\n" +
                "| |\\ \\ | (_) || (_| || | | | | ||  __/| |   \n" +
                "\\_| \\_| \\___/  \\__,_||_| |_| |_| \\___||_|   \n" +
                "                                            ");
        System.out.println("须知少时凌云志,曾许人间第一流\n");
        Log.appendText("须知少时凌云志,曾许人间第一流\n\n");
        Log.appendText(log.INFO("启动程序成功！\n"));
        Yaml yaml = new Yaml();
        String File = "config.yaml";
        try {
            Map YamlConfig = yaml.load(new FileInputStream(File));
            // Hunter初始化
            Map Hunter = (Map) YamlConfig.get("Hunter");
            System.out.println(log.DEBUG("Hunter:|"));
            HunterKeyConf = Hunter.get("ApiKey").toString();
            System.out.println(log.DEBUG("- ApiKey: " + HunterKeyConf));
            HunterNumConf = Hunter.get("Num").toString();
            System.out.println(log.DEBUG("- Num: " + HunterNumConf));
            HunterNum.setText(HunterNumConf);
            HunterApiUrlConf = Hunter.get("ApiUrl").toString();
            System.out.println(log.DEBUG("- ApiUrl: " + HunterApiUrlConf));
            System.out.println(log.DEBUG("------------------------------"));
            // FOFA初始化
            Map FOFA = (Map) YamlConfig.get("FOFA");
            System.out.println(log.DEBUG("FOFA:|"));
            FOFAEmailConf = FOFA.get("Email").toString();
            System.out.println(log.DEBUG("- Email: " + FOFAEmailConf));
            FOFAKeyConf = FOFA.get("ApiKey").toString();
            System.out.println(log.DEBUG("- ApiKey: " + FOFAKeyConf));
            FOFANumConf = FOFA.get("Num").toString();
            System.out.println(log.DEBUG("- Num: " + FOFANumConf));
            FOFANum.setText(HunterNumConf);
            FOFAApiUrlConf = FOFA.get("ApiUrl").toString();
            System.out.println(log.DEBUG("- ApiUrl: " + FOFAApiUrlConf));
            System.out.println(log.DEBUG("------------------------------"));
        } catch (FileNotFoundException e) {
            Log.appendText(log.ERROR(e + "\n"));
        }
    }

    @FXML
    void HunterSearch(ActionEvent event) throws IOException {
        HunterResult.getItems().clear();
        System.out.println(log.DEBUG("当前检测语法：" + HunterSentence.getText()));
        Log.appendText(log.SUCCESS("当前检测语法：" + HunterSentence.getText() + "\n"));
        String str = Hunter.Search(HunterApiUrlConf, HunterKeyConf, HunterNum.getText(), Base64.getEncoder().encodeToString(HunterSentence.getText().getBytes()));
        ExecutorService pool = Executors.newFixedThreadPool(1);

        SwingWorker<Vector, Void> worker = new SwingWorker<Vector, Void>() {
            @Override
            protected Vector doInBackground() {
                try {
                    JSONObject object = JSONObject.parseObject(str);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.appendText(log.SUCCESS("Hunter检索成功！\n"));
                        System.out.println(log.DEBUG("Hunter检索结果: " + message + "（无报错）"));
                        String data = object.getString("data");
                        JSONArray arrays = JSONObject.parseObject(data).getJSONArray("arr");
                        for (int i = 0; i < arrays.size(); i++) {
                            HunterResultTotal.add(new HunterRes(i + 1
                                    , arrays.getJSONObject(i).getString("ip").toString()
                                    , arrays.getJSONObject(i).getString("port").toString()
                                    , arrays.getJSONObject(i).getString("domain").toString()
                                    , arrays.getJSONObject(i).getString("web_title").toString()
                                    , arrays.getJSONObject(i).getString("os").toString()
                                    , arrays.getJSONObject(i).getString("city").toString()));
                            HunterID.setCellValueFactory(cellData -> cellData.getValue().HunterIDText.asObject());
                            HunterIP.setCellValueFactory(cellData -> cellData.getValue().HunterIPText);
                            HunterPort.setCellValueFactory(cellData -> cellData.getValue().HunterPortText);
                            HunterDomain.setCellValueFactory(cellData -> cellData.getValue().HunterDomainText);
                            HunterTitle.setCellValueFactory(cellData -> cellData.getValue().HunterTitleText);
                            ;
                            HunterOs.setCellValueFactory(cellData -> cellData.getValue().HunterOsText);
                            HunterCity.setCellValueFactory(cellData -> cellData.getValue().HunterCityText);
                        }
                        HunterResult.setItems(HunterResultTotal);
                        if (HunterResult == null) {
                            synchronized (this) {
                                System.out.println(log.ERROR("异常"));
                            }
                        }
                    } else {
                        Log.appendText(log.ERROR("检索失败！请查看控制台原因进行修复！\n"));
                        System.out.println(log.ERROR("Hunter检索结果: " + message + "（报错）"));
                    }
                } catch (
                        Exception e) {
                    Log.appendText(log.ERROR(e + "\n"));
                }
                return null;
            }
        };
        Platform.runLater(() -> pool.execute(worker));
    }

    @FXML
    void HunterSave(ActionEvent event) {
        File dir = new File("Output");
        dir.mkdir();
        // 格式化时间
        SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern("yyyyMMddHHmm");
        // 获取当前时间
        Date date = new Date();
        Writer writer;
        try {
            File file = new File("Output/" + sdf.format(date) + "_Result.csv");
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("ID" + "," + "IP" + "," + "Port" + "," + "Domain" + "," + "Title" + "," + "Os" + "," + "City" + "\n");
            for (HunterRes hunterRes : HunterResultTotal) {
                String text = hunterRes.getHunterIDText() + "," + hunterRes.getHunterIPText() + ","
                        + hunterRes.getHunterPortText() + "," + hunterRes.getHunterDomainText() + ","
                        + hunterRes.getHunterTitleText() + "," + hunterRes.getHunterOsText() + "," + hunterRes.getHunterCityText() + "\n";
                writer.write(text);
            }
            writer.flush();
            writer.close();
            Log.appendText(log.SUCCESS("导出成功！路径：" + "Output/" + sdf.format(date) + "_Result.csv" + "\n"));
            System.out.println(log.DEBUG("导出成功！路径：" + "Output/" + sdf.format(date) + "_Result.csv" + "\n"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void FOFASearch(ActionEvent event) throws IOException {
        FOFAResult.getItems().clear();
        System.out.println(log.DEBUG("当前检测语法：" + FOFASentence.getText()));
        Log.appendText(log.SUCCESS("当前检测语法：" + FOFASentence.getText() + "\n"));
        String str = Fofa.Search(FOFAApiUrlConf, FOFAEmailConf, FOFAKeyConf, FOFANum.getText(), Base64.getEncoder().encodeToString(FOFASentence.getText().getBytes()));
        ExecutorService pool = Executors.newFixedThreadPool(1);

        SwingWorker<Vector, Void> worker = new SwingWorker<Vector, Void>() {
            @Override
            protected Vector doInBackground() {
                try {
                    JSONObject object = JSONObject.parseObject(str);
                    String error = object.getString("error");
                    if (error.equals("false")) {
                        Log.appendText(log.SUCCESS("FOFA检索成功！\n"));
                        System.out.println(log.DEBUG("FOFA检索结果:（无报错）"));
                        JSONArray results = object.getJSONArray("results");
                        for (int i = 0; i < results.size(); i++) {
                            FOFAResultTotal.add(new FOFARes(i + 1
                                    , ((JSONArray) results.get(i)).get(0).toString()
                                    , ((JSONArray) results.get(i)).get(1).toString()
                                    , ((JSONArray) results.get(i)).get(2).toString()
                                    , ((JSONArray) results.get(i)).get(3).toString()
                                    , ((JSONArray) results.get(i)).get(4).toString()
                                    , ((JSONArray) results.get(i)).get(5).toString()));
                            FOFAID.setCellValueFactory(cellData -> cellData.getValue().FOFAIDText.asObject());
                            FOFAIP.setCellValueFactory(cellData -> cellData.getValue().FOFAIPText);
                            FOFAPort.setCellValueFactory(cellData -> cellData.getValue().FOFAPortText);
                            FOFADomain.setCellValueFactory(cellData -> cellData.getValue().FOFADomainText);
                            FOFATitle.setCellValueFactory(cellData -> cellData.getValue().FOFATitleText);
                            ;
                            FOFAServer.setCellValueFactory(cellData -> cellData.getValue().FOFAServerText);
                            FOFACity.setCellValueFactory(cellData -> cellData.getValue().FOFACityText);
                        }
                        FOFAResult.setItems(FOFAResultTotal);
                        if (FOFAResult == null) {
                            synchronized (this) {
                                System.out.println(log.ERROR("异常"));
                            }
                        }
                    } else {
                        Log.appendText(log.ERROR("检索失败！请查看控制台原因进行修复！\n"));
                        System.out.println(log.ERROR("FOFA检索结果: （报错）"));
                    }
                } catch (
                        Exception e) {
                    Log.appendText(log.ERROR(e + "\n"));
                }
                return null;
            }
        };
        Platform.runLater(() -> pool.execute(worker));
    }

    @FXML
    void FOFASave(ActionEvent event) {
        File dir = new File("Output");
        dir.mkdir();
        // 格式化时间
        SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern("yyyyMMddHHmm");
        // 获取当前时间
        Date date = new Date();
        Writer writer;
        try {
            File file = new File("Output/" + sdf.format(date) + "_Result.csv");
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("ID" + "," + "IP" + "," + "Port" + "," + "Domain" + "," + "Title" + "," + "Server" + "," + "City" + "\n");
            for (FOFARes fofaRes : FOFAResultTotal) {
                String text = fofaRes.getFOFAIDText() + "," + fofaRes.getFOFAIPText() + ","
                        + fofaRes.getFOFAPortText() + "," + fofaRes.getFOFADomainText() + ","
                        + fofaRes.getFOFATitleText() + "," + fofaRes.getFOFAServerText() + "," + fofaRes.getFOFACityText() + "\n";
                writer.write(text);
            }
            writer.flush();
            writer.close();
            Log.appendText(log.SUCCESS("导出成功！路径：" + "Output/" + sdf.format(date) + "_Result.csv" + "\n"));
            System.out.println(log.DEBUG("导出成功！路径：" + "Output/" + sdf.format(date) + "_Result.csv" + "\n"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class HunterRes {
        private IntegerProperty HunterIDText;
        private StringProperty HunterIPText;
        private StringProperty HunterPortText;
        private StringProperty HunterDomainText;
        private StringProperty HunterTitleText;
        private StringProperty HunterOsText;
        private StringProperty HunterCityText;

        public HunterRes(Integer hunterIDText, String hunterIPText, String hunterPortText, String hunterDomainText, String hunterTitleText, String hunterOsText, String hunterCityText) {
            super();
            HunterIDText = new SimpleIntegerProperty(hunterIDText);
            HunterIPText = new SimpleStringProperty(hunterIPText);
            HunterPortText = new SimpleStringProperty(hunterPortText);
            HunterDomainText = new SimpleStringProperty(hunterDomainText);
            HunterTitleText = new SimpleStringProperty(hunterTitleText);
            HunterOsText = new SimpleStringProperty(hunterOsText);
            HunterCityText = new SimpleStringProperty(hunterCityText);
        }

        public int getHunterIDText() {
            return HunterIDText.get();
        }

        public String getHunterIPText() {
            return HunterIPText.get();
        }

        public String getHunterPortText() {
            return HunterPortText.get();
        }

        public String getHunterDomainText() {
            return HunterDomainText.get();
        }

        public String getHunterTitleText() {
            return HunterTitleText.get();
        }

        public String getHunterOsText() {
            return HunterOsText.get();
        }

        public String getHunterCityText() {
            return HunterCityText.get();
        }
    }

    public static class FOFARes {
        private IntegerProperty FOFAIDText;
        private StringProperty FOFAIPText;
        private StringProperty FOFAPortText;
        private StringProperty FOFADomainText;
        private StringProperty FOFATitleText;
        private StringProperty FOFAServerText;
        private StringProperty FOFACityText;

        public FOFARes(Integer fofaIDText, String fofaIPText, String fofaPortText, String fofaDomainText, String fofaTitleText, String fofaServerText, String fofaCityText) {
            super();
            FOFAIDText = new SimpleIntegerProperty(fofaIDText);
            FOFAIPText = new SimpleStringProperty(fofaIPText);
            FOFAPortText = new SimpleStringProperty(fofaPortText);
            FOFADomainText = new SimpleStringProperty(fofaDomainText);
            FOFATitleText = new SimpleStringProperty(fofaTitleText);
            FOFAServerText = new SimpleStringProperty(fofaServerText);
            FOFACityText = new SimpleStringProperty(fofaCityText);
        }

        public int getFOFAIDText() {
            return FOFAIDText.get();
        }

        public String getFOFAIPText() {
            return FOFAIPText.get();
        }

        public String getFOFAPortText() {
            return FOFAPortText.get();
        }

        public String getFOFADomainText() {
            return FOFADomainText.get();
        }

        public String getFOFATitleText() {
            return FOFATitleText.get();
        }

        public String getFOFAServerText() {
            return FOFAServerText.get();
        }

        public String getFOFACityText() {
            return FOFACityText.get();
        }
    }
}
