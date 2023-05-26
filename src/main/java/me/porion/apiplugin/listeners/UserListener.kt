package me.porion.apiplugin.listeners

import me.porion.apiplugin.controller.ServerReferenceController
import me.porion.npcplugin.dal.UserDal
import me.porion.npcplugin.model.User
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class UserListener : Listener {
    private val serverReferenceController: ServerReferenceController = ServerReferenceController()

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent)
    {
        val userDal = UserDal()
        val userResult = userDal.getUser(event.player.uniqueId.toString())
        val user = User()

        user.uuid = event.player.uniqueId.toString()
        user.serverReference = serverReferenceController.getReferenceId()?.get("serverReferenceId")?.toString()

        if(userResult.uuid == null) { userDal.insertUser(user) }
    }
}