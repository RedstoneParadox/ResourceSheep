package io.github.redstoneparadox.resourcesheep.config

import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier

data class SheepResource(
    val id: Identifier,
    val block: Boolean,
    val baseTexture: Identifier,
    val pattern: String,
    val woolColor: DyeColor,
    val recipe: Identifier
)