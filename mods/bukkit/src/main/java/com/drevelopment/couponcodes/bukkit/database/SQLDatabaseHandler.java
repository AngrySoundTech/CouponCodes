/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.bukkit.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.drevelopment.couponcodes.api.database.DatabaseHandler;
import com.drevelopment.couponcodes.bukkit.BukkitPlugin;
import com.drevelopment.couponcodes.bukkit.database.options.DatabaseOptions;
import com.drevelopment.couponcodes.bukkit.database.options.MySQLOptions;
import com.drevelopment.couponcodes.bukkit.database.options.SQLiteOptions;

public class SQLDatabaseHandler implements DatabaseHandler {

    private DatabaseOptions dop;
    private Connection conn;

    public SQLDatabaseHandler(BukkitPlugin plugin, DatabaseOptions dop) {
        this.dop = dop;
        plugin.getDataFolder().mkdirs();

        if (dop instanceof SQLiteOptions) {
            try {
                ((SQLiteOptions) dop).getSQLFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DatabaseOptions getDatabaseOptions() {
        return dop;
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean open() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            conn = null;
            return false;
        }
        if (dop instanceof MySQLOptions) {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + ((MySQLOptions) dop).getHostname() + ":" +
                            ((MySQLOptions) dop).getPort() + "/" +
                            ((MySQLOptions) dop).getDatabase(),
                    ((MySQLOptions) dop).getUsername(),
                    ((MySQLOptions) dop).getPassword());
            return true;
        } else if (dop instanceof SQLiteOptions) {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + ((SQLiteOptions) dop).getSQLFile().getAbsolutePath());
            return true;
        } else {
            return false;
        }
    }

    public void close() throws SQLException {
        conn.close();
    }

    public boolean reload() {
        try {
            close();
            return open();
        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet query(String query) throws SQLException {
        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement();
        if (query.toLowerCase().contains("delete") || query.toLowerCase().contains("update") || query.toLowerCase().contains("insert") || query.toLowerCase().contains("alter")) {
            st.executeUpdate(query);
            return rs;
        } else {
            rs = st.executeQuery(query);
            return rs;
        }
    }

    public boolean createTable(String table) throws SQLException {
        Statement st = conn.createStatement();
        return st.execute(table);
    }

    @Override
    public String getDatabaseType() {
        if (dop instanceof MySQLOptions) {
            return "MySQL";
        } else if (dop instanceof SQLiteOptions) {
            return "SQLite";
        }
        return "None";
    }
}
