package com.test.controller;

import com.test.entity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@Slf4j
public class ExportController {

    @GetMapping("/com/test")
    public void ErrorExport(HttpServletResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssSSS");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        try {
            List<UserVO> userList = new ArrayList<>();

            for (int i = 1; i <100 ; i++) {
                userList.add(new UserVO("name"+i, "realname"+i,i, "moblie"+i, "email"+i, i, new Date()));
            }
            ExportParams exportParams = new ExportParams();
            exportParams.setType(ExcelType.XSSF);
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams,UserVO.class, userList);
            //设置格式
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setWrapText(true);

            log.info("===== 正在导出Excle =====");
            //取得输出流
            ServletOutputStream output = response.getOutputStream();
            //清空输出流
            response.reset();
//          response.setContentType("application/octet-stream");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("errors","UTF-8") + dateTime + ".xlsx");
            response.setCharacterEncoding("UTF-8");
            //写入文件
            workbook.write(output);
            response.flushBuffer();
            log.info("===== Excle导出完成 =====");
//==================================================================================================
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
