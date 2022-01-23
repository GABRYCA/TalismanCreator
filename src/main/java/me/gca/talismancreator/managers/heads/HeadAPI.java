package me.gca.talismancreator.managers.heads;

import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.managers.heads.database.Category;
import me.gca.talismancreator.managers.heads.database.HeadDatabase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class provides simple methods
 * for interacting with the HeadDB plugin
 *
 * @author TheSilentPro
 */
// TODO: Possibly change to singleton class
public final class HeadAPI {

    private HeadAPI() {}

    /**
     * Main {@link HeadDatabase} that he HeadDB plugin uses.
     */
    private static final HeadDatabase database = new HeadDatabase(TalismanCreator.getInstance());

    /**
     * Retrieves the main {@link HeadDatabase}
     *
     * @return Head Database
     */
    public static HeadDatabase getDatabase() {
        return database;
    }

    /**
     * Retrieve a {@link Head} by it's ID
     *
     * @param id The ID of the head
     * @return The head
     */
    @Nullable
    public static Head getHeadByID(int id) {
        return database.getHeadByID(id);
    }

    /**
     * Retrieve a {@link Head} by it's UUID
     *
     * @param uuid The UUID of the head
     * @return The head
     */
    @Nullable
    public static Head getHeadByUniqueId(UUID uuid) {
        return database.getHeadByUniqueId(uuid);
    }

    /**
     * Retrieve a {@link List} of {@link Head}'s by their tag
     *
     * @param tag The tag
     * @return List of heads
     */
    @Nonnull
    public static List<Head> getHeadsByTag(String tag) {
        return database.getHeadsByTag(tag);
    }

    /**
     * Retrieves a {@link List} of {@link Head}'s matching a name
     *
     * @param name The name to match for
     * @return List of heads
     */
    @Nonnull
    public static List<Head> getHeadsByName(String name) {
        return database.getHeadsByName(name);
    }

    /**
     * Retrieves a {@link List} of {@link Head}'s in a {@link Category} matching a name
     *
     * @param category The category to search in
     * @param name The name to match for
     * @return List of heads
     */
    @Nonnull
    public static List<Head> getHeadsByName(Category category, String name) {
        return database.getHeadsByName(category, name);
    }

    /**
     * Retrieve a {@link Head} by it's value
     *
     * @param value The texture value
     * @return The head
     */
    @Nullable
    public static Head getHeadByValue(String value) {
        return database.getHeadByValue(value);
    }

    /**
     * Retrieve a {@link List} of {@link Head}'s in a specific {@link Category}
     *
     * @param category The category to search in
     * @return List of heads
     */
    @Nonnull
    public static List<Head> getHeads(Category category) {
        return database.getHeads(category);
    }

    /**
     * Retrieve a {@link List} of all {@link Head}'s
     *
     * @return List of all heads
     */
    @Nonnull
    public static List<Head> getHeads() {
        return database.getHeads();
    }
}