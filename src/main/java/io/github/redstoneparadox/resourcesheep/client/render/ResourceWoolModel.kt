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

class ResourceWoolModel: UnbakedModel, BakedModel, FabricBakedModel {
    val SPRITE_ID = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:white_wool"))
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
        val defaultItemModel = loader.getOrLoadModel(Identifier("minecraft:block/block")) as JsonUnbakedModel

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
            DyeColor.WHITE to Identifier.tryParse("minecraft:white_wool")!!,
            DyeColor.ORANGE to Identifier.tryParse("minecraft:orange_wool")!!,
            DyeColor.MAGENTA to Identifier.tryParse("minecraft:magenta_wool")!!,
            DyeColor.LIGHT_BLUE to Identifier.tryParse("minecraft:light_blue_wool")!!,
            DyeColor.YELLOW to Identifier.tryParse("minecraft:yellow_wool")!!,
            DyeColor.LIME to Identifier.tryParse("minecraft:lime_wool")!!,
            DyeColor.PINK to Identifier.tryParse("minecraft:pink_wool")!!,
            DyeColor.GRAY to Identifier.tryParse("minecraft:gray_wool")!!,
            DyeColor.LIGHT_GRAY to Identifier.tryParse("minecraft:light_gray_wool")!!,
            DyeColor.CYAN to Identifier.tryParse("minecraft:cyan_wool")!!,
            DyeColor.PURPLE to Identifier.tryParse("minecraft:purple_wool")!!,
            DyeColor.BLUE to Identifier.tryParse("minecraft:blue_wool")!!,
            DyeColor.BROWN to Identifier.tryParse("minecraft:brown_wool")!!,
            DyeColor.GREEN to Identifier.tryParse("minecraft:green_wool")!!,
            DyeColor.RED to Identifier.tryParse("minecraft:red_wool")!!,
            DyeColor.BLACK to Identifier.tryParse("minecraft:black_wool")!!
        )
    }
}