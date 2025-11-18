-- Comprehensive sample data for OnlineEducationPlatform
-- This file will be loaded automatically by Spring Boot
-- Clear existing data (in reverse order of dependencies)
DELETE FROM quiz_results;
DELETE FROM options;
DELETE FROM questions;
DELETE FROM quizzes;
DELETE FROM assignment_submissions;
DELETE FROM assignments;
DELETE FROM learning_progress;
DELETE FROM videos;
DELETE FROM discussion_replies;
DELETE FROM discussions;
DELETE FROM messages;
DELETE FROM courses;
DELETE FROM categories;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;
-- Insert roles
INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('USER'),
       ('INSTRUCTOR');
-- Insert sample users
INSERT INTO users (username, password, email)
VALUES ('admin', 'admin123', 'admin@eduplatform.com'),
       ('john_doe', 'password123', 'john@example.com'),
       ('jane_smith', 'password123', 'jane@example.com'),
       ('mike_wilson', 'password123', 'mike@example.com'),
       (
              'sarah_jones',
              'password123',
              'sarah@example.com'
       ),
       (
              'david_brown',
              'password123',
              'david@example.com'
       ),
       (
              'emily_davis',
              'password123',
              'emily@example.com'
       ),
       (
              'chris_miller',
              'password123',
              'chris@example.com'
       ),
       ('lisa_taylor', 'password123', 'lisa@example.com'),
       ('tom_anderson', 'password123', 'tom@example.com'),
       ('anna_white', 'password123', 'anna@example.com'),
       ('mark_lee', 'password123', 'mark@example.com'),
       (
              'nicole_green',
              'password123',
              'nicole@example.com'
       ),
       ('alex_harris', 'password123', 'alex@example.com'),
       (
              'sophia_clark',
              'password123',
              'sophia@example.com'
       );
-- Assign roles to users
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),
       -- admin is ADMIN
       (1, 3),
       -- admin is also INSTRUCTOR
       (2, 2),
       -- john_doe is USER
       (3, 2),
       -- jane_smith is USER
       (3, 3),
       -- jane_smith is also INSTRUCTOR
       (4, 2),
       -- mike_wilson is USER
       (5, 2),
       -- sarah_jones is USER
       (6, 2),
       -- david_brown is USER
       (6, 3),
       -- david_brown is also INSTRUCTOR
       (7, 2),
       -- emily_davis is USER
       (8, 2),
       -- chris_miller is USER
       (9, 2),
       -- lisa_taylor is USER
       (10, 2),
       -- tom_anderson is USER
       (11, 2),
       -- anna_white is USER
       (12, 2),
       -- mark_lee is USER
       (13, 2),
       -- nicole_green is USER
       (14, 2),
       -- alex_harris is USER
       (15, 2);
-- sophia_clark is USER
-- Insert categories
INSERT INTO categories (name)
VALUES ('Programming'),
       ('Web Development'),
       ('Data Science'),
       ('Machine Learning'),
       ('Mobile Development'),
       ('DevOps'),
       ('Database'),
       ('UI/UX Design'),
       ('Cybersecurity'),
       ('Cloud Computing'),
       ('Game Development'),
       ('Blockchain'),
       ('AI & Robotics');
-- Insert comprehensive courses
INSERT INTO courses (
              title,
              description,
              category_id,
              created_by,
              status
       )
