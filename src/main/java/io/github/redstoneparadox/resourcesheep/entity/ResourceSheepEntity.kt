package io.github.redstoneparadox.resourcesheep.entity

import io.github.redstoneparadox.resourcesheep.ResourceSheep
import io.github.redstoneparadox.resourcesheep.ResourceSheep.id
import io.github.redstoneparadox.resourcesheep.item.ResourceSheepItems
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.passive.SheepEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class ResourceSheepEntity(entityType: EntityType<out SheepEntity>, world: World): SheepEntity(entityType, world) {
    private val RESOURCE_DROPS: MutableMap<Identifier, ItemStack> = mutableMapOf()
    var resource: Identifier = Identifier("minecraft:air")

    init {
        val defaultStack = ItemStack(ResourceSheepItems.RESOURCE_WOOL)
        var nbt = defaultStack.orCreateTag
        nbt.putString("resource", "minecraft:air")

        RESOURCE_DROPS[Identifier("minecraft:air")] = defaultStack

        val coalStack = defaultStack.copy()
        nbt = coalStack.orCreateTag
        nbt.putString("resource", "minecraft:coal")

        RESOURCE_DROPS[Identifier("minecraft:coal")] = coalStack
    }

    override fun sheared(shearedSoundCategory: SoundCategory?) {
        world.playSoundFromEntity(null as PlayerEntity?, this, SoundEvents.ENTITY_SHEEP_SHEAR, soundCategory, 1.0f, 1.0f)
        this.isSheared = true
        val i = 1 + random.nextInt(3)

        for (j in 0 until i) {
            val itemEntity = this.dropStack(RESOURCE_DROPS[resource], 1.0f)
            if (itemEntity != null) {
                itemEntity.velocity = itemEntity.velocity.add(
                    ((random.nextFloat() - random.nextFloat()) * 0.1f).toDouble(),
                    (random.nextFloat() * 0.05f).toDouble(),
                    ((random.nextFloat() - random.nextFloat()) * 0.1f).toDouble()
                )
            }
        }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        tag.putString("resource", resource.toString())
        return super.toTag(tag)
    }

    override fun fromTag(tag: CompoundTag) {
        if (tag.contains("resource")) resource = Identifier(tag.getString("resource"))
        super.fromTag(tag)
    }

    companion object {
        val TYPE: EntityType<ResourceSheepEntity> = FabricEntityTypeBuilder
            .create(SpawnGroup.CREATURE, ::ResourceSheepEntity)
            .dimensions(EntityDimensions.fixed(0.9f, 1.3f))
            .build()

        fun init() {
            Registry.register(Registry.ENTITY_TYPE, id("resource_sheep"), TYPE)
            FabricDefaultAttributeRegistry.register(TYPE, createSheepAttributes())
        }
    }
}