package me.black_lottus.heads.storage.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.black_lottus.heads.Heads;
import me.black_lottus.heads.storage.StorageManager;


import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class MysqLoader implements StorageManager {

    final static String HEADS_DB = "CREATE TABLE IF NOT EXISTS "
            + "Heads(id INTEGER PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(50) NOT NULL UNIQUE, nickname VARCHAR(30) NOT NULL UNIQUE, clan VARCHAR(20), joined_date TIMESTAMP)";

    final static String LIST_CLANS = "SELECT * FROM DC_Clans;";
    final static String LIST_PCLANS = "SELECT * FROM DC_Users;";
    final static String LIST_HOMES = "SELECT b.id,clan,world,x,y,z,pitch,yaw FROM DC_Bases b, DC_Clans c WHERE c.id = b.id;";
    final static String INSERT_CLAN = "INSERT INTO DC_Clans(clan, leader, have_password, password, ff, created_date) " +
            "VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE clan=?,leader=?,have_password=?,password=?,ff=?,created_date=?;";
    final static String INSERT_USER = "INSERT INTO DC_Users(uuid, nickname, clan, joined_date) " +
            "VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE uuid=?,nickname=?,clan=?,joined_date=?;";
    final static String INSERT_HOME = "INSERT INTO DC_Bases(id, world, x, y, z, pitch, yaw) " +
            "VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?,world=?,x=?,y=?,z=?,pitch=?,yaw=?;";

    final static String DELETE_CLAN = "DELETE FROM DC_Clans WHERE id = ?";
    final static String DELETE_USER = "DELETE FROM DC_Users WHERE uuid = ?";
    final static String DELETE_HOME = "DELETE FROM DC_Bases WHERE id = ?";

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
        create(HEADS_DB);
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
    public HashMap<UUID, Integer> listPlayers() {
        return null;
    }

    @Override
    public void savePlayer(UUID uuid) {

    }

    @Override
    public void deletePlayer(UUID uuid) {

    }

    @Override
    public int getPlayerHeads(UUID uuid) {
        return 0;
    }
}