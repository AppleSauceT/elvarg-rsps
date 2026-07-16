package com.elvarg.game.model.commands.impl;

import com.elvarg.game.entity.impl.object.GameObject;
import com.elvarg.game.entity.impl.object.ObjectManager;
import com.elvarg.game.entity.impl.player.Player;
import com.elvarg.game.model.commands.Command;
import com.elvarg.game.model.rights.PlayerRights;

public class SpawnObjectCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length < 2) {
            player.getPacketSender().sendMessage(
                    "Usage: ::object [id] [type] [rotation]"
            );
            return;
        }

        try {
            int id = Integer.parseInt(parts[1]);
            int type = parts.length >= 3
                    ? Integer.parseInt(parts[2])
                    : 10;
            int face = parts.length >= 4
                    ? Integer.parseInt(parts[3])
                    : 0;

            if (id < 0) {
                player.getPacketSender().sendMessage(
                        "The object ID must be zero or greater."
                );
                return;
            }

            if (face < 0 || face > 3) {
                player.getPacketSender().sendMessage(
                        "Rotation must be between 0 and 3."
                );
                return;
            }

            GameObject gameObject = new GameObject(
                    id,
                    player.getLocation().clone(),
                    type,
                    face,
                    player.getPrivateArea()
            );

            ObjectManager.register(gameObject, true);

            player.getPacketSender().sendMessage(
                    "Placed object " + id
                            + " with type " + type
                            + " and rotation " + face + "."
            );
        } catch (NumberFormatException exception) {
            player.getPacketSender().sendMessage(
                    "Usage: ::object [id] [type] [rotation]"
            );
        }
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
