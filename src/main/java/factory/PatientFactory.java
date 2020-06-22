package factory;

import java.util.Date;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;

public class PatientFactory {

	public Patient createPatient(String[] data, Date birthdate) {

		// Create a patient object
		Patient patient = new Patient();
		patient.addIdentifier().setSystem(data[2]).setValue(data[3]);
		patient.addName().setFamily(data[0]).addGiven(data[1]);
		patient.setGender(AdministrativeGender.MALE);
		patient.setBirthDate(birthdate); 

		// Give the patient a temporary UUID so that other resources in
		// the transaction can refer to it
		patient.setId(IdType.newRandomUuid());

		return patient;
	}
}
