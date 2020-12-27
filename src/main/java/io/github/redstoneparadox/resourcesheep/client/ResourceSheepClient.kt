package io.github.redstoneparadox.resourcesheep.client

import io.github.redstoneparadox.resourcesheep.ResourceSheep
import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.SheepEntityRenderer


object ResourceSheepClient: ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ResourceSheepEntity.TYPE) { dispatcher, context->
            SheepEntityRenderer(
                dispatcher
            )
        }
    }
}