VALUES -- Programming courses
       (
              'JavaScript Fundamentals',
              'Master the basics of JavaScript programming with hands-on projects',
              1,
              1,
              'PUBLISHED'
       ),
       (
              'Python for Beginners',
              'Learn Python programming from scratch with real-world examples',
              1,
              3,
              'PUBLISHED'
       ),
       (
              'Java Object-Oriented Programming',
              'Deep dive into Java OOP concepts and best practices',
              1,
              6,
              'PUBLISHED'
       ),
       (
              'C++ Advanced Concepts',
              'Master advanced C++ programming techniques and memory management',
              1,
              1,
              'PUBLISHED'
       ),
       -- Web Development courses
       (
              'React Complete Guide',
              'Build modern web applications with React, hooks, and context',
              2,
              3,
              'PUBLISHED'
       ),
       (
              'Full Stack Node.js',
              'Complete Node.js backend development with Express and MongoDB',
              2,
              6,
              'PUBLISHED'
       ),
       (
              'Vue.js 3 Masterclass',
              'Learn Vue.js 3 composition API and build scalable applications',
              2,
              1,
              'PUBLISHED'
       ),
       (
              'HTML/CSS Responsive Design',
              'Create beautiful, responsive websites with modern CSS techniques',
              2,
              3,
              'PUBLISHED'
       ),
       -- Data Science courses
       (
              'Python Data Analysis',
              'Analyze data using Python, pandas, and matplotlib',
              3,
              6,
              'PUBLISHED'
       ),
       (
              'Statistical Analysis with R',
              'Master statistical analysis and data visualization with R',
              3,
              1,
              'PUBLISHED'
       ),
       (
              'Data Visualization Mastery',
              'Create stunning data visualizations with D3.js and Tableau',
              3,
              3,
              'PUBLISHED'
       ),
       -- Machine Learning courses
       (
              'Machine Learning Basics',
              'Introduction to ML algorithms and practical implementations',
              4,
              6,
              'PUBLISHED'
       ),
       (
              'Deep Learning with TensorFlow',
              'Build neural networks and deep learning models',
              4,
              1,
              'PUBLISHED'
       ),
       (
              'Natural Language Processing',
              'Process and analyze text data with NLP techniques',
              4,
              3,
              'PUBLISHED'
       ),
       -- Mobile Development courses
       (
              'iOS App Development',
              'Create native iOS apps with Swift and SwiftUI',
              5,
              6,
              'PUBLISHED'
       ),
       (
              'Android Development with Kotlin',
              'Build modern Android apps using Kotlin',
              5,
              1,
              'PUBLISHED'
       ),
       (
              'React Native Cross-Platform',
              'Develop mobile apps for iOS and Android with React Native',
              5,
              3,
              'PUBLISHED'
       ),
       -- DevOps courses
       (
              'Docker Containerization',
              'Master Docker containers and container orchestration',
              6,
              1,
              'PUBLISHED'
       ),
       (
              'Kubernetes Administration',
              'Deploy and manage applications with Kubernetes',
              6,
              6,
              'PUBLISHED'
       ),
       (
              'CI/CD Pipeline Mastery',
              'Build automated deployment pipelines with Jenkins and GitLab',
              6,
              3,
              'PUBLISHED'
       ),
       -- Database courses
       (
              'MySQL Database Design',
              'Design efficient databases and optimize queries',
              7,
              6,
              'PUBLISHED'
       ),
       (
              'MongoDB NoSQL',
              'Work with document databases and aggregation pipelines',
              7,
              1,
              'PUBLISHED'
       ),
       -- Design courses
       (
              'UI/UX Design Principles',
              'Learn design principles and create user-friendly interfaces',
              8,
              3,
              'PUBLISHED'
       ),
       (
              'Figma Design Workshop',
              'Master Figma for professional design workflows',
              8,
              6,
              'PUBLISHED'
       ),
       -- Additional courses
       (
              'Cybersecurity Fundamentals',
              'Protect systems and networks from security threats',
              9,
              1,
              'PUBLISHED'
       ),
       (
              'AWS Cloud Practitioner',
              'Get started with Amazon Web Services cloud computing',
              10,
              6,
              'PUBLISHED'
       ),
       (
              'Unity Game Development',
              'Create 2D and 3D games with Unity engine',
              11,
              3,
              'PUBLISHED'
       );
