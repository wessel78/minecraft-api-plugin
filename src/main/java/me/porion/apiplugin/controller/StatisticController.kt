package me.porion.apiplugin.controller

import io.javalin.http.Context
import me.porion.apiplugin.dal.StatsDal
import me.porion.apiplugin.model.Server
import me.porion.apiplugin.model.UserStatsHistory
import me.porion.npcplugin.model.User
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class StatisticController {

    companion object {
        private val statsDal: StatsDal = StatsDal()

        val getPlayerDeaths
            get() = io.javalin.http.Handler { ctx: Context ->
                val uuid = ctx.queryParam("uuid")
                val jsonObject: JSONObject = JSONObject()
                val jsonArray: JSONArray = JSONArray()

                val user = User()
                user.uuid = uuid
                val playerDeaths = statsDal.getPlayerDeaths(user)

                for (userStatsHistory: UserStatsHistory in playerDeaths) {
                    val playerDeath: JSONObject = JSONObject()
                    playerDeath["id"] = userStatsHistory.id
                    playerDeath["uuid"] = userStatsHistory.uuid
                    playerDeath["statId"] = userStatsHistory.statId
                    playerDeath["total"] = userStatsHistory.total
                    playerDeath["dateTime"] = userStatsHistory.dateTime
                    jsonArray.add(playerDeath)
                }
                jsonObject["data"] = jsonArray
                ctx.json(jsonObject)
        }

        val getPlayerKills
            get() = io.javalin.http.Handler { ctx: Context ->
                val uuid = ctx.queryParam("uuid")
                val jsonObject: JSONObject = JSONObject()
                val jsonArray: JSONArray = JSONArray()

                val user = User()
                user.uuid = uuid
                val playerKills = statsDal.getPlayerKills(user)

                for (userStatsHistory: UserStatsHistory in playerKills) {
                    val playerKills: JSONObject = JSONObject()
                    playerKills["id"] = userStatsHistory.id
                    playerKills["uuid"] = userStatsHistory.uuid
                    playerKills["statId"] = userStatsHistory.statId
                    playerKills["total"] = userStatsHistory.total
                    playerKills["dateTime"] = userStatsHistory.dateTime
                    jsonArray.add(playerKills)
                }
                jsonObject["data"] = jsonArray
                ctx.json(jsonObject)
            }

        val getTotalPlayers
            get() = io.javalin.http.Handler { ctx: Context ->
                val serverReference = ctx.queryParam("serverReference")
                val jsonObject: JSONObject = JSONObject()
                val jsonArray: JSONArray = JSONArray()

                val server = Server()
                server.reference = serverReference
                val totalPlayers = statsDal.getTotalPlayers(server)

                for (user: User in totalPlayers) {
                    val player: JSONObject = JSONObject()
                    player["uuid"] = user.uuid
                    player["serverReference"] = user.serverReference
                    player["dateTime"] = user.dateTime
                    jsonArray.add(player)
                }
                jsonObject["data"] = jsonArray
                ctx.json(jsonObject)
            }

        val getEntityKills
            get() = io.javalin.http.Handler { ctx: Context ->
                val uuid = ctx.queryParam("uuid")
                val jsonObject: JSONObject = JSONObject()
                val jsonArray: JSONArray = JSONArray()

                val user = User()
                user.uuid = uuid
                val entityKills = statsDal.getEntityKills(user)

                for (userStatsHistory: UserStatsHistory in entityKills) {
                    val entities: JSONObject = JSONObject()
                    entities["id"] = userStatsHistory.id
                    entities["uuid"] = userStatsHistory.uuid
                    entities["statId"] = userStatsHistory.statId
                    entities["total"] = userStatsHistory.total
                    entities["dateTime"] = userStatsHistory.dateTime
                    jsonArray.add(entities)
                }
                jsonObject["data"] = jsonArray
                ctx.json(jsonObject)
            }

        val getTotalPlayersGraph
            get() = io.javalin.http.Handler { ctx: Context ->
                val uuid = ctx.queryParam("uuid")
                val jsonObject: JSONObject = JSONObject()
                val jsonArray: JSONArray = JSONArray()

                val user = User()
                user.uuid = uuid
                val entityKills = statsDal.getEntityKills(user)
            }
    }
}