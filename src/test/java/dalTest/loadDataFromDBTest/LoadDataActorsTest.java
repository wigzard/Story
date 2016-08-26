package dalTest.loadDataFromDBTest;

import com.story.dataAccessLayer.dataActions.RetrieveActorAction;
import com.story.dataAccessLayer.dataDescriptors.ActorDescriptor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 22.08.2016.
 */
//This TestClass designed to check the correctness of the RetriveActorAction class  with the database
public class LoadDataActorsTest {

    RetrieveActorAction retrieveActorAction; //This veriable using for conection to database

    //This method checks correctness of connection to the database
    @Test
    public void SuccessConnectTest(){
        retrieveActorAction = new RetrieveActorAction("testDB.sqlite");
        assertNotNull(retrieveActorAction);
    }

    //This method check the behavior of the object when transfer to him incorrect links
    @Test
    public void UseIncorrectUrl(){
        retrieveActorAction = new RetrieveActorAction("test.sqlite");
        assertNull(retrieveActorAction);
    }

    //This method checks correctness of the data obtained from the database
    @Test
    public void DataBaseTest() {
        retrieveActorAction = new RetrieveActorAction("testDB.sqlite");

        ActorDescriptor actorDescriptor = retrieveActorAction.retrievePersonById(1);
        assertNotNull(actorDescriptor);
        assertEquals("First map", actorDescriptor.getName());
        assertEquals("resources/faces.tmx", actorDescriptor.getSpriteSheetPath());
    }

    //This method check the behavior of the object ActorDescriptor in assigning incorrect data
    @Test
    public void UseIncorrectId(){
        retrieveActorAction = new RetrieveActorAction("testDB.sqlite");

        ActorDescriptor actorDescriptor = retrieveActorAction.retrievePersonById(4);
        assertNull(actorDescriptor);
    }

}
