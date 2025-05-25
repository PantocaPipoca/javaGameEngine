package Tests.GameEngine;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.*;
import Figures.Circle;

class GameEngineTest {

    private GameEngine engine;
    private GUI dummyGui;

    // ======= SETUP & TEARDOWN =======
    @BeforeEach
    void setUp() {
        dummyGui = new GUI() {
            @Override
            public InputEvent ie() { return new InputEvent(); }
            @Override
            public void renderGameObjects(List<IGameObject> gos) { }
        };
        engine = GameEngine.getInstance(dummyGui);
    }

    @AfterEach
    void tearDown() throws Exception {
        Field instanceField = GameEngine.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    // ======= DUMMY CLASSES =======
    static class DummyBehaviour implements IBehaviour {
        boolean initCalled = false, enabledCalled = false, disabledCalled = false, destroyCalled = false;
        List<IGameObject> collidedWith = new ArrayList<>();
        IGameObject owner;
        @Override public void onUpdate(double dT, InputEvent ie) { }
        @Override public void onInit() { initCalled = true; }
        @Override public void onEnabled() { enabledCalled = true; }
        @Override public void onDisabled() { disabledCalled = true; }
        @Override public void onDestroy() { destroyCalled = true; }
        @Override public void onCollision(List<IGameObject> gol) { collidedWith.addAll(gol); }
        @Override public IGameObject gameObject() { return owner; }
        @Override public void gameObject(IGameObject go) { this.owner = go; }
    }

    static class DummyGameObject implements IGameObject {
        private final String name;
        private final ITransform transform;
        private final ICollider collider;
        private final DummyBehaviour behaviour;
        private boolean flip = false;
        DummyGameObject(String name, ITransform transform, ICollider collider) {
            this.name = name; this.transform = transform; this.collider = collider;
            this.behaviour = new DummyBehaviour(); this.behaviour.gameObject(this);
        }
        @Override public String name() { return name; }
        @Override public ITransform transform() { return transform; }
        @Override public Shape shape() { return null; }
        @Override public void setShape(Shape shape) { }
        @Override public ICollider collider() { return collider; }
        @Override public void update() { }
        @Override public IBehaviour behaviour() { return behaviour; }
        @Override public boolean isFlipped() { return flip; }
        @Override public void setFlip(boolean flip) { this.flip = flip; }
    }

    // ======= TESTS =======

    // Tests GameEngine.getInstance() without GUI
    @Test
    void getInstance_withoutGui_throwsIllegalState() {
        Assertions.assertDoesNotThrow(() -> tearDown());
        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            GameEngine::getInstance
        );
        assertTrue(ex.getMessage().contains("GameEngine has not been initialized"));
    }

    // Tests GameEngine.getInstance(GUI) and getInstance()
    @Test
    void getInstance_withGui_thenWithoutGui_returnsSameInstance() {
        GameEngine first = GameEngine.getInstance(dummyGui);
        assertNotNull(first);
        GameEngine second = GameEngine.getInstance();
        assertSame(first, second);
    }

    // Tests GameEngine.addEnabled()
    @Test
    void addEnabled_callsOnInitAndOnEnabled_andRegistersInLists() {
        DummyGameObject go = makeDummyGO("go1", 0, 0, 1.0);
        engine.addEnabled(go);
        DummyBehaviour b = (DummyBehaviour) go.behaviour();
        assertTrue(b.initCalled, "onInit() was not called");
        assertTrue(b.enabledCalled, "onEnabled() was not called");
        assertTrue(engine.gameObjects().contains(go));
        assertTrue(engine.enabled().contains(go));
        assertFalse(engine.disabled().contains(go));
    }

    // Tests GameEngine.addDisabled()
    @Test
    void addDisabled_callsOnInitAndOnDisabled_andRegistersInLists() {
        DummyGameObject go = makeDummyGO("go2", 5, 5, 1.0);
        engine.addDisabled(go);
        DummyBehaviour b = (DummyBehaviour) go.behaviour();
        assertTrue(b.initCalled, "onInit() was not called");
        assertTrue(b.disabledCalled, "onDisabled() was not called");
        assertTrue(engine.gameObjects().contains(go));
        assertTrue(engine.disabled().contains(go));
        assertFalse(engine.enabled().contains(go));
    }

    // Tests GameEngine.enable()
    @Test
    void enable_movesFromDisabledToEnabled_andCallsOnEnabledAgain() {
        DummyGameObject go = makeDummyGO("go3", 1, 1, 1.0);
        engine.addDisabled(go);
        DummyBehaviour b = (DummyBehaviour) go.behaviour();
        b.enabledCalled = false;
        engine.enable(go);
        assertTrue(b.enabledCalled, "onEnabled() not called on enable(...)");
        assertTrue(engine.enabled().contains(go));
        assertFalse(engine.disabled().contains(go));
    }

