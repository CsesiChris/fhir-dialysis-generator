package factory;

import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;

public class ObservationFactory {

	public Observation createObservation(Patient patient, Procedure procedure, String[] data, double value) {
		
		// Create an observation object
		Observation observation = new Observation();
		observation.setStatus(Observation.ObservationStatus.FINAL);
		
		observation.addPartOf(new Reference(procedure.getIdElement().getValue()));
		
		observation
			.getCode()
			.addCoding()
			.setSystem(data[0])
			.setCode(data[1])
			.setDisplay(data[2]);
		observation
			.setValue(
					new Quantity()
					.setValue(value)
					.setUnit(data[3])
					.setSystem("http://unitsofmeasure.org")
					.setCode(data[4]));

		// The observation refers to the patient using the ID, which is already
		// set to a temporary UUID
		observation.setSubject(new Reference(patient.getIdElement().getValue()));
		
		return observation;
	}
}
