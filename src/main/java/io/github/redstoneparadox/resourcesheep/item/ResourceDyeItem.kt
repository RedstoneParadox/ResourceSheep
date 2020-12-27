package io.github.redstoneparadox.resourcesheep.item

import io.github.redstoneparadox.resourcesheep.config.SheepResourceLoader
import io.github.redstoneparadox.resourcesheep.entity.ResourceSheepEntity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier

/**
 * Created by RedstoneParadox on 12/24/2020.
 */
class ResourceDyeItem: Item(FabricItemSettings()) {
    override fun getDefaultStack(): ItemStack {
        val stack = super.getDefaultStack()
        val nbt = stack.orCreateTag
        val resource = SheepResourceLoader.getResources()[0]
        nbt.putString("resource", resource.id.toString())

        return stack
    }

    override fun getName(stack: ItemStack): Text {
        val nbt = stack.orCreateTag
        val resourceId = Identifier.tryParse(nbt.getString("resource"))
        val resource = SheepResourceLoader.getResource(resourceId)

        val translationKey = if (resource.block) {
            "block.${resource.id.namespace}.${resource.id.path}"
        } else {
            "item.${resource.id.namespace}.${resource.id.path}"
        }

        return super.getName(stack).copy().append(" - ").append(TranslatableText(translationKey))
    }

    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        if (entity is ResourceSheepEntity) {
            val nbt = stack.orCreateTag
            val resourceId = Identifier(nbt.getString("resource"))

            if (entity.isAlive && !entity.isSheared && entity.resourceId != resourceId) {
                entity.resourceId = resourceId
                stack.decrement(1)
            }

            return ActionResult.success(user.world.isClient)
        }

        return ActionResult.PASS
    }
}