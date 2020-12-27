package io.github.redstoneparadox.resourcesheep.config

import blue.endless.jankson.Jankson
import blue.endless.jankson.JsonArray
import blue.endless.jankson.JsonObject
import blue.endless.jankson.JsonPrimitive
import io.github.cottonmc.jankson.JanksonFactory
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import java.io.File

object SheepResourceLoader: PreLaunchEntrypoint {
    private val RESOURCE_MAP: MutableMap<Identifier, SheepResource> = mutableMapOf()
    private val RESOURCE_LIST: MutableList<SheepResource> = mutableListOf()
    private val DEFAULT_RESOURCE = SheepResource(
        Identifier("minecraft:air"),
        false,
        Identifier("minecraft:block/dirt.png"),
        "ore",
        DyeColor.WHITE,
        Identifier("sheepresources:smelt_wool")
    )

    fun getResources(): List<SheepResource> {
        return RESOURCE_LIST
    }

    fun getResource(id: Identifier?): SheepResource {
        if (id == null) return DEFAULT_RESOURCE

        return RESOURCE_MAP[id] ?: DEFAULT_RESOURCE
    }

    override fun onPreLaunch() {
        try {
            val file = File(FabricLoader.getInstance().configDirectory, "resourcesheep/resources.json")

            if (file.exists()) {
                val fileText = file.readText()
                loadResources(fileText)
            }
            else {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadResources(fileText: String) {
        val jankson = createJankson()
        val json = jankson.load(fileText)

        for (element in (json["resources"] as JsonArray)) {
            val resource = jankson.marshaller.marshall(SheepResource::class.java, element)

            RESOURCE_MAP[resource.id] = resource
            RESOURCE_LIST.add(resource)
        }

        if (RESOURCE_MAP.isEmpty()) {
            RESOURCE_MAP[DEFAULT_RESOURCE.id] = DEFAULT_RESOURCE
            RESOURCE_LIST.add(DEFAULT_RESOURCE)
        }
    }

    fun createJankson(): Jankson {
        return JanksonFactory
            .builder()
            .registerSerializer(DyeColor::class.java) { dyeColor, marshaller ->
                return@registerSerializer JsonPrimitive(dyeColor.getName())
            }
            .registerDeserializer(String::class.java, DyeColor::class.java) { str, marshaller ->
                return@registerDeserializer when(str.toLowerCase()) {
                    "white" -> DyeColor.WHITE
                    "orange" -> DyeColor.ORANGE
                    "magenta" -> DyeColor.MAGENTA
                    "light_blue" -> DyeColor.LIGHT_BLUE
                    "yellow" -> DyeColor.YELLOW
                    "lime" -> DyeColor.LIME
                    "pink" -> DyeColor.PINK
                    "gray" -> DyeColor.GRAY
                    "light_gray" -> DyeColor.LIGHT_GRAY
                    "cyan" -> DyeColor.CYAN
                    "purple" -> DyeColor.PURPLE
                    "blue" -> DyeColor.BLUE
                    "brown" -> DyeColor.BROWN
                    "green" -> DyeColor.GREEN
                    "red" -> DyeColor.RED
                    "black" -> DyeColor.BLACK
                    else -> null
                }
            }
            .registerSerializer(SheepResource::class.java) { sheepResource, marshaller ->
                val obj = JsonObject();
                // TODO maybe
                return@registerSerializer obj
            }
            .registerDeserializer(JsonObject::class.java, SheepResource::class.java) {json, marshaller ->
                return@registerDeserializer SheepResource(
                    marshaller.marshall(Identifier::class.java, json["id"]),
                    json.getBoolean("block", false),
                    marshaller.marshall(Identifier::class.java, json["base_texture"]),
                    (json["pattern"] as JsonPrimitive).asString(),
                    marshaller.marshall(DyeColor::class.java, json["wool_color"]),
                    marshaller.marshall(Identifier::class.java, json["recipe"])
                )
            }
            .build()
    }
}