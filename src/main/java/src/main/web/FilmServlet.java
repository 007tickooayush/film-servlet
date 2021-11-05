package src.main.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import src.main.dao.FilmDAO;
import src.main.model.Film;

/**
 * implementation class FilmServlet
 */
@WebServlet("/")
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private FilmDAO filmDAO;
    private Gson gson;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilmServlet() {
    	super();
    	filmDAO = new FilmDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath()+"\n"
//				+ "SERVER_NAME: "+request.getServerName()+"\n"
//				+"SERVER_PORT: "+request.getServerPort()+"\n"
//				+"REQUEST_METHOD: "+request.getMethod()+"\n"
//				+"QUERY_STRING: "+request.getQueryString()
//				);
		
		String action = request.getServletPath();
		PrintWriter out = response.getWriter();
		
//		HANDLING THE ACTION AND RESPONSE ACCORDING TO REQUEST (||GET||)  
		try {
			switch(action) {
			case "/home":
				showAllFilms(request, response);
				break;
			case "/onlyjson":
//				get entire DataSet in JSON format
				getAllFilmsJSON(request, response);
				break;
			case "/pagedjson":
//				get paged DataSet in JSON format
				getFilmsPaged(request, response);
				break;
			case "/add":
//				handle wrong URL action call (ADD) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/edit":
//				handle wrong URL action call (EDIT) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/delete":
//				handle wrong URL action call (DELETE) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/filmsset":
//				get the limited set of rows required 
				break;
			case "/film":
//				get a row entry by id
				break;
			case "/search":
//				get row by keyword(s) {title & description}
				getFilmsByKeyWords(request, response);
				break;
			default:
//				get all rows by default
				showAllFilms(request, response);
				break;
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		String action = request.getServletPath();
		PrintWriter out = response.getWriter();
		
//		HANDLING THE ACTION AND RESPONSE ACCORDING TO REQUEST (||POST||)  
		try {
			switch(action) {
			case "/add":
				addFilm(request, response);
				break;
			case "/edit":
//				handle wrong URL action call (EDIT) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/delete":
//				handle wrong URL action call (DELETE) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/filmsset":
//				get the limited set of rows required
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/film":
//				handle wrong URL action call (||GET||)
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/search":
//				handle wrong URL action call (||GET||) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			default:
//				get all rows by default
				showAllFilms(request, response);
				break;
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(req, resp);
		String action = request.getServletPath();
		PrintWriter out = response.getWriter();
		
//		HANDLING THE ACTION AND RESPONSE ACCORDING TO REQUEST (||PUT||)  
		try {
			switch(action) {
			case "/add":
//				handle wrong URL action call (ADD) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/edit":
//				perform action (EDIT) 
				editFilm(request, response);
				break;
			case "/delete":
//				handle wrong URL action call (DELETE) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/filmsset":
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/film":
//				handle wrong URL action call (||GET||)
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/search":
//				handle wrong URL action call (||GET||)
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			default:
//				get all rows by default
				showAllFilms(request, response);
				break;
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		String action = request.getServletPath();
		PrintWriter out = response.getWriter();
		
//		HANDLING THE ACTION AND RESPONSE ACCORDING TO REQUEST (||DELETE||)  
		try {
			switch(action) {
			case "/add":
//				handle wrong URL action call (ADD) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/edit":
//				handle wrong URL action call (EDIT) 
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/delete":
//				perform action (DELETE) 
				deleteFilm(request, response);
				break;
			case "/filmsset":
//				handle wrong URL action call (||GET||)
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/film":
//				handle wrong URL action call (||GET||)
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			case "/search":
//				handle wrong URL action call (||GET||)
				out.append("Cannot perform action via Request Method Type:").append(request.getMethod()+"\n");
				break;
			default:
//				get all rows by default
				showAllFilms(request, response);
				break;
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	GET [Film]s by params = [start,limit] ((JSON only))
	private void getFilmsPaged(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException, ServletException {
		int start = Integer.parseInt(req.getParameter("start"));
		if(start <10)
			start = 0;
		int limit = Integer.parseInt(req.getParameter("limit"));
		
		List<Film> filmsPage = filmDAO.getFilmsByLimitParams(start, limit);  	
		gson = new Gson();
		String json = gson.toJson(filmsPage);
		req.setAttribute("data", json);
		resp.setContentType("application/json");
		
		PrintWriter out = resp.getWriter();
		out.append("{\"data\":"+json);
//		get total number of rows
		int total = filmDAO.getTotal();
		
		out.append(",\"total\":"+total);
		out.append("}");
	}
//	GET ALL [Film]s ((JSON only))
	private void getAllFilmsJSON(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException, ServletException {
		List<Film> films = filmDAO.getAllFilms();
		
		gson = new GsonBuilder().create();
		
		String json = gson.toJson(films);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		
//		total number of entries
		req.setAttribute("total", films.size());
	}
	
//	GET ALL [Film]s 
	private void showAllFilms(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException, ServletException {
		List<Film> films = filmDAO.getAllFilms();
		
		gson = new GsonBuilder().create();
		
		String json = gson.toJson(films);

//		req.setAttribute("filmList", films);
		req.setAttribute("data", json);
		req.setAttribute("filmsList", films);
		
		req.getRequestDispatcher("welcome.jsp").forward(req, resp);
		
//		resp.setContentType("application/json");
//		resp.getWriter().write(json);

	}
	
//	SEARCH [Film]
	private void getFilmsByKeyWords(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
		String title=req.getParameter("title");
		String description=req.getParameter("description");
		
		gson = new Gson();
		List<Film> requiredFilms = filmDAO.getFilmsByKeyWords(title, description);
		
		String json = gson.toJson(requiredFilms);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);

	}
//	ADD [Film]
	private void addFilm(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException,SQLException, IOException {
		
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		Integer release_year = Integer.parseInt(req.getParameter("release_year"));
		int language_id = Integer.parseInt(req.getParameter("language_id"));
		int rental_duration = Integer.parseInt(req.getParameter("rental_duration"));
		double rental_rate = Double.parseDouble(req.getParameter("rental_rate"));
		Integer length = Integer.parseInt(req.getParameter("length"));
		double replacement_cost = Double.valueOf(req.getParameter("replacement_cost"));
		String director = req.getParameter("director");
		int special_features_index = Integer.parseInt(req.getParameter("special_features"));
		String rating= req.getParameter("rating");
		
		Film film = new Film(title, description, release_year, 
				language_id,rental_duration, rental_rate,
				length, replacement_cost, rating,director);
		
		film.setSpecial_featuresIndex(special_features_index);
		
		filmDAO.insertFilm(film);
		
		resp.sendRedirect("home");
	}
//	EDIT [Film]
	private void editFilm(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException,SQLException, IOException {
		
		Integer film_id = Integer.parseInt(req.getParameter("film_id"));
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		Integer release_year = Integer.parseInt(req.getParameter("release_year"));
		int language_id = Integer.parseInt(req.getParameter("language_id"));
		int rental_duration = Integer.parseInt(req.getParameter("rental_duration"));
		double rental_rate = Double.parseDouble(req.getParameter("rental_rate"));
		Integer length = Integer.parseInt(req.getParameter("length"));
		double replacement_cost = Double.valueOf(req.getParameter("replacement_cost"));
		String director = req.getParameter("director");
		String rating= req.getParameter("rating");
		int special_features_index = Integer.parseInt(req.getParameter("special_features"));
		
		Film existingFilm = new Film(film_id,title, description, release_year, 
				language_id,rental_duration, rental_rate,
				length, replacement_cost, rating,director);
		
		existingFilm.setSpecial_featuresIndex(special_features_index);
				
		filmDAO.updateFilm(existingFilm);
		
		gson = new Gson();
		resp.sendRedirect("search?title="+existingFilm.getTitle());

	}
//	DELETE [Film]
	private void deleteFilm(HttpServletRequest req,HttpServletResponse resp) throws ClassNotFoundException,SQLException, IOException {
		Integer film_id = Integer.parseInt(req.getParameter("film_id"));
		
		filmDAO.deleteFilm(film_id);
		
//		resp.sendRedirect("home");
	}
}





