# IAT-Server
Server-side counterpart to IAT Design

This is the server end of software that allows people to create Implicit Association Tests, a non-falsifiable test of biases and attitudes developed in the mid 1990s. Previously, such tests were not available for internet administration. 

<details open="open">
  <summary>A few notes</summary>
  <ol>
    <li><a href='#database-setup'>Database Setup</a></li>
    <li><a href="#build">Building the software</a></li>
    <li><a href="#running-it">Running</a></li>
  </ol>
</details>
  
  <h2 id='database-setup'>The Database</h2>
          <p>The <b>iat.sql</b> file in the root directory will create the database. Simply import it with <b>mysql -u root -p < iat.sql</b> It will setup the database iatserver_db and a user that has privlidges only on that database that's used by the software. You might wish to create a product user. That can be done wwith the following SQL statement.
 
  ```sql
  insert into clients(product_key, activations_remaining, activations_consumed, contact_fname, 
    contact_lname, email, administrations, frozen, deleted, kill_filed, invalid_save_files, 
    isolate_users, downloads_consumed) values ('2L9JBMR74EYKHJ7RKWPE', 0, 0, 'Nikki', 'Lissome', 
    'nikki@bix.blue', 0, 0, 0, 0, 0, 0, 0);
```

 <p>This is to accompany a user you can create for the test design software without registering it. In <b>%USER%\AppData\Local\IATSoftware</b> directory, create the following file:</p>

<h3>IATDesign.xml</h3>

```xml
<?xml version="1.0" encoding="utf-8"?>
<IATDesign>
  <Version>1.1.1.43</Version>
  <Version_1_1_confirmed>True</Version_1_1_confirmed>
  <IATActivationKey>laH8pGseVWi++RPTwjWHQxTrCGMBI6ciMwCIWfEWM7qzt9iszRk30wZYdiZqwYPy</IATActivationKey>
  <UserEMail>nikki@bix.blue</UserEMail>
  <IATProductCode>2L9JBMR74EYKHJ7RKWPE</IATProductCode>
  <ClientName>Ms Nikki Lissome</ClientName>
</IATDesign>
```
  
  <h2 id='build'>Building It</h2>
  
  <p>There's not to much to be said. You'll need Apache Maven. If you want to set everything up inside Visual Studio Code, the project file is above. Otherwise, navigate to the root folder and type <b>mvn clean install</b>. Provided you have mvn on your path, that's it. Oh, Maven doesn't install, it just unzips. If I remember correctly, the file <b>mvn.cmd</b> has to be renamed <b>mvn.exe</b>.</p>
    <p>You likely have no reason to care, but <b>NodeJS</b> will be installed during the build process. It was necessary to use a node module to compile the SCSS, a technology I can't recomment enough. It makes CSS at least sane. It reminds me of Javascript 10 years ago. I know there are packages that allow you to dynamically create CSS with Javascript these days but my point of view is that <b>Freemarker</b> has been around for a while and no one's actually used it for that.</p>

  <h2 id='running-it'>Running the Thing</h2>
  
  <p>You'll find the file <b>iat-webapp-1.0.1.jar</b> in the <b>iat-webapp/target</b> directory. It's made with <b>Spring Boot</b> so everything's packaged there. It presumes a few things that likely won't be the case. First, that the directory <b>/var/log/iat</b> exists for keeping log files, that you have a user for sending automated emails with credentials:
    
``` java
package net.iatsoftware.iat.services;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication("YourEmailUser", "Password");
    }
}
```
   <p>Further, it presumes an SMTP relay running on both <b>127.0.0.1:25</b> and <b>127.0.0.1:465</b>.</p>
  <p>None of this should cause the software to abort. It runs on port 8081, which is modifiable in <b>iat-webapp/src/main/resources/application.properties</b>. Start it by executing it. Double-click it.</p>
