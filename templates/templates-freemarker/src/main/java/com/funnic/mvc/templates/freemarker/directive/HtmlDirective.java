package com.funnic.mvc.templates.freemarker.directive;

import freemarker.template.TemplateDirectiveModel;

/**
 * @author Per
 */
public class HtmlDirective {
	private final TemplateDirectiveModel render;
	private final TemplateDirectiveModel href;

	public HtmlDirective(final TemplateDirectiveModel render, final TemplateDirectiveModel href) {
		this.render = render;
		this.href = href;
	}

	public TemplateDirectiveModel getRender() {
		return render;
	}

	public TemplateDirectiveModel getHref() {
		return href;
	}
}
