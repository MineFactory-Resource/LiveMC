package net.teamuni.livemc;

import net.teamuni.livemc.twip.TwipClient;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class LiveMC extends JavaPlugin implements Listener {

    private static LiveMC instance;

    public static LiveMC getInstance() {
        return instance;
    }

    private TwipClient client;
    private String donationCommand;
    private String marketRewardCommand;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        ConfigurationSection section = getConfig().getConfigurationSection("Twip");
        if (section == null) return;
        this.donationCommand = section.getString("Donation", "");
        this.marketRewardCommand = section.getString("MarketReward", "");
        try {
            this.client = new TwipClient(section.getString("key"), section.getString("token"));
        } catch (Exception e) {
            getLogger().severe("트윕 클라이언트 생성 중 예외가 발생했습니다.");
            getServer().getPluginManager().disablePlugin(this);
            e.printStackTrace();
        }
    }

    public String getDonationCommand() {
        return this.donationCommand;
    }

    public String getMarketRewardCommand() {
        return this.marketRewardCommand;
    }

    @Override
    public void onDisable() {
        this.client.close();
    }
}