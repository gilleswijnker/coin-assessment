package nl.sogyo.assessment.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nl.sogyo.assessment.domain.DataNavigator;
import nl.sogyo.assessment.domain.IDataNavigator;

@Controller
public class AppController implements ErrorController {
	private final static String ERROR_PATH = "/error";
	
	@Autowired
	DataNavigator dataNavigator;
	
	@RequestMapping(value = "/")
	public String index() {
		return "search";
	}
	
	@GetMapping(value = "/api/query")
	public ResponseEntity<?> query(@RequestParam(value="searchvalue") String searchValue) {
		IDataNavigator queryResult = dataNavigator.executeQuery(searchValue);
		return new ResponseEntity<>(queryResult.toJson(), HttpStatus.OK);
	}
	
	@RequestMapping(value = ERROR_PATH)
	public ResponseEntity<?> errorPage(HttpServletRequest request) {
		String url = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString();
		return new ResponseEntity<>("ErrorPage<br/><br/>" + url + " not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