    // Tests GameEngine.disable()
    @Test
    void disable_movesFromEnabledToDisabled_andCallsOnDisabledAgain() {
        DummyGameObject go = makeDummyGO("go4", 2, 2, 1.0);
        engine.addEnabled(go);
        DummyBehaviour b = (DummyBehaviour) go.behaviour();
        b.disabledCalled = false;
        engine.disable(go);
        assertTrue(b.disabledCalled, "onDisabled() not called on disable(...)");
        assertTrue(engine.disabled().contains(go));
        assertFalse(engine.enabled().contains(go));
    }

    // Tests GameEngine.destroy()
    @Test
    void destroy_removesFromAllLists_andCallsOnDestroy() {
        DummyGameObject go = makeDummyGO("go5", 3, 3, 1.0);
        engine.addEnabled(go);
        DummyBehaviour b = (DummyBehaviour) go.behaviour();
        engine.destroy(go);
        assertTrue(b.destroyCalled, "onDestroy() was not called on destroy(...)");
        assertFalse(engine.gameObjects().contains(go), "gameObjects still contains after destroy");
        assertFalse(engine.enabled().contains(go), "enabled still contains after destroy");
        assertFalse(engine.disabled().contains(go), "disabled still contains after destroy");
    }

    // Tests GameEngine.checkCollisions() for collision
    @Test
    void checkCollisions_triggersOnCollisionForBothSides() throws Exception {
        DummyGameObject goA = makeDummyGO("A", 0, 0, 2.0);
        DummyGameObject goB = makeDummyGO("B", 1, 0, 2.0);
        engine.addEnabled(goA);
        engine.addEnabled(goB);
        invokeCheckCollisions();
        DummyBehaviour bA = (DummyBehaviour) goA.behaviour();
        DummyBehaviour bB = (DummyBehaviour) goB.behaviour();
        assertEquals(1, bA.collidedWith.size(), "A should have exactly one collidedWith");
        assertSame(goB, bA.collidedWith.get(0), "A.did not record B properly");
        assertEquals(1, bB.collidedWith.size(), "B should have exactly one collidedWith");
        assertSame(goA, bB.collidedWith.get(0), "B.did not record A properly");
    }

    // Tests GameEngine.checkCollisions() for no collision
    @Test
    void checkCollisions_noCollisionWhenFarApart() throws Exception {
        DummyGameObject goA = makeDummyGO("A", 0, 0, 1.0);
        DummyGameObject goB = makeDummyGO("B", 10, 0, 1.0);
        engine.addEnabled(goA);
        engine.addEnabled(goB);
        invokeCheckCollisions();
        DummyBehaviour bA = (DummyBehaviour) goA.behaviour();
        DummyBehaviour bB = (DummyBehaviour) goB.behaviour();
        assertEquals(0, bA.collidedWith.size(), "A should not collide with B");
        assertEquals(0, bB.collidedWith.size(), "B should not collide with A");
    }

    // Tests GameEngine.checkCollisions() for selective collision
    @Test
    void checkCollisions_multipleObjectsOnlySomeCollide() throws Exception {
        DummyGameObject goA = makeDummyGO("A", 0, 0, 2.0);
        DummyGameObject goB = makeDummyGO("B", 1, 0, 2.0);
        DummyGameObject goC = makeDummyGO("C", 10, 10, 1.0);
        engine.addEnabled(goA);
        engine.addEnabled(goB);
        engine.addEnabled(goC);
        invokeCheckCollisions();
        DummyBehaviour bA = (DummyBehaviour) goA.behaviour();
        DummyBehaviour bB = (DummyBehaviour) goB.behaviour();
        DummyBehaviour bC = (DummyBehaviour) goC.behaviour();
        assertEquals(1, bA.collidedWith.size(), "A should collide only with B");
        assertSame(goB, bA.collidedWith.get(0), "A did not record B properly");
        assertEquals(1, bB.collidedWith.size(), "B should collide only with A");
        assertSame(goA, bB.collidedWith.get(0), "B did not record A properly");
        assertEquals(0, bC.collidedWith.size(), "C should not collide with anyone");
    }

    // Tests GameEngine.enable() does nothing if already enabled
    @Test
    void enable_onlyWorksIfDisabled() {
        DummyGameObject go = makeDummyGO("goEnable", 0, 0, 1.0);
        engine.addEnabled(go);
        int enabledCount = engine.enabled().size();
        engine.enable(go); // Already enabled, should do nothing
        assertEquals(enabledCount, engine.enabled().size());
    }

    // Tests GameEngine.disable() does nothing if already disabled
    @Test
    void disable_onlyWorksIfEnabled() {
        DummyGameObject go = makeDummyGO("goDisable", 0, 0, 1.0);
        engine.addDisabled(go);
        int disabledCount = engine.disabled().size();
        engine.disable(go); // Already disabled, should do nothing
        assertEquals(disabledCount, engine.disabled().size());
    }

