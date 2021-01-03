package com.doinb.web.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.doinb.utils.RandomValueUtil;
import com.doinb.web.vo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用easyexcel实现Excel下载功能
 * 案例来源：https://github.com/alibaba/easyexcel
 */
@Api(value = "excel下载", tags = "excel下载")
@RestController
@RequestMapping(value = "/bat")
public class ExcelController {
    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @ApiOperation("excel下载")
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // --- 表格样式设置start ---
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色 灰色25%
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // --- 表格样式设置end ---
        long start = System.currentTimeMillis();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("学生信息导出", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Student.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("学生信息").doWrite(data());
        long end = System.currentTimeMillis();
        System.out.println("运行时间(毫秒)：" + (end - start));
    }

    /**
     * 演示使用模拟数据 输出5列十万条数据大约4秒 5.17MB， 输出5列一百万条数据大约29.173秒 51.8M；
     * 真是情况下需要查询数据源可能需要更多的时间
     *
     * @return 模拟数据
     */
    private List<Student> data() {
        List<Student> list = new ArrayList<Student>();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        for (int i = 0; i < 100000; i++) {
            Student data = new Student();
            data.setName(RandomValueUtil.getChineseName());
            data.setTel(RandomValueUtil.getTel());
            data.setEmail(RandomValueUtil.getEmail(6, 9));
            data.setGender(RandomValueUtil.name_sex);
            data.setRoad(RandomValueUtil.getRoad());
            data.setId(snowflake.nextId());
            list.add(data);
        }
        return list;
    }
}
