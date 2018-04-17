package nl.sogyo.assessment.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.MongoQueryException;

import nl.sogyo.assessment.domain.QueryExec;
import nl.sogyo.assessment.domain.IQueryResult;

@Controller
public class AppController implements ErrorController {
	private final static String ERROR_PATH = "/error";
	private final static Logger LOG = LogManager.getLogger(AppController.class);
	
	@Autowired
	QueryExec dataNavigator;
	
	// mapping to the search.html page
	@RequestMapping(value = "/")
	public String index() {
		return "search";
	}
	
	// mapping for a query request
	@GetMapping(value = "/api/query")
	public ResponseEntity<IQueryResult> query(
			@RequestParam(value="searchvalue") String searchValue,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="pagesize", defaultValue="10") int pageSize
	) {
		IQueryResult queryResult = dataNavigator.executeQuery(searchValue, page, pageSize);
		return new ResponseEntity<>(queryResult, HttpStatus.OK);
	}
	
	// display custom error message when page not found
	@RequestMapping(value = ERROR_PATH)
	public ResponseEntity<?> errorPage(HttpServletRequest request) {
		String url = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString();
		return new ResponseEntity<>("ErrorPage<br/><br/>" + url + " not found.", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({MongoQueryException.class})
	public ResponseEntity<?> handleQueryException(HttpServletRequest req, Exception ex) {
		final String query = req.getParameter("searchvalue");
		final String page = req.getParameter("page");
		final String pageSize = req.getParameter("pagesize");
		String errMessage = "[Query: " + query + "][Page: " + page + "][Page size: " + pageSize + "] ";
		errMessage += ex.getMessage();
		LOG.error(errMessage);
		return new ResponseEntity<>("Invalid query", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
