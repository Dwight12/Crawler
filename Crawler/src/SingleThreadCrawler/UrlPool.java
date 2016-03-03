package SingleThreadCrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by dwight12 on 2016/3/1.
 */
public class UrlPool {
    private static Set<String> visitedUrl = new HashSet<String>();
    private static LinkedList<String> unvisitedUrl = new LinkedList<>();


    /**
     *
     * @param url
     * @return
     */
    public static boolean addVisitedUrl(String url) {
        return visitedUrl.add(url);
    }

    public static boolean addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("")) {
            if (!unvisitedUrl.contains(url) && !visitedUrl.contains(url)) {
                unvisitedUrl.addLast(url);
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @return
     */
    public static boolean isUnvisitedUrlEmpty() {
        return unvisitedUrl.isEmpty();
    }

    public static boolean isVisitedUrlEmpty() {
        return visitedUrl.isEmpty();
    }


    /**
     *
     * @return
     */
    public static String deleteUnvisitedUrl() {
        if (!isUnvisitedUrlEmpty()) {
            return unvisitedUrl.removeFirst();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static int getVisitedUrlNumber() {
        return visitedUrl.size();
    }

    public static int getUnvisitedUrlNumber() {
        return unvisitedUrl.size();
    }

}
