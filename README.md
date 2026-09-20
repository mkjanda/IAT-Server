# IAT-Server

Server counterpart to **[IAT Design](https://github.com/mkjanda/IAT-Design-WPF)**.

Authors build a test in the WPF designer and deploy it here. The server stores the package, turns the configuration into the HTML/JavaScript that administers the test, accepts administrations, and hands encrypted results back to the designer.

Live host: [https://iatsoftware.net](https://iatsoftware.net)

This repository is the Java administration app (`/IAT`, WebSocket deploy/retrieve, product-key and verification forms). The public marketing pages on the same apex are a separate front end. Do not treat this tree as the product site.

## What it does

- **Activate a designer** — product key plus a verified email. Activation exists because authors upload images to the host.
- **Deploy a test** — designer uploads the package over a WebSocket session at `/IAT/DataTransaction`. Name collisions for that client are a hard stop (`IATExists`). There is no “deploy on top of an existing test and keep results.”
- **Administer** — optional survey, then the IAT. Pages and scripts are generated from the uploaded `ConfigFile` via XSLT (`IATPage`, `IATScript`, `SurveyPage`, `SurveyScript`).
- **Store results encrypted** — each administration is an AES-GCM payload. The AES key is RSA-wrapped with the test data-key. The designer’s results password reconstructs the decryptor (`GEncryptedRSAKey`) on the client. The activation email is not the results password. There is no results-password reset.
- **Retrieve** — the designer pulls the envelope, unwraps locally, and can export Excel from the plaintext. The server does not decrypt for the author.

Public sample administrations (same host, `ClientID=1`):

- [gender](https://iatsoftware.net/IAT?IATName=gender&ClientID=1)
- [supernatural](https://iatsoftware.net/IAT?IATName=supernatural&ClientID=1)

## Stack

| Piece | What is in this tree |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.1 (parent `iat-parent`) |
| Modules | `iat-webcontent` (static resources), `iat-webapp` (the runnable app) |
| Persistence | MariaDB / MySQL, schema dump `iat.sql`, database `iatserver_db` |
| Admin wire protocol | WebSocket, JAXB `Message` subclasses as document root (no envelope wrapper) |
| Test pages | XSLT in `iat-webapp/src/main/resources/XSLT` |
| Mail | Local SMTP inject to Postfix on `127.0.0.1:25` (verification and transactional mail) |
| Listen port | `8081` (`iat-webapp/src/main/resources/application.properties`) |
| Public path | `/IAT`, proxied by nginx |

Package root is `net.iatsoftware.iat`. Generated JAXB types come from `schema.xsd` via the CXF XJC plugin.

## Layout

```
iat.sql                         MariaDB dump — creates iatserver_db
pom.xml                         Parent POM
iat-webcontent/                 Static / packaged web assets
iat-webapp/
  pom.xml                       Spring Boot module (executable jar)
  src/main/java/net/iatsoftware/iat/
    admin/  communication/  config/  configfile/
    controllers/  dataservices/  deployment/  entities/
    events/  messaging/  repositories/  resultdata/
    services/  validation/
  src/main/resources/
    application.properties
    datasource.properties       JDBC URL / user — do not commit real passwords
    iat.webapp.properties       Host, file roots, admin version
    handshake.properties        Server handshake material
    schema.xsd                  Wire + config + results schema
    XSLT/                       Live administration transforms
    scripts/                    SurveyValidate.js and related
    thymeleaf/                  Verification and completion pages
    email/                      Transactional templates
```

On-disk roots expected by `iat.webapp.properties` on a typical host:

```
/var/www/iat/deployment
/var/www/iat/tests
/var/www/iat/itemslides
/var/www/iat/test-results
/var/www/iat/ClientSoftware
/var/log/iat                  iat.log, transactions.log, debug.log
```

Create those directories and give the service user write access before the first deploy.

## Requirements

- JDK 21
- Apache Maven 3.9+
- MariaDB 10.6+ (or current MySQL 8) on `localhost:3306`
- nginx in front of `127.0.0.1:8081` if you want the public `/IAT` URLs
- Postfix (or another MTA) accepting local inject on `127.0.0.1:25` for activation mail

Mail that never leaves the box is a product bug, not a designer bug. If verification links do not arrive, fix SMTP before touching the WPF client.

## Database

```bash
sudo mysql -u root -p < iat.sql
```

That creates `iatserver_db`. Create an application user with rights only on that database and put the credentials in `iat-webapp/src/main/resources/datasource.properties`. The checked-in file is a local template. **Do not publish production passwords.** Rotate anything that has ever lived in a zip, a workspace file, or a public gist.

Minimal `datasource.properties`:

```properties
datasource.jdbc-url=jdbc:mysql://localhost:3306/iatserver_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
datasource.username=iat
datasource.password=choose-a-local-secret
datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

To seed a local designer without the public registration flow, insert a `clients` row and point the designer’s `%LOCALAPPDATA%\IATSoftware\IATDesign.xml` at the same product key and email. Use values you generated, not samples copied from an old README.

The dump still contains a `users` table and OAuth-era columns on `tests`. The live activation and deploy path is **client-keyed**. Do not rebuild a multi-user-per-product-key model unless you intend to support it in the designer as well.

## Build

From the repository root:

```bash
mvn clean install
```

The runnable artifact is `iat-webapp/target/iat-webapp-*.jar` (Spring Boot, `executable=true`).

Parent POM version in `pom.xml` and the version the modules declare as `<parent>` must match. If the build dies on an unresolvable `iat-parent`, that mismatch is the first thing to fix — not the JDK.

XJC runs at `generate-sources` against `schema.xsd` and writes `net.iatsoftware.iat.generated`. After a schema change, a clean build is required before the Java side will see new types.

## Run

```bash
java -jar iat-webapp/target/iat-webapp-*.jar
```

The process listens on **8081**. It will start without a working MTA; activation and “resend confirmation” will not.

`iat.webapp.properties` is the host-specific file:

```properties
iat.webapp.host=www.iatsoftware.net
iat.webapp.path=/IAT
iat.webapp.iat-deployment=file:///var/www/iat/deployment
iat.webapp.iat-files=file:///var/www/iat/tests
iat.webapp.item-slide-directory=file:///var/www/iat/itemslides
iat.webapp.result-data=file:///var/www/iat/test-results
iat.webapp.test-resources-path=/var/www/iat/tests
```

Point `iat.webapp.host` at the name nginx serves. Test URLs are `/IAT?IATName={name}&ClientID={id}`.

### Mail

Production injects to local Postfix. Keep Java on localhost:

```
mail.smtp.host=127.0.0.1
mail.smtp.port=25
mail.smtp.auth=false
mail.smtp.starttls.enable=false
```

`127.0.0.0/8` belongs in Postfix `mynetworks`. If `smtpd` is chrooted, milters whose sockets live outside the chroot will kill the banner before `220`. Leave milters off until the sockets exist under the chroot and answer. A missing `220` presents as `jakarta.mail` EOF, not as a Java template bug.

### systemd (Ubuntu)

`/lib/systemd/system/iat-webapp.service`:

```ini
[Unit]
Description=IAT administration
After=network.target mariadb.service postfix.service
Wants=mariadb.service postfix.service

[Service]
User=iat
ExecStart=/usr/bin/java -jar /var/www/iat/iat-webapp.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl daemon-reload
sudo systemctl enable --now iat-webapp.service
```

Use the JDK that is actually on the host. Do not copy a Windows `java.exe` path into this unit.

## nginx

The browser talks to `/IAT`. Spring talks on `8081`. WebSocket upgrades must reach `/IAT/DataTransaction` and must not be forced through a POST-only location.

A working production-shaped server block (HTTP shown; terminate TLS the way the host already does):

```nginx
map $http_origin $origin {
    default $host;
    ~^http(s)?://((.+\.)+.+?)/$ $2;
}

server {
    listen 80;
    server_name iatsoftware.net www.iatsoftware.net;
    client_max_body_size 50M;
    root /var/www/iat;

    location = /IAT/DataTransaction {
        if ($is_args = ?) { return 405; }
        if ($request_method = POST) { return 405; }
        proxy_pass http://127.0.0.1:8081;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    location ~* ^/IAT/([1-9][0-9]*)/([a-zA-Z0-9\-_]+)/([A-Za-z0-9\-_]+\.(png|jpg|js|jpeg))$ {
        add_header Cache-Control no-store;
        rewrite ^/IAT/(.*) $1 break;
        proxy_set_header origin $origin;
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://127.0.0.1:8081/Admin/TestResource/$1/$2/$3;
    }

    location /IAT {
        add_header Cache-Control no-store;
        proxy_set_header origin $origin;
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass http://127.0.0.1:8081;
    }
}
```

The marketing site on `/` is not this jar. Do not `proxy_pass` the apex at `8081` unless you intend to take the public site down.

## Protocol notes (for anyone pairing this with the designer)

- Both sides send **message types as the XML document root**. There is no `GEnvelope` on the wire.
- Handshake is challenge/response around AES of a random string. Do not add a second “security” transaction on top of that.
- Deploy uploads the file manifest and resources, then the server generates administration pages. Trust the transaction result the designer already handles.
- Results: `GTestResults` = descriptor + `EncryptedResultSet` rows. Ciphertext is AES-GCM. `Cipher` is the AES key RSA-wrapped with the test data-key. `Tag` and `Nonce` travel unencrypted next to the ciphertext. Password → decryptor → unwrap each row on the client.
- Display items: resource `Id` is the shared PNG / `img{n}`; placement `Guid` is unique. Generated JS declares `DI{Guid}`. CSS keys `#IATDI{Guid}`. Combined keys reuse a resource id and **must** still emit every placement Guid.
- Error mark resource id is `1000`. ConfigFile stores the placement Guid, not the integer.
- Instruction screens are siblings of `BeginIATBlock`. They run before the block’s items.
- Survey response tokens in the live schema: `TrueFalse`, `Date`, `Likert`, `MultiSelect`, `BoundedText`, `BoundedNumber`, `FixedDigit`, `MultiChoice`, `RegEx`. `UniqueResponse` is not part of the product; do not port it back into `SurveyValidate.js`.

## What this repo is not

- Not the WPF designer. That is [IAT-Design-WPF](https://github.com/mkjanda/IAT-Design-WPF).
- Not the marketing site. Java still owns download / product-key / verify / forgotten-key forms. The public IA lives elsewhere on the host.
- Not a how-to for decrypting someone else’s results. If you do not have the test password, you do not have the data. That is the point.

## Working install

Use the hosted product: [https://iatsoftware.net](https://iatsoftware.net). Download the designer, complete email verification, deploy a test, take it from the URL the designer reports.

Local self-host is for development. Match Java, MariaDB, the file roots, nginx WebSocket upgrade, and SMTP before you decide the protocol is broken.
