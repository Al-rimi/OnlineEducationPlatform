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
            log.info("🚀 Starting application with simple demo data...");
            log.info("📚 Database contains clean sample data including:");
            log.info("   - 3 sample users (admin, teacher, student)");
            log.info("   - 3 courses across 3 categories");
            log.info("   - Basic video lessons and assignments");
            log.info("   - Simple quizzes and discussions");
            log.info("   - Learning progress tracking");
            log.info("✅ Application ready! Visit http://localhost:8081");
            log.info("🎯 Try logging in with: admin/admin123 or teacher/teacher123 or student/student123");
        } catch (Exception e) {
            log.warn("Data initialization info display failed: {}", e.getMessage());
        }
    }
}
