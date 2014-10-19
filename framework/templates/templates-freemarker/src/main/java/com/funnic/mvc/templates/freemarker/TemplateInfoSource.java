package com.funnic.mvc.templates.freemarker;

import com.funnic.mvc.core.api.view.ViewInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * @author Per
 */
public class TemplateInfoSource {
	private final ViewInfo info;
	private URLConnection conn;
	private InputStream inputStream;

	public TemplateInfoSource(final ViewInfo info) throws IOException {
		this.info = info;
		this.conn = info.getUrl().openConnection();
	}

	public InputStream getInputStream() throws IOException {
		inputStream = conn.getInputStream();
		return inputStream;
	}

	public void close() throws IOException {
		try {
			if (inputStream != null) {
				inputStream.close();
			} else {
				conn.getInputStream().close();
			}
		} finally {
			inputStream = null;
			conn = null;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TemplateInfoSource) {
			return info.equals(o);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return info.hashCode();
	}

	public long getLastModified() {
		return info.getBundle().getLastModified();
	}
}
