package es.bifacia.bgg.service.excel.impl;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import es.bifacia.bgg.bean.GameOwners;
import es.bifacia.bgg.service.excel.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.common.usermodel.HyperlinkType;

@Service
public class ExcelServiceImpl implements ExcelService {
    private static final String COLLECTIONS_SHEET_NAME = "Colecciones";
    public static final String GAMES_OWNERS_FILE_PATH = "/Users/alejandro.calle/varios/gamesOwners.xlsx";

    public ExcelServiceImpl() {
        super();
    }

    /**
     * Creates an Excel file with all the information of the games by its owners
     * @param gamesOwnersMap Map with the information of the games owners.
     * @throws Exception
     */
    public void createGamesOwnersExcel(final Map<Long, GameOwners> gamesOwnersMap, final String filePath) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            createGamesOwnersSheet(workbook);
            final CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            addGamesOwnersToSheet(workbook, gamesOwnersMap, style);
            saveExcelFile(workbook, filePath);
        }
    }

    private void addGamesOwnersToSheet(final Workbook workbook, final Map<Long, GameOwners> gamesOwnersMap, final CellStyle cellStyle) {
        int rowNumber = 1;
        final Sheet sheet = workbook.getSheet(COLLECTIONS_SHEET_NAME);
        for (final Map.Entry<Long, GameOwners> entry : gamesOwnersMap.entrySet()) {
            final GameOwners gameOwners = entry.getValue();
            final Row row = sheet.createRow(rowNumber);
            final String ownersString = getOwnersString(gameOwners.getOwners());
            Cell cell = row.createCell(0);
            cell.setCellValue(gameOwners.getGame().getName());
            cell.setCellStyle(cellStyle);
            final Hyperlink link = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
            link.setAddress("https://boardgamegeek.com/boardgame/" + entry.getKey());
            cell.setHyperlink(link);

            cell = row.createCell(1);
            cell.setCellValue(ownersString);
            cell.setCellStyle(cellStyle);
            rowNumber++;
        }
//        final SortedSet<String> keys = new TreeSet<>(gamesOwnersMap.keySet());
//        for (final String key : keys) {
//            final Row row = sheet.createRow(rowNumber);
//            final List<String> owners = gamesOwnersMap.get(key);
//            final String ownersString = getOwnersString(owners);
//            Cell cell = row.createCell(0);
//            cell.setCellValue(key);
//            cell.setCellStyle(cellStyle);
//
//            cell = row.createCell(1);
//            cell.setCellValue(ownersString);
//            cell.setCellStyle(cellStyle);
//            rowNumber++;
//        }
    }

    private void createGamesOwnersSheet(final Workbook workbook) {
        final Sheet sheet = workbook.createSheet(COLLECTIONS_SHEET_NAME);
        sheet.setColumnWidth(0, 18000);
        sheet.setColumnWidth(1, 18000);

        final Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Juego");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Propietarios");
        headerCell.setCellStyle(headerStyle);
    }

    private String getOwnersString(final List<String> owners) {
        String ownersString = "";
        boolean first = true;
        for(final String owner : owners) {
            if (first) {
                first = false;
            } else {
                ownersString += ", ";
            }
            ownersString += owner;
        }
        return ownersString;
    }

    private void saveExcelFile(final Workbook workbook, final String excelFilePath) throws Exception {
        try (final FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

}
