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

import net.iatsoftware.iat.config.IatConfigurationProperties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/AbortTest")
public class TestAbortController {

    @Inject
    IatConfigurationProperties serverConfiguration;
    @Inject
    IATSessionManager sessionManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView onTestAbort(@RequestParam(value = "IATName", required = true) String IATName, @RequestParam(value = "ClientID", required = true) Long clientID,
            @RequestParam(value = "token", required = false, defaultValue = "") String token, @RequestParam(name="IATSESSIONID") String sessID,
            HttpServletRequest request, HttpServletResponse response) {
        
        IATSession sess = this.sessionManager.getSession(sessID);
        ModelAndView mv = new ModelAndView("AbortTest");
        mv.addObject("IATName", IATName);
        mv.addObject("ClientID", clientID.toString());
        mv.addObject("AppPath", serverConfiguration.getPath());
        if (!token.isEmpty()) {
            mv.addObject("TesteeToken", token);
        }
        mv.addObject("HTTP_REFERER", sess.getAttribute(SessionProperties.HTTP_REFERER));
        mv.addObject("CorruptAdministration", Boolean.TRUE);
        return mv;
    }
}
