import org.hl7.fhir.r4.model.Bundle;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import factory.BundleFactory;

public class FHIR_Dialyse_Generator {

	static BundleFactory bundleFactory = new BundleFactory();
	
	
	/**
	 * This is the Java main method, which gets executed
	 */
	public static void main(String[] args) {

		if (args == null || args.length < 1) {
			System.out.println("Parameter:\n[0] FHIR Server Endpoint (REQUIRED)\n[1] Number of iterations (Optional, default: 1)\n");
		}
		
		String fhirEndpoint = args[0];
		System.out.println("Defined FHIR Endpoint: " + fhirEndpoint + "\n");
		
		int nrOfBundles = 1;
		
		if (args.length == 2) {
			nrOfBundles = Integer.parseInt(args[1]);
		}

		System.out.println("Iterations: " + nrOfBundles + "\n");
		
		// Create a context
		FhirContext ctx = FhirContext.forR4();

		IGenericClient client = ctx.newRestfulGenericClient(fhirEndpoint);
		client.registerInterceptor(new BasicAuthInterceptor("testuser", "FKe3t"));

		for (int i = 1; i <= nrOfBundles; i++) {

			System.out.println("\n\n++++++++  Bundle  " + i + "  ++++++++\n\n");

			Bundle bundle = bundleFactory.createBundle();
			System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle) + "\n\n");

			Bundle resp = client.transaction().withBundle(bundle).execute();

			// Log the response
			System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));
		}

	}
}
