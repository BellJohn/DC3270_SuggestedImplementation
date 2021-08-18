package unifiedPointsCalculator.components;

import java.util.Date;

public class Grade implements Comparable<Grade> {

	private float points;
	private SubjectType subjectType;
	private String lowerBound;
	private String upperBound;
	private String subject;
	private boolean verified;
	private GradeType gradeType;
	private Date date;

	public void setPoints(float points) {
		this.points = points;
	}

	public SubjectType getSubjectType() {
		return subjectType;
	}

	public String getLowerBound() {
		return lowerBound;
	}

	public String getUpperBound() {
		return upperBound;
	}

	public String getSubject() {
		return subject;
	}

	public float getPoints() {
		return points;
	}

	public boolean isActual() {
		return gradeType.equals(GradeType.ACTUAL);
	}

	public boolean isVerified() {
		return verified;
	}

	public Date getDate() {
		return date;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;

	}

	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}

	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Grade other) {
		return Float.compare(other.getPoints(), this.getPoints());
	}
	
}
