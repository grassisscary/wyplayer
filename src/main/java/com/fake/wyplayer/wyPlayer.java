package com.fake.wyplayer;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = wyPlayer.MODID, name = wyPlayer.NAME, version = wyPlayer.VERSION)
public class wyPlayer
{
    public static final String MODID = "wyPlayer";
    public static final String NAME = "wyPlayer";
    public static final String VERSION = "0.1";

    static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        ClientCommandHandler.instance.registerCommand(new handleCommand());
    }
}
