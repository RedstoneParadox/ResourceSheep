package io.github.redstoneparadox.resourcesheep

import com.google.gson.*
import io.github.redstoneparadox.resourcesheep.config.SheepResourceLoader
import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import io.github.redstoneparadox.resourcesheep.item.ResourceSheepItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import java.io.File

object ResourceSheep: ModInitializer {
    val MOD_ID: String = "resourcesheep"

    override fun onInitialize() {
        ResourceSheepEntity.init()
        ResourceSheepItems.init()
    }

    fun fillTemplates(): Map<Identifier, JsonElement> {
        val map: MutableMap<Identifier, JsonElement> = mutableMapOf()

        for (resource in SheepResourceLoader.getResources()) {
            val json = loadTemplateFile(resource.recipe)

            if (json.size() == 0) continue

            if (json.has("key")) {
                val key = json["key"] as JsonObject

                for (entry in key.entrySet()) {
                    val value = entry.value

                    if (value is JsonObject && value["item"].asString == "sheepresources:resource_wool") {
                        val data = JsonObject()
                        val require = JsonObject()
                        require.add("resource", JsonPrimitive(resource.id.toString()))
                        data.add("require", require)
                        value.add("data", data)
                    }
                }
            }
            else if (json.has("ingredient")) {
                val ingredient = json["ingredient"] as JsonObject

                if (ingredient["item"].asString == "resourcesheep:resource_wool") {
                    val data = JsonObject()
                    val require = JsonObject()
                    require.add("resource", JsonPrimitive(resource.id.toString()))
                    data.add("require", require)
                    ingredient.add("data", data)
                }
            }
            else if (json.has("ingredients")) {
                val ingredients = json["ingredients"] as JsonArray

                for (element in ingredients) {

                    if (element is JsonObject && element["item"].asString == "sheepresources:resource_wool") {
                        val data = JsonObject()
                        val require = JsonObject()
                        require.add("resource", JsonPrimitive(resource.id.toString()))
                        data.add("require", require)
                        element.add("data", data)
                    }
                }
            }
            else {
                throw NotImplementedError("Template uses unsupported recipe type!")
            }

            if (json.has("result")) {
                when (val result = json["result"]) {
                    is JsonObject -> result.add("item", JsonPrimitive(resource.id.toString()))
                    is JsonPrimitive -> json.add("result", JsonPrimitive(resource.id.toString()))
                    else -> throw NotImplementedError("Template uses unsupported recipe type!")
                }
            }
            else {
                throw NotImplementedError("Template uses unsupported recipe type!")
            }

            map[Identifier("resourcesheep:${resource.id.path}_from_resource_wool")] = json
        }

        return map
    }

    private fun loadTemplateFile(name: String): JsonObject {
        try {
            val file = File(FabricLoader.getInstance().configDirectory, "resourcesheep/templates/$name.json")

            if (file.exists()) {
                val fileText = file.readText()
                val gson = GsonBuilder().create()
                return gson.fromJson(fileText, JsonObject::class.java)
            }
            else {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
        } catch (e: Exception) {
            println("template $name could not be loaded!")
            e.printStackTrace()
        }

        return JsonObject()
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}