package marumasa.toro_resource_downloader.client;

import marumasa.toro_resource_downloader.utils.DownloadManager;
import net.fabricmc.api.ClientModInitializer;

import java.io.File;
import java.nio.file.Files;

import static marumasa.toro_resource_downloader.TORO_Resource_Downloader.CONFIG;
import static marumasa.toro_resource_downloader.TORO_Resource_Downloader.LOGGER;

public class TORO_Resource_DownloaderClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {


        // リソースパックのパス
        File ResourcePack_Path = new File("./resourcepacks/TORO-ResourcePack.zip");
        // GitHub のユーザー名
        String user = "TORO-Server";
        // GitHub のリポジトリ名
        String repository = "TORO-ResourcePack";


        // 最新のリソースパックのURL 取得
        final String url = DownloadManager.getURL(user, repository);

        if (url == null) {
            // もし、URL 取得出来てなかったら
            // エラーログ 出力
            LOGGER.error("GET request failed");

        } else if (!url.equals(CONFIG.getResourceURL()) || !ResourcePack_Path.exists()) {
            // もし、URL が更新されていたら または、リソースパックがダウンロードして無かったら
            // URL からダウンロードして リソースパック保存
            DownloadManager.save(url, ResourcePack_Path);
            // 更新後の URL を記録
            CONFIG.setResourceURL(url);
            // ログ出力
            LOGGER.info(String.format("GET %s", url));
        }
    }
}
