package fi.maanmittauslaitos.pta.search.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HakuTulos {
	private Long startIndex;
	private Long totalHits;
	private List<Hit> hits = new ArrayList<>();
	private List<String> hints = new ArrayList<>();
	
	private Map<String, Facet> facets = new HashMap<>();
	
	public void setStartIndex(Long startIndex) {
		this.startIndex = startIndex;
	}
	
	public Long getStartIndex() {
		return startIndex;
	}
	
	public void setTotalHits(Long totalResults) {
		this.totalHits = totalResults;
	}
	
	public Long getTotalHits() {
		return totalHits;
	}
	
	public List<String> getHints() {
		return hints;
	}
	
	public void setHints(List<String> hints) {
		this.hints = hints;
	}
	
	public List<Hit> getHits() {
		return hits;
	}
	
	public void setHits(List<Hit> hits) {
		this.hits = hits;
	}
	
	public Map<String, Facet> getFacets() {
		return facets;
	}
	
	public void setFacets(Map<String, Facet> facets) {
		this.facets = facets;
	}
	
	public static class Hit {
		private List<HitText> text = new ArrayList<>();		
		private Double score;
		private String url;
		private String dateStamp;
		private List<String> types = new ArrayList<>();
		private List<String> topicCategories = new ArrayList<>();
		private List<String> keywordsInspire = new ArrayList<>();
		private List<String> organisations = new ArrayList<>();
		private List<String> organisationRoles = new ArrayList<>();
		private List<String> distributionFormats = new ArrayList<>();
		
		// Possibly hidden in API response
		private List<String> abstractUris = new ArrayList<>();
		private List<String> abstractTopicUris = new ArrayList<>();
		
		
		public void setText(List<HitText> text) {
			this.text = text;
		}
		
		public List<HitText> getText() {
			return text;
		}
		
		
		
		public void setScore(Double score) {
			this.score = score;
		}
		
		public Double getScore() {
			return score;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getUrl() {
			return url;
		}
		
		public Hit withRelevanssi(Double relevanssi) {
			setScore(relevanssi);
			return this;
		}
		
		public Hit withUrl(String url) {
			setUrl(url);
			return this;
		}

		public void setDateStamp(String dateStamp) {
			this.dateStamp = dateStamp;
		}
		
		public String getDateStamp() {
			return dateStamp;
		}
		
		public void setDistributionFormats(List<String> distributionFormats) {
			this.distributionFormats = distributionFormats;
		}
		
		public List<String> getDistributionFormats() {
			return distributionFormats;
		}
		
		public void setKeywordsInspire(List<String> keywordsInspire) {
			this.keywordsInspire = keywordsInspire;
		}
		
		public List<String> getKeywordsInspire() {
			return keywordsInspire;
		}
		
		public void setOrganisationRoles(List<String> organisationRoles) {
			this.organisationRoles = organisationRoles;
		}
		
		public List<String> getOrganisationRoles() {
			return organisationRoles;
		}
		
		public void setOrganisations(List<String> organisations) {
			this.organisations = organisations;
		}
		
		public List<String> getOrganisations() {
			return organisations;
		}
		
		public void setTopicCategories(List<String> topicCategories) {
			this.topicCategories = topicCategories;
		}
		
		public List<String> getTopicCategories() {
			return topicCategories;
		}
		
		public void setTypes(List<String> types) {
			this.types = types;
		}
		
		public List<String> getTypes() {
			return types;
		}
		
		public void setAbstractTopicUris(List<String> abstractTopicUris) {
			this.abstractTopicUris = abstractTopicUris;
		}
		
		public List<String> getAbstractTopicUris() {
			return abstractTopicUris;
		}
		
		public void setAbstractUris(List<String> abstractUris) {
			this.abstractUris = abstractUris;
		}
		
		public List<String> getAbstractUris() {
			return abstractUris;
		}
	}
	
	public static class HitText {
		private String lang;
		private String title;
		private String abstractText;
		private String organisationName;
		
		public void setAbstractText(String abstractText) {
			this.abstractText = abstractText;
		}
		
		public String getAbstractText() {
			return abstractText;
		}
		
		public void setLang(String lang) {
			this.lang = lang;
		}
		
		public String getLang() {
			return lang;
		}
		
		public void setOrganisationName(String organisationName) {
			this.organisationName = organisationName;
		}
		
		public String getOrganisationName() {
			return organisationName;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return title;
		}
		
		public static HitText create(String lang, String title, String abstractText, String organisationName) {
			HitText ret = new HitText();
			ret.setLang(lang);
			ret.setTitle(title);
			ret.setAbstractText(abstractText);
			ret.setOrganisationName(organisationName);
			return ret;
		}
	}
	
	public static class Facet {
		private String id;
		private Long count;
		
		public void setCount(Long count) {
			this.count = count;
		}
		
		public Long getCount() {
			return count;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getId() {
			return id;
		}
		
		public static Facet create(String id, Long count) {
			Facet ret = new Facet();
			ret.setId(id);
			ret.setCount(count);
			return ret;
		}
	}
}
