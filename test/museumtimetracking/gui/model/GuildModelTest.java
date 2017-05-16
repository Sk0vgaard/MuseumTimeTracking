/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.gui.model;

import java.util.List;
import museumtimetracking.be.Guild;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Rasmus
 */
public class GuildModelTest {

    public GuildModelTest() {
    }

    /**
     * Test of archiveGuild method, of class GuildModel.
     */
    @Test
    public void testArchiveGuild() throws Exception {
        System.out.println("archiveGuild");
        Guild guildToArchive = null;
        GuildModel instance = null;
        instance.archiveGuild(guildToArchive);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteGuild method, of class GuildModel.
     */
    @Test
    public void testDeleteGuild() throws Exception {
        System.out.println("deleteGuild");
        Guild deleteGuild = null;
        GuildModel instance = null;
        instance.deleteGuild(deleteGuild);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGuild method, of class GuildModel.
     */
    @Test
    public void testAddGuild() throws Exception {
        Guild guild = new Guild("TestGuild", "This guild is for unitTesting", false);
        GuildModel instance = GuildModel.getInstance();
        List<Guild> listOfGuilds = instance.getCachedGuilds();

        boolean guildIsNotAdded = listOfGuilds.contains(guild);
        instance.addGuild(guild);
        boolean guildIsAdded = listOfGuilds.contains(guild);

        assertEquals(!guildIsNotAdded, guildIsAdded);
    }

    /**
     * Test of restoreGuild method, of class GuildModel.
     */
    @Test
    public void testRestoreGuild() throws Exception {
        System.out.println("restoreGuild");
        Guild guildToRestore = null;
        GuildModel instance = null;
        instance.restoreGuild(guildToRestore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCachedAvailableGuild method, of class GuildModel.
     */
    @Test
    public void testAddCachedAvailableGuild() {
        System.out.println("addCachedAvailableGuild");
        Guild guildToAdd = null;
        GuildModel instance = null;
        instance.addCachedAvailableGuild(guildToAdd);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCachedAvailableGuild method, of class GuildModel.
     */
    @Test
    public void testRemoveCachedAvailableGuild() {
        System.out.println("removeCachedAvailableGuild");
        Guild guildToRemove = null;
        GuildModel instance = null;
        instance.removeCachedAvailableGuild(guildToRemove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
