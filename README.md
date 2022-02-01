# IAT-Server
Server-side counterpart to IAT Design

This is the server end of software that allows people to create Implicit Association Tests, a non-falsifiable test of biases and attitudes developed in the mid 1990s. Previously, such tests were not available for internet administration. 

<details open="open">
  <summary>A few notes</summary>
  <ol>
    <li><a href='#database-setup'>Database Setup</a></li>
  </ol>
</details>
  
  <h2 id='database-setup'>The Database</h2>
          <p>The <b>iat.sql</b> file in the root directory will create the database. Simply import it with <b>mysql -u root -p < iat.sql</b> It will setup the database iatserver_db and a user that has privlidges only on that database that's used by the software. You might wish to create a product user. That can be done wwith the following SQL statement.
 
  ``` sql
  insert into clients(product_key, activations_remaining, activations_consumed, contact_fname, 
    contact_lname, email, administrations, frozen, deleted, kill_filed, invalid_save_files, 
    isolate_users, downloads_consumed) values ('2L9JBMR74EYKHJ7RKWPE', 0, 0, 'Nikki', 'Lissome', 
    'nikki@bix.blue', 0, 0, 0, 0, 0, 0, 0);</p>
```
