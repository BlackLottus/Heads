package me.black_lottus.heads.file;

import me.black_lottus.heads.Heads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Files {

    private final YamlConfiguration config;
    private final java.io.File file;
    private final boolean comments;

    public static final String COMMAND_PREFIX = ChatColor.BLUE + ChatColor.BOLD.toString() + "Heads " + ChatColor.WHITE + ">> ";

    public Files(Heads m, String s, boolean defaults, boolean comments) {
        this.comments = comments;
        this.file = new File(m.getDataFolder(), s + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
        Reader reader;
        if (comments) {
            InputStream im = getConfigContent(new InputStreamReader(Objects.requireNonNull(m.getResource(s + ".yml")), StandardCharsets.UTF_8));
            reader = new InputStreamReader(im,StandardCharsets.UTF_8);
        } else {
            reader = new InputStreamReader(Objects.requireNonNull(m.getResource(s + ".yml")), StandardCharsets.UTF_8);
        }
        YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(reader);
        try {
            if (!this.file.exists()) {
                this.config.addDefaults(loadConfiguration);
                this.config.options().copyDefaults(true);
                if (comments) {
                    save();
                } else {
                    this.config.save(file);
                }
            } else {
                if (defaults) {
                    this.config.addDefaults(loadConfiguration);
                    this.config.options().copyDefaults(true);
                    if (comments) {
                        save();
                    } else {
                        this.config.save(file);
                    }
                }
                this.config.load(this.file);
            }
        } catch (IOException | InvalidConfigurationException ignored) {
        }
    }

    public void reload() {
        try {
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public InputStream getConfigContent(Reader reader) {
        try {
            String addLine, currentLine;
            int commentNum = 0;
            StringBuilder whole = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.startsWith("#")) {
                    addLine = currentLine.replaceFirst("#", "C_" + commentNum + ": '").concat("'");
                    whole.append(addLine).append("\n");
                    commentNum++;
                }else {
                    whole.append(currentLine).append("\n");
                }
            }
            String config = whole.toString();
            InputStream configStream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
            bufferedReader.close();
            return configStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String prepareConfigString(String configString) {
        String[] lines = configString.split("\n");
        StringBuilder config = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith("C_")) {
                String comment = "#" + line.trim().substring(line.indexOf(":") + 1);
                String normalComment;

                if (comment.startsWith("# '")) {
                    normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# '", "#").replace("'", "");
                } else {
                    normalComment = comment;
                }

                config.append(normalComment).append("\n");
            }else {
                config.append(line).append("\n");
            }
        }
        return config.toString();
    }


    public void save() {
        if (comments) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(prepareConfigString(config.saveToString()));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.config.save(file);
            } catch (IOException ignored) {
            }
        }
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public String getWithoutPrefix(String s) {
        if (config.getString(s) == null) {
            return "";
        }
        return Objects.requireNonNull(this.config.getString(s)).replaceAll("<l>", "¡").replaceAll("&", "§").replaceAll("-,-", "ñ");
    }
    public String get(String s) {
        if (config.getString(s) == null) {
            return "";
        }
        return (COMMAND_PREFIX + this.config.getString(s)).replaceAll("<l>", "¡").replaceAll("&", "§").replaceAll("-,-", "ñ");
    }

    public String get(Player p, String s) {
        if (config.getString(s) == null) {
            return "";
        }
        return (COMMAND_PREFIX + this.config.getString(s)).replaceAll("<l>", "¡").replaceAll("&", "§").replaceAll("-,-", "ñ");
    }

    public String getOrDefault(String s, String def) {
        if (config.isSet(s)) {
            return get(null, s);
        }
        set(s, def);
        save();
        return def;
    }

    public Material getMaterial(String s){
        return Material.valueOf(Objects.requireNonNull(this.config.getString(s)));
    }

    public int getInt(String s) {
        return this.config.getInt(s);
    }

    public int getIntOrDefault(String s, int def) {
        if (config.isSet(s)) {
            return getInt(s);
        }
        set(s, def);
        save();
        return def;
    }

    public double getDouble(String s) {
        return this.config.getDouble(s);
    }

    public double getDoubleOrDefault(String s, double def) {
        if (config.isSet(s)) {
            return getDouble(s);
        }
        set(s, def);
        save();
        return def;
    }

    public List<String> getList(String s) {
        List<String> now = new ArrayList<>();
        for (String st : this.config.getStringList(s)) {
            now.add(st.replaceAll("&", "§"));
        }
        return now;
    }

    public List<String> getListOrDefault(String s, List<String> def) {
        if (config.isSet(s)) {
            return getList(s);
        }
        set(s, def);
        save();
        return def;
    }

    public boolean isSet(String s) {
        return this.config.isSet(s);
    }

    public void set(String s, Object o) {
        this.config.set(s, o);
    }

    public boolean getBoolean(String s) {
        return this.config.getBoolean(s);
    }

    public boolean getBooleanOrDefault(String s, boolean def) {
        if (config.isSet(s)) {
            return getBoolean(s);
        }
        set(s, def);
        save();
        return def;
    }

}
