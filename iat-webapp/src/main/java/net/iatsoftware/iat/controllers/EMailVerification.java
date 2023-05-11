/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

@Controller
@ClientControllerAnnotation
@RequestMapping(value = "/EMailVerification")
public class EMailVerification {

    @Inject
    IATRepositoryManager iatRepositoryManager;

    @RequestMapping(name = "", params = {"VerificationKey"}, method = RequestMethod.GET)
    public String doEMailVerification(Model model, @RequestParam("VerificationKey") String verificationKey) {
        User u = iatRepositoryManager.getUserByVerificationKey(verificationKey);
        if (u == null) {
            return "InvalidEmailVerificationCode";
        }
        if (!u.getVerificationKey().equals(verificationKey)) {
            return "InvalidEmailVerificationCode";
        }
        u.setEMailVerified(true);
        
        iatRepositoryManager.updateUser(u);
        String userName = u.getTitle() + " " + u.getFName() + " " + u.getLName();
        model.addAttribute("userName", userName);
        return "EmailVerificationSuccessful";
    }
}
