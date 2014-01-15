package com.stewsters;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.LinkedList;

public class PhysicsWorld {

    public final static float gravityAcceleration = -9.81f;


    CollisionDispatcher myCd;
    BroadphaseInterface myBi;
    ConstraintSolver myCs;
    CollisionConfiguration myCc;
    Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
    Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);

    RigidBody groundRigidBody;
    public DynamicsWorld myWorld;

    public LinkedList<Ball> balls;


    public PhysicsWorld() {
        balls = new LinkedList<Ball>();

        myCc = new DefaultCollisionConfiguration();
        myBi = new AxisSweep3(worldAabbMin, worldAabbMax);
        myCd = new CollisionDispatcher(myCc);
        myCs = new SequentialImpulseConstraintSolver();

        myWorld = new DiscreteDynamicsWorld(myCd, myBi, myCs, myCc);
        myWorld.setGravity(new Vector3f(0, 0, gravityAcceleration));

        addGround();
    }

    private void addGround() {
        //ADD STATIC GROUND
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 0, 1), 0);
        Transform myTransform = new Transform();
        myTransform.origin.set(new Vector3f(0, 0, 0)); //ground plane position
        myTransform.setRotation(new Quat4f(0, 0, 0, 1));
        DefaultMotionState groundMotionState = new DefaultMotionState(myTransform);
        RigidBodyConstructionInfo groundCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        groundRigidBody = new RigidBody(groundCI);
        myWorld.addRigidBody(groundRigidBody);
    }


    public void addBallAt(float x, float y, float z) {
        Ball ball = new Ball(2f, 2.8f, x, y, z);

        myWorld.addRigidBody(ball.rigidBody);
        balls.add(ball);
    }
}
