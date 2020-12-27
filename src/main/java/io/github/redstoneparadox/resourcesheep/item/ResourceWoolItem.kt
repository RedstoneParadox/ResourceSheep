package io.github.redstoneparadox.resourcesheep.item

import io.github.redstoneparadox.resourcesheep.config.SheepResourceLoader
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

class ResourceWoolItem: Item(FabricItemSettings()) {
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
}