package app;

import app.view.lobbiGame.classes.Lobbi;
import app.view.lobbiGame.classes.LobbiItems;
import app.view.menuGame.classes.Menu;
import app.view.optionGame.classes.OptionController;
import app.webNetwork.classes.StartClient;
import app.webNetwork.config.RootConfig;
import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by kills on 01.03.2017.
 */
public class MainApp extends Application {
    public StatusMainWindow status = new StatusMainWindow(StatusMainWindow.UNCNOWN);

    public Lobbi lobbiRooms;

    public ObservableList<LobbiItems> data = new SimpleListProperty<>();

    private Stage stage;
    private Menu menu;

    public static RootConfig rootConfig = new RootConfig();

    public static boolean fullscreen;
    public static double del;

    public static String nickname;
    @Override
    public void start(Stage primaryStage) {
        rootConfig.setMainApp(this);
        stage = primaryStage;
        try {
            optionPane();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void optionPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/optionGame/classes/option.fxml"));

        AnchorPane anchorPane = (AnchorPane) loader.load();

        OptionController opt = loader.getController();
        Image image = new Image(getClass().getResource("view/gamingTable/images/tableTimes.png").toExternalForm());
        opt.setMainApp(this, image);

        stage = new Stage();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/hearts.png")));
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();

        status.set(StatusMainWindow.OPTION);
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void playGame(double del, boolean selected) {
        this.del = del;
        fullscreen = selected;

        menuGame();

    }

    public void menuGame() {
        Menu menu = new Menu(stage.getIcons().get(0));
        menu.setMainApp(this);

        status.set(StatusMainWindow.MAINMENU);

    }

    public StartClient client;

    public void startClient(String fieldIp, String fieldPort, String fieldName, Stage stage) {
        this.stage = stage;
        nickname=fieldName;
        Thread threadClient = new Thread(() -> {
            client = new StartClient(fieldIp, fieldPort, fieldName);
            client.start();
        });
        threadClient.start();

        //экран загрузки
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //


        if(client.isAlive()) lobbi();
    }

    public void lobbi() {
        lobbiRooms = new Lobbi(stage);
        lobbiRooms.setMainApp(this);
        status.set(StatusMainWindow.LOBBI);
    }
}
