package com.mumu.studentbankmanagement.service;

import com.mumu.studentbankmanagement.model.Stu;

import java.util.List;

public interface StuService {
    public Stu login(String username, String password);
    public void addStudent(Stu user);
    public int deleteStudent(String stuId);
    public Stu checkIsExist(String stuId);
    public List<Stu> getAllStudent();
    List<Stu> selectStuByCondition(Stu stu);
    void updateStu(Stu stu);
}
