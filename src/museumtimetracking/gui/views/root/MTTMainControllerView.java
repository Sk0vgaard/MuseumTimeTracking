/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.gui.views.root;

import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import static museumtimetracking.be.enums.EFXMLName.*;
import museumtimetracking.gui.views.NodeFactory;
import museumtimetracking.gui.views.root.activeGuilds.GuildOverviewController;
import museumtimetracking.gui.views.root.archivedGuilds.ArchivedGuildViewController;
import museumtimetracking.gui.views.root.guildManager.guildManagerOverview.GuildManagerOverviewController;
import museumtimetracking.gui.views.root.idle.IdleViewController;
import museumtimetracking.gui.views.root.volunteer.VolunteerOverviewController;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class MTTMainControllerView implements Initializable {

    @FXML
    private Tab tabGM;
    @FXML
    private Tab tabIdle;
    @FXML
    private Tab tabPaneActiveGuild;
    @FXML
    private Tab tabPaneArchivedGuild;
    @FXML
    private Tab tabStatistics;
    @FXML
    private Tab tabVolunteer;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private ImageView imgHeader;
    @FXML
    private BorderPane borderPane;

    private final Node statistics;
    private final Node guildOverView;
    private final Node archivedGuild;
    private final Node manager;
    private final Node volunteer;
    private final Node idle;

    private GuildOverviewController guildOverViewController;
    private ArchivedGuildViewController archivedGuildViewController;
    private GuildManagerOverviewController guildManagerOverviewController;
    private VolunteerOverviewController volunteerOverviewController;
    private IdleViewController idleViewController;

    private final NodeFactory nodeFactory;

    private String searchID;
    @FXML
    private Button btnClearSearch;

    public MTTMainControllerView() {
        nodeFactory = NodeFactory.getInstance();

        statistics = nodeFactory.createNewView(STATISTICS_OVERVIEW);

        guildOverView = nodeFactory.createNewView(ACTIVE_GUILD);
        guildOverViewController = nodeFactory.getLoader().getController();

        archivedGuild = nodeFactory.createNewView(ARCHIVED_GUILD);
        archivedGuildViewController = nodeFactory.getLoader().getController();

        manager = nodeFactory.createNewView(MANAGER_OVERVIEW);
        guildManagerOverviewController = nodeFactory.getLoader().getController();

        volunteer = nodeFactory.createNewView(VOLUNTEER_OVERVIEW);
        volunteerOverviewController = nodeFactory.getLoader().getController();

        idle = nodeFactory.createNewView(IDLE_OVERVIEW);
        idleViewController = nodeFactory.getLoader().getController();

        searchID = "";
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setContentOfTabs();

        imgHeader.fitWidthProperty().bind(borderPane.widthProperty());
        setSearchBarVisibility(false);
        initializeTabPane();
        initializeTextFieldListener();
    }

    /**
     * Interpolate external views into the TabPanes
     */
    private void setContentOfTabs() {
        tabStatistics.setContent(statistics);
        tabPaneActiveGuild.setContent(guildOverView);
        tabPaneArchivedGuild.setContent(archivedGuild);
        tabGM.setContent(manager);
        tabVolunteer.setContent(volunteer);
        tabIdle.setContent(idle);
    }

    @FXML
    private void handleClearSearchBar() {
        if (txtSearchBar != null) {
            txtSearchBar.clear();
        }
    }

    private void initializeTabPane() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            searchID = newTab.getId();
            if (searchID.equals("statistics")) {
                setSearchBarVisibility(false);
            } else {
                setSearchBarVisibility(true);
            }
        });
    }

    private void handleSearch(String searchText) {
        switch (searchID) {
            case "guildOverView":
                guildOverViewController.handleSearch(searchText);
                break;
            case "archivedGuild":
                archivedGuildViewController.handleSearch(searchText);
                break;
            case "manager":
                guildManagerOverviewController.handleSearch(searchText);
                break;
            case "volunteer":
                volunteerOverviewController.handleSearch(searchText);
                break;
            case "idle":
                idleViewController.handleSearch(searchText);
                break;
            default:
                break;
        }
    }

    private void initializeTextFieldListener() {
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch(newValue);
        });
    }

    private void setSearchBarVisibility(boolean shown) {
        txtSearchBar.setVisible(shown);
        txtSearchBar.setDisable(!shown);
        btnClearSearch.setVisible(shown);
        btnClearSearch.setDisable(!shown);
    }
}
