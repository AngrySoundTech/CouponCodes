/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.feldman.couponcodes.bukkit.database

import tech.feldman.couponcodes.api.database.DatabaseHandler
import tech.feldman.couponcodes.bukkit.BukkitPlugin
import tech.feldman.couponcodes.bukkit.database.options.DatabaseOptions
import tech.feldman.couponcodes.bukkit.database.options.MySQLOptions
import tech.feldman.couponcodes.bukkit.database.options.SQLiteOptions
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class SQLDatabaseHandler(plugin: BukkitPlugin, val databaseOptions: DatabaseOptions) : DatabaseHandler {
    var connection: Connection? = null
        private set

    init {
        plugin.dataFolder.mkdirs()

        if (databaseOptions is SQLiteOptions) {
            try {
                databaseOptions.sqlFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @Throws(SQLException::class)
    fun open(): Boolean {
        try {
            Class.forName("org.sqlite.JDBC")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            connection = null
            return false
        }

        if (databaseOptions is MySQLOptions) {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + databaseOptions.hostname + ":" +
                    databaseOptions.port + "/" +
                    databaseOptions.database + "?autoReconnect=true",
                    databaseOptions.username,
                    databaseOptions.password)
            return true
        } else if (databaseOptions is SQLiteOptions) {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseOptions.sqlFile.absolutePath)
            return true
        } else {
            return false
        }
    }

    @Throws(SQLException::class)
    fun close() {
        connection!!.close()
    }

    fun reload(): Boolean {
        try {
            close()
            return open()
        } catch (e: SQLException) {
            return false
        }

    }

    @Throws(SQLException::class)
    fun query(query: String): ResultSet {
        val st = connection!!.createStatement()
        var rs: ResultSet? = null

        if (query.toLowerCase().contains("delete") || query.toLowerCase().contains("update") || query.toLowerCase().contains("insert") || query.toLowerCase().contains("alter")) {
            st.executeUpdate(query)
            return rs!!
        } else {
            rs = st.executeQuery(query)
            return rs
        }
    }

    @Throws(SQLException::class)
    fun createTable(table: String): Boolean {
        val st = connection!!.createStatement()
        return st.execute(table)
    }

    override fun getDatabaseType(): String {
        if (databaseOptions is MySQLOptions) {
            return "MySQL"
        } else if (databaseOptions is SQLiteOptions) {
            return "SQLite"
        }
        return "None"
    }
}
