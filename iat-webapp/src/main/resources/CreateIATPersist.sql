create database iatserver_db;
use iatserver_db;
create table clients (ClientID int(64) unsigned not null auto_increment primary key, product_key char(20) not null, activations_remaining int(16) unsigned,
    activations_consumed int(16) not null, contact_fname varchar(80) not null, contact_lname varchar(80) not null, organization varchar(255) not null,
    organization_id varchar(255), email varchar(255) not null, phone varchar(25) not null, province varchar(80) not null, streetaddress1 varchar(255) not null, streetaddress2 varchar(255),
    city varchar(80) not null, postalcode varchar(25) not null, country varchar(80) not null, registration_date datetime,
    disk_alottment_mb int(16) unsigned, num_iats_alotted int(16) unsigned, administrations int(32) unsigned not null,
    administrations_remaining int(32) unsigned, frozen bit not null, deleted bit not null, kill_filed bit not null, 
    invalid_save_files int(16) not null, isolate_users bit not null, product_use text, 
    download_password char(20) not null, downloads_remaining int(10) not null, downloads_consumed int(10) not null, oauth_access_expires datetime, 
    constraint unique_email unique(email), index clients_product_keys (product_key)) engine=InnoDB;
create table users (UserID int(64) unsigned not null auto_increment primary key, ClientID int(64) unsigned not null, user_num int(16) unsigned not null, 
    title varchar(25), fname varchar(50) not null, lname varchar(50) not null, email varchar(255) not null, activation_date datetime, 
    verification_code varchar(64) not null, activation_key varchar(64), email_verified bit not null, 
    constraint users_client foreign key (ClientID) references clients(ClientID) on delete cascade, 
    constraint client_user unique index (ClientID, user_num)) engine=InnoDB;
create table deployment_sessions (SessionID int(64) unsigned not null auto_increment primary key, deployment_start datetime not null, ClientID int(64) unsigned not null,
    UserID int(64) unsigned not null, TestID int(64) unsigned, deployment_upload_key varchar(255), item_slide_upload_key varchar(255), 
    reconnection_key varchar(255), web_socket_id varchar(255) not null, constraint sessions_client foreign key (ClientID) references clients(ClientID) on delete cascade,
    constraint sessions_user foreign key (UserID) references users(UserID) on delete cascade) engine=InnoDB;
create table tests (TestID int(64) unsigned not null auto_increment primary key, client_id int(64) unsigned not null, test_name varchar(255) not null,
    user_id int(64) unsigned not null, administration_type enum('POOLED', 'INDIVIDUAL') not null, administrations_remaining int(20) unsigned, 
    num_administrations int(20) unsigned not null, test_size_kb int(16), test_type ENUM('None', 'RandomOrder', 'SetNumberOfPresentations') not null, 
    upload_timestamp datetime not null, last_data_retrieval datetime, version varchar(15) not null, result_format int(16) unsigned not null, alternate bit, 
    alternated bit not null, aes_code text, deployment_descriptor blob, redirect_on_complete varchar(255), num_elems int(16), item_slide_download_key varchar(255), 
    url varchar(255) not null, oauth_client_redirect varchar(512), oauth_client_id varchar(128), oauth_client_secret varchar(128), token_type ENUM('NONE', 'VALUE', 'HEX', 'BASE64', 'BASE64_UTF8') not null, 
    token_name varchar(80) not null, result_retrieval_token binary(64), result_retrieval_token_age datetime, redeployed bit not null,
    constraint tests_client foreign key (client_id) references clients(ClientID) on delete cascade, 
    constraint tests_user foreign key (user_id) references users(UserID) on delete cascade, constraint tests_deployer foreign key (TestID) references deployment_sessions(TestID) on delete cascade,
    index tests (client_id, test_name)) engine=InnoDB;
