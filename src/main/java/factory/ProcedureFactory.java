package factory;

import java.util.Date;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Reference;

public class ProcedureFactory {

	public Procedure createProcedure(Patient patient, String kfhIdentifier) {

		// Create a patient object
		Procedure procedure = new Procedure();

		// Give the procedure a temporary UUID so that other resources in
		// the transaction can refer to it
		procedure.setId(IdType.newRandomUuid());
		procedure.addIdentifier().setSystem("www.kfh.de/disweb/dialysis").setValue(kfhIdentifier);
		
		procedure.setSubject(new Reference(patient.getIdElement().getValue()));
		
		procedure.setStatus(Procedure.ProcedureStatus.COMPLETED);
		
		procedure.getPerformedDateTimeType().setValue(new Date());
		
		procedure
			.getCode()
			.addCoding()
			.setSystem("http://snomed.info/sct")
			.setCode("238318009")
			.setDisplay("Continuous ambulatory peritoneal dialysis (procedure)");

		return procedure;
	}
}

/**
 
 {
	"resourceType": "Procedure",
	"id": "244",
	"text": {
		"status": "additional",
		"div": "The dialyse process itself"
	},
	"status": "completed",
	"code": {
		"coding": [
			{
				"system": "http://snomed.info/sct",
				"code": "238318009",
				"display": "Continuous ambulatory peritoneal dialysis (procedure)"
			}
		]
	},
	"subject": {
		"reference": "Patient/173"
	},
	"performedDateTime": "2020-05-25T19:41:15.268Z"
 }

 */
