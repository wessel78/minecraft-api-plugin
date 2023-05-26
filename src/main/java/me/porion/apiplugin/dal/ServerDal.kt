package me.porion.apiplugin.dal

import me.porion.apiplugin.model.ApiToken
import me.porion.apiplugin.model.Server
import me.porion.npcplugin.dal.Database
import me.porion.npcplugin.model.User
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class ServerDal {
    private var db = Database()

    fun insertServerInfo(server: Server) : Boolean {
        db.connect()
        db.update("INSERT INTO Server (reference, is_active) VALUES ('${server.reference}', ${server.isActive})")
        db.disconnect()
        return true
    }

    fun getServerInfo(server: Server) : Server {
        db.connect()
        val result: ResultSet? = db["SELECT * FROM Server WHERE reference = '${server.reference}'"]

        val serverData = Server()
        try {
            while (result?.next() == true) {
                serverData.id = result.getInt("id")
                serverData.reference = result.getString("reference")
                serverData.isActive = result.getBoolean("is_active")
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            db.disconnect()
        }
        return serverData
    }

    fun activateApiToken(apiToken: ApiToken, server: Server) {
        db.connect()
        db.update("UPDATE Server SET is_active = 1 WHERE reference = '${server.reference}'")
        db.update("INSERT INTO ApiToken (server_id, api_token, is_active) VALUES ('${server.id}', '${apiToken.apiToken}', 1)")
        db.disconnect()
    }
}