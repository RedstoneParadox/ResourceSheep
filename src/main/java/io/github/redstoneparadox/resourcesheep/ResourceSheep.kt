package io.github.redstoneparadox.resourcesheep

import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
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
    val RESOURCE_SHEEP_ENTITY_TYPE: EntityType<ResourceSheepEntity> = FabricEntityTypeBuilder
        .create(SpawnGroup.CREATURE, ::ResourceSheepEntity)
        .dimensions(EntityDimensions.fixed(0.9f, 1.3f))
        .build()

    override fun onInitialize() {
        println("Sanity check!")
        Registry.register(Registry.ENTITY_TYPE, id("resource_sheep"), RESOURCE_SHEEP_ENTITY_TYPE)
        FabricDefaultAttributeRegistry.register(RESOURCE_SHEEP_ENTITY_TYPE, SheepEntity.createSheepAttributes())
    }

    fun id(path: String): Identifier {
        return Identifier(MOD_ID, path)
    }
}