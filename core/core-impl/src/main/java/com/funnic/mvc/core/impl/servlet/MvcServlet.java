package com.funnic.mvc.core.impl.servlet;

import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.api.exceptions.ActionNotFoundException;
import com.funnic.mvc.core.api.exceptions.ControllerNotFoundException;
import com.funnic.mvc.core.api.exceptions.RenderException;
import com.funnic.mvc.core.api.renderer.ControllerRenderer;
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
	private final ControllerRenderer controllerRenderer;

	public MvcServlet(final String contextPath, final ControllerRenderer controllerRenderer) {
		this.contextPath = contextPath;
		this.controllerRenderer = controllerRenderer;
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp, RequestType.POST);
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp, RequestType.GET);
	}

	private void doRequest(final HttpServletRequest req, final HttpServletResponse resp, final RequestType type) throws ServletException {
		ServletInfo.set(req, resp);
		final String requestURI = req.getRequestURI().replace(contextPath, "");
		final RequestInfo requestInfo = new RequestInfo(requestURI);
		try {
			final String controllerName = requestInfo.getControllerName();
			final String actionName = requestInfo.getActionName();

			controllerRenderer.render(controllerName, actionName, resp.getWriter());
		} catch (IOException e) {
			log.error("Could not invoke the controller: " + requestInfo.getControllerName(), e);
			throw new ServletException("Could not handle request", e);
		} catch (ActionNotFoundException e) {
			log.error("Could not invoke the controller: " + requestInfo.getControllerName(), e);
			throw new ServletException("Could not handle request", e);
		} catch (ControllerNotFoundException e) {
			log.error("Could not invoke the controller: " + requestInfo.getControllerName(), e);
			throw new ServletException("Could not handle request", e);
		} catch (RenderException e) {
			log.error("Could not invoke the controller: " + requestInfo.getControllerName(), e);
			throw new ServletException("Could not handle request", e);
		} finally {
			ServletInfo.clear();
		}
	}
}

