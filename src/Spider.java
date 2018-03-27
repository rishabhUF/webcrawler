import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spider {

	private static final int MAX_NO_OF_PAGES = 10;
	private Set<String> visitedPages = new HashSet<>();
	private List<String> pagesToVisit = new LinkedList<>();
	
	private String nextURL()
	{
		String nextUrl;
		do
		{
			nextUrl = this.pagesToVisit.remove(0);
		}
		while(this.visitedPages.contains(nextUrl));
		this.visitedPages.add(nextUrl);
		return nextUrl;
	}

	public void search(String url, String searchWord)
	{
		while(this.pagesToVisit.size() < MAX_NO_OF_PAGES)
		{
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			if(this.pagesToVisit.isEmpty())
			{
				currentUrl = url;
				this.visitedPages.add(currentUrl);
			}
			else
			{
				currentUrl = this.nextURL();
			}
			leg.crawl(currentUrl);
			boolean success = leg.searchForWord(searchWord);
			if(success)
			{
				System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
				break;
			}
			this.pagesToVisit.addAll(leg.getLinks());
		}
		System.out.println(String.format("**Done** Visited %s web page(s)", this.visitedPages.size()));
    }
}
