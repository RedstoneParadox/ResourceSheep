package io.github.redstoneparadox.resourcesheep

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import io.github.redstoneparadox.resourcesheep.config.SheepResourceLoader
import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import io.github.redstoneparadox.resourcesheep.item.ResourceSheepItems
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object ResourceSheep: ModInitializer {
    val MOD_ID: String = "resourcesheep"

    override fun onInitialize() {
        ResourceSheepEntity.init()
        ResourceSheepItems.init()
    }

    private fun fillTemplates() {
        for (resource in SheepResourceLoader.getResources()) {
            // TODO: Actually load the recipe template
            val json = JsonObject()

            if (json.has("key")) {
                val key = json["key"] as JsonObject

                for (entry in key.entrySet()) {
                    val value = entry.value

                    if (value is JsonObject && value["item"].asString == "sheepresources:resource_wool") {
                        val data = JsonObject()
                        val require = JsonObject()
                        require.add("resource", JsonPrimitive(resource.id.toString()))
                        data.add("require", require)
                    }
                }
            }

            val result = json["result"]

            if (result is JsonObject) {
                result.add("item", JsonPrimitive(resource.id.toString()))
            }
            else {
                json.add("result", JsonPrimitive(resource.id.toString()))
            }
        }
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}