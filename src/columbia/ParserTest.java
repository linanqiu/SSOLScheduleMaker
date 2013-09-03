package columbia;

import java.io.IOException;
import java.text.ParseException;

public class ParserTest {
	public static void main(String[] args) throws ParseException, IOException {
		SSOLParser test = new SSOLParser(
				"Fall 2013 Student Schedule by Class\r\nClass\tGrading\tInstructor\tDay\tTime/Location\tStart/End\r\nBUSI W3013 sec 001\r\nFINANCIAL ACCOUNTING\tLetter Grd\r\n3.00(Fix)\tMingcherng Deng\r\nmd2012@columbia.edu\tTu Th\t5:40pm-6:55pm\r\n602 HAMILTON\t09/03/13\r\n12/08/13\r\nCOCI C1101 sec 048\r\nCONTEMP WESTERN CIVILIZAT\tLetter Grd\r\n4.00(Fix)\tMana Kia\r\nmk3586@columbia.edu\tTu Th\t2:10pm-4:00pm\r\n424 PUPIN\t09/03/13\r\n12/08/13\r\nCOMS W3203 sec 001\r\nINTRO-COMBINATORICS/GRAPH\tLetter Grd\r\n3.00(Fix)\tJonathan Gross\r\njlg2@columbia.edu\tMo We\t1:10pm-2:25pm\r\n833 MUDD\t09/03/13\r\n12/08/13\r\nECON V3025 sec 001\r\nFINANCIAL ECONOMICS\tLetter Grd\r\n3.00(Fix)\tSally Davidson\r\nsjm7@columbia.edu\tTu Th\t11:40am-12:55pm\r\n309 HAVEMEYER\t09/03/13\r\n12/08/13\r\nECON W4700 sec 001\r\nFINANCIAL CRISES\tLetter Grd\r\n3.00(Fix)\tJose Scheinkman\r\njs3317@columbia.edu\tMo We\t11:40am-12:55pm\r\n5 KRAFT CENTER\t09/03/13\r\n12/08/13\r\nECON W4750 sec 001\r\nGLOBALIZATION & ITS RISKS\tLetter Grd\r\n3.00(Fix)\tGraciela Chichilnisky\r\ngc9@columbia.edu\tMo We\t5:40pm-6:55pm\r\n413 KENT\t09/03/13\r\n12/08/13\r\nMUSI W2515 sec 006\r\nINTERMEDTE PIANO INSTRUCT\tP/F Grade\r\n1.00(Var)\tReiko Uchida\r\nru2001@columbia.edu\t \t\r\n \t09/01/13\r\n12/30/13\r\n");
		
		test.parse();
		
		CalendarMaker maker = new CalendarMaker();
		maker.makeFiles(test.getSections());
	}
}
