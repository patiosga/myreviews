package api;

import jdk.jshell.execution.Util;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ManageAccommodationsTest {
    private ManageAccommodations accommodationsManager;
    private Accommodation acc1;
    private Accommodation acc2;
    private Accommodation acc3;
    private Provider provider1;
    private Provider provider2;
    private Location loc;
    private ArrayList<Utility> utilities;

    @Before
    public void setUp() throws Exception {
        accommodationsManager = new ManageAccommodations();
        loc = new Location("","","");
        provider1 = new Provider("John","Paps","provider1","pass","provider");
        provider2 = new Provider("John2","Paps2","provider2","pass","provider");
        acc1 = new Accommodation("Tester1","", "", loc, provider1);
        acc2 = new Accommodation("Tester2","","",loc, provider1);
        acc3 = new Accommodation("Tester2", "", "", loc, provider2);
        utilities = new ArrayList<>();
        Utility utility1 = new Utility();
        utility1.addSpecificUtility("pistolaki");
        utility1.addSpecificUtility("sapouni");
        utilities.add(utility1);
        Utility utility2 = new Utility();
        utility2.addSpecificUtility("thea1");
        utility2.addSpecificUtility("thea2");
        utilities.add(utility2);
    }

    @Test
    public void addAccommodation() {
        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).size(), 0);
        assertFalse(accommodationsManager.accommodationExists(acc1));
        assertTrue(accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1));
        assertFalse(accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1));
        assertFalse(accommodationsManager.accommodationExists(acc2));
        assertTrue(accommodationsManager.accommodationExists(acc1));
        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).size(), 1);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider2).size(), 0);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).get(0), acc1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider2);

        assertTrue(accommodationsManager.accommodationExists(acc1));
        assertTrue(accommodationsManager.accommodationExists(acc2));
        assertTrue(accommodationsManager.accommodationExists(acc3));

        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).size(), 2);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider2).size(), 1);

        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).get(0), acc1);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).get(1), acc2);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider2).get(0), acc3);
    }

    @Test
    public void removeAccommodation() {
        accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1);
        //accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider2);

        assertFalse(accommodationsManager.removeAccommodation(acc2));
        assertTrue(accommodationsManager.removeAccommodation(acc1));
        assertEquals(accommodationsManager.getProvidersAccommodations(provider1).size(), 0);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider2).size(), 1);
        assertEquals(accommodationsManager.getProvidersAccommodations(provider2).get(0), acc3);
    }

    @Test
    public void alterAccommodationDetails() {
        accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider2);

        accommodationsManager.alterAccommodationDetails(acc1,"Tester15555","ch", "ch", "ch","ch","chaaanges");

        Accommodation temp = accommodationsManager.getProvidersAccommodations(provider1).get(1); //ξαναπροστίθεται στο τέλος
        assertEquals(temp.getLocation().getAddress(), "ch");
        assertEquals(temp.getLocation().getPostCode(), "chaaanges");
        assertEquals(temp.getLocation().getTown(), "ch");
        assertEquals(temp.getName(), "Tester15555");
        assertEquals(temp.getSingularId(), "Tester15555".hashCode() + "provider1".hashCode());
        assertEquals(temp.getStayType(), "ch");

        accommodationsManager.alterAccommodationDetails(acc3,"Tester15555","ch", "ch", "ch","ch","chaaanges");

        temp = accommodationsManager.getProvidersAccommodations(provider2).get(0);
        assertEquals(temp.getLocation().getAddress(), "ch");
        assertEquals(temp.getLocation().getPostCode(), "chaaanges");
        assertEquals(temp.getLocation().getTown(), "ch");
        assertEquals(temp.getName(), "Tester15555");
        assertEquals(temp.getSingularId(), "Tester15555".hashCode() + "provider2".hashCode());
        assertEquals(temp.getStayType(), "ch");
    }

    @Test
    public void alterAccommodationUtilities() {
        Utility extra = new Utility();
        extra.addSpecificUtility("extra");
        ArrayList<Utility> tempUtilities = new ArrayList<>();
        tempUtilities.add(utilities.get(1));
        tempUtilities.add(extra);
        assertFalse(accommodationsManager.alterAccommodationUtilities(acc1, tempUtilities));
        accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider2);
        assertTrue(accommodationsManager.alterAccommodationUtilities(acc1, tempUtilities));
        assertEquals(acc1.getTypesOfUtilities().get(1).getSpecifics().get(0), "extra");
    }

    @Test
    public void isProvidersAccommodation() {
        accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1);
        accommodationsManager.addAccommodation("Tester2","","","","","",utilities, provider2);

        assertTrue(accommodationsManager.isProvidersAccommodation(provider1 ,acc1));
        assertTrue(accommodationsManager.isProvidersAccommodation(provider2 ,acc3));
        assertFalse(accommodationsManager.isProvidersAccommodation(provider2 ,acc1));
    }

    //@Test
