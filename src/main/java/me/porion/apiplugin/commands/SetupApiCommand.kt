package me.porion.apiplugin.commands

import me.porion.apiplugin.controller.ServerReferenceController
import me.porion.apiplugin.dal.ServerDal
import me.porion.apiplugin.model.Server
import net.kyori.adventure.text.event.HoverEvent
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class SetupApiCommand : CommandExecutor {
    private val serverReferenceController: ServerReferenceController = ServerReferenceController()

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, arg: Array<String>): Boolean {
        if (sender !is Player) { return true }

        // /setupApi
        if (cmd.name.equals("setupApi", ignoreCase = true))
        {
            val serverDal = ServerDal()
            val server = Server()
            var referenceId = serverReferenceController.getReferenceId()

            if(referenceId == null)
            {
                server.reference = serverReferenceController.createReferenceId()
            }
            else
            {
                server.reference = serverReferenceController.getReferenceId()?.get("serverReferenceId")?.toString()
                sender.sendMessage("§6Api key is already set")
                return false
            }

            server.isActive = false

            val serverResult = serverDal.getServerInfo(server)
            if(serverResult.reference == null) {
                serverDal.insertServerInfo(server)
                val textComponent: TextComponent = TextComponent("Click to activate your api")
                textComponent.color = ChatColor.GREEN
                textComponent.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "http://127.0.0.1:8000/register?serverReference=${server.reference}")
                textComponent.hoverEvent = net.md_5.bungee.api.chat.HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, ComponentBuilder("Click to activate your api").color(ChatColor.GRAY).italic(true).create())
                sender.sendMessage(textComponent)
            }
            else {
                sender.sendMessage("§cSomething did go wrong!")
            }
        }
        return true
    }
}