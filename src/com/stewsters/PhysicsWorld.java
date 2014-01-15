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
    Vector3f worldAabbMin = new Vector3f(-100, -100, -100);
    Vector3f worldAabbMax = new Vector3f(100, 100, 100);

    RigidBody groundRigidBody;
    public DynamicsWorld myWorld;

    public LinkedList<Ball> balls;
    public LinkedList<Box> boxes; //boxen?


    public PhysicsWorld() {
        balls = new LinkedList<Ball>();
        boxes = new LinkedList<Box>();

        myCc = new DefaultCollisionConfiguration();
        myBi = new AxisSweep3(worldAabbMin, worldAabbMax);
        myCd = new CollisionDispatcher(myCc);
        myCs = new SequentialImpulseConstraintSolver();

        myWorld = new DiscreteDynamicsWorld(myCd, myBi, myCs, myCc);
        myWorld.setGravity(new Vector3f(0, 0, gravityAcceleration));

        addGround();
        addGoal(40f);
        addGoal(-40f);
    }

    private void addGround() {
        //ADD STATIC GROUND
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 0, 1), 0);
        Transform myTransform = new Transform();
        myTransform.origin.set(new Vector3f(0, 0, 0)); //ground plane position
        myTransform.setRotation(new Quat4f(0, 0, 0, 1));
        DefaultMotionState groundMotionState = new DefaultMotionState(myTransform);
        RigidBodyConstructionInfo groundCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        groundCI.restitution = 0.6f; // this preserves bounce
        groundRigidBody = new RigidBody(groundCI);
        myWorld.addRigidBody(groundRigidBody);
    }

    private void addGoal(float y){
        addBox(new Vector3f(0,y,6f), new Vector3f(12f,0.5f,6f)); //top
        addBox(new Vector3f(0,y,22f), new Vector3f(12f,0.5f,2f)); //bottom

        addBox(new Vector3f(20,y,12f), new Vector3f(8f,0.5f,12f)); //top
        addBox(new Vector3f(-20,y,12f), new Vector3f(8f,0.5f,12f)); //bottom

    }
    private void addBox(Vector3f pos, Vector3f halfSize) {
        Box box = new Box(0, pos, halfSize);
        myWorld.addRigidBody(box.getRigidBody());
        boxes.add(box);
    }

    public void addBallAt(Vector3f location, Vector3f velocity) {
        Ball ball = new Ball(2f, 2.8f, location.x, location.y, location.z);

        myWorld.addRigidBody(ball.getRigidBody());
        ball.getRigidBody().setLinearVelocity(velocity);

        balls.add(ball);
    }
}
