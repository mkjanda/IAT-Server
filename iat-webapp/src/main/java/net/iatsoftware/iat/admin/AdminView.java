/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author Michael Janda
 */
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.servlet.view.AbstractView;

import net.iatsoftware.iat.entities.IAT;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminView extends AbstractView {

    private static final Logger viewLogger = LogManager.getLogger();

    public AdminView() {
        setContentType("text/html");
    }

    @Override
    public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=utf-8");
            response.setHeader("Cache-Control", "no-store");
            IAT test = (IAT) model.get("Test");
            Long clientID = (Long) model.get("ClientID");
            Long testElemID = (Long)model.get("TestSegment");
            addCookie(response, "TestSegment", testElemID.toString());
            addCookie(response, "IATSESSIONID", (String)model.get("IATSESSIONID"));
            if (AdminVersion.compare(new AdminVersion(test.getVersion()), new AdminVersion("iat-1.0.5")) < 0) {
                addCookie(response, "CurrentIAT", test.getTestName());
                addCookie(response, "CurrentIATID", Long.toString(test.getId()));
                addCookie(response, "ClientID", Long.toString(clientID));
                addCookie(response, "ServletPath", "/IAT");
                if (model.containsKey("TesteeToken"))
                    addCookie(response, "TesteeToken", (String)model.get("TesteeToken"));
            } 
            if (model.containsKey("LastAdminPhase"))
                addCookie(response, "LastAdminPhase", (String)model.get("LastAdminPhase"));
            else
                addCookie(response, "LastAdminPhase", "false");
            if (model.containsKey("AdminPhase"))
                addCookie(response, "AdminPhase", ((Integer)model.get("AdminPhase")).toString());
            if (test.isAlternate()) {
                addCookie(response, "Alternate", "yes");
            } else {
                addCookie(response, "Alternate", "no");
            }
            PrintWriter pOut = response.getWriter();
            pOut.write((String)this.getStaticAttributes().get("html"));
            pOut.flush();
        } catch (Exception ex) {
            viewLogger.error("Error rendering html", ex);
        }
    }
    
    private void addCookie(HttpServletResponse resp, String name, String value) {
        Cookie c1 = new Cookie(name, value);
        resp.addCookie(c1);
    }
}
