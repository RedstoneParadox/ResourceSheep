package io.github.redstoneparadox.resourcesheep.item

import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier

/**
 * Created by RedstoneParadox on 12/24/2020.
 */
class ResourceDyeItem: Item(FabricItemSettings()) {
    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        if (entity is ResourceSheepEntity) {
            val nbt = stack.orCreateTag
            val resource = Identifier("minecraft:coal")

            if (entity.isAlive && !entity.isSheared && entity.resource != resource) {
                entity.resource = resource
                stack.decrement(1)
            }

            return ActionResult.success(user.world.isClient)
        }

        return ActionResult.PASS
    }
}