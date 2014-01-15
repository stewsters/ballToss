package com.stewsters;


import com.bulletphysics.dynamics.RigidBody;
import processing.core.PApplet;

public interface PhysicsRenderable {
    public void render(PApplet pApplet);
    public RigidBody getRigidBody();
}
