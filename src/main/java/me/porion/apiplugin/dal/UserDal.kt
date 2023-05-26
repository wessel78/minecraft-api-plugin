package me.porion.npcplugin.dal

import me.porion.npcplugin.model.User
import java.sql.DriverManager.println
import java.sql.ResultSet
import java.sql.SQLException


class UserDal {
    var db = Database()

    fun getUser(uuid: String): User {
        db.connect()
        val result: ResultSet? = db["SELECT * FROM User WHERE uuid = '$uuid'"]

        val user = User()
        try {
            while (result?.next() == true) {
                user.serverReference = result.getString("server_reference")
                user.uuid = result.getString("uuid")
                user.dateTime = result.getString("date_time")
            }
        } catch (ex: SQLException) {
            println(ex.message)
        } finally {
            db.disconnect()
        }
        return user
    }

    fun insertUser(user: User) {
        db.connect()
        db.update("INSERT INTO User (uuid, server_reference, username) VALUES ('${user.uuid}', '${user.serverReference}', NOW())")
        db.disconnect()
    }
}