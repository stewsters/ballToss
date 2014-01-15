package com.stewsters;

import com.bulletphysics.linearmath.Transform;
import peasy.PeasyCam;
import processing.core.PApplet;

public class BallTestApplet extends PApplet {

    PhysicsWorld physicsWorld;
    PeasyCam cam;

    public void setup() {
        size(800, 600, P3D);
        cam = new PeasyCam(this, 180);
        physicsWorld = new PhysicsWorld();
//        cam.rotateY(PI / 5);
//        cam.rotateX(PI / 5);


    }


    public void draw() {
        background(255);
        setupLights();
        physicsWorld.myWorld.stepSimulation((1.0f / 60.0f));

        for (Ball fallRigidBody : physicsWorld.balls) {
//            if (hover)
//                fallRigidBody.stabilize();

            Transform myTransform = new Transform();
            myTransform = fallRigidBody.rigidBody.getMotionState().getWorldTransform(myTransform);

            pushMatrix();

            translate(myTransform.origin.x, myTransform.origin.y, myTransform.origin.z);

            applyMatrix(myTransform.basis.m00, myTransform.basis.m01, myTransform.basis.m02, 0,
                    myTransform.basis.m10, myTransform.basis.m11, myTransform.basis.m12, 0,
                    myTransform.basis.m20, myTransform.basis.m21, myTransform.basis.m22, 0,
                    0, 0, 0, 1);

            fallRigidBody.render(this);

            popMatrix();
        }

        pushMatrix();

        rectMode(CENTER);
//        noStroke();
        fill(255);
        rect(0, 0, 150, 150);
        popMatrix();
    }


    void setupLights() {
        directionalLight(250, 250, 250, 0, 2, -10);
    }

    public void keyPressed() {
        if (key == 'a') {


            physicsWorld.addBallAt(0f, 0f, 150f);
//            addAircraft(0, -100, 0, random(-PI, PI));
        }


    }
}
