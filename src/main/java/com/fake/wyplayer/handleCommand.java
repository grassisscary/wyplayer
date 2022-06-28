package com.fake.wyplayer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class handleCommand extends CommandBase {

    public static void error(MinecraftServer server, ICommandSender sender) {
        String message = "invalid";
        TextComponentString text = new TextComponentString(message);
        text.getStyle().setColor(TextFormatting.RED);
        sender.sendMessage(text);
    }

    public static void sendMsg(String message, MinecraftServer server, ICommandSender sender) {
        String msg = message;
        TextComponentString text = new TextComponentString(msg);
        text.getStyle().setColor(TextFormatting.GOLD);
        sender.sendMessage(text);
    }

    public static void getPlayerStatistics(String player, MinecraftServer server, ICommandSender sender) throws IOException {
        String urlString = "https://api.wynncraft.com/v2/player/" + player + "/stats";
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String pageText = reader.lines().collect(Collectors.joining("\n"));

            Gson gson = new Gson();
            JsonObject js = gson.fromJson(pageText, JsonObject.class);

            String firstJoin = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("meta").getAsJsonObject().get("firstJoin").getAsString();
            String blocksWalked = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("blocksWalked").getAsString();
            String itemsIdentified = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("itemsIdentified").getAsString();
            String mobsKilled = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("mobsKilled").getAsString();
            String combat = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("totalLevel").getAsJsonObject().get("combat").getAsString();
            String profession = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("totalLevel").getAsJsonObject().get("profession").getAsString();
            String logins = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("logins").getAsString();
            String deaths = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("deaths").getAsString();
            String discoveries = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("global").getAsJsonObject().get("discoveries").getAsString();
            String playtime = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("meta").getAsJsonObject().get("playtime").getAsString();
            String rank = js.get("data").getAsJsonArray().get(0).getAsJsonObject().get("rank").getAsString();

            String stats = player + "'s Statistics\n\n" +
                    "Rank: " + rank + "\n" +
                    "Playtime: " + playtime + "\n" +
                    "Blocks Walked: " + blocksWalked + "\n" +
                    "Items Identified: " + itemsIdentified + "\n" +
                    "Mobs Killed: " + mobsKilled + "\n" +
                    "Levels (C/P): " + combat + "/" + profession + "\n" +
                    "Total Logins: " + logins + "\n" +
                    "Total Deaths: " + deaths + "\n" +
                    "Total Discoveries: " + discoveries + "\n" +
                    "Playtime: " + playtime + "\n" +
                    "First Join: " + firstJoin + "\n";
            sendMsg(stats,server,sender);
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {

        if (params != null && params.length > 0) {
            String player = params[0];

            if(player != null) {
                try {
                    getPlayerStatistics(player, server, sender);
                } catch (IOException e) {
                    error(server, sender);
                }
            }
            else {
                error(server, sender);
            }
        }
        else {
            error(server, sender);
        }
    }

    @Override
    public String getName() {
        return "wyplayer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.wyplayer.usage";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}