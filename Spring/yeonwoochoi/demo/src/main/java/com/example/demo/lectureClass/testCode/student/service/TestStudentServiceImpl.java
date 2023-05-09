package com.example.demo.lectureClass.testCode.student.service;

import com.example.demo.lectureClass.testCode.student.entity.TestStudent;
import com.example.demo.lectureClass.testCode.student.repository.TestStudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestStudentServiceImpl implements TestStudentService {

    final private TestStudentRepository studentRepository;

    @Override
    public TestStudent register(TestStudent testStudent) {
        return studentRepository.save(testStudent);
    }
}