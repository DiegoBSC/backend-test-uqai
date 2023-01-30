package com.uqai.demo.app.uqaidemo.util;

import com.uqai.demo.app.uqaidemo.enums.EnumCarStatus;
import com.uqai.demo.app.uqaidemo.model.car.Brand;
import com.uqai.demo.app.uqaidemo.model.car.Car;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelUploadUtil {
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }

    public static List<Car> getCarsDataFromExcel(InputStream inputStream){
        List<Car> cars = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("cars");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Car car = new Car();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> car.setName(cell.getCellType().name().equals("NUMERIC")?
                                String.valueOf(cell.getNumericCellValue()):
                                cell.getStringCellValue());
                        case 1 -> car.setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                        case 2 -> car.setDescription(cell.getStringCellValue());
                        case 3 -> car.setKilometres((long) cell.getNumericCellValue());
                        case 4 -> car.setYearCar((long) cell.getNumericCellValue());
                        case 7 -> car.setImage(cell.getStringCellValue() );
                        case 5 -> car.setStatus(cell.getStringCellValue().toUpperCase().equals("Nuevo".toUpperCase()) ? EnumCarStatus.NEW: EnumCarStatus.USE);
                        case 6 -> car.setBrand(Brand.builder().name(cell.getStringCellValue()).build());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                cars.add(car);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return cars;
    }
}
