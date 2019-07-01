package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectRepository;
import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserModelResolver implements GraphQLResolver<YacserModel> {

	public YacserModelResolver() {
	}

	/**
	 * Get the name of the YACSER model.
	 * 
	 * @param yacserModel the YACSER model instance
	 * @return the name string
	 * @throws IOException
	 */
	public String getName(YacserModel yacserModel) throws IOException {
		return (String) YacserObjectRepository.getLiteral(yacserModel.getId(), YacserObjectRepository.SKOS_PREF_LABEL);
	}

	/**
	 * Get the description of the YACSER object.
	 * 
	 * @param yacserModel the YACSER object instance
	 * @return the description string
	 * @throws IOException
	 */
	public String getDescription(YacserModel yacserModel) throws IOException {
		return (String) YacserObjectRepository.getLiteral(yacserModel.getId(), YacserObjectRepository.DB_DESCRIPTION);
	}

}
