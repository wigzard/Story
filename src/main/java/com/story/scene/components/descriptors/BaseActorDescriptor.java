package com.story.scene.components.descriptors;

import com.story.dataAccessLayer.dataActions.RetrieveActorAction;
import com.story.dataAccessLayer.dataDescriptors.ActorDescriptor;
import com.story.dataAccessLayer.dataDescriptors.BaseDescriptor;

/**
 * Created by alex on 06.08.16.
 * Represent common data for actors (player, npc)
 */
abstract class BaseActorDescriptor extends BaseDescriptor {
    private ActorDescriptor actorDescriptor;

    BaseActorDescriptor(int id) {
        super(id);
        this.actorDescriptor = new RetrieveActorAction().retrievePersonById(id);
    }

    public String getName(){
        return this.actorDescriptor.getName();
    }

    public String getSpriteSheetPath(){
        return this.actorDescriptor.getSpriteSheetPath();
    }

    @Override
    public void dispose() {
        if (this.actorDescriptor != null){
            this.actorDescriptor.dispose();
        }

        this.actorDescriptor = null;
    }
}
