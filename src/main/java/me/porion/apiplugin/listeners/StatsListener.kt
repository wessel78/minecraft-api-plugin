package me.porion.apiplugin.listeners

import me.porion.apiplugin.dal.StatsDal
import me.porion.npcplugin.model.User
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

class StatsListener : Listener {
    private val statsDal: StatsDal = StatsDal()

    @EventHandler
    fun onPlayerDie(event: PlayerDeathEvent) {
        val user = User()
        user.uuid = event.player.uniqueId.toString()
        statsDal.addDied(user)
    }

    @EventHandler
    fun onPlayerKill(event: PlayerDeathEvent) {
        val user = User()
        user.uuid = event.player.uniqueId.toString()
        val killer = event.entity.killer?.name
        if (killer != null) { statsDal.addPlayerKill(user) }
    }

    @EventHandler
    fun onEntityKill(event: EntityDeathEvent) {
        val user = User()
        user.uuid = event.entity.killer?.uniqueId.toString()
        val killer = event.entity.killer
        if(killer is Player) { statsDal.addEntityKill(user) }
    }

    @EventHandler
    fun onPlayerEat(event: PlayerItemConsumeEvent) {
        val user = User()
        user.uuid = event.player.uniqueId.toString()
        statsDal.addPlayerEat(user)
    }
}