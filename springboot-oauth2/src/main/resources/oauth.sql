-- 官方提供的schema的sql
create table if not exists oauth_client_details (
    client_id VARCHAR(255) PRIMARY KEY,
    resource_ids VARCHAR(255),
    client_secret VARCHAR(255),
    scope VARCHAR(255),
    authorized_grant_types VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(255)
    );

create table if not exists oauth_client_token (
    token_id VARCHAR(255),
    token LONG VARBINARY,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name VARCHAR(255),
    client_id VARCHAR(255)
    );

create table if not exists oauth_access_token (
    token_id VARCHAR(255),
    token LONG VARBINARY,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication LONG VARBINARY,
    refresh_token VARCHAR(255)
    );

create table if not exists oauth_refresh_token (
    token_id VARCHAR(255),
    token LONG VARBINARY,
    authentication LONG VARBINARY
    );

create table if not exists oauth_code (
    code VARCHAR(255), authentication LONG VARBINARY
    );

create table if not exists oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt DATE,
    lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

create table if not exists ClientDetails (
    appId VARCHAR(255) PRIMARY KEY,
    resourceIds VARCHAR(255),
    appSecret VARCHAR(255),
    scope VARCHAR(255),
    grantTypes VARCHAR(255),
    redirectUrl VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation VARCHAR(4096),
    autoApproveScopes VARCHAR(255)
    );

INSERT INTO `springboot-oauth2`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('barClientIdPassword', 'oauth2-resource', 'secret', 'bar,read,write', 'password,authorization_code,refresh_token', 'http://127.0.0.1:8082/login/my', NULL, '36000', '36000', NULL, '1');


INSERT INTO `springboot-oauth2`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('fooClientIdPassword', NULL, 'secret', 'foo,read,write', 'password,authorization_code,refresh_token', NULL, NULL, '36000', '36000', NULL, '1');


INSERT INTO `springboot-oauth2`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('sampleClientId', NULL, 'secret', 'read,write,foo,bar', 'implicit', NULL, NULL, '36000', '36000', NULL, '0');
