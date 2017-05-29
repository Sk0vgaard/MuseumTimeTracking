/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.gui.views.root.statistics.ROIOverview;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import museumtimetracking.be.Guild;
import museumtimetracking.gui.model.GuildModel;
import museumtimetracking.gui.model.ModelFacade;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class ROIGmHoursViewController implements Initializable {

    @FXML
    private PieChart chartPie;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private TableView<Guild> tableROI;
    @FXML
    private TableColumn<Guild, String> clmName;
    @FXML
    private TableColumn<Guild, String> clmInvestment;

    private GuildModel guildModel;

    public ROIGmHoursViewController() {
        guildModel = ModelFacade.getInstance().getGuildModel();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chartPie.setLabelsVisible(true);
        chartPie.setLegendVisible(false);
        updateDataForChart();
        initializeTable();

        //Set a search listener on serach textfield
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            guildModel.searchGuilds(newValue);
        });
    }

    /**
     * Updates the pieChart with ROI data.
     */
    public void updateDataForChart() {
        Map<String, Integer> ROIHours = guildModel.getGuildROI();

        ObservableList<Data> chartData = FXCollections.observableArrayList();
        if (!ROIHours.isEmpty()) {
            for (Map.Entry<String, Integer> entry : ROIHours.entrySet()) {
                chartData.add(new Data(entry.getKey(), entry.getValue()));
            }
            chartPie.setData(chartData);
        }
    }

    /**
     * Sets the items in the tableview and specifies what data each column
     * holds.
     */
    private void initializeTable() {
        tableROI.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableROI.setItems(guildModel.getCachedGuilds());
        //Sets the name of each guild in the column.
        clmName.setCellValueFactory(g -> g.getValue().getNameProperty());
        //Checks if the guild has ROI. If yes - displays it. Else display 0.
        clmInvestment.setCellValueFactory(g -> {
            if (guildModel.getGuildROI().get(g.getValue().getName()) != null) {
                return new SimpleStringProperty(guildModel.getGuildROI().get(g.getValue().getName()) + "");
            }
            return new SimpleStringProperty(0 + "");
        });
    }

    public void clearSearch() {
        txtSearchBar.clear();
    }
}
