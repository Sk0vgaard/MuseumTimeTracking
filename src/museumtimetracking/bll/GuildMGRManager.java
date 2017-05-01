/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.bll;

import java.util.List;
import museumtimetracking.be.APerson;
import museumtimetracking.be.GuildManager;
import museumtimetracking.dal.FacadeDAO;

/**
 *
 * @author Mathias
 */
public class GuildMGRManager {

    private final FacadeDAO facadeDAO;

    public GuildMGRManager() {
        facadeDAO = FacadeDAO.getInstance();
    }

    /**
     * Sends the Person object through to the facadeDAO to add it to the DB.
     * Returns the new GuildManager.
     *
     * @param person
     * @param guildName
     * @return
     */
    public GuildManager createNewGuildManager(APerson person, String guildName) {
        return facadeDAO.createNewGuildManager(person, guildName);
    }

    /**
     * Gets a list of GuildManagers from the FacadeDAO that holds all
     * GuildManagers and it's Guilds.
     *
     * @return
     */
    public List<GuildManager> getAllGuildManagers() {
        return facadeDAO.getAllGuildManagers();
    }
}