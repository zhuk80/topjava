package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * Created by evgeniy on 26.04.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class UserMealServiceParent {

    static final Logger LOG = LoggerFactory.getLogger(MealServiceTestParent.class);
    static StringBuilder results = new StringBuilder();

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    public abstract void testSave() throws Exception;

    public abstract void testGet() throws Exception;

    public abstract void testGetNotFound() throws Exception;

    public abstract void testGetAll() throws Exception;

    public abstract void testDeleteNotFound() throws Exception;

    public abstract void testDelete() throws Exception;

    public abstract void testUpdate() throws Exception;

}
