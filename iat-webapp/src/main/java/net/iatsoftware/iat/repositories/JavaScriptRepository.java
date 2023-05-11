package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.JavaScript;

public interface JavaScriptRepository extends GenericRepository<Long, JavaScript> {
    String getScript(IAT test, int index);
}
