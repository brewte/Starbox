package se.starbox.util;

import org.json.simple.JSONObject;

public class SearchResult {
	
	private String name, url, doctype, timestamp;
	
	private int filesize;

	/**
	 * Default constructor.
	 */
	public SearchResult() {
		
		// SearchModel skickar inte med n�gra parametrar vid skapandet av SearchResult-objekt, t�nkte att det var dumt att �ndra 
	}
	
	/**
	 * Returns a JSON representation of this object.
	 * This method supress a warning because JSON is not type-safe.
	 * @return JSON- A JSON formatted string representing this object.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		json.put("name", this.getName());
		json.put("url", this.getUrl());
		json.put("doctype", this.getDoctype());
		json.put("timestamp", this.getTimestamp());
		json.put("filesize", this.getFilesize());
		
		return json;
	}
	
	/**
	 * Get the name of this search result.
	 * @return String - The name of this search result.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this search result.
	 * @param name - The name of this search result.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the url of this search result.
	 * @return String - The url of this search result.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the url of this search result.
	 * @param url - The url of this search result.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Get the doctype of this search result.
	 * @return String - The doctype of this search result.
	 */
	public String getDoctype() {
		return doctype;
	}

	/**
	 * Set the doctype of this search result.
	 * @param doctype - The doctype of this search result.
	 * @param doctype - The doctype of this search result.
	 */
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	
	/**
	 * Get the timestamp of this search result.
	 * @return String - The timestamp of this search result.
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Set the timestamp of this search result.
	 * @param timestamp - The timestamp of this search result.
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Get the filesize of this search result.
	 * @return String - The filesize of this search result.
	 */
	public int getFilesize() {
		return filesize;
	}

	/**
	 * Set the filesize of this search result.
	 * @param filesize - The filesize of this search result.
	 */
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
}