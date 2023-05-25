package edu.unomaha.pkischeduler;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import edu.unomaha.pkischeduler.ui.ExportView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.core.io.ClassPathResource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "flowcrmtutorial", variant = Lumo.DARK)
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator, CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Auto popoulate room table if it's empty (it's empty on first run)
     * @param args incoming main method arguments
     * @throws SQLException
     */
    @Override
    public void run(String... args) throws SQLException {
        // Check if the room table is empty
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM room", Integer.class);
        LOG.info("Room table count: " + count);
        if (count != null && count == 0) {
            try (Connection connection = dataSource.getConnection()) {
                LOG.info("Populating room table from sql/room.sql");
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/room.sql"));
            }
        }
    }

}
