package com.stewsters;

import com.bulletphysics.linearmath.Transform;
import peasy.PeasyCam;
import processing.core.PApplet;

import javax.vecmath.Vector3f;

public class BallTestApplet extends PApplet {

    PhysicsWorld physicsWorld;
    PeasyCam cam;

    public void setup() {
        size(800, 600, P3D);
        cam = new PeasyCam(this, 180);
        physicsWorld = new PhysicsWorld();

        noStroke();
    }


    public void draw() {
        background(255);
        setupLights();
        physicsWorld.myWorld.stepSimulation((1.0f / 60.0f));

        fill(200,100,100);
        for (Ball ball : physicsWorld.balls) {
            render(ball);
        }
        for (Box box : physicsWorld.boxes) {
            render(box);
        }

        pushMatrix();

        rectMode(CENTER);

        fill(255);
        rect(0, 0, 150, 150);
        popMatrix();
    }

    public void render(PhysicsRenderable renderable){

        Transform myTransform = new Transform();
        myTransform = renderable.getRigidBody().getMotionState().getWorldTransform(myTransform);

        pushMatrix();

        translate(myTransform.origin.x, myTransform.origin.y, myTransform.origin.z);

        applyMatrix(myTransform.basis.m00, myTransform.basis.m01, myTransform.basis.m02, 0,
                myTransform.basis.m10, myTransform.basis.m11, myTransform.basis.m12, 0,
                myTransform.basis.m20, myTransform.basis.m21, myTransform.basis.m22, 0,
                0, 0, 0, 1);

        renderable.render(this);

        popMatrix();
    }


    void setupLights() {
        directionalLight(250, 250, 250, 0, 2, -10);
    }

    public void keyPressed() {
        if (key == 'a') {
            //Equations go here

            physicsWorld.addBallAt(new Vector3f(0f, 0f, 6f), new Vector3f(1,20,14));
        }


    }
}
