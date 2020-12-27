package io.github.redstoneparadox.resourcesheep.client.render

import com.mojang.datafixers.util.Pair
import io.github.redstoneparadox.resourcesheep.config.SheepResourceLoader
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.model.*
import net.minecraft.client.render.model.json.JsonUnbakedModel
import net.minecraft.client.render.model.json.ModelOverrideList
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Function
import java.util.function.Supplier

class ResourceDyeModel: UnbakedModel, BakedModel, FabricBakedModel {
    val SPRITE_ID = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:white_dye"))
    lateinit var modelTransformation: ModelTransformation
    lateinit var itemSprite: Sprite

    override fun getModelDependencies(): MutableCollection<Identifier> {
        return mutableListOf()
    }

    override fun getTextureDependencies(
        unbakedModelGetter: Function<Identifier, UnbakedModel>?,
        unresolvedTextureReferences: MutableSet<Pair<String, String>>?
    ): MutableCollection<SpriteIdentifier> {
        return mutableListOf()
    }

    override fun bake(loader: ModelLoader, textureGetter: Function<SpriteIdentifier, Sprite>, rotationContainer: ModelBakeSettings, modelId: Identifier): BakedModel {
        val defaultItemModel = loader.getOrLoadModel(Identifier("minecraft:item/generated")) as JsonUnbakedModel

        itemSprite = textureGetter.apply(SPRITE_ID)
        modelTransformation = defaultItemModel.transformations

        return this
    }

    override fun getQuads(state: BlockState?, face: Direction?, random: Random?): MutableList<BakedQuad> {
        return mutableListOf()
    }

    override fun useAmbientOcclusion(): Boolean {
        return true
    }

    override fun hasDepth(): Boolean {
        return false
    }

    override fun isSideLit(): Boolean {
        return true
    }

    override fun isBuiltin(): Boolean {
        return false
    }

    override fun getSprite(): Sprite {
        return itemSprite
    }

    override fun getTransformation(): ModelTransformation? {
        return modelTransformation
    }

    override fun getOverrides(): ModelOverrideList {
        return ModelOverrideList.EMPTY
    }

    override fun isVanillaAdapter(): Boolean {
        return false
    }

    override fun emitBlockQuads(blockView: BlockRenderView, state: BlockState, pos: BlockPos, randomSupplier: Supplier<Random>, context: RenderContext) {

    }

    override fun emitItemQuads(stack: ItemStack, randomSupplier: Supplier<Random>, context: RenderContext) {
        val nbt = stack.orCreateTag
        val resourceId = Identifier.tryParse(nbt.getString("resource"))
        val resource = SheepResourceLoader.getResource(resourceId)

        val manager = MinecraftClient.getInstance().bakedModelManager
        context.fallbackConsumer().accept(manager.getModel(ModelIdentifier(DYE_MODELS[resource.woolColor], "inventory")))
    }

    companion object {
        val DYE_MODELS: Map<DyeColor, Identifier> = mutableMapOf(
            DyeColor.WHITE to Identifier.tryParse("minecraft:white_dye")!!,
            DyeColor.ORANGE to Identifier.tryParse("minecraft:orange_dye")!!,
            DyeColor.MAGENTA to Identifier.tryParse("minecraft:magenta_dye")!!,
            DyeColor.LIGHT_BLUE to Identifier.tryParse("minecraft:light_blue_dye")!!,
            DyeColor.YELLOW to Identifier.tryParse("minecraft:yellow_dye")!!,
            DyeColor.LIME to Identifier.tryParse("minecraft:lime_dye")!!,
            DyeColor.PINK to Identifier.tryParse("minecraft:pink_dye")!!,
            DyeColor.GRAY to Identifier.tryParse("minecraft:gray_dye")!!,
            DyeColor.LIGHT_GRAY to Identifier.tryParse("minecraft:light_gray_dye")!!,
            DyeColor.CYAN to Identifier.tryParse("minecraft:cyan_dye")!!,
            DyeColor.PURPLE to Identifier.tryParse("minecraft:purple_dye")!!,
            DyeColor.BLUE to Identifier.tryParse("minecraft:blue_dye")!!,
            DyeColor.BROWN to Identifier.tryParse("minecraft:brown_dye")!!,
            DyeColor.GREEN to Identifier.tryParse("minecraft:green_dye")!!,
            DyeColor.RED to Identifier.tryParse("minecraft:red_dye")!!,
            DyeColor.BLACK to Identifier.tryParse("minecraft:black_dye")!!
        )
    }
}