alter table deployment_sessions add constraint dsessions_test foreign key (TestID) references tests(TestID) on delete cascade;
create table test_resources(ResourceID int(64) unsigned not null primary key, TestID int(64) unsigned not null, resource_name varchar(255) not null,
    resource mediumblob not null, constraint foreign key (TestID) references tests(TestID) on delete cascade) engine=InnoDB;
create table admin_timers(timer_id int(64) unsigned not null auto_increment primary key, last_timer_refresh datetime not null, TestID int(64) unsigned not null,
    iatsessionid varchar(255) not null, complete bit not null, testee_token varbinary(1024), constraint timers_test foreign key (TestID) references tests(TestID) on delete cascade) engine=InnoDB;
create table results (ResultID int(64) unsigned not null auto_increment primary key, TestID int(64) unsigned not null, 
    admin_time datetime not null, results mediumblob not null, toc text not null, testee_token varbinary(1024), 
    constraint results_test foreign key (TestID) references tests(TestID) on delete cascade, index results_tests_id (TestID)) engine=InnoDB;
create table test_segments (TestSegmentID int(64) unsigned not null auto_increment primary key, TestID int(64) unsigned not null, elem_name varchar(255) not null, 
    jskeys_xml text not null, html MEDIUMTEXT character set utf8mb8 collate utf8mb8_unicode_ci not null, alternation_priority int(16) not null, initial_pos int(16) not null, 
    num_alternations int(16) not null, iat bit not null) constraint segments_test foreign key (TestID) references tests(TestID) on delete cascade, 
    index segments_test_id (TestID)) engine=InnoDB;
create table dynamic_specifiers (SpecifierID int(64) unsigned not null auto_increment primary key, TestID int(64) unsigned not null, 
    TestSegmentID int(64) unsigned not null, test_specifier_id int(16) unsigned not null, item_num int(16) unsigned not null, 
    SpecifierType ENUM('Mask', 'TrueFalse', 'Selection', 'Range'), 
    constraint specifiers_test foreign key (TestID) references tests (TestID) on delete cascade, 
    constraint specifiers_test_segment foreign key (TestSegmentID) references test_segments(TestSegmentID) on delete cascade,
    index specifiers_tests_id (TestID)) engine=InnoDB;
create table mask_specifiers (SpecifierID int(64) unsigned not null primary key, mask varchar(255) not null, 
    constraint mask_spec_fk foreign key (SpecifierID) references dynamic_specifiers(SpecifierID) on delete cascade) engine=InnoDB;
create table selection_specifiers (SpecifierID int(64) unsigned not null primary key, key_specifiers varchar(2500) not null,
    constraint selection_spec_fk foreign key (SpecifierID) references dynamic_specifiers(SpecifierID) on delete cascade) engine=InnoDB;
create table range_specifiers (SpecifierID int(64) unsigned not null primary key, cutoff int(16) unsigned not null, 
    reverse_scored bit not null, cutoff_excludes bit not null, 
    constraint range_spec_fk foreign key (SpecifierID) references dynamic_specifiers(SpecifierID) on delete cascade) 
    engine=InnoDB;
create table true_false_specifiers (SpecifierID int(64) unsigned not null primary key, 
    constraint tf_spec_fk foreign key (SpecifierID) references dynamic_specifiers(SpecifierID) on delete cascade) engine=InnoDB;
create table specifier_values(SpecifierValueID int(64) unsigned not null auto_increment primary key, AdminID int(64) unsigned not null,
    specifier_value varchar(255) not null, test_specifier_id int(16) not null, 
    constraint specifier_values_admin foreign key (AdminID) references admin_timers(timer_id) on delete cascade,
    index specifier_values_admin_ndx (AdminID)) engine=InnoDB;
create table test_encryption_keys (KeyID int(64) unsigned auto_increment not null primary key, TestID int(64) unsigned,
    keytype ENUM('DATA', 'ADMIN', 'NONE') not null, modulus varbinary(255) not null, exponent varbinary(255) not null, encrypted_key varbinary(2500) not null, 
    constraint encryption_keys_test foreign key (TestID) references tests(TestID) on delete cascade, index encryption_keys_test_id (TestID)) engine=InnoDB;
