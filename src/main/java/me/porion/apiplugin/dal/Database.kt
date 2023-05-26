package me.porion.npcplugin.dal

import java.sql.Connection
import java.sql.DriverManager
import java.sql.DriverManager.println
import java.sql.ResultSet
import java.sql.SQLException

class Database {
    //Gegevens kunnen hier worden hardgecodeerd of worden meegegeven in de constructor
    var host = "..."
    var database = "..."
    var user = "..."
    var password = "..."
    var conn: Connection? = null

    constructor() {
        //lege constructor
    }

    constructor(host: String, database: String, user: String, password: String) {
        this.host = host
        this.database = database
        this.user = user
        this.password = password
    }

    /**
     * Maak verbinding met de database.
     */
    fun connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://$host/$database?user=$user&password=$password")
            println("Verbonden met database.")
        } catch (ex: SQLException) {
            // handle any errors
            println("Er kon geen verbinding worden gemaakt met de database.")
            println("SQLException: " + ex.message)
            println("SQLState: " + ex.sqlState)
            println("VendorError: " + ex.errorCode)
        }
    }

    /**
     * Deze methode wordt gebruikt om gegevens op te halen vanuit de database.
     * @param sql De SELECT query die uitgevoerd moet worden.
     * @return Het result in de vorm van een ResultSet of null als er een error is.
     */
    operator fun get(sql: String?): ResultSet? {
        try {
            val stmt = conn!!.createStatement()
            val result = stmt.executeQuery(sql)
            println("Query uitgevoerd")
            return result
        } catch (e: SQLException) {
            println(e.message)
            e.printStackTrace()
        }
        return null
    }

    /**
     * Deze methode wordt gebruikt om aanpassingen aan de data uit te voeren op je database.
     * @param sql De INSERT, UPDATE, of DELETE query die je wilt uitvoeren op de database.
     * @return Als antwoord krijg een een integer die aangeeft hoeveel records er zijn gewijzigd.
     */
    fun update(sql: String?): Int {
        try {
            val stmt = conn!!.createStatement()
            val result = stmt.executeUpdate(sql)
            println("Query uitgevoerd")
            return result
        } catch (e: SQLException) {
            println(e.message)
            e.printStackTrace()
        }
        return 0
    }

    /**
     * Sluit de database verbinding weer.
     */
    fun disconnect() {
        try {
            conn!!.close()
            conn = null
            println("Verbinding verbroken met de database")
        } catch (e: SQLException) {
            println(e.message)
            System.exit(0)
        }
    }
}