    // Tests GameEngine.updateObjectLayer()
    @Test
    void updateObjectLayer_movesObjectBetweenLayers() throws Exception {
        DummyGameObject go = makeDummyGO("goLayer", 0, 0, 1.0);
        engine.addEnabled(go);
        int oldLayer = go.transform().layer();
        int newLayer = oldLayer + 1;
        invokeUpdateObjectLayer(go, oldLayer, newLayer);
        assertTrue(isInLayerGroup(go, newLayer));
    }

    // Tests GameEngine.getLayerGroup()
    @Test
    void getLayerGroup_returnsCorrectGroupOrNull() throws Exception {
        DummyGameObject go = makeDummyGO("goLayerGroup", 0, 0, 1.0);
        engine.addEnabled(go);
        int layer = go.transform().layer();
        Object group = invokeGetLayerGroup(layer);
        assertNotNull(group);
        Object nullGroup = invokeGetLayerGroup(layer + 9999);
        assertNull(nullGroup);
    }

    // Tests GameEngine.getOrCreateLayerGroup()
    @Test
    void getOrCreateLayerGroup_returnsExistingOrCreatesNew() throws Exception {
        DummyGameObject go = makeDummyGO("goOrCreateLayer", 0, 0, 1.0);
        engine.addEnabled(go);
        int layer = go.transform().layer();
        Object group1 = invokeGetOrCreateLayerGroup(layer);
        assertNotNull(group1);
        int newLayer = layer + 1234;
        Object group2 = invokeGetOrCreateLayerGroup(newLayer);
        assertNotNull(group2);
        assertNotEquals(group1, group2);
    }

    // Tests GameEngine.checkCollisionsInGroup()
    @Test
    void checkCollisionsInGroup_detectsCollisions() throws Exception {
        DummyGameObject goA = makeDummyGO("A", 0, 0, 2.0);
        DummyGameObject goB = makeDummyGO("B", 1, 0, 2.0);
        List<IGameObject> group = new ArrayList<>();
        group.add(goA);
        group.add(goB);
        invokeCheckCollisionsInGroup(group);
        DummyBehaviour bA = (DummyBehaviour) goA.behaviour();
        DummyBehaviour bB = (DummyBehaviour) goB.behaviour();
        assertEquals(1, bA.collidedWith.size());
        assertEquals(1, bB.collidedWith.size());
        assertSame(goB, bA.collidedWith.get(0));
        assertSame(goA, bB.collidedWith.get(0));
    }

    // ======= TEST HELPERS =======
    // ----------------------------

    private DummyGameObject makeDummyGO(String name, double x, double y, double radius) {
        ColliderCircle c = makeCircleCollider(x, y, radius);
        Transform t = new Transform(new Figures.Point(x, y), 0, 0.0, 1.0);
        return new DummyGameObject(name, t, c);
    }

    private ColliderCircle makeCircleCollider(double centerX, double centerY, double radius) {
        Circle circle = new Circle(centerX + " " + centerY + " " + radius);
        Transform t = new Transform(new Figures.Point(centerX, centerY), 0, 0.0, 1.0);
        return new ColliderCircle(circle, t);
    }

    private void invokeCheckCollisions() throws Exception {
        java.lang.reflect.Method m = GameEngine.class.getDeclaredMethod("checkCollisions");
        m.setAccessible(true);
        m.invoke(engine);
    }

    private void invokeUpdateObjectLayer(IGameObject go, int oldLayer, int newLayer) throws Exception {
        java.lang.reflect.Method m = engine.getClass().getDeclaredMethod("updateObjectLayer", IGameObject.class, int.class, int.class);
        m.setAccessible(true);
        m.invoke(engine, go, oldLayer, newLayer);
    }

    private Object invokeGetLayerGroup(int layer) throws Exception {
        java.lang.reflect.Method m = engine.getClass().getDeclaredMethod("getLayerGroup", int.class);
        m.setAccessible(true);
        return m.invoke(engine, layer);
    }

    private Object invokeGetOrCreateLayerGroup(int layer) throws Exception {
        java.lang.reflect.Method m = engine.getClass().getDeclaredMethod("getOrCreateLayerGroup", int.class);
        m.setAccessible(true);
        return m.invoke(engine, layer);
    }

    private void invokeCheckCollisionsInGroup(List<IGameObject> group) throws Exception {
        java.lang.reflect.Method m = engine.getClass().getDeclaredMethod("checkCollisionsInGroup", List.class);
        m.setAccessible(true);
        m.invoke(engine, group);
    }

    private boolean isInLayerGroup(IGameObject go, int layer) throws Exception {
        java.lang.reflect.Field f = engine.getClass().getDeclaredField("layerGroups");
        f.setAccessible(true);
        List<?> groups = (List<?>) f.get(engine);
        for (Object group : groups) {
            int groupLayer = (int) group.getClass().getDeclaredMethod("layer").invoke(group);
            List<?> objs = (List<?>) group.getClass().getDeclaredMethod("objects").invoke(group);
            if (groupLayer == layer && objs.contains(go)) return true;
        }
        return false;
    }
}