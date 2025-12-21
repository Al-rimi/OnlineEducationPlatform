package com.example.onlineeducationplatform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            seedData();
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
            log.warn("Data initialization failed: {}", e.getMessage());
        }
    }

    private void seedData() {
        // Seed roles
        jdbcTemplate.update("INSERT IGNORE INTO roles (name) VALUES ('ADMIN'), ('USER'), ('TEACHER'), ('STUDENT')");

        // Seed users with UPSERT to ensure passwords are set
        jdbcTemplate.update(
                "INSERT INTO users (username, password, email) VALUES ('admin', 'admin123', 'admin@eduplatform.com') " +
                        "ON DUPLICATE KEY UPDATE password = 'admin123', email = 'admin@eduplatform.com'");
        jdbcTemplate.update(
                "INSERT INTO users (username, password, email) VALUES ('teacher', 'teacher123', 'teacher@eduplatform.com') "
                        +
                        "ON DUPLICATE KEY UPDATE password = 'teacher123', email = 'teacher@eduplatform.com'");
        jdbcTemplate.update(
                "INSERT INTO users (username, password, email) VALUES ('student', 'student123', 'student@eduplatform.com') "
                        +
                        "ON DUPLICATE KEY UPDATE password = 'student123', email = 'student@eduplatform.com'");

        // Seed user roles - need to get actual user IDs
        jdbcTemplate.update("DELETE FROM user_roles"); // Clear existing role assignments
        jdbcTemplate.update(
                "INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'admin' AND r.name = 'ADMIN'");
        jdbcTemplate.update(
                "INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'teacher' AND r.name = 'TEACHER'");
        jdbcTemplate.update(
                "INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'student' AND r.name = 'STUDENT'");

        // Seed categories
        jdbcTemplate.update(
                "INSERT IGNORE INTO categories (name) VALUES ('Programming'), ('Web Development'), ('Data Science'), ('Design')");

        // Seed courses - assign to teacher (user_id = 2)
        jdbcTemplate.update(
                "INSERT IGNORE INTO courses (title, description, category_id, status, price, currency, visibility, created_by) VALUES ('JavaScript Fundamentals', 'Learn the basics of JavaScript programming', 1, 'PUBLISHED', 0.00, 'USD', 'PUBLIC', 2)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO courses (title, description, category_id, status, price, currency, visibility, created_by) VALUES ('React for Beginners', 'Build modern web apps with React', 2, 'PUBLISHED', 0.00, 'USD', 'PUBLIC', 2)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO courses (title, description, category_id, status, price, currency, visibility, created_by) VALUES ('Python Data Analysis', 'Analyze data using Python and pandas', 3, 'PUBLISHED', 0.00, 'USD', 'PUBLIC', 2)");

        // Update existing courses to have created_by if null
        jdbcTemplate.update("UPDATE courses SET created_by = 2 WHERE created_by IS NULL");

        // Seed videos
        jdbcTemplate.update(
                "INSERT IGNORE INTO videos (course_id, title, video_url, duration_seconds) VALUES (1, 'Variables and Data Types', 'https://example.com/video1.mp4', 600)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO videos (course_id, title, video_url, duration_seconds) VALUES (1, 'Functions and Scope', 'https://example.com/video2.mp4', 720)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO videos (course_id, title, video_url, duration_seconds) VALUES (2, 'Components and Props', 'https://example.com/video3.mp4', 900)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO videos (course_id, title, video_url, duration_seconds) VALUES (2, 'State and Hooks', 'https://example.com/video4.mp4', 1200)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO videos (course_id, title, video_url, duration_seconds) VALUES (3, 'Pandas Introduction', 'https://example.com/video5.mp4', 800)");

        // Seed assignments
        jdbcTemplate.update(
                "INSERT IGNORE INTO assignments (course_id, title, description, due_date, file_url) VALUES (1, 'Build a Calculator', 'Create a simple calculator using JavaScript', '2025-12-31', 'https://example.com/assignment1.pdf')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO assignments (course_id, title, description, due_date) VALUES (2, 'Todo App', 'Build a todo application with React', '2025-12-31')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO assignments (course_id, title, description, due_date) VALUES (3, 'Data Visualization', 'Create charts using Python', '2025-12-31')");

        // Seed quizzes
        jdbcTemplate.update(
                "INSERT IGNORE INTO quizzes (course_id, title) VALUES (1, 'JavaScript Basics Quiz')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO quizzes (course_id, title) VALUES (2, 'React Fundamentals Quiz')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO quizzes (course_id, title) VALUES (3, 'Python Quiz')");

        // Seed questions
        jdbcTemplate.update(
                "INSERT IGNORE INTO questions (quiz_id, question_text) VALUES (1, 'What is a variable?')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO questions (quiz_id, question_text) VALUES (1, 'What does === do?')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO questions (quiz_id, question_text) VALUES (2, 'What is JSX?')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO questions (quiz_id, question_text) VALUES (3, 'What is pandas?')");

        // Seed options
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (1, 'A storage location', true)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (1, 'A function', false)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (2, 'Strict equality', true)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (2, 'Assignment', false)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (3, 'A JavaScript library', true)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (3, 'A database', false)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (4, 'A Python library', true)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO options (question_id, option_text, correct) VALUES (4, 'A snake', false)");

        // Seed enrollments
        jdbcTemplate.update(
                "INSERT IGNORE INTO enrollments (user_id, course_id) VALUES (3, 1)");
        jdbcTemplate.update(
                "INSERT IGNORE INTO enrollments (user_id, course_id) VALUES (3, 2)");

        // Seed discussions
        jdbcTemplate.update(
                "INSERT IGNORE INTO discussions (user_id, course_id, title, content) VALUES (3, 1, 'Help with variables', 'I need help understanding variables')");
        jdbcTemplate.update(
                "INSERT IGNORE INTO discussions (user_id, course_id, title, content) VALUES (2, 1, 'Great course!', 'This course is amazing')");

        // Seed discussion replies
        jdbcTemplate.update(
                "INSERT IGNORE INTO discussion_replies (discussion_id, user_id, content) VALUES (1, 2, 'Variables are storage locations')");
    }
}
