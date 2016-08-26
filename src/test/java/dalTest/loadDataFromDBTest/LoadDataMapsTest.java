package dalTest.loadDataFromDBTest;

import com.story.dataAccessLayer.dataActions.RetrieveMapsAction;
import com.story.dataAccessLayer.dataDescriptors.MapDescriptor;
import org.junit.Test;


import static  org.junit.Assert.*;
/**
 * Created by User on 22.08.2016.
 */
//This TestClass designed to check the correctness of the RetriveMapsAction class  with the database
public class LoadDataMapsTest {

    RetrieveMapsAction retrieveMapsAction; //This veriable using for conection to database

    //This method checks correctness of connection to the database
    @Test
    public void SuccessConnectTest(){
        retrieveMapsAction = new RetrieveMapsAction("testDB.sqlite");
        assertNotNull(retrieveMapsAction);
    }

    //This method check the behavior of the object when transfer to him incorrect links
    @Test
    public void UseIncorrectUrl(){
        retrieveMapsAction = new RetrieveMapsAction("test.sqlite");
        assertNull(retrieveMapsAction);
    }

    //This method checks correctness of the data obtained from the database
    @Test
    public void DataBaseTest() {
        retrieveMapsAction = new RetrieveMapsAction("testDB.sqlite");

        MapDescriptor mapDescriptor = retrieveMapsAction.retrieveObjectById(1);
        assertNotNull(mapDescriptor);
        assertEquals("First map", mapDescriptor.getName());
        assertEquals("Test description", mapDescriptor.getDescription());
        assertEquals("resources/1.tmx", mapDescriptor.getPathToTMX());
    }

    //This method check the behavior of the object mapDescriptor in assigning incorrect data
    @Test
    public void UseIncorrectId(){
        retrieveMapsAction = new RetrieveMapsAction("testDB.sqlite");

        MapDescriptor mapDescriptor = retrieveMapsAction.retrieveObjectById(3);
        assertNull(mapDescriptor);
    }

}
