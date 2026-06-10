# Enter The Ualg

A 2D top-down shooter built in Java from scratch as a university project. Theme and architecture were self-directed.

## Implementation
- Custom game engine: layer-based collision detection, transform system
- Component pattern for game objects (behaviour, collider, transform, shape all separate)
- State machine pattern for entity AI (patrol, chase, attack, knockback, stunned, dead)
- Observer pattern for event-driven UI updates (health, ammo, score)
- Factory pattern for room and weapon construction from JSON config
- Singleton pattern for engine, camera, and UI instances
- Custom JSON parser built without external libraries
- Geometry module with polygon/circle intersection, ray casting for point-in-polygon

## Stack
Java, Swing, JUnit

## Testing
Unit tests covering geometry primitives, colliders, transforms, game objects, and layer groups

## Documentation
Full Javadoc generated for all packages and UML diagrams covering the full class hierarchy
