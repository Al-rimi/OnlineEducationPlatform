package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.CourseMapper;
import com.example.onlineeducationplatform.mapper.EnrollmentMapper;
import com.example.onlineeducationplatform.mapper.PurchaseMapper;
import com.example.onlineeducationplatform.mapper.UserMapper;
import com.example.onlineeducationplatform.model.Course;
import com.example.onlineeducationplatform.model.Purchase;
import com.example.onlineeducationplatform.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.math.BigDecimal;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;
    private final EnrollmentMapper enrollmentMapper;
    private final PurchaseMapper purchaseMapper;
    private final UserMapper userMapper;

    public CourseServiceImpl(CourseMapper courseMapper, EnrollmentMapper enrollmentMapper,
            PurchaseMapper purchaseMapper, UserMapper userMapper) {
        this.courseMapper = courseMapper;
        this.enrollmentMapper = enrollmentMapper;
        this.purchaseMapper = purchaseMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Course getById(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public List<Course> getAll() {
        return courseMapper.selectAll();
    }

    @Override
    public List<Course> getByCategory(Integer categoryId) {
        return courseMapper.selectByCategory(categoryId);
    }

    @Override
    public List<Course> search(String keyword) {
        return courseMapper.searchByTitle(keyword);
    }

    @Override
    public List<Course> getEnrolledCourses(Integer userId) {
        return courseMapper.selectEnrolledCourses(userId);
    }

    @Override
    public int create(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int update(Course course) {
        return courseMapper.update(course);
    }

    @Override
    public int delete(Integer id) {
        return courseMapper.delete(id);
    }

    @Override
    public boolean enrollUser(Integer courseId, String username) {
        com.example.onlineeducationplatform.model.User user = userMapper.selectUserByUsername(username);
        if (user == null)
            return false;
        // Avoid duplicate (unique key constraint handles too)
        if (enrollmentMapper.select(user.getId(), courseId) != null)
            return true;
        return enrollmentMapper.insert(user.getId(), courseId) > 0;
    }

    @Override
    public boolean purchaseCourse(Integer courseId, String username) {
        com.example.onlineeducationplatform.model.User user = userMapper.selectUserByUsername(username);
        if (user == null)
            return false;
        Course c = courseMapper.selectById(courseId);
        if (c == null)
            return false;
        BigDecimal amount = c.getPrice() == null ? BigDecimal.ZERO : c.getPrice();
        if (purchaseMapper.select(user.getId(), courseId) != null)
            return true;
        Purchase p = new Purchase();
        p.setUserId(user.getId());
        p.setCourseId(courseId);
        p.setAmount(amount);
        p.setCurrency(c.getCurrency() == null ? "USD" : c.getCurrency());
        p.setStatus("PAID");
        boolean ok = purchaseMapper.insert(p) > 0;
        if (ok) {
            // auto-enroll upon purchase
            if (enrollmentMapper.select(user.getId(), courseId) == null) {
                enrollmentMapper.insert(user.getId(), courseId);
            }
        }
        return ok;
    }
}
