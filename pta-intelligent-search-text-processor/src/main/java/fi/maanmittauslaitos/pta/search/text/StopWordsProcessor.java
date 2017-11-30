package fi.maanmittauslaitos.pta.search.text;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWordsProcessor implements TextProcessor {

	// Poistettavat sanat lowercasena
	private Set<String> stopwords = new HashSet<>();
	
	public void setStopwords(Collection<String> stopwords) {
		
		this.stopwords = new HashSet<>();
		for (String word : stopwords) {
			this.stopwords.add(word.toLowerCase());
		}
	}
	
	public Set<String> getStopwords() {
		return stopwords;
	}
	
	@Override
	public List<String> process(String str) {
		if (stopwords.contains(str.toLowerCase())) {
			return Collections.emptyList();
		}
		return Collections.singletonList(str);
	}

}
