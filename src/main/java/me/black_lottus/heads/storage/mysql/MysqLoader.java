package me.black_lottus.heads.storage.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.black_lottus.heads.Heads;
import me.black_lottus.heads.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;


import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class MysqLoader implements StorageManager {

    final static String LOCATIONS_DB = "CREATE TABLE IF NOT EXISTS "
            + "Locations(id INTEGER PRIMARY KEY AUTO_INCREMENT, location VARCHAR(50) NOT NULL UNIQUE)";
    final static String PLAYER_DB = "CREATE TABLE IF NOT EXISTS "
            + "Players(id INTEGER PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(50) NOT NULL UNIQUE, nickname VARCHAR(30) NOT NULL UNIQUE)";
    final static String HEADS_DB = "CREATE TABLE IF NOT EXISTS "
            + "Heads(id INTEGER PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(50), headID INTEGER)";

    final static String COUNT_HEADS = "SELECT COUNT(*) FROM Heads WHERE uuid=?";
    final static String LIST_LOCATIONS = "SELECT * FROM Locations";

    final static String INSERT_LOCATION = "INSERT IGNORE INTO Locations(location) " +
            "VALUES (?);";
    final static String INSERT_PLAYER = "INSERT IGNORE INTO Players(uuid, nickname) " +
            "VALUES (?,?);";
    final static String INSERT_HEAD = "INSERT INTO Heads(uuid, headID) " +
            "VALUES (?,?);";

    final static String DELETE_LOCATION = "DELETE FROM Locations WHERE id = ?";
    final static String DELETE_PLAYER = "DELETE FROM Players WHERE uuid = ?";
    final static String DELETE_HEAD = "DELETE FROM Heads WHERE id = ? AND uuid = ?";

    private final HikariDataSource source;

    public MysqLoader(Heads plugin) {
        // Connect database
        String host = plugin.getConfig().getString("mysql-database.host");
        int port = plugin.getConfig().getInt("mysql-database.port");
        String database = plugin.getConfig().getString("mysql-database.database");
        String username = plugin.getConfig().getString("mysql-database.username");
        String password = plugin.getConfig().getString("mysql-database.password");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&allowMultiQueries=true");
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        hikariConfig.setMaxLifetime(280000);
        hikariConfig.setPoolName("Heads Pool");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");

        source = new HikariDataSource(hikariConfig);

        createDB();
    }

    /***************************************/
    /***         Create & Connect        ***/
    /***************************************/

    private void createDB(){
        create(HEADS_DB); create(PLAYER_DB); create(LOCATIONS_DB);
    }

    private void create(String sentence){
        try (Connection connection = source.getConnection();
             PreparedStatement ps = connection.prepareStatement(sentence)){
            ps.execute();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    /***************************************/
    /***              METHODS            ***/
    /***************************************/

    @Override
    public HashMap<Location, Integer> listLocations() {
        HashMap<Location, Integer> locations = new HashMap<>();

        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(LIST_LOCATIONS)){
            ResultSet r = pr.executeQuery();
            while (r.next()) {
                Location loc = stringToLocation(r.getString("location"));
                locations.put(loc,r.getInt("id"));
            }
            r.close();
        }
        catch (Exception e) {e.printStackTrace();}
        return locations;
    }

    @Override
    public Integer getHeads(UUID uuid) {
        int heads = 0;
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(COUNT_HEADS)){
            pr.setString(1, uuid.toString());
            ResultSet r = pr.executeQuery();
            if(r.next()) heads = r.getInt(1);
            r.close();
        }
        catch (Exception e) {e.printStackTrace();}
        return heads;
    }

    @Override
    public void addLocation(Location loc) {
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(INSERT_LOCATION)){
            pr.setString(1, stringFromLocation(loc));
            pr.execute();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void addHead(UUID uuid, Integer id) {
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(INSERT_HEAD)){
            pr.setString(1, uuid.toString());
            pr.setInt(2,id);
            pr.execute();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void addPlayer(UUID uuid, String nickname) {
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(INSERT_PLAYER)){
            pr.setString(1, uuid.toString());
            pr.setString(2, nickname);
            pr.execute();
        }
        catch (Exception e) {e.printStackTrace();}
    }


    @Override
    public void removeHead(Integer id, UUID uuid) {
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(DELETE_HEAD)){
            pr.setInt(1, id);
            pr.setString(2, uuid.toString());
            pr.execute();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void removePlayer(UUID uuid) {
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(DELETE_PLAYER)){
            pr.setString(1, uuid.toString());
            pr.execute();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void removeLocation(Integer id) {
        try (Connection connection = source.getConnection();
             PreparedStatement pr = connection.prepareStatement(DELETE_LOCATION)){
            pr.setInt(1, id);
            pr.execute();
        }
        catch (Exception e) {e.printStackTrace();}
    }


    private Location stringToLocation(String s){
        String[] a = s.split(":");
        return new Location(Bukkit.getWorld(a[0]),Double.parseDouble(a[1]),Double.parseDouble(a[2]),Double.parseDouble(a[3]));
    }

    private String stringFromLocation(Location loc){
        return  loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }

}