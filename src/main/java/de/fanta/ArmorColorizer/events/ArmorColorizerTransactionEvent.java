package de.fanta.ArmorColorizer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorColorizerTransactionEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final ItemStack stack;
    private final double transactionMoney;

    public ArmorColorizerTransactionEvent(Player player, ItemStack stack, double transactionMoney) {
        super(player);
        this.stack = stack;
        this.transactionMoney = transactionMoney;
    }

    public ItemStack getItem() {
        return stack;
    }

    public double getTransactionMoney() {
        return transactionMoney;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
