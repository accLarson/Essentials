package com.earth2me.essentials.perm;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractVaultHandler extends SuperpermsHandler {
    private static Permission perms = null;
    private static Chat chat = null;

    public boolean setupProviders() {
        try {
            Class.forName("net.milkbowl.vault.permission.Permission");
            Class.forName("net.milkbowl.vault.chat.Chat");
        } catch (ClassNotFoundException e) {
            return false;
        }

        RegisteredServiceProvider<Permission> permsProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        perms = permsProvider.getProvider();
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = chatProvider.getProvider();
        return perms != null && chat != null;
    }

    @Override
    public String getGroup(final Player base) {
        return perms.getPrimaryGroup(base);
    }

    @Override
    public List<String> getGroups(final Player base) {
        return Arrays.asList(perms.getPlayerGroups(base));
    }

    @Override
    public boolean inGroup(final Player base, final String group) {
        return perms.playerInGroup(base, group);
    }

    @Override
    public String getPrefix(final Player base) {
        String playerPrefix = chat.getPlayerPrefix(base);
        if (playerPrefix == null) {
            String playerGroup = perms.getPrimaryGroup(base);
            return chat.getGroupPrefix((String) null, playerGroup);
        } else {
            return playerPrefix;
        }
    }

    @Override
    public String getSuffix(final Player base) {
        String playerSuffix = chat.getPlayerSuffix(base);
        if (playerSuffix == null) {
            String playerGroup = perms.getPrimaryGroup(base);
            return chat.getGroupSuffix((String) null, playerGroup);
        } else {
            return playerSuffix;
        }
    }
}