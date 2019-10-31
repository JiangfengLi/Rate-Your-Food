package model;

public abstract class DataObject {

	/**
	 * COMPARE TWO STRINGS
	 * checks two strings for comparison even if one or both might be null
	 * @return
	 */
	protected boolean compareTwoStrings(String stringA, String stringB) {
		boolean same = false;
		// compare emails
		if (stringA!=null) {
			if (stringA.equals(stringB)) {
				same = true;
			}
		} else {
			if (stringB == null) {
				same = true;
			}
		}
		return same;
	}
	
}
