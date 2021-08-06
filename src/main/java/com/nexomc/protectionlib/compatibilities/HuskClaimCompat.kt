package com.nexomc.protectionlib.compatibilities

import com.nexomc.protectionlib.ProtectionCompatibility
import net.william278.huskclaims.api.BukkitHuskClaimsAPI
import net.william278.huskclaims.api.HuskClaimsAPI
import net.william278.huskclaims.libraries.cloplib.operation.OperationType
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class HuskClaimCompat(mainPlugin: JavaPlugin, plugin: Plugin) : ProtectionCompatibility(mainPlugin, plugin) {
    private val huskClaimsCommon = HuskClaimsAPI.getInstance()
    private val huskClaimsBukkit = BukkitHuskClaimsAPI.getInstance()

    override fun canBuild(player: Player, target: Location): Boolean {
        val onlineUser = huskClaimsCommon.getOnlineUser(player.uniqueId)
        val position = huskClaimsBukkit.getPosition(target)

        val trustLevel = huskClaimsBukkit.getTrustLevelAt(position, onlineUser)
        return trustLevel.isEmpty || trustLevel.get().flags.contains(OperationType.BLOCK_PLACE)
    }

    override fun canBreak(player: Player, target: Location): Boolean {
        val onlineUser = huskClaimsCommon.getOnlineUser(player.uniqueId)
        val position = huskClaimsBukkit.getPosition(target)

        val trustLevel = huskClaimsBukkit.getTrustLevelAt(position, onlineUser)
        return trustLevel.isEmpty || trustLevel.get().flags.contains(OperationType.BLOCK_BREAK)
    }

    override fun canInteract(player: Player, target: Location): Boolean {
        val onlineUser = huskClaimsCommon.getOnlineUser(player.uniqueId)
        val position = huskClaimsBukkit.getPosition(target)

        val operationType = if (target.block.type.isBlock) OperationType.BLOCK_INTERACT else OperationType.ENTITY_INTERACT
        val trustLevel = huskClaimsBukkit.getTrustLevelAt(position, onlineUser)
        return trustLevel.isEmpty || trustLevel.get().flags.contains(operationType)
    }

    override fun canUse(player: Player, target: Location): Boolean {
        val onlineUser = huskClaimsCommon.getOnlineUser(player.uniqueId)
        val position = huskClaimsBukkit.getPosition(target)

        val trustLevel = huskClaimsBukkit.getTrustLevelAt(position, onlineUser)
        return trustLevel.isEmpty || trustLevel.get().flags.contains(OperationType.BLOCK_INTERACT)
    }
}
