package io.github.redstoneparadox.resourcesheep.client

import io.github.redstoneparadox.resourcesheep.client.render.ResourceDyeModel
import io.github.redstoneparadox.resourcesheep.client.render.ResourceWoolModel
import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.SheepEntityRenderer
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier


object ResourceSheepClient: ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ResourceSheepEntity.TYPE) { dispatcher, context->
            SheepEntityRenderer(
                dispatcher
            )
        }
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { manager ->
            object: ModelResourceProvider {
                val DYE_MODEL = ResourceDyeModel()
                val DYE_ID = Identifier("resourcesheep:item/resource_dye")
                val WOOL_MODEL = ResourceWoolModel()
                val WOOL_ID = Identifier("resourcesheep:item/resource_wool")

                override fun loadModelResource(identifier: Identifier, context: ModelProviderContext): UnbakedModel? {
                    return when (identifier) {
                        DYE_ID -> DYE_MODEL
                        WOOL_ID -> WOOL_MODEL
                        else -> null
                    }
                }
            }
        }
    }
}