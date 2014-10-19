package com.funnic.mvc.templates.freemarker.directive;

import freemarker.template.TemplateDirectiveModel;

/**
 * @author Per
 */
public interface HtmlDirective {

	TemplateDirectiveModel getRender();

	TemplateDirectiveModel getHref();
}
