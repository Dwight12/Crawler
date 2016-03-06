
package SingleThreadCrawler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by dwight12 on 2016/1/2.
 */

public class HtmlParser {

    public static Set<String> extracLinks(String url, LinkFilter filter) {
        Set<String> links= new HashSet<String>();
        try {
            Parser parser = new Parser(url);
            /**
             * 有关于编码的问题还是要改一下，不能定死
             */
            parser.setEncoding("UTF-8");

            /**
             * new NodeClassFilter(LinkTag.class)过滤<a>，frameFilter过滤<frame>。OrFilter表示两者皆可
             * nodeList存储经过过滤的标签
             */

            NodeFilter frameFilter = new NodeFilter() {
                @Override
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            OrFilter linkFilter = new OrFilter(new NodeClassFilter(
                    LinkTag.class), frameFilter);

            NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);

            for(int i = 0; i < nodeList.size(); i++) {
                Node node = nodeList.elementAt(i);
                if (node instanceof LinkTag) {
                    LinkTag linkTag = (LinkTag) node;
                    String linkUrl = linkTag.getLink();
                    if (filter.accept(linkUrl))
                        links.add(linkUrl);
                } else {
                    String frame = node.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1)
                        end = frame.indexOf(">");

                    String frameUrl = frame.substring(5,end-1); //"src="有四个字符，所以从5开始

                    if (filter.accept(frameUrl))
                        links.add(frameUrl);
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }

        return links;
    }

}




