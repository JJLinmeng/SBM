package com.mumu.studentbankmanagement.service.impl;

import com.mumu.studentbankmanagement.frame.ConfigJFrame;
import com.mumu.studentbankmanagement.mapper.StuMapper;
import com.mumu.studentbankmanagement.model.Stu;
import com.mumu.studentbankmanagement.service.StuService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;

    @PostConstruct
    public void init() {
        ConfigJFrame.set(this);
    }

    @Override
    public Stu login(String username, String password) {
        return stuMapper.findByIdAndPassword(username, password);
    }

    @Override
    public void addStudent(Stu stu) {
        stuMapper.addStu(stu);
    }

    @Override
    public int deleteStudent(String stuId) {
        return stuMapper.deleteStu(stuId);
    }

    @Override
    public Stu checkIsExist(String stuId) {
        return stuMapper.checkIsExist(stuId);
    }

    @Override
    public List<Stu> getAllStudent() {
        return stuMapper.getAllStudent();
    }

    @Override
    public List<Stu> selectStuByCondition(Stu stu) {
        return stuMapper.selectStuByCondition(stu);
    }

    @Override
    public void updateStu(Stu stu) {
        stuMapper.updateStu(stu);
    }

    @Override
    public void addSpeciality(int id, String speciality) {
        stuMapper.addSpeciality(id, speciality);
    }

    @Override
    public String getSpecId(String speciality) {
        return stuMapper.getSpecId(speciality);
    }

    @Override
    public Integer getNumber(String speciality, int entryYear) {
        return stuMapper.getNumber(speciality,entryYear);
    }


}
