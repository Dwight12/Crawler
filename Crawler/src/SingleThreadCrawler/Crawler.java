package SingleThreadCrawler;

import java.util.Set;

/**
 * Created by dwight12 on 2016/1/2.
 */

public class Crawler {
    public void crawling(String[] seeds) {
        LinkFilter linkFilter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith("http://zzk.cnblogs.com/s?w=crawler&t=b&views=1000"))
                    return true;
                else return false;
            }
        };

        for (int i = 0; i < seeds.length; i++) {
            UrlPool.addUnvisitedUrl(seeds[i]);
        }

        while (!UrlPool.isUnvisitedUrlEmpty() && UrlPool.getVisitedUrlNumber() <= 2000) {
            String url = UrlPool.deleteUnvisitedUrl();

            if (url == null)
                continue;

            FileDownloader fileDownloader = new FileDownloader();
            fileDownloader.download(url);

            UrlPool.addVisitedUrl(url);
            Set<String> links = HtmlParser.extracLinks(url,linkFilter);
            for (String link: links) {
                UrlPool.addUnvisitedUrl(link);

            }
        }
    }

    public static void main(String[] args) {
        String[] strings = new String[] {
                //"http://www.nwu.edu.cn"
                "http://zzk.cnblogs.com/s?w=crawler&t=b&views=1000"
        };
        Crawler crawler = new Crawler();
        crawler.crawling(strings);
    }

}

