package com.elvarg.game.model.commands.impl;

import com.elvarg.game.World;
import com.elvarg.game.entity.impl.object.GameObject;
import com.elvarg.game.entity.impl.object.ObjectManager;
import com.elvarg.game.entity.impl.player.Player;
import com.elvarg.game.model.commands.Command;
import com.elvarg.game.model.rights.PlayerRights;

import java.util.Optional;

public class RemoveObjectCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {

        Optional<GameObject> objectAtTile = World.getObjects()
                .stream()
                .filter(object ->
                        object.getLocation().equals(player.getLocation()))
                .findFirst();

        if (objectAtTile.isEmpty()) {
            player.getPacketSender().sendMessage(
                    "There is no registered object on your current tile."
            );
            return;
        }

        GameObject object = objectAtTile.get();

        ObjectManager.deregister(object, true);

        player.getPacketSender().sendMessage(
                "Removed object " + object.getId()
                        + " from your current tile."
        );
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();

        return rights == PlayerRights.OWNER
                || rights == PlayerRights.DEVELOPER;
    }
}