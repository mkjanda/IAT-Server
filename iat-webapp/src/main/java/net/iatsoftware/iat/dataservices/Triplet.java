/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author michael
 */
public class Triplet<A, B, C> {

    private final A a;
    private final B b;
    private final C c;

    public Triplet(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A getFirst() {
        return a;
    }

    public B getSecond() {
        return b;
    }

    public C getThird() {
        return c;
    }

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode() ^ c.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Triplet)) {
            return false;
        }
        Triplet<A, B, C> t = (Triplet<A, B, C>) o;
        return (t.a.equals(a) && t.b.equals(b) && t.c.equals(c));
    }
}
