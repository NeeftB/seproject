package nl.pse.site.seproject.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

/**
 * Configuration of the connection with mySQL.
 * Change the user and password so it can connect with your mySQL database.
 */
@DataSourceDefinition(
        name = "java:app/env/jdbc/SEProjectDB",
        className = "com.mysql.cj.jdbc.MysqlXADataSource",

//        url = "jdbc:mysql://localhost:3306/truthortravel?createDatabaseIfNotExist=true&serverTimezone=CET",
        url = "jdbc:mysql://localhost:3306/projectse?createDatabaseIfNotExist=true&serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true",
        user = "root",
        password = "Dr!ger13"
)


@Stateless
public class DataSourceDefinitionConfig {
}
