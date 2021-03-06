package se.starbox.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.starbox.models.SearchModel;
import se.starbox.models.SettingsModel;
import se.starbox.models.User;
import se.starbox.models.UserModel;
import se.starbox.util.SearchResult;

/**
 * Servlet implementation class SearchController
 * 
 * @author Kim Nilsson
 */
@WebServlet(
	description = "Handles search requests", 
	urlPatterns = { "/search/", "/search" }
)
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 132158L;
	private static SearchModel sm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchController() {
		super();

		if (sm == null)
			sm = new SearchModel();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Initializes background processes if necessary
		new InitBackgroundProcessesServlet().doGet(request, response);
		
		String query = request.getParameter("query");
		String params = "";
		
		// If query is empty, return HTML view.
		if (query == null) {
			List<User> acceptedUsers = UserModel.getWhitelistStatic();
			SettingsModel sm = new SettingsModel();
			System.out.println("-----Rendering HTML-----");
			RequestDispatcher view = request.getRequestDispatcher("/search.jsp");
			request.setAttribute("query", query);
			request.setAttribute("params", params);
			request.setAttribute("USERS_ACCEPTED", acceptedUsers);
			request.setAttribute("me", sm.getDisplayName());
			view.forward(request, response);
		} else {
			// Else, return JSON data from SearchModel.
			// query="seanbanan"
			// params="filetype:exe;minfilesize:20;maxfilesize:10"
			// Parse out params and remove them from query.
			System.out.println("-----Rendering JSON-----");
			
			Pattern pFileType = Pattern.compile("filetype[:=][,a-z0-9]*");
			Matcher mFileType = pFileType.matcher(query);
			query = query.replaceAll("filetype[:=][,a-z0-9]*", "");
			Pattern pMinFileSize = Pattern.compile("minfilesize[:=][0-9]*");
			Matcher mMinFileSize = pMinFileSize.matcher(query);
			query = query.replaceAll("minfilesize[:=][0-9]*", "");
			Pattern pMaxFileSize = Pattern.compile("maxfilesize[:=][0-9]*");
			Matcher mMaxFileSize = pMaxFileSize.matcher(query);
			query = query.replaceAll("maxfilesize[:=][0-9]*", "");
			
			while (mFileType.find()) {
				params += mFileType.group() + ";";
			}

			while (mMinFileSize.find()) {
				params += mMinFileSize.group() + ";";
			}

			while (mMaxFileSize.find()) {
				params += mMaxFileSize.group() + ";";
			}

			// Trim off trailing ;
			if (params.endsWith(";"))
				params = params.substring(0, params.length() - 1);
			
			// If search query isn't null someone is trying to search.
			// Fetch a list of SearchResult from the SearchModel and put them
			// in the request.
			if (query != "" || query != null) {
				LinkedList<SearchResult> searchResults = null;
				Iterator<SearchResult> it = null;
				searchResults = sm.query(query, params);
				boolean resultNotEmpty = false;
				
				// Iterate through the results and print the JSON representation of the results.
				if (searchResults != null) {
					it = searchResults.iterator();
					resultNotEmpty = true;
				}
			
				// Begin JSON array.
				response.setContentType("application/json");
				response.getWriter().println("[");
			
				if (resultNotEmpty) {
					while(it.hasNext()) {
						SearchResult res = (SearchResult) it.next();
						response.getWriter().println(res.toJSON().toJSONString());
						
						// If there's more. Print a comma so the array will be correctly formatted.
						if (it.hasNext())
							response.getWriter().println(",");
					}
				}
				
				// End JSON array.
				response.getWriter().print("]");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
