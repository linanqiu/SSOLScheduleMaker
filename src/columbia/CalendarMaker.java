package columbia;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.RecurrenceRule;
import biweekly.property.RecurrenceRule.Frequency;
import biweekly.property.Summary;

public class CalendarMaker {

	private ArrayList<File> calendarFiles;

	public static final String PREFIX = "SSOL";

	public CalendarMaker() {
		calendarFiles = new ArrayList<File>();
	}

	public void makeFiles(Collection<Section> sections) throws ParseException,
			IOException {
		int i = 0;

		for (Section section : sections) {

			if (section.getDays().length > 1) {

				i++;

				ICalendar ical = new ICalendar();
				{
					VEvent event = new VEvent();

					Summary summary = event.setSummary(section.getName());
					summary.setLanguage("en-us");

					SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yy h:mma");

					event.setDateStart(ft.parse(section.getStartDate() + " "
							+ section.getStartTime()));
					event.setDateEnd(ft.parse(section.getStartDate() + " "
							+ section.getEndTime()));

					RecurrenceRule rrule = new RecurrenceRule(Frequency.WEEKLY);

					for (String day : section.getDays()) {
						rrule.addByDay(RecurrenceRule.DayOfWeek
								.valueOfAbbr(day));
					}
					rrule.setInterval(1);

					SimpleDateFormat endFormat = new SimpleDateFormat(
							"MM/dd/yy");
					rrule.setUntil(endFormat.parse(section.getEndDate()));
					event.setRecurrenceRule(rrule);
					event.setLocation(section.getLocation());

					ical.addEvent(event);
				}

				File file = new File(PREFIX + "_" + section.getShortName()
						+ ".ics");
				Biweekly.write(ical).go(file);

			}
		}
	}
}
