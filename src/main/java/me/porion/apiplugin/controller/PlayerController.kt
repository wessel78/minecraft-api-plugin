package me.porion.apiplugin.controller

import io.javalin.http.Context
import me.porion.apiplugin.ApiPlugin
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.event.Listener
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class PlayerController() : Listener {

    companion object {

        val getOnlinePlayers
            get() = io.javalin.http.Handler  { ctx: Context ->
                val allPlayers = JSONObject()
                val onlinePlayers = Bukkit.getOnlinePlayers();
                val jsonArray = JSONArray()

                for (player: Player in onlinePlayers) {
                    val specificPlayer = JSONObject()
                    val playerName = player.name
                    val playerUuid = player.uniqueId.toString()
                    val playerHealth = player.health.toString()
                    val playerWorld = player.world.name

                    specificPlayer["playerName"] = playerName
                    specificPlayer["playerUuid"] = playerUuid
                    specificPlayer["playerHealth"] = playerHealth
                    specificPlayer["playerWorld"] = playerWorld
                    jsonArray.add(specificPlayer)
                    allPlayers["response"] = jsonArray
                }

                ctx.json(allPlayers)
            }

        fun kickOnlinePlayer(jsonBody: String) {
            val jsonParser: JSONParser = JSONParser()
            val obj : JSONObject = jsonParser.parse(jsonBody) as JSONObject
            val onlinePlayers = Bukkit.getOnlinePlayers()

            for (player: Player in onlinePlayers) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(ApiPlugin.instance, {
                    if(player.uniqueId.toString() == obj["uuid"]) {
                        player.kick(Component.text(obj["kickMessage"].toString() + " || web-client"))
                    }
                }, 1L)
            }
        }

        fun spawnEntityOnPlayer(jsonBody: String) {
            val jsonParser: JSONParser = JSONParser()
            val obj : JSONObject = jsonParser.parse(jsonBody) as JSONObject
            val onlinePlayers = Bukkit.getOnlinePlayers()
            val entityName = obj["name"]
            val entityAmount = obj["amount"] as Long

            for (player: Player in onlinePlayers) {
                if(player.uniqueId.toString() == obj["uuid"]) {
                    for (i in 0..entityAmount)
                    {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ApiPlugin.instance, {
                            val entity = player.world.spawnEntity(player.location, EntityType.ZOMBIE) as Zombie

                            entity.customName = "§c$entityName"
                            entity.isCustomNameVisible = true
                            entity.health = 50.0
                        }, 1L)
                    }
                }
            }
        }
    }
}
