package marumasa.toro_resource_downloader;

import com.mojang.logging.LogUtils;
import marumasa.toro_resource_downloader.utils.Config;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class TORO_Resource_Downloader implements ModInitializer {
    // ModのID
    public static final String MOD_ID = "toro_resource_downloader";
    // ロガー
    public static final Logger LOGGER = LogUtils.getLogger();
    // コンフィグ
    public static final Config CONFIG = new Config();

    @Override
    public void onInitialize() {
        LOGGER.info("Start: " + MOD_ID);
    }
}
