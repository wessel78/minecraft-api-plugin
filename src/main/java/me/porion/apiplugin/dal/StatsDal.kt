package me.porion.apiplugin.dal

import me.porion.apiplugin.model.Server
import me.porion.apiplugin.model.UserStatsHistory
import me.porion.npcplugin.dal.Database
import me.porion.npcplugin.model.User
import java.sql.ResultSet
import java.sql.SQLException

class StatsDal {
    private var db = Database()

    fun addDied(user: User) : Boolean {
        db.connect()
        db.update("INSERT INTO UserStatsHistory (total, uuid, stat_id, date_time) SELECT COUNT(UserStatsHistory.uuid) + 1, '${user.uuid}', 1, NOW() FROM UserStatsHistory WHERE stat_id = 1")
        db.disconnect()
        return true
    }

    fun addPlayerKill(user: User): Boolean {
        db.connect()
        db.update("INSERT INTO UserStatsHistory (total, uuid, stat_id, date_time) SELECT COUNT(UserStatsHistory.uuid) + 1, '${user.uuid}', 2, NOW() FROM UserStatsHistory WHERE stat_id = 2")
        db.disconnect()
        return true
    }

    fun addEntityKill(user: User): Boolean {
        db.connect()
        db.update("INSERT INTO UserStatsHistory (total, uuid, stat_id, date_time) SELECT COUNT(UserStatsHistory.uuid) + 1, '${user.uuid}', 3, NOW() FROM UserStatsHistory WHERE stat_id = 3")
        db.disconnect()
        return true
    }

    fun addPlayerEat(user: User): Boolean {
        db.connect()
        db.update("INSERT INTO UserStatsHistory (total, uuid, stat_id, date_time) SELECT COUNT(UserStatsHistory.uuid) + 1, '${user.uuid}', 4, NOW() FROM UserStatsHistory WHERE stat_id = 4")
        db.disconnect()
        return true
    }

    fun getPlayerDeaths(user: User): ArrayList<UserStatsHistory> {
        db.connect()
        var result: ResultSet? = null
        if (user.uuid != null)
        {
             result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 1 AND uuid = '${user.uuid}' ORDER BY date_time ASC"]
        }
        else
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 1 ORDER BY date_time ASC"]
        }
        val userStatHistorys: ArrayList<UserStatsHistory> = ArrayList<UserStatsHistory>()

        try {
            while (result?.next() == true) {
                val userStatsHistory = UserStatsHistory()
                userStatsHistory.id = result.getInt("id")
                userStatsHistory.uuid = result.getString("uuid")
                userStatsHistory.statId = result.getInt("stat_id")
                userStatsHistory.total = result.getInt("total")
                userStatsHistory.dateTime = result.getString("date_time")
                userStatHistorys.add(userStatsHistory)
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            db.disconnect()
        }
        return userStatHistorys
    }

    fun getPlayerKills(user: User): ArrayList<UserStatsHistory> {
        db.connect()
        var result: ResultSet? = null
        if (user.uuid != null)
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 2 AND uuid = '${user.uuid}' ORDER BY date_time ASC"]
        }
        else
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 2 ORDER BY date_time ASC"]
        }
        val userStatHistorys: ArrayList<UserStatsHistory> = ArrayList<UserStatsHistory>()

        try {
            while (result?.next() == true) {
                val userStatsHistory = UserStatsHistory()
                userStatsHistory.id = result.getInt("id")
                userStatsHistory.uuid = result.getString("uuid")
                userStatsHistory.statId = result.getInt("stat_id")
                userStatsHistory.total = result.getInt("total")
                userStatsHistory.dateTime = result.getString("date_time")
                userStatHistorys.add(userStatsHistory)
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            db.disconnect()
        }
        return userStatHistorys
    }

    fun getTotalPlayers(server: Server): ArrayList<User> {
        db.connect()
        var result: ResultSet? = null
        result = db["SELECT * FROM User WHERE server_reference = '${server.reference}'"]

        val users: ArrayList<User> = ArrayList<User>()

        try {
            while (result?.next() == true) {
                val user = User()
                user.uuid = result.getString("uuid")
                user.serverReference = result.getString("server_reference")
                user.dateTime = result.getString("date_time")
                users.add(user)
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            db.disconnect()
        }
        return users
    }

    fun getEntityKills(user: User): ArrayList<UserStatsHistory> {
        db.connect()
        var result: ResultSet? = null
        if (user.uuid != null)
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 3 AND uuid = '${user.uuid}' ORDER BY date_time ASC"]
        }
        else
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 3 ORDER BY date_time ASC"]
        }
        val userStatHistorys: ArrayList<UserStatsHistory> = ArrayList<UserStatsHistory>()

        try {
            while (result?.next() == true) {
                val userStatsHistory = UserStatsHistory()
                userStatsHistory.id = result.getInt("id")
                userStatsHistory.uuid = result.getString("uuid")
                userStatsHistory.statId = result.getInt("stat_id")
                userStatsHistory.total = result.getInt("total")
                userStatsHistory.dateTime = result.getString("date_time")
                userStatHistorys.add(userStatsHistory)
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            db.disconnect()
        }
        return userStatHistorys
    }

    fun getTotalPlayersGraph(user: User): ArrayList<UserStatsHistory> {
        db.connect()
        var result: ResultSet? = null
        if (user.uuid != null)
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 3 AND uuid = '${user.uuid}' ORDER BY date_time ASC"]
        }
        else
        {
            result = db["SELECT * FROM UserStatsHistory WHERE stat_id = 3 ORDER BY date_time ASC"]
        }
        val userStatHistorys: ArrayList<UserStatsHistory> = ArrayList<UserStatsHistory>()

        try {
            while (result?.next() == true) {
                val userStatsHistory = UserStatsHistory()
                userStatsHistory.id = result.getInt("id")
                userStatsHistory.uuid = result.getString("uuid")
                userStatsHistory.statId = result.getInt("stat_id")
                userStatsHistory.total = result.getInt("total")
                userStatsHistory.dateTime = result.getString("date_time")
                userStatHistorys.add(userStatsHistory)
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            db.disconnect()
        }
        return userStatHistorys
    }
}