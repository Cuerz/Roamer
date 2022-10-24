# Roamer

对[InfoSmse](https://github.com/Security-Magic-Weapon/InfoSmse)源码二次开发

可视化信息搜集工具，支持Hunter和Fofa语法查询。

# 使用方法

1,运行环境为java11+，此版本JDK中因为不自带JavaFx模块，所以需要自行下载JavaFx模块。
2.运行前请填写在jar包所在目录的config.yaml文件，里面是相关资产测绘平台的配置。

3.运行命令为

```
java -p javafx-sdk路径\lib --add-modules javafx.controls,javafx.base,javafx.fxml,javafx.graphics,javafx.media,javafx.web --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED -jar Roamer.jar
```

![image-20221023133921322](C:\Users\16337\AppData\Roaming\Typora\typora-user-images\image-20221023133921322.png)