-- Insert comprehensive videos for each course
INSERT INTO videos (course_id, title, video_url, duration_seconds)
VALUES -- JavaScript Fundamentals videos
       (
              1,
              'Introduction to JavaScript',
              'https://www.youtube.com/watch?v=PkZNo7MFNFg',
              900
       ),
       (
              1,
              'Variables and Data Types',
              'https://www.youtube.com/watch?v=9YffrCViTVk',
              1200
       ),
       (
              1,
              'Functions and Scope',
              'https://www.youtube.com/watch?v=N8ap4k_1QEQ',
              1500
       ),
       (
              1,
              'Objects and Arrays',
              'https://www.youtube.com/watch?v=vhc4I1fTqQ8',
              1800
       ),
       (
              1,
              'DOM Manipulation',
              'https://www.youtube.com/watch?v=wiozYyXQEVk',
              2100
       ),
       -- Python for Beginners videos
       (
              2,
              'Python Setup and Hello World',
              'https://www.youtube.com/watch?v=kqtD5dpn9C8',
              800
       ),
       (
              2,
              'Python Syntax and Variables',
              'https://www.youtube.com/watch?v=cQT33yu9pY8',
              1000
       ),
       (
              2,
              'Control Structures and Loops',
              'https://www.youtube.com/watch?v=9Os0o3wzS_I',
              1400
       ),
       (
              2,
              'Functions and Modules',
              'https://www.youtube.com/watch?v=9Os0o3wzS_I',
              1600
       ),
       (
              2,
              'File Handling and Exceptions',
              'https://www.youtube.com/watch?v=Ej_02ICOIgs',
              1300
       ),
       -- Java OOP videos
       (
              3,
              'Classes and Objects',
              'https://www.youtube.com/watch?v=Ej_02ICOIgs',
              1100
       ),
       (
              3,
              'Inheritance and Polymorphism',
              'https://www.youtube.com/watch?v=9AWMNa4co_4',
              1700
       ),
       (
              3,
              'Encapsulation and Abstraction',
              'https://www.youtube.com/watch?v=BSVKUk58K6U',
              1400
       ),
       (
              3,
              'Interfaces and Abstract Classes',
              'https://www.youtube.com/watch?v=HvPlEJ3LHgE',
              1600
       ),
       -- React Complete Guide videos
       (
              5,
              'React Introduction and Setup',
              'https://www.youtube.com/watch?v=Ke90Tje7VS0',
              1200
       ),
       (
              5,
              'Components and JSX',
              'https://www.youtube.com/watch?v=SqcY0GlETPk',
              1500
       ),
       (
              5,
              'Props and State Management',
              'https://www.youtube.com/watch?v=35lXWvCuM8o',
              1800
       ),
       (
              5,
              'React Hooks Deep Dive',
              'https://www.youtube.com/watch?v=TNhaISOUy6Q',
              2200
       ),
       (
              5,
              'Context API and State Management',
              'https://www.youtube.com/watch?v=5LrDIWkK_Bc',
              2000
       ),
       -- Python Data Analysis videos
       (
              9,
              'Pandas Introduction',
              'https://www.youtube.com/watch?v=vmEHCJofslg',
              1300
       ),
       (
              9,
              'Data Cleaning Techniques',
              'https://www.youtube.com/watch?v=ZOX18HfLHGQ',
              1600
       ),
       (
              9,
              'Data Visualization with Matplotlib',
              'https://www.youtube.com/watch?v=3Xc3CA655Y4',
              1900
       ),
       (
              9,
              'Statistical Analysis',
              'https://www.youtube.com/watch?v=Iq9DzN6mvYA',
              1700
       ),
       -- Machine Learning Basics videos
       (
              12,
              'Introduction to Machine Learning',
              'https://www.youtube.com/watch?v=ukzFI9rgwfU',
              1400
       ),
       (
              12,
              'Linear Regression',
              'https://www.youtube.com/watch?v=7ArmBVF2dCs',
              1800
       ),
       (
              12,
              'Classification Algorithms',
              'https://www.youtube.com/watch?v=yIYKR4sgzI8',
              2100
       ),
       (
              12,
              'Clustering Techniques',
              'https://www.youtube.com/watch?v=4b5d3muPQmA',
              1900
       ),
       -- Docker Containerization videos
       (
              18,
              'Docker Basics and Installation',
              'https://www.youtube.com/watch?v=fqMOX6JJhGo',
              1100
       ),
       (
              18,
              'Creating Docker Images',
              'https://www.youtube.com/watch?v=LQjaJINkQXY',
              1500
       ),
       (
              18,
              'Docker Compose',
              'https://www.youtube.com/watch?v=Qw9zlE3t8Ko',
              1700
       ),
       -- Add more videos for other courses
       (
              6,
              'Node.js Fundamentals',
              'https://www.youtube.com/watch?v=TlB_eWDSMt4',
              1200
       ),
       (
              6,
              'Express.js Framework',
              'https://www.youtube.com/watch?v=L72fhGm1tfE',
              1600
       ),
       (
              6,
              'MongoDB Integration',
              'https://www.youtube.com/watch?v=fgTGADljAeg',
              1800
       ),
       (
              15,
              'iOS Development Setup',
              'https://www.youtube.com/watch?v=09TeUXjzpKs',
              1000
       ),
       (
              15,
              'Swift Programming Basics',
              'https://www.youtube.com/watch?v=comQ1-x2a1Q',
              1400
       ),
       (
              15,
              'SwiftUI Interface Design',
              'https://www.youtube.com/watch?v=F2ojC6TNwws',
              1700
       );
-- Insert sample quizzes
INSERT INTO quizzes (course_id, title)
VALUES (1, 'JavaScript Basics Quiz'),
       (2, 'Python Fundamentals Test'),
       (5, 'React Components Assessment'),
       (9, 'Data Analysis Challenge'),
       (12, 'Machine Learning Concepts'),
       (15, 'iOS Development Quiz');
