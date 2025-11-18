package com.example.onlineeducationplatform.config;

import com.example.onlineeducationplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(UserService userService) {
        // UserService injection for potential future use
    }

    @Override
    public void run(String... args) {
        try {
            log.info("🚀 Starting application with comprehensive sample data...");
            log.info("📚 Database contains rich sample data including:");
            log.info("   - 15 sample users (admin, instructors, students)");
            log.info("   - 25+ courses across 13 categories");
            log.info("   - 40+ video lessons");
            log.info("   - Interactive quizzes and assignments");
            log.info("   - Discussion forums and messages");
            log.info("   - Learning progress tracking");
            log.info("✅ Application ready! Visit http://localhost:8081");
            log.info("🎯 Try logging in with: admin/admin123 or john_doe/password123");
        } catch (Exception e) {
            log.warn("Data initialization info display failed: {}", e.getMessage());
        }
    }
}
