package me.porion.apiplugin

//import me.porion.npcplugin.listeners.TestListener
import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import io.javalin.http.Context
import io.javalin.plugin.bundled.CorsContainer
import io.javalin.plugin.bundled.CorsPluginConfig
import me.porion.apiplugin.commands.SetupApiCommand
import me.porion.apiplugin.controller.ServerReferenceController
import me.porion.apiplugin.listeners.StatsListener
import me.porion.apiplugin.listeners.UserListener
import me.porion.apiplugin.controller.PlayerController
import me.porion.apiplugin.controller.StatisticController
import org.bukkit.plugin.java.JavaPlugin


class ApiPlugin : JavaPlugin() {
    private val serverReferenceController: ServerReferenceController = ServerReferenceController()

    companion object {
        lateinit var instance: ApiPlugin
    }

    override fun onEnable() {
        //Set instance
        instance = this
        registerCommand()
        if(serverReferenceController.getReferenceId() != null) {registerListener()}
        if(serverReferenceController.getReferenceId() != null) {createApi()}
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun registerListener()
    {
        server.pluginManager.registerEvents(UserListener(), this)
        server.pluginManager.registerEvents(StatsListener(), this)
    }

    fun registerCommand()
    {
        getCommand("setupApi")?.setExecutor(SetupApiCommand())
    }

    fun createApi()
    {
        val app = Javalin.create { config: JavalinConfig -> config.plugins.enableCors { cors: CorsContainer -> cors.add { it: CorsPluginConfig -> it.anyHost() } } }.start(7070)
        app.get("/getOnlinePlayers", PlayerController.getOnlinePlayers)
        app.put("/kickOnlinePlayer") { ctx: Context -> PlayerController.kickOnlinePlayer(ctx.body()) }
        app.put("/spawnEntityOnPlayer") { ctx: Context -> PlayerController.spawnEntityOnPlayer(ctx.body()) }

        //Statistics
        app.get("/getPlayerDeaths", StatisticController.getPlayerDeaths)
        app.get("/getPlayerKills", StatisticController.getPlayerKills)
        app.get("/getTotalPlayers", StatisticController.getTotalPlayers)
        app.get("/getEntityKills", StatisticController.getEntityKills)

        //Api activation
        app.get("/checkReferenceId", ServerReferenceController.getReferenceIdInfo)
        app.post("/activateApiToken") { ctx: Context -> ServerReferenceController.activateApiToken(ctx.body()) }

//        app.get("/getTotalPlayerDeaths", StatisticController.getTotalPlayerDeaths)
//        app.get("/getTotalPlayerKills", StatisticController.getTotalPlayerDeaths)
//        app.get("/getTotalPlayers", StatisticController.getTotalPlayerDeaths)


    }
}