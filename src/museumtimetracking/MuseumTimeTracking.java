/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import museumtimetracking.be.enums.EAppLanguage;
import static museumtimetracking.be.enums.EAppLanguage.*;
import museumtimetracking.exception.DALException;

public class MuseumTimeTracking extends Application {

    public static final String RESOURCE_LOCATION = "museumtimetracking.gui.language.UIResources";
    public static final String ICON = "museumtimetracking/asset/img/icon.png";

    private static final Stage mainStage = new Stage();

    public static ResourceBundle bundle;
    public static StringProperty LOCALE = new SimpleStringProperty(DANISH.toString());

    @Override
    public void start(Stage stage) throws Exception {
        bundle = ResourceBundle.getBundle(RESOURCE_LOCATION, new Locale(LOCALE.get()));

        instatiateLanguageListener();

        Parent startRoot = createLoadingView(stage);

        createMainView();

        FadeTransition fadeIn = createFadeIn(startRoot, stage);

        setOnFadeInFinished(fadeIn, startRoot, mainStage, stage);

    }

    /**
     * Create a changelistener for the locale language
     */
    private void instatiateLanguageListener() {
        LOCALE.addListener((observable, oldValue, newValue) -> {

            bundle = ResourceBundle.getBundle(RESOURCE_LOCATION, new Locale(LOCALE.get()));

            try {
                createMainView();
            } catch (IOException ex) {
                Logger.getLogger(MuseumTimeTracking.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Change the language of the application
     *
     * @param language
     */
    public static void changeLanguage(EAppLanguage language) {
        LOCALE.set(language.toString());
    }

    /**
     * Create the start view
     *
     * @param stage
     * @return
     * @throws IOException
     */
    private Parent createLoadingView(Stage stage) throws IOException {
        Parent startRoot = FXMLLoader.load(getClass().getResource("gui/views/startScreen/StartView.fxml"), bundle);
        Scene startScene = new Scene(startRoot);
        stage.getIcons().add(new Image(ICON));
        stage.setScene(startScene);
        stage.initStyle(StageStyle.UNDECORATED);
        return startRoot;
    }

    /**
     * Create the mainview
     *
     * @return
     * @throws IOException
     */
    private Stage createMainView() throws IOException {
        //Start loading main view
        URL location = getClass().getResource("/museumtimetracking/gui/views/root/MTTMainView.fxml");
        FXMLLoader loader = new FXMLLoader(location, bundle);
        Parent root = loader.load();
        mainStage.getIcons().add(new Image(ICON));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        return mainStage;
    }

    /**
     * Create the fadein effect
     *
     * @param startRoot
     * @param stage
     * @return
     * @throws IOException
     * @throws DALException
     */
    private FadeTransition createFadeIn(Parent startRoot, Stage stage) throws IOException, DALException {
        //Start fade in of start view
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), startRoot);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        stage.show();

        return fadeIn;
    }

    /**
     * Set on fadein finished functionality
     *
     * @param fadeIn
     * @param startRoot
     * @param mainStage
     * @param stage
     */
    private void setOnFadeInFinished(FadeTransition fadeIn, Parent startRoot, Stage mainStage, Stage stage) {
        fadeIn.setOnFinished((e) -> {
            //Start fade out of start view
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), startRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);
            fadeOut.play();

            //Display main and close start view
            fadeOut.setOnFinished((finishedEvent) -> {
                mainStage.show();
                stage.close();
            });
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
