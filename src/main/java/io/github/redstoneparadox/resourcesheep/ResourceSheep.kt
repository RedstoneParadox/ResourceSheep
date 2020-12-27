package io.github.redstoneparadox.resourcesheep

import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import io.github.redstoneparadox.resourcesheep.item.ResourceSheepItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.passive.SheepEntity
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ResourceSheep: ModInitializer {
    val MOD_ID: String = "resourcesheep"

    override fun onInitialize() {
        ResourceSheepEntity.init()
        ResourceSheepItems.init()
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}