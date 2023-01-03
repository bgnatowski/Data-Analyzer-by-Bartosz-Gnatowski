package agh.inzapp.inzynierka;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.util.TableTools;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

public class TestCreateTable {
	String u1 = "ul1zgod";
	String u2 = "ul2zgod";
	String u3 = "ul3zgod";
	String asy = "asyzgod";
	String plt1 = "pltl1zgod";
	String plt2 = "pltl2zgod";
	String plt3 = "pltl3zgod";
	String thd1 = "thdl1zgod";
	String thd2 = "thdl2zgod";
	String thd3 = "thdl3zgod";
	String h1= "harml1zgod";
	String h2= "harml2zgod";
	String h3= "harml3zgod";
	static HashMap<String, Object> data = new HashMap<>();
	{
		data.put(u1, "TAK");
		data.put(u2, "TAK");
		data.put(u3, "TAK");
		data.put(asy, "TAK");
		data.put(plt1, "TAK");
		data.put(plt2, "TAK");
		data.put(plt3, "TAK");
		data.put(thd1, "TAK");
		data.put(thd2, "TAK");
		data.put(thd3, "TAK");
		data.put(h1, "TAK");
		data.put(h2, "TAK");
		data.put(h3, "TAK");
	}
	public static void main(String[] args) throws IOException {
//		XWPFDocument document = new XWPFDocument();
//		// utworzenie nowej tabeli o 4 wierszach i 4 kolumnach.
//		XWPFTable table = document.createTable(4, 4);
//
//		// ustawienie szerokości kolumn na równe
//		CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
//		width.setType(STTblWidth.DXA);
//		width.setW(BigInteger.valueOf(9072));
//
//		// pętla iterująca po wierszach i komórkach tabeli
//		for (int i = 0; i < table.getNumberOfRows(); i++) {
//			XWPFTableRow row = table.getRow(i);
//			for (int j = 0; j < row.getTableCells().size(); j++) {
//				XWPFTableCell cell = row.getCell(j);
//
//				// jeśli jest to ostatnia kolumna, podziel ją na 3 wiersze
//				if (j == 3) {
//					XWPFParagraph p = cell.getParagraphs().get(0);
//					XWPFRun r = p.createRun();
//					r.setText("Row 1");
//					r.addBreak();
//					r.setText("Row 2");
//					r.addBreak();
//					r.setText("Row 3");
//				} else {
//					// w przeciwnym razie wpisz do komórki numer wiersza i kolumny
//					cell.setText(i + "," + j);
//				}
//			}
//		}

		RowRenderData header = Rows.of("", "Kryterium", "", "Zgodność")
				.textColor("FFFFFF")
				.bgColor("ff9800")
				.center()
				.rowHeight(2.5f)
				.create();
		RowRenderData row0 = Rows.create("Wartość skuteczna napięcia [V]",
				"w każdym tygodniu 95 % ze zbioru 10 minutowych średnich wartości skutecznych napięcia zasilającego powinno mieścić się \n" +
						"w przedziale odchyleń ±10 % napięcia znamionowego;\n");
		RowRenderData row1 = Rows.create("Współczynnik asymetrii napięcia [%]",
				"w ciągu każdego tygodnia 95 % ze zbioru 10-minutowych średnich wartości skutecznych:\n" +
						"a) składowej symetrycznej kolejności przeciwnej napięcia zasilającego powinno mieścić się \n" +
						"w przedziale od 0 % do 2 % wartości składowej\n" +
						"kolejności zgodnej;\n");
		RowRenderData row2 = Rows.create("Współczynnik uciążliwości migotania światła Plt", "przez 95 % czasu każdego tygodnia wskaźnik długookresowego migotania światła Plt spowodowanego wahaniami napięcia zasilającego nie powinien być większy od 1;");
		TableRenderData table = Tables.create(header, row0, row1, row2);

		data.put("table_one", table);
		XWPFTemplate compile = XWPFTemplate.compile("src/main/resources/data/test_szablon.docx");
		compile.render(data);
		compile.writeToFile("src/main/resources/data/test_output.docx");
		compile.close();
	}
}
