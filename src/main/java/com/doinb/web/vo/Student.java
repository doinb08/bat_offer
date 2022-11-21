package com.doinb.web.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    /**
     * 学生姓名
     */
    @ColumnWidth(value = 15)
    @ExcelProperty("学生姓名")
    private String name;
    /**
     * 学生性别
     */
    @ColumnWidth(value = 15)
    @ExcelProperty("学生性别")
    private String gender;

    /**
     * 学生手机号
     */
    @ColumnWidth(value = 15)
    @ExcelProperty("学生手机号")
    private String tel;

    @ColumnWidth(value = 20)
    @ExcelProperty("学生邮箱")
    private String email;

    @ColumnWidth(value = 50)
    @ExcelProperty("学生住址")
    private String road;
    /**
     * id
     */
    @ExcelIgnore
    private Long id;
}

