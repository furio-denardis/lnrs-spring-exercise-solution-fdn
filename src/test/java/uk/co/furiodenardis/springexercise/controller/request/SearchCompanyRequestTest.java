package uk.co.furiodenardis.springexercise.controller.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SearchCompanyRequestTest {

    @ParameterizedTest
    @CsvSource({
            ",,false",              // both null
            ",'',false",            // one null one empty
            "'',,false",            // one null one empty
            "'','',false",          // both empty
            "prova,,true",          // one null one non empty
            "prova,'',true",        // one empty one non empty
            ",12345,true",          // one null one non empty
            "'',12345,true",        // one empty one non empty
            "prova,12345,true"      // both non empty
    })
    void testSearchCriteriaValidationAtLeastOneNonEmpty(String companyName, String companyNo, boolean expected) {
        SearchCompanyRequest request = new SearchCompanyRequest(companyName, companyNo);

        boolean actual = request.atLeastOneSearchCriteria();

        assertEquals(expected, actual);
    }
}
