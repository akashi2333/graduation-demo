CREATE DATABASE ckmp;
USE ckmp;
CREATE TABLE USER (
                      id INT,
                      NAME VARCHAR(10),
                      PASSWORD VARCHAR(10),
                      e_mail VARCHAR(255),
                      PRIMARY KEY (id)
);

CREATE TABLE project (
                         pid INT,
                         uid INT,
                         PRIMARY KEY (pid)
);

CREATE TABLE user_project (
                              uid INT,
                              pid INT
);

CREATE TABLE project_resource (
                                  pid INT,
                                  uid INT,
                                  resource_name VARCHAR(255)
);

CREATE TABLE chat (
                      sid INT,
                      rid INT,
                      message VARCHAR(255),
                      send_data DATETIME,
                      send_all BOOLEAN
);

CREATE TABLE idea (
                      id INT,
                      idea VARCHAR(255)
);

CREATE TABLE todo (
                      id INT,
                      todo VARCHAR(255)
);

CREATE TABLE project_join (
                              pid INT,
                              uid INT
);


CREATE TABLE project_exit (
                              pid INT,
                              uid INT
);

ALTER TABLE project ADD state VARCHAR(255);
ALTER TABLE chat CHANGE send_data send_date DATETIME;
