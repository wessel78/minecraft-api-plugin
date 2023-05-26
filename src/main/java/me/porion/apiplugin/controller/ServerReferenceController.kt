package me.porion.apiplugin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.http.Context
import me.porion.apiplugin.dal.ServerDal
import me.porion.apiplugin.model.ApiToken
import me.porion.apiplugin.model.Server
import me.porion.apiplugin.model.UserStatsHistory
import me.porion.npcplugin.model.User
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

class ServerReferenceController {
    fun createReferenceId() : String {
        val uuid: UUID = UUID.randomUUID()
        val jsonObject : JSONObject = JSONObject()

        jsonObject["serverReferenceId"] = uuid.toString()

        try {
            val file = FileWriter("./plugins/apiSettings.json")
            file.write(jsonObject.toJSONString())
            file.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return uuid.toString()
    }

    fun getReferenceId(): JSONObject? {
        val objectMapper = ObjectMapper()
        val jsonFile = File("./plugins/apiSettings.json")
        if(!jsonFile.exists()) {return null}

        return objectMapper.readValue(File("./plugins/apiSettings.json"), JSONObject::class.java)
    }

    companion object {
        val serverDal: ServerDal = ServerDal()
        val getReferenceIdInfo
            get() = io.javalin.http.Handler { ctx: Context ->
                val reference = ctx.queryParam("reference")
                val server: Server = Server()
                server.reference = reference

                val serverInfo: JSONObject = JSONObject()
                val jsonObject: JSONObject = JSONObject()

                val referenceInfo: Server = serverDal.getServerInfo(server)
                serverInfo["id"] = referenceInfo.id
                serverInfo["reference"] = referenceInfo.reference
                serverInfo["active"] = referenceInfo.isActive

                jsonObject["data"] = serverInfo
                ctx.json(jsonObject)
            }

        fun activateApiToken(jsonBody: String): Boolean {
            val newApiToken: String = UUID.randomUUID().toString()
            val jsonParser: JSONParser = JSONParser()
            val obj : JSONObject = jsonParser.parse(jsonBody) as JSONObject
            val serverReference = obj["reference"]

            val apiToken: ApiToken = ApiToken()
            val server: Server = Server()
            server.reference = serverReference.toString()
            val serverInfo: Server = serverDal.getServerInfo(server)

            server.id = serverInfo.id
            server.isActive = serverInfo.isActive

            apiToken.serverId = server.id
            apiToken.apiToken = newApiToken

            serverDal.activateApiToken(apiToken, server)
            return true
        }
    }
}