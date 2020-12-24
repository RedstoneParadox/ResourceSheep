package io.github.redstoneparadox.resourcesheep.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class ResourceWoolItem: BlockItem(Blocks.AIR, FabricItemSettings()) {
    override fun getName(stack: ItemStack): Text {
        val nbt = stack.orCreateTag
        val resource = Identifier.tryParse(nbt.getString("resource"))
        val translationKey = "item.${resource?.namespace}.${resource?.path}"

        return super.getName(stack).copy().append(" - ").append(TranslatableText(translationKey))
    }
}