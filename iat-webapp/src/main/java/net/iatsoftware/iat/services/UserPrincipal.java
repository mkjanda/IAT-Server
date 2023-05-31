/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author Michael Janda
 */
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.Principal;

public class UserPrincipal implements Principal, Cloneable, Serializable
{
    private final String username;

    public UserPrincipal(String username)
    {
        this.username = username;
    }

    @Override
    public String getName()
    {
        return this.username;
    }

    @Override
    public int hashCode()
    {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof UserPrincipal &&
                ((UserPrincipal)other).username.equals(this.username);
    }

    @Override
    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    protected UserPrincipal clone()
    {
        try {
            return (UserPrincipal)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // not possible
        }
    }

    @Override
    public String toString()
    {
        return this.username;
    }

    public static Principal getPrincipal(HttpSession session)
    {
        return session == null ? null :
                (Principal)session.getAttribute("net.iatsoftware.iatserver.principal");
    }

    public static void setPrincipal(HttpSession session, Principal principal)
    {
        session.setAttribute("net.iatsoftware.iatserver.principal", principal);
    }
}