create table test_code (TestCodeID int(64) unsigned not null auto_increment primary key, TestSegmentID int(64) unsigned not null, 
    entity_name varchar(255) not null, andx int(16) unsigned not null, bndx int(16) unsigned not null, cl int(16) unsigned not null, 
    code_type ENUM('CONSTRUCTOR', 'DECLARATION', 'CODE', 'GLOBAL_DECLARATION', 'GLOBAL_CODE', 'TOC') not null, code1 text not null, code2 text not null, code3 text not null, 
    code4 text not null, ordinal int(16) not null, constraint codes_segment foreign key (TestSegmentID) references test_segments(TestSegmentID) on delete cascade,
    index codes_index (TestSegmentID)) engine=InnoDB;
create table test_result_fragments (ResultFragmentID int(64) unsigned not null auto_increment primary key, TestAdminID int(64) unsigned not null, 
    ordinal int(16) not null, result_fragment blob not null, cipher varbinary(256) not null, iv varbinary(256) not null, 
    constraint fragments_result foreign key (TestAdminID) references admin_timers(timer_id) on delete cascade, index admin_id (TestAdminID)) engine=InnoDB;
create table manifest_files (ManifestFileID int(64) unsigned not null auto_increment primary key, SessionID int(64) unsigned not null, file_name varchar(80) not null, 
    file_size int(64) not null, file_path varchar(2500) not null, transmission_order int(32) unsigned not null, file_type ENUM('ITEM_SLIDES', 'DEPLOYMENT_FILES') not null,
    constraint files_session foreign key (SessionID) references deployment_sessions(SessionID) on delete cascade, index files_deployment_session (SessionID)) engine=InnoDB;
create table deployment_packets (DeploymentPacketID int(64) unsigned not null auto_increment primary key, DeploymentSessionID int(64) unsigned not null, 
    packet_data MEDIUMBLOB not null, upload_ordinal int(32) unsigned not null, packet_type ENUM('DEPLOYMENT_DATA', 'ITEM_SLIDE') not null,
    constraint packets_deployment_session foreign key (DeploymentSessionID) references deployment_sessions(SessionID) on delete cascade, 
    index tests_deployment (DeploymentSessionID)) engine=InnoDB;
create table unique_response_items (UniqueResponseItemID int(64) unsigned not null auto_increment primary key, TestID int(64) unsigned not null, 
    additive bit not null, survey_name varchar(255) not null, item_num int(16) not null, 
    constraint unique_response_items_test foreign key (TestID) references tests(TestID) on delete cascade, 
    index questionnaire (TestID, survey_name)) engine=InnoDB;
create table unique_responses (UniqueResponseID int(64) unsigned not null auto_increment primary key, ItemID int(64) unsigned not null, val varchar(255) not null,
    taken bit not null, consumed bit not null, AdminID int(64) unsigned, 
    constraint unique_responses_item foreign key (ItemID) references unique_response_items(UniqueResponseItemID) on delete cascade,
    constraint unique_responses_admin foreign key (AdminID) references admin_timers(timer_id) on delete cascade, index items_id_index (ItemID)) engine=InnoDB;
create table requests (request_id int(64) unsigned not null auto_increment primary key, fname varchar(80) not null, lname varchar(80) not null,
    organization varchar(255), email varchar(255) not null, phone varchar(25) not null, address1 varchar(255) not null, address2 varchar(255),
    city varchar(255) not null, province varchar(255) not null, postalcode varchar(25) not null, country varchar(80) not null,
    product_use text not null, registration_timestamp datetime not null, delete_flag bit not null) engine=InnoDB;
create table item_slides (SlideID int(64) unsigned not null auto_increment primary key, TestID int(64) unsigned not null, file_name varchar(255) not null, slide_num int(16) not null,
    slide_size int(32) not null, image_data mediumblob not null, constraint slides_test foreign key (TestID) references tests(TestID) on delete cascade, 
    index items_test_ndx (TestID)) engine=InnoDB;
