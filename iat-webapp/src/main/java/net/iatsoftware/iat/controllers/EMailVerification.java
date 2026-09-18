/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

import net.iatsoftware.iat.communication.TransactionContext;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import java.time.Duration;

import jakarta.inject.Inject;

@Controller
@ClientControllerAnnotation
@RequestMapping(value = "/EMailVerification")
public class EMailVerification {
    static public final Cache<String, TransactionContext> contextCache = Caffeine.newBuilder()
        .maximumSize(10_000).expireAfterWrite(Duration.ofMinutes(10)).build();

    @Inject
    ClientRepositoryManager clientRepositoryManager;

    @RequestMapping(name = "", params = {"VerificationKey"}, method = RequestMethod.GET)
    public String doEMailVerification(Model model, @RequestParam("VerificationKey") String productKey) {
        var ctx = contextCache.getIfPresent(productKey);
        if (ctx == null) {
            return "InvalidEmailVerificationCode";
        }
        var client = ctx.client();
        client.setEmailVerified(true);
        ctx.clientRepositoryManager().addClient(client);
        ctx.reply().send(new TransactionRequest(TransactionType.EMAIL_VERIFIED));
        contextCache.invalidate(productKey);
        model.addAttribute("userName", client.getName());
        return "EmailVerificationSuccessful";
    }
}
