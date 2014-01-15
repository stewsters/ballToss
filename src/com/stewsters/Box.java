package com.stewsters;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import processing.core.PApplet;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.Vector;

public class Box implements  PhysicsRenderable{

    private Vector3f size;
    private RigidBody rigidBody;

    public Box(float mass, Vector3f pos, Vector3f halfBoxExtents) {

       size = halfBoxExtents;


        CollisionShape fallShape = new BoxShape(halfBoxExtents);
        Transform myTransform = new Transform();
        myTransform.origin.set(pos);
        myTransform.setRotation(new Quat4f(0, 0, 0, 1));

        DefaultMotionState fallMotionState = new DefaultMotionState(myTransform);

        Vector3f myFallInertia = new Vector3f(1, 1, 1);
        fallShape.calculateLocalInertia(mass, myFallInertia);
        RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass, fallMotionState, fallShape, myFallInertia);

        rigidBody = new RigidBody(fallRigidBodyCI);


//        rigidBody.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
    }

    public void render(PApplet context) {
        context.box(size.x * 2, size.y* 2, size.z* 2);
    }

    @Override
    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
