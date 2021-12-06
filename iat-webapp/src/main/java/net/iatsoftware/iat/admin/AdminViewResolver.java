/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;

public class AdminViewResolver extends AbstractCachingViewResolver implements Ordered {
    private IATRepositoryManager iatRepositoryManager = null;
    private int order;
    
    public AdminViewResolver() {
        super();
        order = 1;
    }

    public void setRepositoryManager(IATRepositoryManager mgr) {
        this.iatRepositoryManager = mgr;
    }
    
    @Override
    public View resolveViewName(String viewName, Locale locale) {
    	return loadView(viewName, locale);
    }
    
    @Override
    protected View loadView(String viewName, Locale locale) {
        AdminView view = new AdminView();
        Pattern patt = Pattern.compile("Admin/([1-9][0-9]+)");
        Matcher m = patt.matcher(viewName);
        if (!m.matches()) {
            return null;
        }
        String html = this.iatRepositoryManager.getTestSegmentHtml(Long.parseLong(m.group(1)));
        view.addStaticAttribute("html", html);
        return view;
    }
    
    @Override
    public int getOrder() {
        return this.order;
    }
    
    public void setOrder(int n) {
        this.order = n;
    }
}
