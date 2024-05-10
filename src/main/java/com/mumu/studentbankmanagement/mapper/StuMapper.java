package com.mumu.studentbankmanagement.mapper;

import com.mumu.studentbankmanagement.model.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StuMapper {
    Stu findByIdAndPassword(@Param("id") String id , @Param("password") String password);

    void  addStu(Stu stu);

    int deleteStu(String stuId);

    Stu checkIsExist(String stuId);

    List<Stu> getAllStudent();

    List<Stu> selectStuByCondition(Stu stu);

    void updateStu(Stu stu);

    void addSpeciality(int id, String speciality);

    String getSpecId(String speciality);

    Integer getNumber(String speciality, int entryYear);

    void setToBePaidToOne(String id, String amount);

    void setToBePaidToAll(String amount);
}
