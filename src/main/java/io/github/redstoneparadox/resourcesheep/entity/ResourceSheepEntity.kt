package io.github.redstoneparadox.resourcesheep.entity

import io.github.redstoneparadox.resourcesheep.item.ResourceSheepItems
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.SheepEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class ResourceSheepEntity(entityType: EntityType<out SheepEntity>, world: World): SheepEntity(entityType, world) {
    private val RESOURCE_DROPS: MutableMap<String, ItemStack> = mutableMapOf()
    var resource: String = ""

    init {
        val defaultStack = ItemStack(ResourceSheepItems.RESOURCE_WOOL)
        val nbt = defaultStack.orCreateTag
        nbt.putString("resource", "minecraft:air")

        RESOURCE_DROPS[""] = defaultStack
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
        tag.putString("resource", resource)
        return super.toTag(tag)
    }

    override fun fromTag(tag: CompoundTag) {
        if (tag.contains("resource")) resource = tag.getString("resource")
        super.fromTag(tag)
    }
}