//    public void checkSubmissionInaccuracies() {
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("", "", "", "","",""), "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.");
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("egf ef", "f efg ", "fv ef", "f ","v",""), "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.");
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("   ", "  ", "  ", " "," "," "), "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.");
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("a", "a", "a", "a","a","a"), "Παρακαλώ δηλώστε τον τύπο του καταλύματος ως Ξενοδοχείο, Διαμέρισμα ή Μεζονέτα.");
//        accommodationsManager.addAccommodation("Tester1","", "", "","","", utilities,provider1);
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("Tester1", "a", "Ξενοδοχείο", "a","a","a"), "Έχετε ήδη καταχωρήσει κατάλυμα με αυτό το όνομα παρακαλώ επιλέξτε άλλο");
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("α", "a", "Μεζονέτα", "α","α","α"), "Παρακαλώ εισάγετε αριθμό στο πεδίο του ταχυδρομικού κώδικα.");
//        assertEquals(accommodationsManager.checkSubmissionInaccuracies("α", "α", "Διαμέρισμα", "α","α","-1243"), "Εισάγετε έγκυρο ταχυδρομικό κώδικα");
//        assertNull(accommodationsManager.checkSubmissionInaccuracies("α", "α", "Διαμέρισμα", "α", "α", "1235"));
//}

    @Test
    public void searchAccommodations() {
        accommodationsManager.addAccommodation("Tester1","", "Διαμέρισμα", "","","", utilities,provider1);
        accommodationsManager.addAccommodation("Tester2","","Μεζονέτα","","","",utilities, provider1);
        accommodationsManager.addAccommodation("Tester2","","Διαμέρισμα","","","",utilities, provider2);

        ArrayList<Utility> emptyList = new ArrayList<>();
        ArrayList<Accommodation> results = new ArrayList<>();

        assertEquals(accommodationsManager.searchAccommodations("a","","",emptyList), results);
        results.add(acc2);
        assertEquals(accommodationsManager.searchAccommodations("","Μεζονέτα","",emptyList), results);
        results = new ArrayList<>();
        results.add(acc1);
        results.add(acc3);
        assertEquals(accommodationsManager.searchAccommodations("","Διαμέρισμα","",emptyList), results);
        results = new ArrayList<>();
        results.add(acc1);
        results.add(acc2);
        results.add(acc3);
        assertEquals(accommodationsManager.searchAccommodations("","","",emptyList), results);
        assertEquals(accommodationsManager.searchAccommodations("","","",utilities), results);

        results = new ArrayList<>();
        results.add(acc1);
        ArrayList<Utility> notEmpty = new ArrayList<>();
        Utility utility2 = new Utility();
        utility2.addSpecificUtility("thea1");
        utility2.addSpecificUtility("thea2");
        notEmpty.add(utility2);

        accommodationsManager.alterAccommodationUtilities(acc1, notEmpty);

        assertEquals(accommodationsManager.searchAccommodations("","","", notEmpty), results);
        notEmpty.remove(utility2);
        utility2 = new Utility();
        utility2.addSpecificUtility("thea1");
        notEmpty.add(utility2);
        assertEquals(accommodationsManager.searchAccommodations("","","", notEmpty), results);
    }
}