create table rsa_key_data (KeyID int(64) unsigned not null auto_increment primary key, priv_key varbinary(64) not null, pub_key varbinary(64) not null,
    modulus varbinary(128) not null) engine=InnoDB;
create table test_backup_files (ManifestFileID int(64) unsigned not null auto_increment primary key, TestID int(64) unsigned not null, 
    file_name varchar(2500) not null, DeploymentSessionID int(64) unsigned not null, file_data mediumblob not null, 
    constraint backups_test foreign key (TestID) references tests(TestID) on delete cascade,
    constraint backups_deployment_session foreign key (DeploymentSessionID) references deployment_sessions(SessionID) on delete cascade) engine=InnoDB;
create table requests (request_id int(64) unsigned not null auto_increment primary key, fname varchar(80) not null, lname varchar(80) not null, organization varchar(255), 
    email varchar(255) not null, phone varchar(25), address1 varchar(255) not null, address2 varchar(255), city varchar(80) not null, province varchar(80) not null, 
    postal_code varchar(25) not null, country varchar(80) not null, product_use text not null, time_stamp datetime not null, delete_flag bit not null) engine=InnoDB;
create table resource_prices (ResourcePriceID int(16) unsigned not null auto_incrememnt primary key, resource_type ENUM('ADMINISTRATION', 'DISK_SPACE', 'IAT_WITH_10_MB') not null,
    quantity int(16) unsigned not null, price int(16) unsigned not null) engine=InnoDB;
create table purchases(PurchaseID int(32) unsigned not null auto_increment primary key, purchase_time datetime not null, ClientID int(64) unsigned not null, 
    num_administrations int(16) unsigned not null, administrations_price int(16) unsigned not null, num_tests int(16) unsigned not null, tests_price int(16) unsigned not null, 
    disk_space int(16) unsigned not null, disk_space_price int(16) unsigned not null, total int(16) unsigned not null, credit_card varchar(20) not null,
    ending_card_digits char(4) not null, cardholder_fname varchar(255) not null, cardholder_lname varchar(255) not null, card_exp_month int(16) unsigned not null,
    card_exp_year int(16) unsigned not null, constraint purchases_client foreign key (ClientID) references clients(ClientID) on delete cascade,
    index purchases_client_ndx (ClientID)) engine=InnoDB;
create table country_codes (CodeID int(16) unsigned not null auto_increment primary key, code char(2) not null, country varchar(80) not null) engine=InnoDB;
create table oauth_access (AccessID int(64) unsigned not null auto_increment primary key, ClientID int(64) unsigned not null, TestID int(64) unsigned not null, 
    access_token char(40) not null, refresh_token char(40) not null, auth_token char(40) not null, access_expires datetime not null, 
    refresh_expires datetime not null, auth_token_consumed bit not null, 
    constraint oauths_client foreign key (ClientID) references clients(ClientID) on delete cascade, 
    constraint oauths_test foreign key (TestID) references tests(TestID) on delete cascade, index oauth_access_ndx (access_token)) engine=InnoDB;
create table client_exceptions(ExceptionID int(64) unsigned not null auto_increment primary key, product_key char(20) not null, UserID int(64) unsigned not null, 
    ClientID int(64) unsigned not null, exception_timestamp datetime not null, exception_xml text not null, 
    constraint exceptions_client foreign key (ClientID) references clients(ClientID) on delete cascade,
    constraint exceptions_user foreign key (UserID) references users(UserID) on delete cascade) engine=InnoDB;
create table cors_origins (OriginID int(64) unsigned not null auto_increment primary key, origin varchar(512) not null,
    ClientID int(64) unsigned not null, constraint cors_origins_client foreign key (ClientID) references clients(ClientID) on delete cascade) engine=InnoDB;

