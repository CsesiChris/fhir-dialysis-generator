package factory;

import java.text.ParseException;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationStatement;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Timing;
import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;
import org.hl7.fhir.r4.model.Timing.UnitsOfTime;

public class MedicationStatementFactory extends AbstractFactory {

	public MedicationStatement createMedicationStatement(Patient patient, Procedure procedure) {
		
		// Create an observation object
		MedicationStatement medStmt = new MedicationStatement();
	
		medStmt.setStatus(MedicationStatement.MedicationStatementStatus.ACTIVE);
		
		medStmt.addPartOf(new Reference(procedure.getIdElement().getValue()));
		
		medStmt
			.getCategory()
			.addCoding()
			.setSystem("http://terminology.hl7.org/CodeSystem/medication-statement-category")
			.setCode("inpatient")
			.setDisplay("Inpatient");
		
		try {
			medStmt.setDateAsserted(df.parse("2015-02-22"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		medStmt.setInformationSource(new Reference(patient.getIdElement().getValue()));
		
		medStmt
		.getReasonCode()
		.add(
			new CodeableConcept()
				.addCoding(
					new Coding()
						.setSystem("http://snomed.info/sct")
						.setCode("32914008")
						.setDisplay("Restless Legs")));
		
		
		Timing.TimingRepeatComponent repeat = 
			new TimingRepeatComponent();
		repeat
		.setFrequency(1)
		.setPeriod(1)
		.setPeriodUnit(UnitsOfTime.D);
		
		medStmt
		.getDosage()
		.add(
			new Dosage()
				.setSequence(1)
				.setText("1-2 tablets once daily at bedtime as needed for restless legs")
				.setTiming(new Timing().setRepeat(repeat)));

		// The observation refers to the patient using the ID, which is already
		// set to a temporary UUID
		medStmt.setSubject(new Reference(patient.getIdElement().getValue()));
		
		return medStmt;
	}
	
	/**
	
{
	"resourceType": "MedicationStatement",
	"id": "178",
	"text": {
		"status": "generated",
		"div": "<div xmlns=\"http://www.w3.org/1999/xhtml\"><p><b>Generated Narrative with Details</b></p></div>"
	},
	"status": "active",
	"category": {
		"coding": [
			{
				"system": "http://terminology.hl7.org/CodeSystem/medication-statement-category",
				"code": "inpatient",
				"display": "Inpatient"
			}
		]
	},
	"medicationReference": {
		"reference": "Medication/177"
	},
	"subject": {
		"reference": "Patient/173"
	},
	"effectiveDateTime": "2015-01-23",
	"dateAsserted": "2015-02-22",
	"informationSource": {
		"reference": "Patient/173"
	},
	"reasonCode": [
		{
			"coding": [
				{
					"system": "http://snomed.info/sct",
					"code": "32914008",
					"display": "Restless Legs"
				}
			]
		}
	],
	"note": [
		{
			"text": "Patient indicates they miss the occasional dose"
		}
	],
	"dosage": [
		{
			"sequence": 1,
			"text": "1-2 tablets once daily at bedtime as needed for restless legs",
			"additionalInstruction": [
				{
					"text": "Taking at bedtime"
				}
			],
			"timing": {
				"repeat": {
					"frequency": 1,
					"period": 1,
					"periodUnit": "d"
				}
			},
			"asNeededCodeableConcept": {
				"coding": [
					{
						"system": "http://snomed.info/sct",
						"code": "32914008",
						"display": "Restless Legs"
					}
				]
			},
			"route": {
				"coding": [
					{
						"system": "http://snomed.info/sct",
						"code": "26643006",
						"display": "Oral Route"
					}
				]
			},
			"doseAndRate": [
				{
					"type": {
						"coding": [
							{
								"system": "http://terminology.hl7.org/CodeSystem/dose-rate-type",
								"code": "ordered",
								"display": "Ordered"
							}
						]
					},
					"doseRange": {
						"low": {
							"value": 1,
							"unit": "TAB",
							"system": "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm",
							"code": "TAB"
						},
						"high": {
							"value": 2,
							"unit": "TAB",
							"system": "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm",
							"code": "TAB"
						}
					}
				}
			]
		}
	]
}	 
	 
	 
	 
	 
	 
	 
	 */
	
	
}
