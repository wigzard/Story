package dalTest.DataActionsTest;

import com.story.dataAccessLayer.dataActions.RetrieveMapsAction;
import com.story.dataAccessLayer.dataDescriptors.MapDescriptor;
import org.junit.Test;
import org.lwjgl.Sys;


import static  org.junit.Assert.*;
/**
 * Created by User on 22.08.2016.
 */
public class RetrieveMapsActionTest {

    String nameTest = "First map";
    String descriptionTest = "Test description";
    String pathToFileTest = "resources/1.tmx";

    @Test
    public void DataBaseTest(){
        RetrieveMapsAction retrieveMapsAction = new RetrieveMapsAction("testDB.sqlite");
//        проверка на корректность подключения к БД
        assertNotNull(retrieveMapsAction);

//        проверка значений БД
        MapDescriptor mapDescriptor = retrieveMapsAction.retrieveObjectById(1);
        assertNotNull(mapDescriptor);
        assertEquals(nameTest, mapDescriptor.getName());
        assertEquals(descriptionTest, mapDescriptor.getDescription());
        assertEquals(pathToFileTest, mapDescriptor.getPathToTMX());


//        не корпектный ввод id
        mapDescriptor = retrieveMapsAction.retrieveObjectById(3);
        assertNull(mapDescriptor);



    }



}
