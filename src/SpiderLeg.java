
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Document;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {

	 private static final String USER_AGENT =
	            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>(); // Just a list of URLs
	private org.jsoup.nodes.Document htmlDocument; // This is our web page, or in other words, our document

	
	public void crawl(String url)
	{
		try{
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			org.jsoup.nodes.Document htmlDocument =  connection.get();
            this.htmlDocument = htmlDocument;
            System.out.println("Received web page at " + url);

            Elements linksOnPage =  htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
		}
		catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            System.out.println("Error in out HTTP request " + ioe);
        }
	}
	
	public boolean searchForWord(String searchWord)
    {
        // Defensive coding. This method should only be used after a successful crawl.
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }
	
	public List<String> getLinks()
	{
		return this.links;
	}
}
