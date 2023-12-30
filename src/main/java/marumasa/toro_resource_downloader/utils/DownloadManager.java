package marumasa.toro_resource_downloader.utils;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadManager {

    private static class GitHubJSON {
        public List<Assets> assets;

        public GitHubJSON(String assets) {
            this.assets = new ArrayList<Assets>();
        }

        private static class Assets {
            public String browser_download_url;

            public Assets(String browser_download_url) {
                this.browser_download_url = browser_download_url;
            }
        }
    }

    // 最新のリソースパックのURLを取得
    public static String getURL(String user, String repository) {// JSONを取得するURL

        String url = String.format("https://api.github.com/repos/%s/%s/releases/latest", user, repository);

        // Gsonのインスタンスを作成
        Gson gson = new Gson();
        try {
            // URLオブジェクトを作成
            URL obj = new URL(url);
            // HTTPコネクションを開く
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // GETメソッドを設定
            con.setRequestMethod("GET");
            // レスポンスコードを取得
            int responseCode = con.getResponseCode();
            // レスポンスコードが200（成功）の場合
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // レスポンスを読み込む
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // レスポンスをJSONとしてパース
                GitHubJSON json = gson.fromJson(response.toString(), GitHubJSON.class);
                // パースした結果を出力
                return json.assets.get(0).browser_download_url;
            } else {
                // レスポンスコードが200以外の場合はエラー
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    // URL から ダウンロード
    public static void save(String FILE_URL, String FILE_PATH) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
