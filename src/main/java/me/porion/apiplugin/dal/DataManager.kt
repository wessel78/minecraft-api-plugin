package me.porion.npcplugin.dal

import me.porion.npcplugin.model.User
import java.sql.DriverManager.println
import java.sql.ResultSet
import java.sql.SQLException
import java.util.concurrent.Executors


class DataManager {
    private val service = Executors.newCachedThreadPool()
    var db = Database()

//    fun getUser(uuid: String): User {
//        db.connect()
//        val result: ResultSet? = db["SELECT * FROM user WHERE uuid = '$uuid'"]
//
//        val user = User()
//        try {
//            while (result?.next() == true) {
//                user.id = result.getInt("id")
//                user.uuid = result.getString("uuid")
//                user.username = result.getString("username")
//            }
//        } catch (ex: SQLException) {
//            println(ex.message)
//        } finally {
//            db.disconnect()
//        }
//        return user
//    }
//
//    fun insertUser(username: String, uuid: String) {
//        db.connect()
//        db.update("INSERT INTO user (username, uuid) VALUES ('$username', '$uuid')")
//        db.disconnect()
//    }

//    val tempSettings: ArrayList<Any>
//        get() {
//            db.connect()
//            val result: ResultSet? = db["SELECT * FROM temp_settings"]
//            val tempSettings: ArrayList<TempSetting> = ArrayList<TempSetting>()
//            try {
//                while (result.next()) {
//                    val tempSetting = TempSetting()
//                    tempSetting.setId(result.getInt("id"))
//                    tempSetting.setRed(result.getInt("red"))
//                    tempSetting.setGreen(result.getInt("green"))
//                    tempSetting.setBlue(result.getInt("blue"))
//                    tempSetting.setTemp(result.getInt("temp"))
//                    tempSetting.setSlug(result.getString("slug"))
//                    tempSettings.add(tempSetting)
//                }
//            } catch (ex: SQLException) {
//                println(ex)
//            }
//            db.disconnect()
//            return tempSettings
//        }
//
//    fun updateTempSettings(tempSetting: TempSetting) {
//        db.connect()
//        db.update("UPDATE temp_settings SET red = '" + tempSetting.getRed() + "', green = '" + tempSetting.getGreen() + "', blue = '" + tempSetting.getBlue() + "', temp = '" + tempSetting.getTemp() + "' WHERE slug = '" + tempSetting.getSlug() + "'")
//        db.disconnect()
//    }
//
//    fun addTempHistory(tempHistory: TempHistory) {
//        db.connect()
//        db.update("INSERT INTO temp_history (temp, temp_refrence, datum, locatie) VALUES('" + tempHistory.getTemp() + "','" + tempHistory.getTempRefrence() + "','" + tempHistory.getDatum() + "', '" + tempHistory.getAdres() + "')")
//        db.disconnect()
//    }
//
//    fun addTempRefrence(tempHistory: String) {
//        db.connect()
//        db.update("INSERT INTO temp_history_instance (temp_refrence, date) VALUES('$tempHistory', NOW())")
//        db.disconnect()
//    }
//
//    val tempHistoryOptions: ArrayList<Any>
//        get() {
//            db.connect()
//            val tempHistorys: ArrayList<TempHistoryInstance> = ArrayList<TempHistoryInstance>()
//            val result: ResultSet? = db["SELECT temp_history.temp_refrence , temp_history_instance.temp_refrence, temp_history.locatie, temp_history_instance.id, temp_history_instance.date  FROM temp_history_instance INNER JOIN temp_history ON (temp_history_instance.temp_refrence = temp_history.temp_refrence) GROUP BY temp_history_instance.temp_refrence"]
//            try {
//                while (result.next()) {
//                    val tempHistory = TempHistoryInstance()
//                    tempHistory.setId(result.getInt("id"))
//                    tempHistory.setHistoryReference(result.getString("temp_refrence"))
//                    tempHistory.setLocation(result.getString("locatie"))
//                    tempHistory.setDate(result.getString("date"))
//                    tempHistorys.add(tempHistory)
//                }
//            } catch (ex: SQLException) {
//                println(ex)
//            }
//            db.disconnect()
//            return tempHistorys
//        }
//
//    @kotlin.Throws(SQLException::class)
//    fun getTempHistory(reference: String): ArrayList<TempHistory> {
//        db.connect()
//        val tempHistories: ArrayList<TempHistory> = ArrayList<TempHistory>()
//        val result: ResultSet? = db["SELECT * FROM temp_history WHERE temp_refrence = '$reference'"]
//        while (result.next()) {
//            val tempHistory = TempHistory()
//            tempHistory.setTemp(result.getDouble("temp"))
//            tempHistory.setDatum(result.getString("datum"))
//            tempHistory.setAdres(result.getString("locatie"))
//            tempHistories.add(tempHistory)
//        }
//        return tempHistories
//    }
}