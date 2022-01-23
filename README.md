# BFAPI

## Recipes

BFAPI has support for complex recipes making customising a server easier

### Recipe types

These are used to identify what type of recipe you want to make

|Type|Identifier|
|Shapeless Crafting|minecraft:shapeless_crafting|

### Recipe results

Recipes can have custom 'results' that are given to the player after they craft an item, for example xp can be given



## Events

Events have been changed to make them more modular, now all events must be registered and have an identifier

### Creating an event, an example

> Just want a list of events? If so [click me!](#out-of-the-box-events)

Creating an event is super simple, you don't even need to make it statically accessible!

To create a simple event we can do as follows

```java
// First we need an identifier the mod will be under,
// it should always start with your modid if its generated by your event
// if its called from a mixin and is correspondent to a minecraft event you can use the minecraft identifier
// The identifiers path should be a simple name for the event, here we are going to make an event to handle
// and send internal messages: so we aptly name it message
Identifier identifier = new Identifier("MODID", "message");
// The class/type inside the Input<> is what will actually be sent over an event
// here we use a `String` as that is what we will send our message as
// The Event constructor takes in an identifier and a boolean
// the boolean dictates if the event should be automatically registered
Event<Input<String>> messageEvent = new Event<Input<String>>(identifier, true);
```

And that's it! Your message event has been created and can now be used by other developers

### Handling an event, an example

> This example will talk about handling the event created in [the prior example](#creating-an-event-an-example), so its recommended that you read it for context

#### Direct handling

The simplest way to handle an event is to directly add a handler to it

This can be done by either getting a [static instance](#getting-an-event-from-an-access-point) or [grabbing it from the registry](#getting-an-event-from-the-registry)

So when you have an instance of the event how do you add a handler?

In this example I will be using [the registry](#getting-an-event-from-the-registry) to get [the event](#creating-an-event-an-example)

```java
// Using the registry to get the event
Event<Input<String>> messageEvent = (Event<Input<String>>) BFAPIRegistry.EVENTS.get(new Identifier("MODID", "message"));
messageEvent.addHandler((input)->{
	// Here you can handle the event, any arguments will be passed through an event
});
```

##### Getting an event from the registry

All events should be registered to the `BFAPIRegistry#EVENTS` registry, this means we can get an event by simply querying it with the events identifier

```java
// It is vital that we use the same identifier as before
Identifier identifier = new Identifier("MODID", "message");
// When we pass the identifier to the registry we receive any event with the corresponding identifier
// however, since we get a generic event back we need to cast to the appropriate event
// Since we have to cast we need to use a unchecked suppression to avoid warnings
@SuppressWarnings("unchecked")
Event<Input<String>> messageEvent = (Event<Input<String>>) BFAPIRegistry.EVENTS.get(identifier);
```

##### Getting an event from an access point

If an event has a public access point you can simply grab it from there

```java
// Just generic direct access
Event<Input<?>> example = ExampleEvent.INSTANCE;
```

#### Reference handling

If you don't want to [get an event from the registry](#getting-an-event-from-the-registry) and don't have [an access point](#getting-an-event-from-an-access-point) you can use an EventListener to handle the event

Here I will still be using [the event created in a prior example](#creating-an-event-an-example)

```java
public class ExampleListener implements EventListener {
	
	public ExampleListener() {
		// Here we auto register all events
		// Doing it in the constructor removes complexity
		register();
	}
	
	// We use an annotation to say what event to bind to
	// The event must be on the BFAPIRegistry#EVENTS registry
	@EventHandler(eventIdentifier = "MODID:message")
	public void handleMessageEvent(Input<String> event) {
		// You can handle the event like normal here
	}
	
}
```

#### Reference handling with decomposition

If you want a simpler approach to dealing with inputs then you can have an events input decomposed into the inner arguments

Here I will still be using [the event created in a prior example](#creating-an-event-an-example)

```java
public class ExampleListener implements EventListener {
	
	public ExampleListener() {
		// Here we auto register all events
		// Doing it in the constructor removes complexity
		register();
	}
	
	// We use an annotation to say what event to bind to
	// The event must be on the BFAPIRegistry#EVENTS registry
	@EventHandler(eventIdentifier = "MODID:message", decomposeArguments = true)
	public void handleMessageEvent(String message) {
		// You can handle the event like normal here
		// However you will notice that we now have decomposed/direct access to the inputs inner values
		// this can make your code look cleaner when handling events
	}
	
}
```

##### Controlling event binding

By default an event is not required, if it isn't bound to then it nothing will happen other than a simple log to console

You can modify this behaviour in the `EventHandler` annotation as it has a few fields

|Field|DataType|Description|Default|
|-----|--------|-----------|-------|
|eventIdentifier|String|The identifer of the event in string form|N/A|
|required|boolean|If the event is required, if it is an exception will be thrown if it fails to bind|false|
|logOnFailedBinding|Boolean|If a message should be logged when the event fails to bind|true|

### 'Out of the box' events

> THIS IS OUT OF DATE, I have added alot of events since then and plan to update this documentation soon, however, I might move to githubs wiki feature for this

#### Minecraft events

> Minecraft events use the generic minecraft:*id* identifier

|Event|Description|Identifer|Gives|
|-----|-----------|---------|-----|
|CommandRegistrationEvent|Called when minecraft wishes to register all commands, you should use this to create commands|minecraft:command_registration|DualInput\<CommandDispatcher\<ServerCommandSource\>, CommandManager.RegistrationEnvironment\>|

#### BFAPI events

> BFAPI events use the generic bfapi:*id* identifier

|Event|Description|Identifer|Gives|
|-----|-----------|---------|-----|
|LoadedEvent|Called when BFAPI is finished loading|bfapi:loaded|Input\<Loader\>|