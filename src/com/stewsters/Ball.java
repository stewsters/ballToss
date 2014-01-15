package com.stewsters;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import processing.core.PApplet;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class Ball {
    public float radius;
    public float mass;
    public RigidBody rigidBody;

    public Ball(float radius, float mass, float x, float y, float z) {
        this.radius = radius;


        CollisionShape fallShape = new SphereShape(radius);
        Transform myTransform = new Transform();
        myTransform.origin.set(new Vector3f(x, y, z));
        myTransform.setRotation(new Quat4f(0, 0, 0, 1));

        DefaultMotionState fallMotionState = new DefaultMotionState(myTransform);

        Vector3f myFallInertia = new Vector3f(1, 1, 1);
        fallShape.calculateLocalInertia(mass, myFallInertia);
        RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass, fallMotionState, fallShape, myFallInertia);

        rigidBody = new RigidBody(fallRigidBodyCI);


        rigidBody.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
    }

    public void render(PApplet context) {
        context.sphere(radius);
    }
}
