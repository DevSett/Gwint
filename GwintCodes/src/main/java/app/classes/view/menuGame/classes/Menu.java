package app.classes.view.menuGame.classes;

import app.classes.MainApp;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by kills on 01.03.2017.
 */
public class Menu {

    private Stage stage;
    private BorderPane pane;
    private Button singlePlay;
    private Button multyPlay;
    private Button exit;
    private VBox vBox;
    private TextField fieldIp;
    private TextField fieldPort;
    private TextField fieldName;

    public Menu(Stage stage) {
        this.stage = stage;
        stage.setMaxHeight(1080 / MainApp.getSingleton().getDel());
        stage.setMaxWidth(1920 / MainApp.getSingleton().getDel());
        stage.setFullScreen(MainApp.getSingleton().isFullscreen());
        stage.setResizable(false);
        stage.getIcons().add(stage.getIcons().get(0));
        mainPane();
    }
    public Menu(Image image) {
        this.stage = new Stage();
        stage.setMaxHeight(1080 / MainApp.getSingleton().getDel());
        stage.setMaxWidth(1920 / MainApp.getSingleton().getDel());
        stage.setFullScreen(MainApp.getSingleton().isFullscreen());
        stage.setResizable(false);
        stage.getIcons().add(image);
        mainPane();
    }
    private void mainPane() {
        pane = new BorderPane();
        pane.setPrefSize(stage.getMaxWidth(), stage.getMaxHeight());

        Image image = new Image(MainApp.class.getResource("/images/menuGame/background.jpg").toExternalForm());
        pane.setBackground(new Background(
                new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(
                                1920 / MainApp.getSingleton().getDel(),
                                1080 / MainApp.getSingleton().getDel(),
                                true,
                                true,
                                true,
                                true))));


        showButton();


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void showButton() {
        pane.getChildren().clear();
        pane.getStylesheets().add(MainApp.class.getResource("/css/button.css").toExternalForm());

        singlePlay = singleButton();
        singlePlay.setId("single-button");
//        singlePlay.setStyle("-fx-background-size: " + 500d / MainApp.getSingleton().getDel() +
//                " " + 100d / MainApp.getSingleton().getDel() + ";");

        multyPlay = multyButton();
        multyPlay.setId("multy-button");
//        multyPlay.setStyle("-fx-background-size: " + 500d / MainApp.getSingleton().getDel() +
//                " " + 100d / MainApp.getSingleton().getDel() + ";");

        exit = exitButton();
        exit.setId("exit-button");
//        exit.setStyle("-fx-background-size: " + 500d / MainApp.getSingleton().getDel() +
//                " " + 50d / MainApp.getSingleton().getDel() + ";");

        vBox = new VBox(10);
        vBox.getChildren().addAll(singlePlay, multyPlay, exit);
        vBox.setAlignment(Pos.CENTER);

        pane.setCenter(vBox);
    }

    private Button exitButton() {
        Button exit = new Button();
        exit.setPrefSize(500d / MainApp.getSingleton().getDel(), 50d / MainApp.getSingleton().getDel());
        exit.setOnAction(event -> stage.close());
        return exit;
    }

    private Button multyButton() {
        Button multy = new Button();
        multy.setPrefSize(500d / MainApp.getSingleton().getDel(), 100d / MainApp.getSingleton().getDel());
        multy.setOnAction(event -> {
            actionMultyButton();
        });
        return multy;
    }

    private void actionMultyButton() {
        pane.getChildren().clear();

        VBox vBox = new VBox(100);

        HBox hBox = new HBox(30);
        HBox hBox2 = new HBox(30);
        HBox hBox3 = new HBox(30);
        Button acceptMultyButton = buttonAccept();
        Button backMultyButton = buttonBack();

        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(backMultyButton, acceptMultyButton);


        fieldIp = new TextField("localhost");
        fieldIp.setPromptText("Введите ip адресс");
        fieldPort = new TextField("7896");
        fieldPort.setPromptText("Введите порт");
        fieldName = new TextField("killsett");
        fieldName.setPromptText("Введите ваш никнейм");


        hBox3.getChildren().addAll(fieldName);
        hBox3.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(fieldIp, fieldPort);
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(hBox, hBox3, hBox2);
        pane.setCenter(vBox);

        vBox.setAlignment(Pos.CENTER);
    }

    private Button buttonBack() {
        Button button = new Button("Назад");
        button.setPrefSize(100 / MainApp.getSingleton().getDel(), 50 / MainApp.getSingleton().getDel());
        button.setOnAction(event ->
        {
            actionBackButton();
        });
        return button;
    }

    private void actionBackButton() {
        showButton();
    }

    private Button buttonAccept() {
        Button button = new Button("Принять");
        button.setPrefSize(100 / MainApp.getSingleton().getDel(), 50 / MainApp.getSingleton().getDel());
        button.setOnAction(event ->
        {
            actionAcceptButton();
        });
        return button;
    }

    private void actionAcceptButton() {
        MainApp.getSingleton().startClient(fieldIp.getText(), fieldPort.getText(), fieldName.getText(), stage);
    }


    private Button singleButton() {
        Button single = new Button();
        single.setPrefSize(500d / MainApp.getSingleton().getDel(), 100d / MainApp.getSingleton().getDel());
        single.setOnAction(event -> {
            MainApp.getSingleton().setStage(stage);
            MainApp.getSingleton().playGameTable();
        });
        return single;
    }

}
