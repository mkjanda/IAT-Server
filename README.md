![images](https://user-images.githubusercontent.com/35156960/151936166-f8106cc5-3171-490d-b107-d05cb44fcaa9.gif)

# IAT-Server
Server-side counterpart to IAT Design

This is the server end of software that allows people to create Implicit Association Tests, a non-falsifiable test of biases and attitudes developed in the mid 1990s. Previously, such tests were not available for internet administration. 

<details open="open">
  <summary>A few notes</summary>
  <ol>
    <li><a href='#database-setup'>Database Setup</a></li>
    <li><a href="#build">Building the software</a></li>
    <li><a href="#running-it">Running</a></li>
    <li><a href="#setting-it-up-with-nginx">Setting it up with Nginx</a></li>
	  <li><a href="#setting-it-up-as-a-service">Setting it up to run as a service</a></li>
    <li><a href="#working-version">Obtaining a Working Version</a></li>
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

 This is to accompany a user you can create for the test design software without registering it. In <b>%USER%\AppData\Local\IATSoftware</b> directory, create the following file:

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
  
  <p>There's not to much to be said. You'll need Apache Maven which you can downloaod here: **https://maven.apache.org/download.cgi**. If you want to set everything up inside Visual Studio Code, the project file is above. Otherwise, navigate to the root folder and type <b>mvn clean install</b>. Provided you have mvn on your path, that's it. Oh, Maven doesn't install, it just unzips. If I remember correctly, the file <b>mvn.cmd</b> has to be renamed **mvn.exe**.</p>
    <p>You likely have no reason to care, but <b>NodeJS</b> will be installed during the build process. It was necessary to use a node module to compile the SCSS, a technology I can't recomment enough. It makes CSS at least sane. It reminds me of Javascript 10 years ago. I know there are packages that allow you to dynamically create CSS with Javascript these days but my point of view is that <b>Freemarker</b> has been around for a while and no one's actually used it for that.</p>

  <h2 id='running-it'>Running the Thing</h2>
  
  <p>You'll find the file <b>iat-webapp-1.0.1.jar</b> in the <b>iat-webapp/target</b> directory. It's made with <b>Spring Boot</b> so everything's packaged there. It presumes a few things that likely won't be the case. First, that the directory <b>/var/log/iat</b> exists for keeping log files, that you have a user for sending automated emails with credentials:
    
``` properties
# change this
mail.server.host=your-host

#change if necessary
mail.server.port=465

mail.server.protocol=smtp

#change these 
mail.server.username=your-user
mail.server.password=your-password 

mail.server.user-personal=IAT Software

#may leave. sends to localhost so dkim signatures are not an issue
mail.server.user-address=iatsoftware@iatsoftware.net

mail.images.logo-classpath-location=classpath:email/images/logo.png
mail.images.header-classpath-location=classpath:email/images/header.png
```
	  
   <p>Further, it presumes an SMTP relay running on both <b>127.0.0.1:25</b> and <b>127.0.0.1:465</b>.</p>
  <p>None of this should cause the software to abort. It runs on port 8081, which is modifiable in <b>iat-webapp/src/main/resources/application.properties</b>. Start it by executing it. Double-click it. Oh, you will need Java installed on your machine. Windows 10 ships Java. Vista, 7, and 8 do not include it. You can find it here: https://www.java.com/en/download/manual.jsp</p>
	
<h2 id='setting-it-up-with-nginx'>Setting it up with Nginx</h2>

	If you're setting it up for your own computer, here's the <b>nginx.conf</b> I use to proxy back to virtual box on 192.158.56.101:
	
	
``` nginx

worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

events {
    worker_connections  1024;
}

http {
	include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    server {
       listen  81;
	client_max_body_size 50m;
       server_name localhost;
       location /bixblue {

	server {
        listen       80;
        server_name  localhost;
		client_max_body_size 50m;

		location = /IAT/DataTransaction {
        	if ($is_args = ?) {
            	return 405;
        	}
        	if ($request_method = POST) {
            	return 405;
        	}
        	proxy_pass http://192.168.56.101;
        	proxy_set_header Host $host;
        	proxy_set_header X-Real-IP $remote_addr;
        	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        	proxy_http_version 1.1;
        	proxy_set_header Upgrade $http_upgrade;
        	proxy_set_header Connection "upgrade";
    	}

    	location / {
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			proxy_set_header Host $host;
            proxy_redirect http://192.168.56.105 http://127.0.0.1;
			proxy_pass http://192.168.56.105:80;
    	}
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
	        root   html;
        }
    }
}
	
```
	
	This is the nginx .conf file I use on the server. It's been modified to run on non-HTTPS connections and errors might have been introduced in the process.
	
	
	
 ``` nginx
map $http_origin $origin {
	default $host;
	~^http(s)?://((.+\.)+.+?)/$ $2;
}

#server {
#	listen 80;
#	server_name iatsoftware.net www.iatsoftware.net;
##	return 301 https://$host$request_uri;
#}

server {
#	listen 443 ssl;
#	ssl on;
#	ssl_certificate /etc/pki/tls/certs/iatsoftware.net-bundle-and-crt;
#	ssl_certificate_key /etc/pki/tls/private/iatsoftware.net.key;	
    listen 80;
	server_name YOUR_HOST localhost 127.0.0.1;
	root /var/www/iat;
	client_max_body_size 50M;

	access_log /var/log/nginx/iatsoftware.net-access.log;
	error_log /var/log/nginx/iatsoftware.net-error.log;

	location ~* ^/IAT/([1-9][0-9]*)/([a-zA-Z0-9\-_]+)/([A-Za-z0-9\-_]+\.(png|jpg|js|jpeg))$ {
		add_header Cache-Control: no-store;	
		rewrite ^/IAT/(.*) $1  break;
		proxy_set_header origin $origin;
		proxy_set_header X-Forwarded-Host $host;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $server_protocol;
		proxy_redirect http://127.0.0.1:8081/Admin/TestResource/ https://$host/IAT/;
		proxy_pass http://127.0.0.1:8081/Admin/TestResource/$1/$2/$3;
	}

	location ~* ^/IAT/itemslides/.+\.slides$ {
		root /var/www/iat/itemslides;
		rewrite /IAT/itemslides(.*) $1 break;
	}

	location  /IAT {
		add_header Cache-Control: no-store;
		rewrite ^/IAT /Admin break;
		proxy_set_header origin $origin;
		proxy_set_header X-Forwarded-Host $host;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $server_protocol;
		proxy_redirect http://127.0.0.1/Admin $scheme://$host/IAT;
		proxy_pass http://127.0.0.1:8081;
	}

	location /IAT/ {
		add_header Cache-Control: no-store;
		rewrite ^/IAT/(.+) /$1 break;
		proxy_set_header origin $origin;
		proxy_set_header X-Forwarded-Host $host;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $server_protocol;
		proxy_redirect http://127.0.0.1:8081/(.+) $scheme://$host/IAT/$1;
		proxy_pass http://127.0.0.1:8081;
	}

	location = /IAT/OAuth/RequestAuth {
		proxy_redirect http://127.0.0.1:8081 $scheme://$host;
		proxy_set_header Host 127.0.0.1;
		proxy_pass http://127.0.0.1:8081;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header Access-Control-Allow-Origin *;
		add_header Access-Control-Allow-Origin: *;
	}	

	location / {
		proxy_redirect http://127.0.0.1:8082 $scheme://127.0.0.1;
		proxy_set_header Host $host;
		proxy_pass http://127.0.0.1:8082;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	}

    location = /IAT/DataTransaction {
        if ($is_args = ?) {
            return 405;
        }
        if ($request_method = POST) {
            return 405;
        }
		
        proxy_pass http://127.0.0.1:8081;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    } 
}
```

	<h2 id="setting-it-up-to-run-as-a-service<>Setting It Up to Run as a Service/h2>
			<p>Here are instructions for Ubutu. Navigate to <b>/lib/systemd/system</b> and create the <b>iat-webapp.service</b> file and copy the following into it, changing your java path and the directory you put the app in.</h2>
			
			
			
``` service
			
[Unit]
Description=The IAT Administration and Manager
After=syslog.target network.target
Wants=mariadb.service postfix.service

[Service]
User=iat
ExecStart=/usr/lib/jvm/default-java/bin/java -jar /var/www/iat/iat-webapp
SuccessExitStart=143

[Install]
WantedBy=multi-user.target
			
```
			
			
			
From there it's a matter of the following typed at the command line.
			
			
			

``` bash
systemctl dameon-reload
systemctl enable iat-webapp.servoce
systemctl start iat-webapp.service
```
      
      
  <h2 id="working-version">Obtaining a Working Version</h2>
  <p>Just visit https://iatsoftware.net and download it. There's an email verification step because I'm allowing random members of the general public to upload images to my server, but it only takes a second and I promise you'll never hear from me.</p>
