package com.funnic.mvc.core.impl.servlet;

import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.impl.controllers.ControllerInfo;
import com.funnic.mvc.core.impl.controllers.ControllerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Per
 */
public class MvcServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(MvcServlet.class);

	private final String contextPath;
	private final ControllerRepository controllerRepository;

	public MvcServlet(final String contextPath, ControllerRepository controllerRepository) {
		this.contextPath = contextPath;
		this.controllerRepository = controllerRepository;
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp, RequestType.POST);
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp, RequestType.GET);
	}

	private void doRequest(final HttpServletRequest req, final HttpServletResponse resp, final RequestType type) {
		final String requestURI = req.getRequestURI().replace(contextPath, "");
		final RequestInfo requestInfo = new RequestInfo(requestURI);

		final ControllerInfo controllerInfo = controllerRepository.getController(requestInfo.getControllerPath());
		if (controllerInfo == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			log.debug("Could not find controller: " + requestInfo.getControllerPath());
			return;
		}

		try {
			controllerInfo.invoke(requestInfo, type, resp.getWriter());
		} catch (IOException e) {
			log.error("Could not invoke the controller: " + requestInfo.getControllerPath(), e);
		}
	}
}

