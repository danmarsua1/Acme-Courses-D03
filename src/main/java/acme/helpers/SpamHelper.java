package acme.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SpamRecord;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;

@Service
public class SpamHelper {
	
	@Autowired
	private AdministratorConfigurationRepository configuration;

	public List<SpamRecord> convertStringToSpamRecords(String stringSpamRecords) {

		List<SpamRecord> res = new ArrayList<SpamRecord>();
		SpamRecord newSpamRecord = null;

		List<String> spamRecords = Arrays.asList(stringSpamRecords.split(","));
		for (String oldSpamRecord : spamRecords) {
			String[] terms = oldSpamRecord.split("-");
			newSpamRecord = new SpamRecord(terms[0], Double.valueOf(terms[1]), "X".equals(terms[2]) ? "" : terms[2]);
			res.add(newSpamRecord);
		}

		return res;

	}
	
	public boolean isSpamText(String stringSpamRecords, String text) {
		
		boolean res = false;
		Double totalFactor = 0.0;
		Double spamBooster = this.configuration.findConfiguration().getSpamBooster();
		Double spamThreshold = this.configuration.findConfiguration().getSpamThreshold();
		
		List<SpamRecord> spamRecords = this.convertStringToSpamRecords(stringSpamRecords);
		
		for (SpamRecord sr:spamRecords) {
			totalFactor += this.applySpamFactorByText(sr, text, spamBooster);
		}
		
		return totalFactor > spamThreshold || res;
	}
	
	public Double applySpamFactorByText(SpamRecord spamRecords, String text, Double spamBooster) {
		
		Double totalWeight;
		Double totalBoosted = 0.0;
		
		String term = spamRecords.getTerm();
		Double termWeight = spamRecords.getWeight();
		String termBooster = spamRecords.getBoosterTerm();
		
		
		String str = StringUtils.lowerCase(text);
		
		int countTerm = this.countOccurrences(str, term);
		totalWeight = countTerm * termWeight;
		
		if(countTerm>0) {
			int countTermBooster = (termBooster==null || termBooster.equals("")) ? 0 : this.countOccurrences(str, termBooster);
			totalBoosted = countTermBooster * spamBooster;
		}
		
		return totalWeight + totalBoosted;
	}
	
	public int countOccurrences(String text, String word)
	{
	    // split the string by spaces in a
	    String[] a = text.split(" ");
	 
	    // search for pattern in a
	    int count = 0;
	    for (int i = 0; i < a.length; i++)
	    {
	    // if exact match found increase count
	    if (word.equals(a[i]))
	        count++;
	    }
	 
	    return count;
	}
}
