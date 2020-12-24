package io.github.redstoneparadox.resourcesheep.item

import io.github.redstoneparadox.resourcesheep.ResourceSheep.id
import net.minecraft.util.registry.Registry

object ResourceSheepItems {
    val RESOURCE_WOOL = ResourceWoolItem()

    fun init() {
        Registry.register(Registry.ITEM, id("resource_wool"), RESOURCE_WOOL)
    }
}