package com.elvarg.game.model.commands.impl;

import com.elvarg.game.entity.impl.player.Player;
import com.elvarg.game.model.commands.Command;
import com.elvarg.game.model.rights.PlayerRights;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FindObjectCommand implements Command {

    private static final Path OBJECT_LIST =
            Paths.get(
                    "C:/pvp/elvarg-rsps/ElvargServer/objectlist.txt"
            );

    private static final int MAX_RESULTS = 20;

    @Override
    public void execute(Player player, String command, String[] parts) {

        if (parts.length < 2) {
            player.getPacketSender().sendMessage(
                    "Usage: ::findobject [name]"
            );
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (int index = 1; index < parts.length; index++) {
            if (index > 1) {
                builder.append(" ");
            }

            builder.append(parts[index]);
        }

        String search = builder.toString()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (search.length() < 2) {
            player.getPacketSender().sendMessage(
                    "Enter at least two letters."
            );
            return;
        }

        if (!Files.exists(OBJECT_LIST)) {
            player.getPacketSender().sendMessage(
                    "Could not find objectlist.txt."
            );

            System.out.println(
                    "Missing object list: "
                            + OBJECT_LIST.toAbsolutePath()
            );
            return;
        }

        try {
            List<String> matches = Files.lines(OBJECT_LIST)
                    .filter(line ->
                            line.toLowerCase(Locale.ROOT).contains(search))
                    .limit(MAX_RESULTS)
                    .collect(Collectors.toList());

            if (matches.isEmpty()) {
                player.getPacketSender().sendMessage(
                        "No objects found matching: " + search
                );
                return;
            }

            player.getPacketSender().sendMessage(
                    "Object results for: " + search
            );

            for (String match : matches) {
                player.getPacketSender().sendMessage(match);
            }

            if (matches.size() >= MAX_RESULTS) {
                player.getPacketSender().sendMessage(
                        "Showing the first " + MAX_RESULTS
                                + " results."
                );
            }

        } catch (IOException exception) {
            player.getPacketSender().sendMessage(
                    "Could not read objectlist.txt."
            );

            exception.printStackTrace();
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getRights() == PlayerRights.DEVELOPER;
    }
}