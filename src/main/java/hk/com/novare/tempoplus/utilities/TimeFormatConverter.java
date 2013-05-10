package hk.com.novare.tempoplus.utilities;

import hk.com.novare.tempoplus.bmnmanager.biometric.Biometric;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimeFormatConverter {

	public ArrayList<Biometric> convertToDateTime(ArrayList<Biometric> list) {
		Date parseDate = null;
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		for (Biometric biometric : list) {

			try {
				parseDate = dateTimeFormat.parse(biometric.getDate() + " " + biometric.getTime());
				biometric.setTime(dateTimeFormat.format(parseDate));
//				System.out.println(dateTimeFormat.format(parseDate));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
