package factory;

import java.text.ParseException;
import java.util.Date;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MedicationStatement;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Procedure;

import com.github.javafaker.Faker;

public class BundleFactory extends AbstractFactory  {
	
	private PatientFactory patientFactory = new PatientFactory();
	private ProcedureFactory procedureFactory = new ProcedureFactory();
	private ObservationFactory observationFactory = new ObservationFactory();
	private MedicationStatementFactory medicationStatementFactory = new MedicationStatementFactory();
	
	public Bundle createBundle() {

	    Faker faker = new Faker();
	    
	    String patientId = faker.code().ean8();
	    Date birthdate = null;
	    try {
			birthdate = df.parse(faker.business().creditCardExpiry());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String[] p1 = new String[] {faker.name().lastName(), faker.name().firstName(), "http://acme.org/mrns", patientId};
								   // CodingSystem, Code, CodingDisplay, Unit, UnitCode
		String[] o1 = new String[] {"https://fhir.loinc.org", "789-8", "Erythrocytes [#/volume] in Blood by Automated count", "10 trillion/L", "10*12/L"};
		String[] o2 = new String[] {"https://fhir.loinc.org", "1988-5", "C reactive protein [Mass/volume] in Serum or Plasma", "g/dL", "g/dL"};
		
		Patient patient = patientFactory.createPatient(p1, birthdate);
		Procedure procedure = procedureFactory.createProcedure(patient, faker.code().ean13());
		Observation observation = observationFactory.createObservation(patient, procedure, o1, 4.12);
		Observation observation2 = observationFactory.createObservation(patient, procedure, o2, 17.14301557752162);
		MedicationStatement medStmt = medicationStatementFactory.createMedicationStatement(patient, procedure);

		// Create a bundle that will be used as a transaction
		Bundle bundle = new Bundle();
		bundle.setType(Bundle.BundleType.TRANSACTION);

		// Add the patient as an entry. This entry is a POST with an
		// If-None-Exist header (conditional create) meaning that it
		// will only be created if there isn't already a Patient with
		// the identifier 12345
		bundle
			.addEntry()
			.setFullUrl(patient.getIdElement().getValue())
			.setResource(patient)
			.getRequest()
			.setUrl("Patient")
			.setIfNoneExist("identifier=http://acme.org/mrns|" + patientId)
			.setMethod(Bundle.HTTPVerb.POST);

		bundle
			.addEntry()
			.setFullUrl(procedure.getIdElement().getValue())
			.setResource(procedure)
			.getRequest()
			.setUrl("Procedure")
			.setMethod(Bundle.HTTPVerb.POST);
		
		// Add the observation. This entry is a POST with no header
		// (normal create) meaning that it will be created even if
		// a similar resource already exists.
		bundle
			.addEntry()
			.setResource(observation)
			.getRequest()
			.setUrl("Observation")
			.setMethod(Bundle.HTTPVerb.POST);
		
		bundle
			.addEntry()
			.setResource(observation2)
			.getRequest()
			.setUrl("Observation")
			.setMethod(Bundle.HTTPVerb.POST);
		
		bundle
			.addEntry()
			.setResource(medStmt)
			.getRequest()
			.setUrl("MedicationStatement")
			.setMethod(Bundle.HTTPVerb.POST);

		return bundle;
	}
}
