CREATE KEYSPACE config WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 } ; 
use config;
CREATE TABLE config (app_name text, property_name text , property_value text , primary KEY (app_name , property_name)) ;
