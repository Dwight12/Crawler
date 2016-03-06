package SingleThreadCrawler;

/**
 * Created by dwight12 on 2016/1/2.
 */
/**
 * LinkFilter类其实是个接口，实现为一个内部类。
 */
public interface LinkFilter {
    public boolean accept(String url);
}
