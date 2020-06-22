# FHIR - Dialysis Data Generator

This project is a simple generator, which creates simplified FHIR dialysis data in a defined FHIR server.

Instructions:

Executing the class `FHIR_Dialyse_Generator.java` with the following parameter:
* [0] FHIR server endpoint (mandatory)
* [1] Iterations (optional, default =1)

e.g. use of the BlackTusk Demo HAPI server and create 6 dialysis bundles for 6 differnt patients

	FHIR_Dialyse_Generator.java https://hs-preview.k8s.blacktusk.eu/hapi-fhir-jpaserver/fhir 6

	

# What is generated and how?

The implementation is based on the [HAPI FHIR starter client skeleton](https://github.com/FirelyTeam/fhirstarters/tree/master/java/hapi-fhirstarters-rest-server-skeleton) using the [HAPI Generic (Fluent) Client](https://hapifhir.io/hapi-fhir/docs/client/generic_client.html).

The randomly generated data (patient name and birth ,...) is created via [Java Faker](https://github.com/DiUS/java-faker)

Every FHIR dialysis bundle exists of

* a **Patient** with family and given name, gender and birthdate
* a **Procedure** indicating the accomplished dialyis procedure
* two monitored **Observations** during the process 
* one dispended **Medication** done during the dialysis

In the end 5 resources are created on the FHIR server for each iteration which belong together

# Contributing