-- Insert quiz questions
INSERT INTO questions (quiz_id, text)
VALUES -- JavaScript quiz questions
       (
              1,
              'What is the correct way to declare a variable in JavaScript?'
       ),
       (
              1,
              'Which method is used to add an element to the end of an array?'
       ),
       (1, 'What does DOM stand for?'),
       -- Python quiz questions
       (
              2,
              'Which keyword is used to define a function in Python?'
       ),
       (
              2,
              'What is the correct way to create a list in Python?'
       ),
       (
              2,
              'Which operator is used for exponentiation in Python?'
       ),
       -- React quiz questions
       (3, 'What is JSX?'),
       (
              3,
              'Which hook is used to manage component state?'
       ),
       (
              3,
              'How do you pass data from parent to child component?'
       ),
       -- Data Analysis quiz questions
       (
              4,
              'Which library is commonly used for data manipulation in Python?'
       ),
       (4, 'What does SQL stand for?'),
       -- Machine Learning quiz questions
       (5, 'What is supervised learning?'),
       (
              5,
              'Which algorithm is used for classification problems?'
       );
-- Insert quiz options
INSERT INTO options (question_id, text, correct)
VALUES -- JavaScript question 1 options
       (1, 'var myVariable;', true),
       (1, 'variable myVariable;', false),
       (1, 'declare myVariable;', false),
       -- JavaScript question 2 options
       (2, 'push()', true),
       (2, 'add()', false),
       (2, 'append()', false),
       -- JavaScript question 3 options
       (3, 'Document Object Model', true),
       (3, 'Data Object Management', false),
       (3, 'Dynamic Object Mapping', false),
       -- Python question 1 options
       (4, 'def', true),
       (4, 'function', false),
       (4, 'func', false),
       -- Python question 2 options
       (5, '[1, 2, 3]', true),
       (5, '(1, 2, 3)', false),
       (5, '{1, 2, 3}', false),
       -- React question 1 options
       (7, 'JavaScript XML', true),
       (7, 'JavaScript Extension', false),
       (7, 'Java Syntax Extension', false),
       -- React question 2 options
       (8, 'useState', true),
       (8, 'useEffect', false),
       (8, 'useContext', false);
-- Insert assignments
INSERT INTO assignments (course_id, title, description, due_date)
VALUES (
              1,
              'Build a Calculator',
              'Create a functional calculator using JavaScript',
              '2025-12-15 23:59:59'
       ),
       (
              2,
              'Data Analysis Project',
              'Analyze a dataset of your choice using Python',
              '2025-12-20 23:59:59'
       ),
       (
              5,
              'Todo App with React',
              'Build a complete todo application using React hooks',
              '2025-12-25 23:59:59'
       ),
       (
              9,
              'Sales Data Dashboard',
              'Create an interactive dashboard for sales data analysis',
              '2026-01-10 23:59:59'
       ),
       (
              12,
              'Prediction Model',
              'Build a machine learning model to predict house prices',
              '2026-01-15 23:59:59'
       );
-- Insert assignment submissions
INSERT INTO assignment_submissions (assignment_id, user_id, content, grade, feedback)
VALUES (
              1,
              2,
              'Here is my calculator implementation: https://github.com/john/calculator',
              85,
              'Great work! The logic is sound, but could use better error handling.'
       ),
       (
              1,
              4,
              'Calculator project completed: https://codepen.io/mike/calculator',
              92,
              'Excellent implementation with clean code and good UI design.'
       ),
       (
              2,
              3,
              'Data analysis of customer behavior: https://github.com/jane/data-project',
              88,
              'Good insights and visualization. Consider adding more statistical analysis.'
       ),
       (
              3,
              2,
              'React Todo App: https://github.com/john/react-todo',
              90,
              'Well-structured components and good use of hooks.'
       ),
       (
              3,
              5,
              'Todo application with additional features: https://github.com/sarah/advanced-todo',
              95,
              'Exceptional work with bonus features and excellent code quality.'
       );
-- Insert learning progress
INSERT INTO learning_progress (
              user_id,
              video_id,
              progress_percent,
              last_position_seconds
       )
VALUES (2, 1, 100.00, 900),
       (2, 2, 75.50, 900),
       (2, 3, 45.20, 678),
       (3, 1, 100.00, 900),
       (3, 6, 100.00, 800),
       (3, 7, 60.30, 603),
       (4, 9, 100.00, 1300),
       (4, 10, 85.40, 1367),
       (4, 11, 20.15, 383),
       (5, 13, 100.00, 1200),
       (5, 14, 90.25, 1354),
       (5, 15, 75.80, 1365);
