package columbia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SSOLParser {

	private String clipboard;
	private HashMap<String, Section> sections;

	public SSOLParser(String clipboard) {
		this.clipboard = clipboard;
		sections = new HashMap<String, Section>();
	}

	public void parse() {

		String[] lines = clipboard.split(System.getProperty("line.separator"));

		int lineCount = 0;
		Section currentSection = null;

		for (String line : lines) {
			Pattern pattern = Pattern
					.compile(".*([A-Z][A-Z][A-Z][A-Z]\\s[A-Z][0-9][0-9][0-9][0-9]\\s[a-z][a-z][a-z]\\s[0-9][0-9][0-9])");
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				lineCount = 0;
			}

			if (lineCount > -1 && lineCount < 7) {
				lineCount++;

				if (lineCount == 1) {
					currentSection = new Section();
					currentSection.setName(line.substring(0, 10));
					currentSection.setShortName(line.substring(0, 10).replace(
							" ", ""));
				}

				if (lineCount == 2) {
					if (line.indexOf("Letter Grd") > 0
							|| line.indexOf("P/F Grade") > 0) {
						line = line.replace("Letter Grd", "");
						line = line.replace("P/F Grade", "");
					}

					line = line.replace("\t", "");

					line = toTitleCase(line);

					currentSection.setName(currentSection.getName() + " "
							+ line);
				}

				if (lineCount == 3) {

				}

				if (lineCount == 4) {
					Pattern pattern4 = Pattern
							.compile("[A-Za-z0-9]+\\@[a-z]+\\.[a-z]+\\s([A-Za-z][a-z])\\s([A-Za-z][a-z])*\\s(.*)\\-(.*)");
					Matcher matcher4 = pattern4.matcher(line);

					if (matcher4.find()) {
						currentSection.setDays(new String[] {
								matcher4.group(1), matcher4.group(2) });

						currentSection.setStartTime(matcher4.group(3));
						currentSection.setEndTime(matcher4.group(4));
					}
				}

				if (lineCount == 5) {
					Pattern pattern5 = Pattern.compile("(.*)\\t(.*)");
					Matcher matcher5 = pattern5.matcher(line);

					if (matcher5.find()) {
						currentSection.setLocation(matcher5.group(1));
						currentSection.setStartDate(matcher5.group(2));
					}
				}

				if (lineCount == 6) {
					currentSection.setEndDate(line);
					lineCount = -1;

					sections.put(currentSection.getName(), currentSection);
					currentSection = null;
				}
			}
		}

		cleanUp();
	}

	private void cleanUp() {

		ArrayList<Section> tempSections = new ArrayList<Section>(
				sections.values());

		for (Section section : tempSections) {
			if (!section.isValid()) {
				sections.remove(section.getName());
				System.out.println(section.getName() + " removed");
			}
		}
	}

	public Collection<Section> getSections() {
		return sections.values();
	}

	public static String toTitleCase(String input) {
		StringBuilder titleCase = new StringBuilder();
		boolean nextTitleCase = true;

		for (char c : input.toCharArray()) {
			if (Character.isSpaceChar(c)) {
				nextTitleCase = true;
			} else if (nextTitleCase) {
				c = Character.toTitleCase(c);
				nextTitleCase = false;
			}

			titleCase.append(c);
		}

		return titleCase.toString();
	}

}