-- Insert messages
INSERT INTO messages (sender_id, receiver_id, content)
VALUES (
              2,
              3,
              'Hey Jane, could you help me with the React assignment?'
       ),
       (
              3,
              2,
              'Sure John! I can help you with the component structure.'
       ),
       (
              4,
              1,
              'Admin, I am having trouble accessing the advanced courses.'
       ),
       (
              1,
              4,
              'Hi Mike, I have enabled access to all courses for your account.'
       ),
       (
              5,
              6,
              'David, your machine learning course is excellent!'
       ),
       (
              6,
              5,
              'Thank you Sarah! I am glad you are enjoying it.'
       ),
       (
              7,
              3,
              'Jane, when is the next live session for React course?'
       ),
       (
              3,
              7,
              'Emily, the next session is scheduled for Friday at 3 PM.'
       ),
       (
              8,
              1,
              'Could you add more beginner-friendly courses?'
       ),
       (
              1,
              8,
              'Thanks for the feedback Chris, we are working on it!'
       );
-- Insert discussions
INSERT INTO discussions (course_id, user_id, title, content)
VALUES (
              1,
              2,
              'Best practices for JavaScript functions',
              'What are some best practices when writing JavaScript functions? I want to improve my coding style.'
       ),
       (
              5,
              3,
              'React vs Angular debate',
              'I am curious about the community opinion on React vs Angular. What are your thoughts?'
       ),
       (
              9,
              4,
              'Data cleaning techniques',
              'What are the most effective techniques for cleaning messy datasets? Share your experiences.'
       ),
       (
              12,
              5,
              'Overfitting in machine learning',
              'I am struggling with overfitting in my models. Any tips on how to prevent it?'
       ),
       (
              1,
              7,
              'Asynchronous JavaScript challenges',
              'Promises and async/await are confusing me. Can someone explain the differences?'
       ),
       (
              2,
              8,
              'Python virtual environments',
              'When should I use virtual environments in Python? What are the benefits?'
       );
-- Insert discussion replies
INSERT INTO discussion_replies (discussion_id, user_id, content)
VALUES (
              1,
              3,
              'Some good practices: use descriptive names, keep functions small, and avoid side effects.'
       ),
       (
              1,
              6,
              'Also, consider using arrow functions for shorter syntax and proper this binding.'
       ),
       (
              1,
              4,
              'Documentation is key! Always comment your complex functions.'
       ),
       (
              2,
              2,
              'I prefer React for its flexibility and large community. Angular is great for enterprise apps.'
       ),
       (
              2,
              6,
              'React has a gentler learning curve, while Angular provides more structure out of the box.'
       ),
       (
              2,
              5,
              'Both are excellent choices. It depends on your project requirements and team preferences.'
       ),
       (
              3,
              3,
              'Start by identifying missing values and outliers. Pandas has great tools for this.'
       ),
       (
              3,
              6,
              'Do not forget about data validation. Always check for unexpected data types and formats.'
       ),
       (
              3,
              1,
              'Great discussion! Data quality is crucial for any analysis project.'
       ),
       (
              4,
              6,
              'Try regularization techniques like L1 or L2. Also, use cross-validation to evaluate your model.'
       ),
       (
              4,
              1,
              'Feature selection can help reduce overfitting. Remove irrelevant or redundant features.'
       ),
       (
              4,
              3,
              'Increasing training data often helps. Also, consider ensemble methods.'
       ),
       (
              5,
              2,
              'Promises handle asynchronous operations. Async/await is syntactic sugar that makes promises easier to read.'
       ),
       (
              5,
              3,
              'Think of async/await as a way to write asynchronous code that looks synchronous.'
       ),
       (
              5,
              6,
              'Great question! I recommend practicing with setTimeout and fetch examples.'
       );
-- Insert quiz results
INSERT INTO quiz_results (quiz_id, user_id, score)
VALUES (1, 2, 85),
       (1, 3, 92),
       (1, 4, 78),
       (2, 3, 95),
       (2, 5, 88),
       (3, 2, 90),
       (3, 7, 87),
       (4, 4, 92),
       (4, 5, 96),
       (5, 5, 89),
       (5, 6, 94);
-- Show completion message
SELECT 'Sample data loaded successfully!' as status,
       (
              SELECT COUNT(*)
              FROM users
       ) as users_count,
       (
              SELECT COUNT(*)
              FROM courses
       ) as courses_count,
       (
              SELECT COUNT(*)
              FROM videos
       ) as videos_count,
       (
              SELECT COUNT(*)
              FROM discussions
       ) as discussions_count;